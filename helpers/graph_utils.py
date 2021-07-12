import numpy as np
import torch
import json
from matplotlib import colors
from pyvis.network import Network
from torch.utils.data import DataLoader, Dataset

from model_generator import model_generator
from model_generator.model_generator import ModelGenerator
from value_approximator.graph.attack_graph import AttackGraph as AttackGraph


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
    with open("value_approximator/Resources/pyvis_options.json") as f:
        net.set_options(f.read())

    net.show("GraphVisualizations/" + file_name + ".html")


def print_sample_graph_values():
    """
    Performs value iteration and prints it's results
    """
    np.set_printoptions(formatter={'float': lambda x: "{0:0.2f}".format(x)})
    graph = AttackGraph(model_generator.getModel())
    V, Q = graph.value_iteration()
    i = 0
    for key in graph.graph_expanded.keys():
        if V[i] > 0:
            print(f'{graph.key_indices[key]}   {key} - {V[i]}')
        i = i + 1
