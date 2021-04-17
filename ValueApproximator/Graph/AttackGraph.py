import ValueApproximator.Helpers.Importer as Importer
import Generator.modelGenerator as ModelGenerator
import numpy as np
import math
import re


class AttackGraph:
    # all attack steps in the system with their respective children
    model: dict

    # relevant (to the entry point(s)) attack steps with their respective children
    graph: dict

    # time to compromise distributions
    ttc_dict: dict

    # mal specification of assets and their steps
    object_dict: dict

    rewards: np.ndarray
    success_probabilities: np.ndarray
    effort = 10.0

    def __init__(self):
        self.graph = {}
        self.model = {}
        self.concatenate_model_instances(ModelGenerator.getModel())
        self.build_graph("Network.Network2.successfulAccess")
        self.object_dict, self.ttc_dict = Importer.readObjectSpecifications(False)
        self.rewards, self.success_probabilities = self.build_rewards_and_success_probabilities()


    def build_graph(self, step: str):
        """
        Recursively builds up an attack graph from a specific attack step
        """
        self.graph[step] = self.model[step]
        for child in self.model[step]:
            self.build_graph(child)

    def concatenate_model_instances(self, model: dict):
        """
        Concatenates all steps of all instances in the generated model to one dictionary
        """
        for instance in model:
            for _, step in instance.attack_steps.items():
                s = f'{instance.type}.{instance.name}.{step.name}'
                self.model[s] = []
                if step.has_children():
                    if type(step.children) == dict:
                        for _, child in step.children.items():
                            self.model[s].append(f'{child.instance.type}.'
                                                 f'{child.instance.name}.'
                                                 f'{child.name}')
                    else:
                        self.model[s].append(f'{step.children.instance.type}.'
                                             f'{step.children.instance.name}.'
                                             f'{step.children.name}')

    def build_rewards_and_success_probabilities(self):
        """
        Builds a rewards and success probability matrix, each entry corresponding to a transition from step i to j
        """
        key_indices = dict(zip(self.graph.keys(), [i for i in range(len(self.graph))]))
        n = len(key_indices)
        rewards = np.ones((n, n)) * -999
        success_probabilities = np.zeros((n, n))

        # for every item in the graph, their reward is calculated with the reward of reaching the step and the time
        # needed to compromise
        # for distributions, the maximum effort is assumed which gives a success probability that is later regarded
        # in the value iteration
        for key, steps in self.graph.items():
            if len(steps) == 0:
                # print(f'{self.getReward(key)} {key}')
                success_probabilities[key_indices[key], key_indices[key]] = 1.0
                rewards[key_indices[key], key_indices[key]] = self.get_reward(key) * 0.00001
            for entry in steps:
                reward = self.get_reward(entry)
                distribution = self.parse_distribution(entry)
                probability = self.get_success_probability(distribution)
                ttc = 1.0
                if distribution != '':
                    ttc = self.effort
                v = reward - ttc
                success_probabilities[key_indices[key], key_indices[entry]] = probability
                rewards[key_indices[key], key_indices[entry]] = v
        return rewards, success_probabilities

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
            return ["", 0.0]
        ordinal_match = re.match("^.*(?!\d+).*$", distribution)
        distribution_name: str
        distribution_parameter: float
        if ordinal_match:
            return self.parse_ordinal(distribution)
        else:
            distribution_match = re.match("^(\w+)\((.+)\)$", distribution)
            return [distribution_match.group(1), distribution_match.group(2)]

    def parse_ordinal(self, distribution: str):
        """
        Translates the ordinal description to the distributions used
        https://github.com/mal-lang/malcompiler/wiki/Supported-distribution-functions
        """
        if distribution == "EasyAndCertain":
            return ["Exponential", 1.0]
        elif distribution == "EasyAndUncertian":
            return ["Bernouilli", 0.5]
        elif distribution == "HardAndCertain":
            return ["Exponential", 0.1]
        elif distribution == "HardAndUncertain":
            success_probability_exponential = self.get_success_probability(["Exponential", 0.1])
            return ["Bernouilli", 0.5 * success_probability_exponential]
        elif distribution == "VeryHardAndCertain":
            return ["Exponential", 0.01]
        elif distribution == "VeryHardAndUncertain":
            success_probability_exponential = self.get_success_probability(["Exponential", 0.01])
            return ["Bernouilli", 0.5 * success_probability_exponential]
        elif distribution == "Enabled":
            return ["Bernouilli", 1.0]
        elif distribution == "Disabled":
            return ["Bernouilli", 0.0]
