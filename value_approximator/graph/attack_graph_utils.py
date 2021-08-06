import json

from helpers.importer import read_object_specifications
import numpy as np
import re
from scipy.stats import expon
from scipy.stats import bernoulli
from random import random

from model_generator.model_generator import ModelGenerator

# A large value to reflect an unreachable transition (infinity values lead to NaN entries during the machine learning)
UNREACHABLE = 1000000

# The rewards and ttc distributions
object_dict, ttc_dict = read_object_specifications()

# A dictionary with all possible node names of the expanded graph
with open("Resources/Mappings/assets_and_steps.json") as f:
    _dictionary = json.load(f)
    _full_list = []

    for _asset, _names in _dictionary["asset_names"].items():
        if len(_names) > 1:
            for _name in _names:
                _steps = _dictionary["step_names"][_asset]
                for _step in _steps:
                    _full_list.append(f'{_asset}.{_name}.{_step}')
        else:
            _steps = _dictionary["step_names"][_asset]
            for _step in _steps:
                _full_list.append(f'{_asset}.{_names[0]}.{_step}')
    vocabulary = {i: c for c, i in enumerate(_full_list)}


def concatenate_model_instances(model: ModelGenerator):
    """
    Concatenates all steps of all instances in the generated model to one dictionary
    """
    graph = {}
    for instance in model.model:
        for step in instance.attack_steps.values():
            s = f'{instance.type}.{instance.name}.{step.name}'
            if step.has_children():
                graph[s] = []
                if type(step.children) == dict:
                    for _, child in step.children.items():
                        graph[s].append(f'{child.instance.type}.'
                                        f'{child.instance.name}.'
                                        f'{child.name}')
                else:
                    graph[s].append(f'{step.children.instance.type}.'
                                    f'{step.children.instance.name}.'
                                    f'{step.children.name}')
    # add all steps without children
    graph.update({item: [] for items in graph.values() for item in items if item not in graph.keys()})
    return graph


def build_and_parents(model: ModelGenerator):
    """
    Takes the model and builds a list of AND steps with their respective parents
    This dictionary is then used to check if the condition for reaching an AND step is fulfilled
    """
    graph_and_parents = {}
    for instance in model.model:
        for step in instance.attack_steps.values():
            s = f'{instance.type}.{instance.name}.{step.name}'
            if step.has_children():
                if type(step.children) == dict:
                    for child in step.children.values():
                        if child.type != 'AND':
                            continue
                        ss = f'{child.instance.type}.{child.instance.name}.{child.name}'
                        if ss not in graph_and_parents:
                            graph_and_parents[ss] = [s]
                        elif s not in graph_and_parents[ss]:
                            graph_and_parents[ss].append(s)
                else:
                    if step.children.type != 'AND':
                        continue
                    ss = f'{step.children.instance.type}.{step.children.instance.name}.{step.children.name}'
                    if ss not in graph_and_parents:
                        graph_and_parents[ss] = [s]
                    else:
                        graph_and_parents[ss].append(s)
    return graph_and_parents


# a static variable used to perform recursion without having to pass it as parameter
graph_to_expand = {}


def expand_graph(model, current_nodes_to_expand):
    """
    Expands the graph to intermediate steps in order to reflect AND steps with only OR steps

    Algo:
      * Initiate recursive method expand_graph with entry points as list
      * For each entry point, loop through their children
      * If the child is not in the nodes name (i.e. already reached)
       and the resulting node (node name | child) is not in the expanded graph
       add the successor (node name | child)

       AND: if the child is an AND step, check if all it's parents are in the nodes name and only add it if true

      * Call the method again for each list of children added
    """
    if type(current_nodes_to_expand) != list:
        expansion_loop(model, current_nodes_to_expand)
    else:
        for node in current_nodes_to_expand:
            expansion_loop(model, node)


def expansion_loop(model: ModelGenerator, node):
    if node in graph_to_expand:
        return
    graph_to_expand[node] = []
    # sub_node = single node as found in original graph
    for sub_node in node.split("|"):
        # children of each sub_node
        for child in model.graph[sub_node]:
            if child not in node and sort(node, child) not in graph_to_expand[node]:
                # child is an AND step
                if child in model.graph_and_parents:
                    condition_fulfilled = True
                    for parent in model.graph_and_parents[child]:
                        condition_fulfilled = condition_fulfilled and parent in node
                    if condition_fulfilled:
                        graph_to_expand[node].append(sort(node, child))
                else:
                    graph_to_expand[node].append(sort(node, child))
        expand_graph(model, graph_to_expand[node])


def build_rewards(key_indices, graph_expanded):
    """
    Builds a reward matrix, each entry corresponding to a transition from step i to j
    """
    n = len(key_indices)
    rewards = np.ones((n, n)) * -999

    for key, steps in graph_expanded.items():
        for entry_ in steps:
            entry = get_entry(key, entry_)
            reward = get_reward(entry)
            ttc = parse_distribution(entry)
            ttc = 1.0 if ttc == 0 else ttc
            v = reward - ttc
            rewards[key_indices[key], key_indices[entry_]] = v
    return rewards


def get_reward(step: str) -> float:
    (assetID, assetName, stepName) = step.split(".")
    return object_dict[assetID][stepName]


def get_step_distribution(entry: str) -> str:
    (asset, _, step) = entry.split(".")
    return ttc_dict[asset][step]


def parse_distribution(entry: str):
    """
    Returns the distribution name and parameter if one is associated with the entry given
    """
    distribution = get_step_distribution(entry)
    if distribution == "":
        return 0
    ordinal_match = re.match("^\w*$", distribution)
    if ordinal_match:
        return parse_ordinal(distribution)
    else:
        distribution_match = re.match("^(\w+)\((.+)\)$", distribution)
        return get_ttc([distribution_match.group(1), distribution_match.group(2)])


def get_ttc(distribution):
    '''
    Samples from the given distribution
    '''
    distr, value = distribution
    if distr.lower() == "bernoulli":
        return bernoulli.ppf(q=value, p=random())
    else:
        return expon.ppf(q=0.95, scale=1 / float(value))


def parse_ordinal(distribution: str):
    """
    Translates the ordinal description to the distributions used
    https://github.com/mal-lang/malcompiler/wiki/Supported-distribution-functions
    """
    if distribution == "EasyAndCertain":
        return expon.ppf(q=0.95, scale=1)
    elif distribution == "EasyAndUncertian":
        return bernoulli.ppf(q=0.5, p=random())
    elif distribution == "HardAndCertain":
        return expon.ppf(q=0.95, scale=1 / 0.1)
    elif distribution == "HardAndUncertain":
        return UNREACHABLE if bernoulli.ppf(q=0.5, p=random()) == 0 else expon.ppf(q=0.95, scale=1 / 0.1)
    elif distribution == "VeryHardAndCertain":
        return expon.ppf(q=0.95, scale=1 / 0.01)
    elif distribution == "VeryHardAndUncertain":
        return UNREACHABLE if bernoulli.ppf(q=0.5, p=random()) == 0 else expon.ppf(q=0.95, scale=1 / 0.01)
    elif distribution == "Enabled":
        return 0
    elif distribution == "Disabled":
        return UNREACHABLE


def sort(node: str, child: str):
    """
    Concatenates the node name and the child to a new, sorted node name
    """
    nodes = node.split("|")
    nodes.append(child)
    nodes.sort()
    return '|'.join(nodes)


def get_entry(parent, child):
    """
    Returns the node name of the step that has been added when transitioning from the parent to the child
    """
    for c in child.split("|"):
        if c not in parent:
            return c
