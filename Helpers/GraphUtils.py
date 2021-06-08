import numpy as np
from matplotlib import colors
from pyvis.network import Network

from ValueApproximator.Graph.AttackGraph import AttackGraph as Graph


def visualize_graph():
    '''
    Generate a html with Pyvis for an interactive graph visualization
    '''
    attack_graph = Graph()
    V, _ = attack_graph.value_iteration()
    net = Network(height='900px', width='75%', notebook=True)

    max_v = max(V)
    min_v = min(V)
    for key, items in attack_graph.graph.items():
        if not items == []:
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

    net.show("GraphVisualizations/graph_visualization.html")


def create_and_export_attack_graphs_for_learning():
    pass
    # generator = ModelGenerator()
    # model = generator.generate_model()
    # generator.generate_model_based_on_random_parameters(5, 2, 8, 4, 5, 1, 3, 1, 4, 2)


graph_parents = {}


def build_node_parent_relations():
    pass
    # generator = ModelGenerator()
    # model = generator.generate_model()
    #
    # for instance in model:
    #     for _, step in instance.attack_steps.items():
    #         s = f'{instance.type}.{instance.name}.{step.name}'
    #         if step.has_children():
    #             if type(step.children) == dict:
    #                 for _, child in step.children.items():
    #                     ss = f'{child.instance.type}.{child.instance.name}.{child.name}'
    #                     if ss not in graph_parents:
    #                         graph_parents[ss] = [s]
    #                     elif s not in graph_parents[ss]:
    #                         graph_parents[ss].append(s)
    #             else:
    #                 ss = f'{step.children.instance.type}.{step.children.instance.name}.{step.children.name}'
    #                 if ss not in graph_parents:
    #                     graph_parents[ss] = [s]
    #                 else:
    #                     graph_parents[ss].append(s)
    # print(graph_parents)


def print_sample_graph_values():
    '''
    Performs value iteration and prints it's results
    '''
    np.set_printoptions(formatter={'float': lambda x: "{0:0.2f}".format(x)})
    graph = Graph()
    V, Q = graph.value_iteration()
    i = 0
    for key in graph.graph.keys():
        if V[i] > 0:
            print(f'{graph.key_indices[key]}   {key} - {V[i]}')
        i = i + 1
