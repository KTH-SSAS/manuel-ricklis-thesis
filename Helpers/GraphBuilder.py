from ValueApproximator.Graph.AttackGraph import AttackGraph as Graph
import numpy as np


def init():
    np.set_printoptions(formatter={'float': lambda x: "{0:0.2f}".format(x)})
    graph = Graph()
    V, Q = value_iteration(graph.rewards)
    i = 0
    for key in graph.graph.keys():
        if V[i] > 0:
            print(f'{graph.key_indices[key]}   {key} - {V[i]}')
        i = i + 1


def value_iteration(graph, m, gamma=0.9, tolerance=1e-3):
    n = m.shape[0]
    V = np.zeros(n)
    Q = np.zeros((n, n))
    error = tolerance + 1
    i = 0
    while error > tolerance:
        Q = (m + gamma * V) * graph.success_probabilities
        new_V = np.max(Q, axis=1)
        error = np.max(np.abs(V - new_V))
        V = np.copy(new_V)
        i = i + 1
    return V, Q
