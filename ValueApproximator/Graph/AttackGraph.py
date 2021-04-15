import Helpers.Importer as Importer
import Graph.GraphGenerator as GraphGenerator
import numpy as np
import math


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
        self.ttc_dict = Importer.readTTCSpecifications()
        self.object_dict = Importer.readObjectSpecifications()
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
        for key, step in self.graph.items():
            if len(step) == 0:
                # print(f'{self.getReward(key)} {key}')
                success_probabilities[key_indices[key], key_indices[key]] = 1.0
                rewards[key_indices[key], key_indices[key]] = self.getReward(key) * 0.00001
            for entry in step:
                reward = self.getReward(entry)
                distribution = self.getStepDistribution(entry)
                probability = self.getSuccessProbability(distribution)
                ttc = 1.0
                if distribution[0] != '':
                    ttc = self.effort
                v = reward - ttc
                success_probabilities[key_indices[key], key_indices[entry]] = probability
                rewards[key_indices[key], key_indices[entry]] = v
        return rewards, success_probabilities

    def getStepDistribution(self, step: str) -> []:
        (assetID, assetName, stepName) = step.split(".")
        if assetID not in self.ttc_dict:
            return ['', 0]
        return self.ttc_dict[assetID][stepName]

    def getReward(self, step) -> float:
        (assetID, assetName, stepName) = step.split(".")
        return self.object_dict[assetID][stepName]

    def getSuccessProbability(self, distr: []) -> float:
        distribution = distr[0]
        parameter = distr[1]
        if distribution.lower() == "bernoulli":
            return parameter
        elif distribution.lower() == "exponential":
            return 1 - (1.0 / math.pow(math.e, parameter * self.effort))
        else:
            return 1.0
