from torch.optim import Adam

from Helpers.GATUtils import load_example_graph
from Helpers.GraphUtils import *
from ValueApproximator.GAT.GAT import GAT
import torch.nn as nn


if __name__ == '__main__':
    # create_and_export_attack_graphs_for_learning()

    visualize_graph()

    # number_of_features = 5
    # embedding_vector_lengths = [3, 3, 3]
    # num_heads_per_layer = [4, 2, 4]
    #
    # node_features, node_labels, topology = load_example_graph(number_of_features, embedding_vector_lengths)
    # gat = GAT(num_of_layers=3, num_heads_per_layer=num_heads_per_layer,
    #           num_features_per_layer=[sum(embedding_vector_lengths) + (number_of_features - 3), 20, 20, 20]).to("cpu")
    # loss_fn = nn.CrossEntropyLoss(reduction='mean')
    # optimizer = Adam(gat.parameters(), lr=5e-3, weight_decay=5e-4)
    #
    # gat.train()
    # nodes_unnormalized_scores = gat((node_features, topology))
    # print(nodes_unnormalized_scores)
