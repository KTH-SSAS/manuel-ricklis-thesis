from torch.optim import Adam

from ValueApproximator.Helpers.GATUtils import load_example_graph
from ValueApproximator.GAT.GAT import GAT
import torch.nn as nn

if __name__ == '__main__':
    node_features, node_labels, topology = load_example_graph()
    gat = GAT(num_of_layers=1, num_heads_per_layer=[1], num_features_per_layer=[5, 10]).to("cpu")
    loss_fn = nn.CrossEntropyLoss(reduction='mean')
    optimizer = Adam(gat.parameters(), lr=5e-3, weight_decay=5e-4)

    gat.train()
    nodes_unnormalized_scores = gat((node_features, topology))