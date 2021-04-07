from Graph.AttackGraph import AttackGraph as Graph
import numpy as np

graph = Graph()

def init():
    np.set_printoptions(formatter={'float': lambda x: "{0:0.2f}".format(x)})
    V, Q = value_iteration(graph.rewards)
    print(V)
    print()
    print(Q)


def value_iteration(m, gamma=0.9, tolerance=1e-3):
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