import json


def export_attack_graphs(graphs: list, prefix: str):
    i = 0
    for graph in graphs:
        with open("ValueApproximator/AttackGraphs/" + prefix + "_" + str(i) + ".json", "w") as f:
            json.dump(graph, f)
        i += 1