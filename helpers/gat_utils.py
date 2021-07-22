import json
import re

import numpy as np
import torch.nn as nn
import torch
import math

from model_generator import model_generator
from value_approximator.graph import attack_graph
from value_approximator.graph.attack_graph import AttackGraph
from helpers.constants import *

from helpers.constants import BINARIES_PATH


def load_example_graph(number_of_features, embedding_vector_lengths):
    device = "cpu"
    with open("AttackGraphs/default_graph_0_1.json", "r") as f:
        dictionary = json.load(f)
    graph = AttackGraph(
        graph_expanded=dictionary["graph_expanded"],
        key_indices=dictionary["key_indices"],
        rewards=np.asarray(dictionary["rewards"])
    )

    node_features, adjacency_list = parse_features(graph, number_of_features)
    node_labels, _ = graph.value_iteration()

    topology = build_edge_index(adjacency_list, len(node_labels), False)

    topology = torch.tensor(topology, dtype=torch.long, device=device)
    node_labels = torch.tensor(node_labels, dtype=torch.long, device=device)
    node_features = node_features.clone().detach()  # torch.tensor(node_features, device=device)

    return node_features, node_labels, topology


def parse_features(graph: AttackGraph, number_of_features):
    # features: (step name, asset type, asset name), reward, neighbourhood rank
    N = len(graph.graph_expanded)

    # the node feature matrix
    M = torch.ones((N, len(attack_graph.vocabulary) + (number_of_features - 1)), dtype=torch.double)

    adjacency_matrix = {}
    for step, children in graph.graph_expanded.items():
        # build M using embeddings
        M[graph.key_indices[step], STEP:STEP + len(attack_graph.vocabulary)] = get_feature_vector_from_expanded_node_name(step)
        step_rewards = [reward for reward in graph.rewards[:, graph.key_indices[step]] if reward != -999]
        M[graph.key_indices[step], REWARD] = np.mean(step_rewards) if len(step_rewards) > 0 else -999
        M[graph.key_indices[step], RANK] = len(graph.graph_expanded[step])

        # build the adjacency matrix
        children_indx = []
        for child in children:
            children_indx.append(graph.key_indices[child])
        adjacency_matrix[graph.key_indices[step]] = children_indx

    return M, adjacency_matrix


def get_feature_vector_from_expanded_node_name(name_expanded: str) -> torch.tensor:
    vector = torch.zeros(len(attack_graph.vocabulary))
    for _name in name_expanded.split("|"):
        name = ''.join([i for i in _name if not i.isdigit()])
        vector[attack_graph.vocabulary[name]] += 1
    return vector


def build_edge_index(adjacency_list_dict, num_of_nodes, add_self_edges=False):
    source_nodes_ids, target_nodes_ids = [], []
    seen_edges = set()

    for src_node, neighboring_nodes in adjacency_list_dict.items():
        for trg_node in neighboring_nodes:
            # if this edge hasn't been seen so far we add it to the edge index (coalescing - removing duplicates)
            if (src_node, trg_node) not in seen_edges:
                source_nodes_ids.append(src_node)
                target_nodes_ids.append(trg_node)

                seen_edges.add((src_node, trg_node))

    if add_self_edges:
        source_nodes_ids.extend(np.arange(num_of_nodes))
        target_nodes_ids.extend(np.arange(num_of_nodes))

    # shape = (2, E), where E is the number of edges in the graph
    edge_index = np.row_stack((source_nodes_ids, target_nodes_ids))

    return edge_index


def get_training_state(training_config, model):
    training_state = {
        # "commit_hash": git.Repo(search_parent_directories=True).head.object.hexsha,

        # Training details
        "dataset_name": training_config['dataset_name'],
        "num_of_epochs": training_config['num_of_epochs'],
        "test_perf": training_config['test_perf'],

        # Model structure
        "num_of_layers": training_config['num_of_layers'],
        "num_heads_per_layer": training_config['num_heads_per_layer'],
        "num_features_per_layer": training_config['num_features_per_layer'],
        "add_skip_connection": training_config['add_skip_connection'],
        "bias": training_config['bias'],
        "dropout": training_config['dropout'],

        # Model state
        "state_dict": model.state_dict()
    }

    return training_state


def get_available_binary_name(dataset_name='attack_graphs'):
    prefix = f'gat_{dataset_name}'

    def valid_binary_name(binary_name):
        # First time you see raw f-string? Don't worry the only trick is to double the brackets.
        pattern = re.compile(rf'{prefix}_[0-9]{{6}}\.pth')
        return re.fullmatch(pattern, binary_name) is not None

    # Just list the existing binaries so that we don't overwrite them but write to a new one
    valid_binary_names = list(filter(valid_binary_name, os.listdir(BINARIES_PATH)))
    if len(valid_binary_names) > 0:
        last_binary_name = sorted(valid_binary_names)[-1]
        new_suffix = int(last_binary_name.split('.')[0][-6:]) + 1  # increment by 1
        return f'{prefix}_{str(new_suffix).zfill(6)}.pth'
    else:
        return f'{prefix}_000000.pth'


def print_model_metadata(training_state):
    header = f'\n{"*" * 5} Model training metadata: {"*" * 5}'
    print(header)

    for key, value in training_state.items():
        if key != 'state_dict':  # don't print state_dict it's a bunch of numbers...
            print(f'{key}: {value}')
    print(f'{"*" * len(header)}\n')
