import Helpers.Importer as Importer
import Graph.GraphGenerator as GraphGenerator
import numpy as np
import math
import regex as re


class AttackGraph:
    # nodes with their respective children
    graph: dict

    # time to compromise distributions
    ttc_dict: dict

    # mal specification of assets and their steps
    object_dict: dict

    rewards: np.ndarray
    success_probabilities: np.ndarray
    effort = 10.0

    def __init__(self):
        # self.graph = Importer.getExampleGraph()
        model = GraphGenerator.getModel()
        self.graph = self.build_transition_matrix_from_instances(model)
        self.object_dict, self.ttc_dict = Importer.readObjectSpecifications(False)
        self.rewards, self.success_probabilities = self.build_rewards_and_success_probabilities()

    def build_transition_matrix_from_instances(self, model: dict):
        graph = {}
        for instance in model:
            for _, step in instance.attack_steps.items():
                s = f'{instance.type}.{instance.name}.{step.name}'
                graph[s] = []
                if step.has_children():
                    if type(step.children) == dict:
                        for _, child in step.children.items():
                            graph[s].append(f'{child.instance.type}.{child.instance.name}.{child.name}')
                    else:
                        graph[s].append(f'{step.children.instance.type}.'
                                        f'{step.children.instance.name}.'
                                        f'{step.children.name}')
        return graph

    def build_rewards_and_success_probabilities(self):
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
                rewards[key_indices[key], key_indices[key]] = self.getReward(key) * 0.00001
            for entry in steps:
                reward = self.getReward(entry)
                distribution = self.parse_distribution(entry)
                probability = self.getSuccessProbability(distribution)
                ttc = 1.0
                if distribution != '':
                    ttc = self.effort
                v = reward - ttc
                success_probabilities[key_indices[key], key_indices[entry]] = probability
                rewards[key_indices[key], key_indices[entry]] = v
        return rewards, success_probabilities

    def getReward(self, step) -> float:
        (assetID, assetName, stepName) = step.split(".")
        return self.object_dict[assetID][stepName]

    def getStepDistribution(self, entry: str) -> str:
        (asset, _, step) = entry.split(".")
        return self.ttc_dict[asset][step]

    def getSuccessProbability(self, distr: []) -> float:
        distribution = distr[0]
        parameter = distr[1]
        if distribution.lower() == "bernoulli":
            return parameter
        elif distribution.lower() == "exponential":
            return 1 - (1.0 / math.pow(math.e, parameter * self.effort))
        else:
            return 1.0

    def parse_distribution(self, entry: str):
        distribution = self.getStepDistribution(entry)
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
        if distribution == "EasyAndCertain":
            return ["Exponential", 1.0]
        elif distribution == "EasyAndUncertian":
            return ["Bernouilli", 0.5]
        elif distribution == "HardAndCertain":
            return ["Exponential", 0.1]
        elif distribution == "HardAndUncertain":
            success_probability_exponential = self.getSuccessProbability(["Exponential", 0.1])
            return ["Bernouilli", 0.5 * success_probability_exponential]
        elif distribution == "VeryHardAndCertain":
            return ["Exponential", 0.01]
        elif distribution == "VeryHardAndUncertain":
            success_probability_exponential = self.getSuccessProbability(["Exponential", 0.01])
            return ["Bernouilli", 0.5 * success_probability_exponential]
        elif distribution == "Enabled":
            return ["Bernouilli", 1.0]
        elif distribution == "Disabled":
            return ["Bernouilli", 0.0]
