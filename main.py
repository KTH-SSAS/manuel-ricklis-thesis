from helpers.graph_utils import create_and_export_attack_graphs_for_learning
from value_approximator.gat import train
from value_approximator.gat.test import test_predictions

if __name__ == '__main__':

    #####################################################################

    params = {
        "networks": 2,
        "services": 2,
        "data": 3,
        "id_data": 2,
        "service_id": 2
    }
    create_and_export_attack_graphs_for_learning("test", params)

    #####################################################################

    train.train_gat(config=train.get_training_args())

    test_predictions("models/binaries/gat_NonRandomAttackGraphs_2_000000.pth", "cpu")
