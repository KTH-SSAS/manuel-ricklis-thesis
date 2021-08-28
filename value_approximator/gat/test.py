import json
from glob import glob
from math import sqrt

import torch
from sklearn.metrics import r2_score

import matplotlib.pyplot as plt
from torch import nn
import numpy as np

from value_approximator.gat.gat import GAT
from value_approximator.gat.train import get_graph_data, AttackGraph, parse_features, build_edge_index


def test(model_file_path: str, device):
    """
    Test an existing model with the test data set
    """
    gat_state = torch.load(model_file_path, map_location=torch.device(device))
    gat = GAT(
        num_of_layers=gat_state['num_of_layers'],
        num_heads_per_layer=gat_state['num_heads_per_layer'],
        num_features_per_layer=gat_state['num_features_per_layer'],
        add_skip_connection=gat_state['add_skip_connection'],
        bias=gat_state['bias'],
        dropout=gat_state['dropout'],
        log_attention_weights=False
    ).to(device)

    gat.load_state_dict(gat_state["model_state_dict"])
    # optimizer = torch.optim.Adam(gat.parameters(), lr=5e-3, weight_decay=0)
    # optimizer = torch.optim.Adam(gat.parameters(), lr=gat_state['lr'], weight_decay=gat_state['weight_decay'])
    # optimizer.load_state_dict(gat_state["optimizer_state_dict"])

    gat.eval()

    test_set = glob(f'AttackGraphs/train_[0-9]*.json')
    # loss_fn = gat_state['loss_fn']
    loss_fn = nn.MSELoss(reduction='mean')

    accuracies = []
    losses = []

    for i in range(len(test_set)):
        node_features, gt_node_labels, edge_index = get_graph_data([test_set[i]], gat_state, device=device)
        graph_data = (node_features, edge_index)
        nodes_unnormalized_scores = gat(graph_data)[0]
        r2 = r2_score(nodes_unnormalized_scores.cpu().detach().numpy(), gt_node_labels.cpu().detach().numpy())

        losses.append(loss_fn(nodes_unnormalized_scores, gt_node_labels.unsqueeze(-1)).item())

        accuracies.append(r2)

        print(f'Accuracy = {accuracies[-1]}\nLoss = {losses[-1]}\n')

    print(f'Accuracy  mean = {np.mean(accuracies)}')

    variance = np.sqrt(
        sum(
            np.square(
                np.array(accuracies) - (np.ones(len(accuracies)) * np.mean(accuracies))
            )) / len(test_set))
    print(f'Variance = {variance}')

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


def test_predictions(model_file_path: str, device):
    """
    Checks if the actions based on predicted values equals the actions based on the ground truth labels
    """
    gat_state = torch.load(model_file_path, map_location=torch.device(device))
    gat = GAT(
        num_of_layers=gat_state['num_of_layers'],
        num_heads_per_layer=gat_state['num_heads_per_layer'],
        num_features_per_layer=gat_state['num_features_per_layer'],
        add_skip_connection=gat_state['add_skip_connection'],
        bias=gat_state['bias'],
        dropout=gat_state['dropout'],
        log_attention_weights=False
    ).to(device)

    gat.load_state_dict(gat_state["model_state_dict"])
    gat.eval()

    test_set = glob(f'AttackGraphs/train_[0-9]*.json')

    prediction_accuracies = []

    for i in range(len(test_set)):
        with open(test_set[i], "r") as f:
            dictionary = json.load(f)
            graph = AttackGraph(
                graph_expanded=dictionary["graph_expanded"],
                key_indices=dictionary["key_indices"],
                rewards=np.asarray(dictionary["rewards"])
            )
            node_features, adjacency_list = parse_features(graph, gat_state['num_features_per_layer'][0], device)
            _node_labels, _ = graph.value_iteration()
            _topology = build_edge_index(adjacency_list, len(_node_labels), False)

            node_labels = torch.tensor(_node_labels, dtype=torch.double, device=device)
            edge_index = torch.tensor(_topology, dtype=torch.long, device=device)

            graph_data = (node_features, edge_index)
            nodes_unnormalized_scores = gat(graph_data)[0]

            graph_predictions = {}
            for node, children in adjacency_list.items():
                tpl = (node, node_labels[node].item(), nodes_unnormalized_scores[node].item())
                graph_predictions[tpl] = []
                for child in children:
                    graph_predictions[tpl].append(
                        (child, node_labels[child].item(), nodes_unnormalized_scores[child].item()))

            scores = []
            for _, children in graph_predictions.items():
                predictions_gt = []
                predictions_nn = []
                for child in children:
                    predictions_gt.append(child[1])
                    predictions_nn.append(child[2])

                if len(predictions_gt) > 0:
                    maxes_gt = [i for i, j in enumerate(predictions_gt) if j == max(predictions_gt)]
                    maxes_nn = [i for i, j in enumerate(predictions_nn) if j == max(predictions_nn)]

                    if len(maxes_nn) > 1:
                        if any(m in maxes_gt for m in maxes_nn):
                            scores.append(1)
                        else:
                            scores.append(0)
                    elif len(maxes_gt) > 1:
                        if any(m in maxes_nn for m in maxes_gt):
                            scores.append(1)
                        else:
                            scores.append(0)
                    else:
                        if predictions_nn.index(max(predictions_nn)) == predictions_gt.index(max(predictions_gt)):
                            scores.append(1)
                        else:
                            scores.append(0)
            prediction_accuracies.append(sum(scores) / len(scores))
            print(prediction_accuracies[-1])
            print()
    variance = np.sqrt(
        sum(
            np.square(
                np.array(prediction_accuracies) - (np.ones(len(prediction_accuracies)) * np.mean(prediction_accuracies))
            )) / len(test_set))
    print(f'Variance = {variance}')

    print(f'Total score = {sum(prediction_accuracies) / len(prediction_accuracies)}')


def prediction_baseline():
    test_set = glob(f'AttackGraphs/test_[0-9]*.json')
    scores = []
    for i in range(len(test_set)):
        with open(test_set[i], "r") as f:
            dictionary = json.load(f)
            graph = AttackGraph(
                graph_expanded=dictionary["graph_expanded"],
                key_indices=dictionary["key_indices"],
                rewards=np.asarray(dictionary["rewards"])
            )
            adjacency_list = {}
            for step, children in graph.graph_expanded.items():
                children_indx = []
                for child in children:
                    children_indx.append(graph.key_indices[child])
                adjacency_list[graph.key_indices[step]] = children_indx
            v, _ = graph.value_iteration()

            graph_predictions = {}
            for node, children in adjacency_list.items():
                graph_predictions[node] = []
                for child in children:
                    graph_predictions[node].append(v[child])

            for _, children in graph_predictions.items():
                if len(children) > 0:
                    maxes_gt = [i for i, j in enumerate(children) if j == max(children)]
                    scores.append(len(maxes_gt) / len(children))
                else:
                    scores.append(1)
    print(f'Prediction baseline is {"{:.3f}".format(sum(scores) / len(scores))}')
