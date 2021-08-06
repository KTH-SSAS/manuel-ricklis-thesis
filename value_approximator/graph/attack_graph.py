import numpy as np


class AttackGraph:
    """
    Class for the attack graphs that includes the minimal information to perform machine learning on it.
    """
    def __init__(self, graph_expanded, key_indices, rewards):
        self.graph_expanded = graph_expanded
        self.key_indices = key_indices
        self.rewards = rewards

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
