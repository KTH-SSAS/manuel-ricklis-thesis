import numpy as np
import torch
import json
from matplotlib import colors
from pyvis.network import Network
from torch.utils.data import DataLoader, Dataset

from Generator import modelGenerator
from Generator.modelGenerator import ModelGenerator
from ValueApproximator.Graph.AttackGraph import AttackGraph as AttackGraph


def create_and_export_attack_graphs_for_learning(prefix: str, amount: int):
    generator = ModelGenerator()
    for i in range(amount):
        model = generator.generate_model_based_on_random_parameters(5, 2, 8, 4, 5, 1, 3, 1, 4, 2)
        attack_graph = AttackGraph(model)
        with open("AttackGraphs/" + prefix + "_" + str(i) + ".json", "w+") as f:
            json.dump({
                "graph_expanded": attack_graph.graph_expanded,
                "key_indices": attack_graph.key_indices,
                "rewards": attack_graph.rewards.tolist()
            }, f)


def load_graph_data(batch_size=2):
    # Collect train/val/test graphs here
    edge_index_list = []
    node_features_list = []
    node_labels_list = []

    # Dynamically determine how many graphs we have per split (avoid using constants when possible)
    num_graphs_per_split_cumulative = [0]

    # Small optimization "trick" since we only need test in the playground.py
    splits = ['train', 'valid', 'test']

    for split in splits:
        # shape = (NS, nr_feat) - where NS is the number of (N)odes in the training/val/test (S)plit
        node_features = None
        # shape = (NS, 1)
        node_labels = None
        collection_of_graphs = None  # nx.DiGraph(json_graph.node_link_graph(nodes_links_dict))
        # For each node in the above collection, ids specify to which graph the node belongs to
        graph_ids = None  # np.load(os.path.join(PPI_PATH, F'{split}_graph_id.npy'))
        num_graphs_per_split_cumulative.append(num_graphs_per_split_cumulative[-1] + len(np.unique(graph_ids)))

        # Split the collection of graphs into separate graphs
        for graph_id in range(np.min(graph_ids), np.max(graph_ids) + 1):
            mask = graph_ids == graph_id  # find the nodes which belong to the current graph (identified via id)
            graph_node_ids = np.asarray(mask).nonzero()[0]
            graph = collection_of_graphs.subgraph(graph_node_ids)  # returns the induced subgraph over these nodes
            print(f'Loading {split} graph {graph_id} to CPU. '
                  f'It has {graph.number_of_nodes()} nodes and {graph.number_of_edges()} edges.')

            # shape = (2, E) - where E is the number of edges in the graph
            edge_index = torch.tensor(list(graph.edges), dtype=torch.long).transpose(0, 1).contiguous()
            edge_index = edge_index - edge_index.min()  # bring the edges to [0, num_of_nodes] range
            edge_index_list.append(edge_index)
            # shape = (N, nr_feat) - where N is the number of nodes in the graph
            node_features_list.append(torch.tensor(node_features[mask], dtype=torch.float))
            # shape = (N, 1), !!!!!!!!! BCEWithLogitsLoss doesn't require long/int64 so saving some memory by using float32 !!!!!!!! change maybe for mse?
            node_labels_list.append(torch.tensor(node_labels[mask], dtype=torch.float))
    #
    # Prepare graph data loaders
    #

    data_loader_train = GraphDataLoader(
        node_features_list[num_graphs_per_split_cumulative[0]:num_graphs_per_split_cumulative[1]],
        node_labels_list[num_graphs_per_split_cumulative[0]:num_graphs_per_split_cumulative[1]],
        edge_index_list[num_graphs_per_split_cumulative[0]:num_graphs_per_split_cumulative[1]],
        batch_size=batch_size,
        shuffle=True
    )

    data_loader_val = GraphDataLoader(
        node_features_list[num_graphs_per_split_cumulative[1]:num_graphs_per_split_cumulative[2]],
        node_labels_list[num_graphs_per_split_cumulative[1]:num_graphs_per_split_cumulative[2]],
        edge_index_list[num_graphs_per_split_cumulative[1]:num_graphs_per_split_cumulative[2]],
        batch_size=batch_size,
        shuffle=False  # no need to shuffle the validation and test graphs
    )

    data_loader_test = GraphDataLoader(
        node_features_list[num_graphs_per_split_cumulative[2]:num_graphs_per_split_cumulative[3]],
        node_labels_list[num_graphs_per_split_cumulative[2]:num_graphs_per_split_cumulative[3]],
        edge_index_list[num_graphs_per_split_cumulative[2]:num_graphs_per_split_cumulative[3]],
        batch_size=batch_size,
        shuffle=False
    )

    return data_loader_train, data_loader_val, data_loader_test


class GraphDataLoader(DataLoader):
    def __init__(self, node_features_list, node_labels_list, edge_index_list, batch_size=1, shuffle=False):
        graph_dataset = GraphDataset(node_features_list, node_labels_list, edge_index_list)
        # We need to specify a custom collate function, it doesn't work with the default one
        super().__init__(graph_dataset, batch_size, shuffle, collate_fn=graph_collate_fn)


class GraphDataset(Dataset):
    """
    This one just fetches a single graph from the split when GraphDataLoader "asks" it
    """

    def __init__(self, node_features_list, node_labels_list, edge_index_list):
        self.node_features_list = node_features_list
        self.node_labels_list = node_labels_list
        self.edge_index_list = edge_index_list

    # 2 interface functions that need to be defined are len and getitem so that DataLoader can do it's magic
    def __len__(self):
        return len(self.edge_index_list)

    def __getitem__(self, idx):  # we just fetch a single graph
        return self.node_features_list[idx], self.node_labels_list[idx], self.edge_index_list[idx]


def graph_collate_fn(batch):
    """
    The main idea here is to take multiple graphs as defined by the batch size
    and merge them into a single graph with multiple connected components.
    It's important to adjust the node ids in edge indices such that they form a consecutive range. Otherwise
    the scatter functions in the implementation 3 will fail.
    :param batch: contains a list of edge_index, node_features, node_labels tuples (as provided by the GraphDataset)
    """

    edge_index_list = []
    node_features_list = []
    node_labels_list = []
    num_nodes_seen = 0

    for features_labels_edge_index_tuple in batch:
        # Just collect these into separate lists
        node_features_list.append(features_labels_edge_index_tuple[0])
        node_labels_list.append(features_labels_edge_index_tuple[1])

        edge_index = features_labels_edge_index_tuple[2]  # all of the components are in the [0, N] range
        edge_index_list.append(edge_index + num_nodes_seen)  # very important! translate the range of this component
        num_nodes_seen += len(features_labels_edge_index_tuple[1])  # update the number of nodes we've seen so far

    # Merge the PPI graphs into a single graph with multiple connected components
    node_features = torch.cat(node_features_list, 0)
    node_labels = torch.cat(node_labels_list, 0)
    edge_index = torch.cat(edge_index_list, 1)

    return node_features, node_labels, edge_index


def visualize_graph(file_name="graph_visualization"):
    """
    Generate a html with Pyvis for an interactive graph visualization
    """
    attack_graph = AttackGraph()
    V, _ = attack_graph.value_iteration()
    net = Network(height='900px', width='75%', notebook=True)

    max_v = max(V)
    min_v = min(V)
    for key, items in attack_graph.graph_expanded.items():
        key_idx = attack_graph.key_indices[key]
        v = (V[key_idx] - min_v) / (max_v - min_v)
        color = colors.to_hex([v, 0.0, 1 - v])
        net.add_node(key, key + "\n" + '%.2f' % V[key_idx], title=key, color=color)
        for item in items:
            item_idx = attack_graph.key_indices[item]
            v = (V[item_idx] - min_v) / (max_v - min_v)
            color = colors.to_hex([v, 0.0, 1 - v])
            net.add_node(item, item + "\n" + '%.2f' % V[item_idx], title=item, color=color)

            net.add_edge(key, item, title=attack_graph.rewards[key_idx, item_idx])

    # enable buttons to change and generate options (omit filter for all buttons)
    # NOTE: showing buttons AND setting options does not work...
    # net.show_buttons(filter_=['physics'])

    # load options and override standard
    with open("ValueApproximator/Resources/pyvis_options.json") as f:
        net.set_options(f.read())

    net.show("GraphVisualizations/" + file_name + ".html")


def print_sample_graph_values():
    """
    Performs value iteration and prints it's results
    """
    np.set_printoptions(formatter={'float': lambda x: "{0:0.2f}".format(x)})
    graph = AttackGraph(modelGenerator.getModel())
    V, Q = graph.value_iteration()
    i = 0
    for key in graph.graph_expanded.keys():
        if V[i] > 0:
            print(f'{graph.key_indices[key]}   {key} - {V[i]}')
        i = i + 1
