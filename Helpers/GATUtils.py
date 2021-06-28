import numpy as np
import torch.nn as nn
import torch

from Helpers.Importer import get_vocabularies
from ValueApproximator.Graph.AttackGraph import AttackGraph
from Helpers.Constants import *


def load_example_graph(number_of_features, embedding_vector_lengths):
    device = "cpu"
    graph = AttackGraph()

    node_features, adjacency_list = parse_features(graph, number_of_features, embedding_vector_lengths)
    node_labels, _ = graph.value_iteration()

    topology = build_edge_index(adjacency_list, len(node_labels), False)

    topology = torch.tensor(topology, dtype=torch.long, device=device)
    node_labels = torch.tensor(node_labels, dtype=torch.long, device=device)
    node_features = node_features.clone().detach()  # torch.tensor(node_features, device=device)

    return node_features, node_labels, topology


def parse_features(graph: AttackGraph, number_of_features, embedding_vector_length: int):
    # features: (step name, asset type, asset name), reward, neighbourhood rank
    N = len(graph.graph_expanded)

    # the node feature matrix
    M = torch.ones((N, embedding_vector_length + (number_of_features - 1)),
                   dtype=torch.double) * (-999)

    embeddings = nn.Embedding(len(graph.vocabulary), embedding_vector_length)

    adjacency_matrix = {}
    for step, children in graph.graph_expanded.items():
        # build M using embeddings
        M[graph.key_indices[step], STEP:STEP + embedding_vector_length] = \
            embeddings(torch.tensor(graph.vocabulary[step], dtype=torch.long))

        step_rewards = [reward for reward in graph.rewards[:, graph.key_indices[step]] if reward != -999]
        M[graph.key_indices[step], REWARD] = np.mean(step_rewards) if len(step_rewards) > 0 else -999
        M[graph.key_indices[step], RANK] = len(graph.graph_expanded[step])

        # build the adjacency matrix
        children_indx = []
        for child in children:
            children_indx.append(graph.key_indices[child])
        adjacency_matrix[graph.key_indices[step]] = children_indx
    return M, adjacency_matrix


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
