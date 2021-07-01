import json

import Helpers.Importer as Importer
import numpy as np
import math
import re
from scipy.stats import expon
from scipy.stats import bernoulli
from random import random


class AttackGraph:
    # attack steps with their respective children
    graph: dict

    # time to compromise distributions
    ttc_dict: dict

    # mal specification of assets and their steps
    object_dict: dict

    # mapping from attack steps to an index
    key_indices: dict

    # effective reward (immediate reward - ttc) for all transitions
    rewards: np.ndarray

    # vocabulary used for embeddings            -> can be static, as it includes ALL steps from ALL graphs
    vocabulary: dict

    def __init__(self, model):
        self.graph = {}
        self.concatenate_model_instances(model)
        self.object_dict, self.ttc_dict = Importer.readObjectSpecifications(False)

        self.graph_and_parents = {}
        self.build_and_parents(model)

        # determine entry points from which the graph expansion takes place
        #  -> possibly not necessary anymore once there is only one fully connected graph per generated model
        children = [item for items in self.graph.values() for item in items]
        eps = [key for key in self.graph if key not in np.unique(children)]

        self.graph_expanded = {}
        self.expand_graph(eps)
        self.key_indices = dict(zip(self.graph_expanded.keys(), [i for i in range(len(self.graph_expanded))]))
        self.rewards = self.build_rewards()
        self.update_vocabulary()

    graph_expanded: dict
    graph_and_parents: dict
    and_steps: list

    def expand_graph(self, current_nodes_to_expand):
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

        for node in current_nodes_to_expand:
            if node in self.graph_expanded.keys():
                continue
            self.graph_expanded[node] = []
            # sub_node = single node as found in original graph
            for sub_node in node.split("|"):
                # children of each sub_node
                for child in self.graph[sub_node]:
                    if child not in node and self.sort(node, child) not in self.graph_expanded[node]:
                        # child is an AND step
                        if child in self.graph_and_parents:
                            condition_fulfilled = True
                            for parent in self.graph_and_parents[child]:
                                condition_fulfilled = condition_fulfilled and parent in node
                            if condition_fulfilled:
                                self.graph_expanded[node].append(self.sort(node, child))
                        else:
                            self.graph_expanded[node].append(self.sort(node, child))
                self.expand_graph(self.graph_expanded[node])

    @staticmethod
    def sort(node: str, child: str):
        """
        Concatenates the node name and the child to a new, sorted node name
        """
        nodes = node.split("|")
        nodes.append(child)
        nodes.sort()
        return '|'.join(nodes)

    def build_and_parents(self, model):
        """
        Takes the model and builds a list of AND steps with their respective parents
        This dictionary is then used to check if the condition for reaching an AND step is fulfilled
        """
        for instance in model:
            for step in instance.attack_steps.values():
                s = f'{instance.type}.{instance.name}.{step.name}'
                if step.has_children():
                    if type(step.children) == dict:
                        for child in step.children.values():
                            if child.type != 'AND':
                                continue
                            ss = f'{child.instance.type}.{child.instance.name}.{child.name}'
                            if ss not in self.graph_and_parents:
                                self.graph_and_parents[ss] = [s]
                            elif s not in self.graph_and_parents[ss]:
                                self.graph_and_parents[ss].append(s)
                    else:
                        if step.children.type != 'AND':
                            continue
                        ss = f'{step.children.instance.type}.{step.children.instance.name}.{step.children.name}'
                        if ss not in self.graph_and_parents:
                            self.graph_and_parents[ss] = [s]
                        else:
                            self.graph_and_parents[ss].append(s)

    def concatenate_model_instances(self, model: dict):
        """
        Concatenates all steps of all instances in the generated model to one dictionary
        """
        for instance in model:
            for step in instance.attack_steps.values():
                s = f'{instance.type}.{instance.name}.{step.name}'
                if step.has_children():
                    self.graph[s] = []
                    if type(step.children) == dict:
                        for _, child in step.children.items():
                            self.graph[s].append(f'{child.instance.type}.'
                                                 f'{child.instance.name}.'
                                                 f'{child.name}')
                    else:
                        self.graph[s].append(f'{step.children.instance.type}.'
                                             f'{step.children.instance.name}.'
                                             f'{step.children.name}')
        # add all steps without children
        self.graph.update(
            {item: [] for items in self.graph.values() for item in items if item not in self.graph.keys()})

    def build_rewards(self):
        """
        Builds a reward matrix, each entry corresponding to a transition from step i to j
        """
        n = len(self.key_indices)
        rewards = np.ones((n, n)) * -999

        for key, steps in self.graph_expanded.items():
            for entry_expanded in steps:
                entry = self.get_entry(key, entry_expanded)
                reward = self.get_reward(entry)
                ttc = self.parse_distribution(entry)
                ttc = 1.0 if ttc == 0 else ttc
                v = reward - ttc
                rewards[self.key_indices[key], self.key_indices[entry_expanded]] = v

        return rewards

    def get_entry(self, parent, child):
        for c in child.split("|"):
            if c not in parent:
                return c

    def value_iteration(self, gamma=0.9, tolerance=1e-3):
        n = self.rewards.shape[0]
        V = np.zeros(n)
        Q = np.zeros((n, n))
        error = tolerance + 1
        i = 0
        while error > tolerance:
            Q = (self.rewards + gamma * V)
            new_V = np.max(Q, axis=1)
            error = np.max(np.abs(V - new_V))
            V = np.copy(new_V)
            i = i + 1
        return V, Q

    def get_reward(self, step) -> float:
        (assetID, assetName, stepName) = step.split(".")
        return self.object_dict[assetID][stepName]

    def get_step_distribution(self, entry: str) -> str:
        (asset, _, step) = entry.split(".")
        return self.ttc_dict[asset][step]

    def parse_distribution(self, entry: str):
        """
        Returns the distribution name and parameter if one is associated with the entry given
        """
        distribution = self.get_step_distribution(entry)
        if distribution == "":
            return 0
        ordinal_match = re.match("^.*(?!\d+).*$", distribution)
        distribution_name: str
        distribution_parameter: float
        if ordinal_match:
            return self.parse_ordinal(distribution)
        else:
            distribution_match = re.match("^(\w+)\((.+)\)$", distribution)
            return self.get_ttc([distribution_match.group(1), distribution_match.group(2)])

    def get_ttc(self, distribution):
        '''
        Samples from the given distribution
        '''
        distr, value = distribution
        if distr.lower() == "bernoulli":
            return bernoulli.ppf(q=value, p=random())
        else:
            return expon.ppf(q=0.95, scale=1 / value)

    def parse_ordinal(self, distribution: str):
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
            return math.inf if bernoulli.ppf(q=0.5, p=random()) == 0 else expon.ppf(q=0.95, scale=1 / 0.1)
        elif distribution == "VeryHardAndCertain":
            return expon.ppf(q=0.95, scale=1 / 0.01)
        elif distribution == "VeryHardAndUncertain":
            return math.inf if bernoulli.ppf(q=0.5, p=random()) == 0 else expon.ppf(q=0.95, scale=1 / 0.01)
        elif distribution == "Enabled":
            return 0
        elif distribution == "Disabled":
            return math.inf

    def update_vocabulary(self):
        with open("ValueApproximator/Resources/vocabulary.json", "r") as vocab:
            self.vocabulary = json.load(vocab)
        if len(self.vocabulary) == 0:
            counter = 0
        else:
            # continue the counting from the last entry in the vocabulary
            counter = list(self.vocabulary.items())[1][-1] + 1
        for key in self.graph_expanded.keys():
            if key not in self.vocabulary:
                self.vocabulary[key] = counter
                counter += 1
        with open("ValueApproximator/Resources/vocabulary.json", "w") as vocab:
            json.dump(self.vocabulary, vocab)
