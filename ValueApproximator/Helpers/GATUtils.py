import numpy as np
import torch.nn as nn
import torch

from ValueApproximator.Helpers.Exporter import get_vocabularies
from ValueApproximator.Helpers.GraphBuilder import value_iteration
from ValueApproximator.Graph.AttackGraph import AttackGraph

STEP_NAME = 0
ASSET_TYPE = 1
ASSET_NAME = 2
REWARD = 3
RANK = 4


def load_example_graph(number_of_features, embedding_vector_lengths):
    device = "cpu"
    graph = AttackGraph()
    node_features, adjacency_list = parse_features(graph, number_of_features, embedding_vector_lengths)
    node_labels, _ = value_iteration(graph, graph.rewards)

    topology = build_edge_index(adjacency_list, len(node_labels), False)

    topology = torch.tensor(topology, dtype=torch.long, device=device)
    node_labels = torch.tensor(node_labels, dtype=torch.long, device=device)
    node_features = torch.tensor(node_features, device=device)

    return node_features, node_labels, topology


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


def parse_features(graph: AttackGraph, number_of_features, embedding_vector_lengths: list):
    vocabularies = get_vocabularies()

    # features: step name, asset type, asset name, reward, neighbourhood rank
    N = len(graph.graph)
    M = np.ones((N, sum(embedding_vector_lengths) + (number_of_features - len(embedding_vector_lengths)))) * (-999)

    embeddings = [nn.Embedding(len(vocabularies["step_names"]), embedding_vector_lengths[STEP_NAME]),
                  nn.Embedding(len(vocabularies["asset_types"]), embedding_vector_lengths[ASSET_TYPE]),
                  nn.Embedding(len(vocabularies["asset_names"]), embedding_vector_lengths[ASSET_NAME])]

    adjacency_matrix = {}
    for step, children in graph.graph.items():
        asset_type, asset_name, step_name = step.split(".")

        # build M without using embeddings
        # M[graph.key_indices[step], STEP_NAME] = step_names[str(asset_type + "." + step_name)]
        # M[graph.key_indices[step], ASSET_TYPE] = asset_types[asset_type]
        # M[graph.key_indices[step], ASSET_NAME] = asset_names[
        #     "".join(i for i in asset_name if not i.isdigit())]
        # M[graph.key_indices[step], REWARD] = graph.get_reward(step)
        # M[graph.key_indices[step], RANK] = len(graph.graph[step])

        # build M using embeddings
        M[graph.key_indices[step], STEP_NAME:STEP_NAME + embedding_vector_lengths[STEP_NAME]] = \
            embeddings[STEP_NAME](torch.tensor(vocabularies["step_names"][str(asset_type + "." + step_name)],
                                               dtype=torch.long)).detach().numpy()

        M[graph.key_indices[step], ASSET_TYPE:ASSET_TYPE + embedding_vector_lengths[ASSET_TYPE]] = \
            embeddings[ASSET_TYPE](torch.tensor(vocabularies["asset_types"][asset_type],
                                                dtype=torch.long)).detach().numpy()

        M[graph.key_indices[step], ASSET_NAME:ASSET_NAME + embedding_vector_lengths[ASSET_NAME]] = \
            embeddings[ASSET_NAME](torch.tensor(vocabularies["asset_names"]
                                                ["".join(i for i in asset_name if not i.isdigit())],
                                                dtype=torch.long)).detach().numpy()

        M[graph.key_indices[step], REWARD] = graph.get_reward(step)
        M[graph.key_indices[step], RANK] = len(graph.graph[step])

        # build the adjacency matrix
        children_indx = []
        for child in children:
            children_indx.append(graph.key_indices[child])
        adjacency_matrix[graph.key_indices[step]] = children_indx
    return M, adjacency_matrix
