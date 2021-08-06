import torch

from helpers.constants import *
from helpers.constants import BINARIES_PATH
from value_approximator.gat.gat import GAT
from value_approximator.graph.attack_graph import AttackGraph
from value_approximator.graph.attack_graph_utils import *


def load_example_graph(number_of_features, device="cuda"):
    """
    Loads an example graph and returns it's feature matrix, labels and edge index
    """
    with open("AttackGraphs/default_graph_0_1.json", "r") as f:
        dictionary = json.load(f)
    graph = AttackGraph(
        graph_expanded=dictionary["graph_expanded"],
        key_indices=dictionary["key_indices"],
        rewards=np.asarray(dictionary["rewards"])
    )

    node_features, adjacency_list = parse_features(graph, number_of_features, device)
    node_labels, _ = graph.value_iteration()

    topology = build_edge_index(adjacency_list, len(node_labels), False)

    topology = torch.tensor(topology, dtype=torch.long, device=device)
    node_labels = torch.tensor(node_labels, dtype=torch.long, device=device)
    node_features = node_features.clone().detach()

    return node_features, node_labels, topology


def parse_features(graph: AttackGraph, number_of_features, device):
    """
    Takes an expanded attack graph and creates the according feature matrix and adjacency list from it
    """
    N = len(graph.graph_expanded)

    # the node feature matrix
    M = torch.ones((N, number_of_features), dtype=torch.double, device=device)

    adjacency_matrix = {}
    for step, children in graph.graph_expanded.items():
        M[graph.key_indices[step], STEP:STEP + len(vocabulary)] = get_feature_vector_from_expanded_node_name(step,
                                                                                                             device)
        step_rewards = [reward for reward in graph.rewards[:, graph.key_indices[step]] if reward != -999]
        M[graph.key_indices[step], REWARD] = np.mean(step_rewards) if len(step_rewards) > 0 else -999
        M[graph.key_indices[step], RANK] = len(graph.graph_expanded[step])

        # build the adjacency matrix
        children_indx = []
        for child in children:
            children_indx.append(graph.key_indices[child])
        adjacency_matrix[graph.key_indices[step]] = children_indx

    return M, adjacency_matrix


def get_feature_vector_from_expanded_node_name(name_expanded: str, device) -> torch.tensor:
    """
    Takes the (expanded) node name and creates a vector following the bag-of-words approach
    """
    vector = torch.zeros(len(vocabulary), device=device)
    for _name in name_expanded.split("|"):
        name = ''.join([i for i in _name if not i.isdigit()])
        vector[vocabulary[name]] += 1
    return vector


def build_edge_index(adjacency_list_dict, num_of_nodes, add_self_edges=False):
    """
    from https://github.com/gordicaleksa/pytorch-GAT/blob/main/utils/data_loading.py#L344
    """
    source_nodes_ids, target_nodes_ids = [], []
    seen_edges = set()

    for src_node, neighboring_nodes in adjacency_list_dict.items():
        for trg_node in neighboring_nodes:
            if (src_node, trg_node) not in seen_edges:
                source_nodes_ids.append(src_node)
                target_nodes_ids.append(trg_node)

                seen_edges.add((src_node, trg_node))

    if add_self_edges:
        source_nodes_ids.extend(np.arange(num_of_nodes))
        target_nodes_ids.extend(np.arange(num_of_nodes))

    edge_index = np.row_stack((source_nodes_ids, target_nodes_ids))

    return edge_index


def get_training_state(training_config, model: GAT, optimizer, loss_fn):
    """
    Reutrns the current training state of the model that can be used to save it
    """
    training_state = {
        # Training details
        "dataset_name": training_config['dataset_name'],
        "num_of_epochs": training_config['num_of_epochs'],
        "test_perf": training_config['test_perf'],
        "device": training_config['device'],

        # Model structure
        "num_of_layers": training_config['num_of_layers'],
        "num_heads_per_layer": training_config['num_heads_per_layer'],
        "num_features_per_layer": training_config['num_features_per_layer'],
        "add_skip_connection": training_config['add_skip_connection'],
        "bias": training_config['bias'],
        "dropout": training_config['dropout'],

        # Model state, loss and optimizer state
        "model_state_dict": model.state_dict(),
        "optimizer_state_dict": optimizer.state_dict(),
        "loss_fn": loss_fn,
        "lr": training_config['lr'],
        "weight_decay": training_config['weight_decay']
    }

    return training_state


def get_available_binary_name(dataset_name='attack_graphs'):
    prefix = f'gat_{dataset_name}'

    def valid_binary_name(binary_name):
        pattern = re.compile(rf'{prefix}_[0-9]{{6}}\.pth')
        return re.fullmatch(pattern, binary_name) is not None

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
        if key != 'state_dict':
            print(f'{key}: {value}')
    print(f'{"*" * len(header)}\n')
