from glob import glob

import torch
from sklearn.metrics import r2_score

import matplotlib.pyplot as plt
from torch import nn
import numpy as np

from value_approximator.gat.gat import GAT
from value_approximator.gat.train import get_graph_data


def test(path: str, device):
    gat_state = torch.load(path, map_location=torch.device(device))
    gat = GAT(
        num_of_layers=gat_state['num_of_layers'],
        num_heads_per_layer=gat_state['num_heads_per_layer'],
        num_features_per_layer=gat_state['num_features_per_layer'],
        add_skip_connection=gat_state['add_skip_connection'],
        bias=gat_state['bias'],
        dropout=gat_state['dropout'],
        log_attention_weights=False
    ).to(device)

    gat.load_state_dict(gat_state["state_dict"])
    # optimizer = torch.optim.Adam(gat.parameters(), lr=5e-3, weight_decay=0)
    # optimizer = torch.optim.Adam(gat.parameters(), lr=gat_state['lr'], weight_decay=gat_state['weight_decay'])
    # optimizer.load_state_dict(gat_state["optimizer_state_dict"])

    gat.eval()

    test_set = glob(f'AttackGraphs/test_[0-9]*.json')
    # loss_fn = gat_state['loss_fn']
    loss_fn = nn.MSELoss(reduction='mean')

    accuracies = []
    losses = []

    for i in range(len(test_set)):
        node_features, gt_node_labels, edge_index = get_graph_data([test_set[i]], gat_state, device=device)
        graph_data = (node_features, edge_index)
        nodes_unnormalized_scores = gat(graph_data)[0]

        losses.append(loss_fn(nodes_unnormalized_scores, gt_node_labels.unsqueeze(-1)).item())
        accuracies.append(r2_score(nodes_unnormalized_scores.cpu().detach().numpy(), gt_node_labels.cpu().detach().numpy()))

        print(f'Accuracy = {accuracies[-1]}\nLoss = {losses[-1]}\n')

    print(f'Accuracy  mean = {np.mean(accuracies)}')

    fig, axs = plt.subplots(2)
    axs[0].set_yscale("log")
    axs[0].set_title("Test Loss")
    axs[0].set_xlabel("Batch number")
    axs[0].set_ylabel("MSE mean")
    axs[0].plot(losses)

    axs[1].set_title("Test Accuracy")
    axs[1].set_xlabel("Batch number")
    axs[1].set_ylabel("R2 Score")
    axs[1].set_ylim([-0.1, 1.1])
    axs[1].plot(accuracies)

    fig.tight_layout()
    plt.savefig("loss_accuracy_plots.png")
    plt.close(fig)
