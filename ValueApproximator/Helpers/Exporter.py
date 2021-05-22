import json


def get_vocabularies():
    # load integer mappings
    with open("ValueApproximator/Resources/Mappings/vocabularies.json", "r") as f:
        input_vocab = json.load(f)
    # build a vocabulary for each feature that will use embeddings
    vocabularies = {}
    for key in input_vocab.keys():
        vocabularies[key] = {k: i for i, k in enumerate(input_vocab[key])}

    return vocabularies
