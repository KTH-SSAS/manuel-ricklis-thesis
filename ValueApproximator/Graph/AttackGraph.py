import Helpers.Importer as Importer
import Generator.modelGenerator as ModelGenerator
import numpy as np
import math
import re
from scipy.stats import expon
from scipy.stats import bernoulli
from random import random


class AttackGraph:
    # all attack steps in the system with their respective children
    model: dict

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

    def __init__(self):
        self.graph = {}
        self.concatenate_model_instances(ModelGenerator.getModel())
        self.object_dict, self.ttc_dict = Importer.readObjectSpecifications(False)
        self.rewards = self.build_rewards()

    def concatenate_model_instances(self, model: dict):
        """
        Concatenates all steps of all instances in the generated model to one dictionary
        """
        i = 0
        for instance in model:
            for _, step in instance.attack_steps.items():
                s = f'{instance.type}.{instance.name}.{step.name}'
                self.graph[s] = []
                if step.has_children():
                    if type(step.children) == dict:
                        for _, child in step.children.items():
                            self.graph[s].append(f'{child.instance.type}.'
                                                 f'{child.instance.name}.'
                                                 f'{child.name}')
                    else:
                        self.graph[s].append(f'{step.children.instance.type}.'
                                             f'{step.children.instance.name}.'
                                             f'{step.children.name}')
        self.key_indices = dict(zip(self.graph.keys(), [i for i in range(len(self.graph))]))

    def build_rewards(self):
        """
        Builds a reward matrix, each entry corresponding to a transition from step i to j
        """
        n = len(self.key_indices)
        rewards = np.ones((n, n)) * -999

        for key, steps in self.graph.items():
            for entry in steps:
                reward = self.get_reward(entry)
                ttc = self.parse_distribution(entry)
                ttc = 1.0 if ttc == 0 else ttc
                v = reward - ttc
                rewards[self.key_indices[key], self.key_indices[entry]] = v

        return rewards

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

    def get_success_probability(self, distr: []) -> float:
        """
        Returns the success probability [0, 1] of the given distribution (bernouilli or exponential)
        taken the maximum effort of the attacker
        """
        distribution = distr[0]
        parameter = distr[1]
        if distribution.lower() == "bernoulli":
            return parameter
        elif distribution.lower() == "exponential":
            return 1 - (1.0 / math.pow(math.e, parameter * self.effort))
        else:
            return 1.0

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
