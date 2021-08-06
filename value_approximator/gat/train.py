import argparse
import time
from glob import glob

from sklearn.metrics import r2_score
from torch import nn
from torch.optim import Adam

from helpers.gat_utils import *
from value_approximator.gat.gat import GAT

import matplotlib

matplotlib.use('Agg')
import matplotlib.pyplot as plt


def train_gat(config):
    global BEST_VAL_PERF, BEST_VAL_LOSS
    global val_accuracies, val_losses, test_accuracies
    val_accuracies = []
    val_losses = []
    test_accuracies = []

    plt.interactive(False)

    device = config["device"]

    graph_data_sets = [
        glob(f'AttackGraphs/train_[0-9]*.json'),
        glob(f'AttackGraphs/val_[0-9]*.json'),
        glob(f'AttackGraphs/test_[0-9]*.json')
    ]

    gat = GAT(
        num_of_layers=config['num_of_layers'],
        num_heads_per_layer=config['num_heads_per_layer'],
        num_features_per_layer=config['num_features_per_layer'],
        add_skip_connection=config['add_skip_connection'],
        bias=config['bias'],
        dropout=config['dropout'],
        log_attention_weights=False
    ).to(device)

    loss_fn = nn.MSELoss(reduction='mean')
    optimizer = Adam(gat.parameters(), lr=config['lr'], weight_decay=config['weight_decay'])

    main_loop = get_main_loop(
        graph_data_sets=graph_data_sets,
        config=config,
        gat=gat,
        loss_function=loss_fn,
        optimizer=optimizer,
        patience_period=config['patience_period'],
        time_start=time.time()
    )

    BEST_VAL_PERF, BEST_VAL_LOSS, PATIENCE_CNT = [0, 0, 0]

    for epoch in range(config['num_of_epochs']):
        # Training loop
        main_loop(phase=LoopPhase.TRAIN, epoch=epoch, device=device)

        # Validation loop
        with torch.no_grad():
            try:
                main_loop(phase=LoopPhase.VAL, epoch=epoch, device=device)
            except Exception as e:
                print(str(e))
                break

    if config['should_test']:
        main_loop(phase=LoopPhase.TEST, device=device)
        test_accuracy_mean = np.mean(test_accuracies)
        config['test_perf'] = test_accuracy_mean

        print('*' * 50)
        print(f'Test accuracy mean = {test_accuracy_mean}')
    else:
        config['test_perf'] = -1

    torch.save(
        get_training_state(config, gat, optimizer, loss_fn),
        os.path.join(BINARIES_PATH, get_available_binary_name(config['dataset_name']))
    )


def get_main_loop(graph_data_sets, config, gat, loss_function, optimizer, patience_period, time_start):
    def main_loop(phase, device="cuda", epoch=0):
        global BEST_VAL_PERF, BEST_VAL_LOSS, PATIENCE_CNT
        if phase == LoopPhase.TRAIN:
            gat.train()
            graph_data_set = graph_data_sets[0]
        elif phase == LoopPhase.VAL:
            gat.eval()
            graph_data_set = graph_data_sets[1]
        else:
            gat.eval()
            graph_data_set = graph_data_sets[2]

        for i in range(0, len(graph_data_set) - 1, 2):
            node_features, gt_node_labels, edge_index = get_graph_data(graph_data_set[i:i + 2], config, device=device)
            graph_data = (node_features, edge_index)

            nodes_unnormalized_scores = gat(graph_data)[0]

            loss = loss_function(nodes_unnormalized_scores, gt_node_labels.unsqueeze(-1))

            if phase == LoopPhase.TRAIN:
                optimizer.zero_grad()
                loss.backward()
                optimizer.step()

            accuracy = r2_score(nodes_unnormalized_scores.cpu().detach().numpy(), gt_node_labels.cpu().detach().numpy())

            #
            # Logging
            #

            if phase == LoopPhase.TRAIN:
                if config['checkpoint_freq'] is not None and (epoch + 1) % config['checkpoint_freq'] == 0:
                    ckpt_model_name = f'gat_{config["dataset_name"]}{epoch + 1}.pth'
                    config['test_perf'] = -1
                    torch.save(get_training_state(config, gat, optimizer, loss_function),
                               os.path.join(CHECKPOINTS_PATH, ckpt_model_name))

            elif phase == LoopPhase.VAL:
                val_accuracies.append(accuracy)
                val_losses.append(loss.item())

                if config['enable_tensorboard']:
                    print(f'loss={loss.item()}\naccuracy={accuracy}')

                if config['console_log_freq'] is not None and epoch % config['console_log_freq'] == 0:
                    print(f'GAT training: time elapsed= '
                          f'{(time.time() - time_start):.2f} [s] | '
                          f'epoch={epoch + 1} | '
                          f'val acc={accuracy} | '
                          f'loss={loss.item()}')
                    #
                    # Plotting
                    #

                    plt.yscale("log")
                    plt.title("Validation Loss")
                    plt.xlabel("Batch number")
                    plt.ylabel("MSE mean")
                    plt.plot(val_losses)
                    plt.savefig("loss.png")
                    plt.close()

                    with open("loss.npy", "wb") as f:
                        np.save(f, np.array(val_losses))

                    plt.title("Validation Accuracy")
                    plt.xlabel("Batch number")
                    plt.ylabel("R2 Score")
                    plt.ylim([-0.1, 1.1])
                    plt.plot(val_accuracies)
                    plt.savefig("accuracy.png")
                    plt.close()

                    with open("accuracy.npy", "wb") as f:
                        np.save(f, np.array(val_accuracies))

                if accuracy > BEST_VAL_PERF or loss.item() < BEST_VAL_LOSS:
                    BEST_VAL_PERF = max(accuracy, BEST_VAL_PERF)
                    BEST_VAL_LOSS = min(loss.item(), BEST_VAL_LOSS)
                    PATIENCE_CNT = 0
                else:
                    PATIENCE_CNT += 1

                if PATIENCE_CNT >= patience_period:
                    raise Exception('Stopping, out of patience.')

            else:
                test_accuracies.append(accuracy)

    return main_loop  # return the decorated function


def get_graph_data(file_names: [], config, device="cuda"):
    node_features = []
    node_labels = []
    edge_index = []
    for file_name in file_names:
        with open(file_name, "r") as f:
            dictionary = json.load(f)
            graph = AttackGraph(
                graph_expanded=dictionary["graph_expanded"],
                key_indices=dictionary["key_indices"],
                rewards=np.asarray(dictionary["rewards"])
            )
            _node_features, _adjacency_list = parse_features(graph, config["num_features_per_layer"][0], device)
            _node_labels, _ = graph.value_iteration()
            _topology = build_edge_index(_adjacency_list, len(_node_labels), False)

            node_features.append(_node_features.clone().detach())
            node_labels.append(torch.tensor(_node_labels, dtype=torch.double, device=device))
            edge_index.append(torch.tensor(_topology, dtype=torch.long, device=device))

    return torch.cat(node_features, 0), torch.cat(node_labels, 0), torch.cat(edge_index, 1)


def get_training_args():
    parser = argparse.ArgumentParser()

    # Training related
    parser.add_argument("--device", type=str, default="cuda")
    parser.add_argument("--num_of_epochs", type=int, help="number of training epochs", default=200)
    parser.add_argument("--patience_period", type=int,
                        help="number of epochs with no improvement on val before terminating", default=100)
    parser.add_argument("--lr", type=float, help="model learning rate", default=5e-3)
    parser.add_argument("--weight_decay", type=float, help="L2 regularization on model weights", default=0)
    parser.add_argument("--should_test", action='store_true',
                        help='should test the model on the test dataset? (no by default)')
    parser.add_argument("--force_cpu", action='store_true', help='use CPU if your GPU is too small (no by default)')

    parser.add_argument("--dataset_name", help='dataset to use for training', default="AttackGraph")
    parser.add_argument("--batch_size", type=int, help='number of graphs in a batch', default=2)
    parser.add_argument("--should_visualize", action='store_true', help='should visualize the dataset? (no by default)')

    parser.add_argument("--enable_tensorboard", action='store_true', help="enable tensorboard logging (yes by default)",
                        default=True)
    parser.add_argument("--console_log_freq", type=int, help="log to output console (batch) freq (None for no logging)",
                        default=10)
    parser.add_argument("--checkpoint_freq", type=int,
                        help="checkpoint model saving (epoch) freq (None for no logging)", default=5)
    args = parser.parse_args()

    gat_config = {
        "num_of_layers": 3,
        "num_heads_per_layer": [6, 4, 4],
        "num_features_per_layer": [len(vocabulary) + 2, 512, 256, 1],
        "add_skip_connection": True,
        "bias": True,
        "dropout": 0.0,
        "device": "cpu",
        "patience_period": 75,
        "num_of_epochs": 250,
        "should_test": True,
        "dataset_name": "test",
        "enable_tensorboard": False
    }

    training_config = dict()
    for arg in vars(args):
        training_config[arg] = getattr(args, arg)

    training_config.update(gat_config)

    return training_config
