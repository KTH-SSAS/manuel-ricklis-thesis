import torch.nn as nn
from torch.optim import Adam

from helpers.gat_utils import load_example_graph
from value_approximator.gat.gat import GAT

# TODO: When -inf is present in the rewards, the resulting tensor in the unnormalized scores contain NaN
from value_approximator.graph.attack_graph import import_attack_graphs

if __name__ == '__main__':
    # graph = graph()
    # V, _ = graph.value_iteration()
    # print(V)

    # attack_graph = graph()
    # print(attack_graph.graph["ManualHighImpactVulnerability.Vulnerability.impact"])

    # create_and_export_attack_graphs_for_learning()

    # visualize_graph("expanded_test_1")

    ########################################################################

    num_heads_per_layer = [4, 2, 4]
    embedding_vector_length = 10
    number_of_features = 3

    node_features, node_labels, topology = load_example_graph(number_of_features, embedding_vector_length)
    gat = GAT(num_of_layers=len(num_heads_per_layer), num_heads_per_layer=num_heads_per_layer,
              num_features_per_layer=[embedding_vector_length + (number_of_features - 1), 20, 20, 20]).to("cpu")
    loss_fn = nn.CrossEntropyLoss(reduction='mean')
    optimizer = Adam(gat.parameters(), lr=5e-3, weight_decay=5e-4)

    gat.train()
    nodes_unnormalized_scores = gat((node_features, topology))
    print(nodes_unnormalized_scores)

    ######################################################################

    # GraphUtils.create_and_export_attack_graphs_for_learning(prefix="test", amount=10)
    print(import_attack_graphs("test"))
