import json
import numpy as np
import torch

from ValueApproximator.Graph.AttackGraph import AttackGraph


class Exporter:
    def __init__(self):
        self.STEP_NAME = 0
        self.ASSET_TYPE = 1
        self.ASSET_NAME = 2
        self.REWARD = 3
        self.RANK = 4

        self.asset_names: dict
        self.asset_types: dict
        self.step_names: dict

    def parse_features(self, graph: AttackGraph, number_of_features):
        # load mappings
        with open("ValueApproximator/Resources/Mappings/asset_names.json", "r") as f:
            self.asset_names = json.load(f)
        with open("ValueApproximator/Resources/Mappings/asset_types.json", "r") as f:
            self.asset_types = json.load(f)
        with open("ValueApproximator/Resources/Mappings/step_names.json", "r") as f:
            self.step_names = json.load(f)

        # features: step name, asset type, asset name, reward, neighbourhood rank
        N = len(graph.graph)
        M = np.ones((N, number_of_features)) * (-999)
        adjacency_matrix = {}
        for step, children in graph.graph.items():
            asset_type, asset_name, step_name = step.split(".")

            M[graph.key_indices[step], self.STEP_NAME] = self.step_names[str(asset_type + "." + step_name)]
            M[graph.key_indices[step], self.ASSET_TYPE] = self.asset_types[asset_type]
            M[graph.key_indices[step], self.ASSET_NAME] = self.asset_names[
                "".join(i for i in asset_name if not i.isdigit())]
            M[graph.key_indices[step], self.REWARD] = graph.get_reward(step)
            M[graph.key_indices[step], self.RANK] = len(graph.graph[step])

            children_indx = []
            for child in children:
                children_indx.append(graph.key_indices[child])
            adjacency_matrix[graph.key_indices[step]] = children_indx
        return M, adjacency_matrix

    def parse_step_name(self, step: str):
        asset_type, asset_name, step_name = step.split(".")
        return self.asset_types[asset_type], self.asset_names[asset_name], self.step_names[step_name]
