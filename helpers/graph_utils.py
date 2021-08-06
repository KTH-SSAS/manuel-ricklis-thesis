from matplotlib import colors
from pyvis.network import Network

from value_approximator.graph.attack_graph import AttackGraph as AttackGraph
from value_approximator.graph.attack_graph_utils import *


def create_and_export_attack_graphs_for_learning(prefix: str, params: dict):
    """
    Creates a model with the given parameters and exports all resulting attack graphs with the parameters appended
    to the provided prefix
    """
    generator = ModelGenerator()
    generator.model = generator.generate_model(
        params["networks"],
        params["services"],
        params["data"],
        params["id_data"],
        params["service_id"])

    prefix += "_"
    for _, item in params.items():
        prefix += str(item)

    create_and_export_attack_graphs_from_model(generator, prefix, 200)


def create_and_export_attack_graphs_from_model(model: ModelGenerator, prefix: str, min_graph_size=100):
    """
    Builds up single, expanded attack graphs from the given model and exports them separately
    """
    graph = concatenate_model_instances(model)
    model.add_graph(graph)

    graph_and_parents = build_and_parents(model)
    model.add_graph_and_parents(graph_and_parents)

    children = [item for items in graph.values() for item in items]
    eps = [key for key in graph if key not in children]

    for entry_point in eps:
        expand_graph(model, entry_point)

        if len(graph_to_expand) < min_graph_size:
            continue

        key_indices = dict(zip(graph_to_expand.keys(), [i for i in range(len(graph_to_expand))]))
        rewards = build_rewards(key_indices=key_indices, graph_expanded=graph_to_expand)

        export_attack_graph_for_learning(prefix, AttackGraph(graph_to_expand, key_indices, rewards))
        graph_to_expand.clear()


def export_attack_graph_for_learning(prefix: str, attack_graph: AttackGraph):
    """
    Writes the provided attack graph into a json file in the AttackGraphs directory
    """
    with open("AttackGraphs/" + prefix + ".json", "w+") as f:
        json.dump({
            "graph_expanded": attack_graph.graph_expanded,
            "key_indices": attack_graph.key_indices,
            "rewards": attack_graph.rewards.tolist()
        }, f)


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


single_graph: dict
graph_tmp: dict


def create_single_graph(node):
    if node in single_graph.keys():
        return
    single_graph[node] = []
    for child in graph_tmp[node]:
        single_graph[node].append(child)
        create_single_graph(child)
