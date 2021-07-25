from helpers.graph_utils import create_and_export_attack_graphs_for_learning

if __name__ == '__main__':
    params = {
        "networks_mean": 2,
        "networks_sd": 1,
        "services_mean": 1,
        "services_sd": 1,
        "data_mean": 1,
        "data_sd": 1,
        "id_data_mean": 2,
        "id_data_sd": 1,
        "service_id_mean": 2,
        "service_id_sd": 1
    }
    create_and_export_attack_graphs_for_learning("train", 1, params)
