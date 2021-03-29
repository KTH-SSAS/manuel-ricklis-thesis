import Helpers.Importer as Importer
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
        self.graph = Importer.getExampleGraph()
        self.ttc_dict = Importer.readTTCSpecifications()
        self.object_dict = Importer.readObjectSpecifications()
        self.rewards, self.success_probabilities = self.build_rewards_and_success_probabilities()

    def build_rewards_and_success_probabilities(self):
        key_indices = dict(zip(self.graph.keys(), [i for i in range(len(self.graph))]))
        n = len(key_indices)
        rewards = np.ones((n, n)) * -999
        success_probabilities = np.zeros((n, n))

        # for every item in the graph, their reward is calculated with the reward of reaching the step and the time
        # needed to compromise
        # for distributions, the maximum effort is assumed which gives a success probability that is later regarder
        # in the value iteration
        for item in self.graph.items():
            for entry in item[1]:
                reward = self.getReward(entry)
                distribution = self.getStepDistribution(entry)
                probability = self.getSuccessProbability(distribution)
                ttc = 1.0
                if distribution[0] != '':
                    ttc = self.effort
                v = reward - ttc
                success_probabilities[key_indices[item[0]], key_indices[entry]] = probability
                rewards[key_indices[item[0]], key_indices[entry]] = v
        return rewards, success_probabilities

    def getStepDistribution(self, step: str) -> []:
        (assetID, assetName, stepName) = step.split(".")
        if assetID not in self.ttc_dict:
            return ['', 0]
        return self.ttc_dict[assetID][stepName]

    def getReward(self, step: str) -> float:
        s = step.split(".")
        return self.object_dict[s[0]][s[2]]

    def getSuccessProbability(self, distr: []) -> float:
        distribution = distr[0]
        parameter = distr[1]
        if distribution.lower() == "bernoulli":
            return parameter
        elif distribution.lower() == "exponential":
            return 1 - (1.0 / math.pow(math.e, parameter * self.effort))
        else:
            return 1.0
