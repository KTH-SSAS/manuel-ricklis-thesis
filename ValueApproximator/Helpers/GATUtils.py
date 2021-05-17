import numpy as np
import torch

from ValueApproximator.Helpers.Exporter import Exporter
from ValueApproximator.Helpers.GraphBuilder import value_iteration
from ValueApproximator.Graph.AttackGraph import AttackGraph


def load_example_graph():
    device = "cpu"
    graph = AttackGraph()
    node_features, adjacency_list = Exporter().parse_features(graph, 5)
    node_labels, _ = value_iteration(graph, graph.rewards)

    # i = 0
    # for key in graph.graph.keys():
    #     if node_labels[i] > 0:
    #         print(f'{graph.key_indices[key]}   {key} - {node_labels[i]}')
    #     i = i + 1

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
