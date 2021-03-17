import Helpers.Importer as Importer
from Graph.AttackGraph import AttackGraph as Graph
import math


def init():
    print(Importer.readSpecification())
    # graph = Graph(1, 2)
    # graph.buildGraph()


def getSuccessProbability(distribution: str, x: float) -> float:
    if distribution.lower() in "bernoulli":
        return x
    elif distribution.lower() in "exponential":
        return 1 / math.e
    else:
        return 0.0
