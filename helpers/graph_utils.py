import numpy as np
import json
from matplotlib import colors
from pyvis.network import Network

from typing import List

from model_generator import model_generator
from model_generator.model_generator import ModelGenerator
from value_approximator.graph import attack_graph
from value_approximator.graph.attack_graph import AttackGraph as AttackGraph


def create_attack_graphs_from_model(model: ModelGenerator, min_graph_size=100) -> List[AttackGraph]:
    graph = attack_graph.concatenate_model_instances(model)
    model.add_graph(graph)

    graph_and_parents = attack_graph.build_and_parents(model)
    model.add_graph_and_parents(graph_and_parents)

    children = [item for items in graph.values() for item in items]
    eps = [key for key in graph if key not in children]

    attack_graphs = []
    for entry_point in eps:
        attack_graph.expand_graph(model, entry_point)
        graph_expanded = attack_graph.graph_to_expand
        attack_graph.graph_to_expand = {}

        if len(graph_expanded) < min_graph_size:
            continue

        key_indices = dict(zip(graph_expanded.keys(), [i for i in range(len(graph_expanded))]))
        rewards = attack_graph.build_rewards(key_indices=key_indices, graph_expanded=graph_expanded)
        # attack_graph.update_vocabulary(graph_expanded)

        attack_graphs.append(
            AttackGraph(graph_expanded, key_indices, rewards)
        )

    return attack_graphs


single_graph: dict
graph_tmp: dict


def create_single_graph(node):
    if node in single_graph.keys():
        return
    single_graph[node] = []
    for child in graph_tmp[node]:
        single_graph[node].append(child)
        create_single_graph(child)


def create_and_export_attack_graphs_for_learning(prefix: str, amount: int):
    generator = ModelGenerator()
    for i in range(amount):
        generator.model = generator.generate_model_based_on_random_parameters(4, 1, 5, 2, 3, 1, 3, 1, 3, 1)
        attack_graphs = create_attack_graphs_from_model(generator)

        for j, graph in enumerate(attack_graphs):
            with open("AttackGraphs/" + prefix + "_" + str(j) + ".json", "w+") as f:
                json.dump({
                    "graph_expanded": graph.graph_expanded,
                    "key_indices": graph.key_indices,
                    "rewards": graph.rewards.tolist()
                }, f)
        print(f'Exportet graphs of model {i}')


# def visualize_graph(graph: dict, V, key_indices, rewards, file_name="graph_visualization"):
#     net = Network(height='900px', width='75%', notebook=True)
#
#     max_v = max(V)
#     min_v = min(V)
#     for key, items in graph.items():
#         key_idx = key_indices[key]
#         v = (V[key_idx] - min_v) / (max_v - min_v)
#         color = colors.to_hex([v, 0.0, 1 - v])
#         net.add_node(key, key + "\n" + '%.2f' % V[key_idx], title=key, color=color)
#         for item in items:
#             item_idx = key_indices[item]
#             v = (V[item_idx] - min_v) / (max_v - min_v)
#             color = colors.to_hex([v, 0.0, 1 - v])
#             net.add_node(item, item + "\n" + '%.2f' % V[item_idx], title=item, color=color)
#
#             net.add_edge(key, item, title=rewards[key_idx, item_idx])
#
#     # enable buttons to change and generate options (omit filter for all buttons)
#     # NOTE: showing buttons AND setting options does not work...
#     # net.show_buttons(filter_=['physics'])
#
#     # load options and override standard
#     with open("value_approximator/Resources/pyvis_options.json") as f:
#         net.set_options(f.read())
#
#     net.show("GraphVisualizations/" + file_name + ".html")


def visualize_graph(attack_graph: AttackGraph, file_name="graph_visualization"):
    """
    Generate a html with Pyvis for an interactive graph visualization
    """
    V, _ = attack_graph.value_iteration()
    net = Network(height='900px', width='75%', notebook=True)

    max_v = max(V)
    min_v = min(V)
    graph = attack_graph.graph_expanded
    for key, items in graph.items():
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
