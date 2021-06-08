import json
import re


def get_vocabularies():
    """
    Reads the vocabularies for attack step names, asset types and asset names
    Each entry is assigned an integer value
    """
    # load integer mappings
    with open("ValueApproximator/Resources/vocabularies.json", "r") as f:
        input_vocab = json.load(f)
    # build a vocabulary for each feature that will use embeddings
    vocabularies = {}
    for key in input_vocab.keys():
        vocabularies[key] = {k: i for i, k in enumerate(input_vocab[key])}

    return vocabularies


def readObjectSpecifications(from_mal=False) -> [dict, dict]:
    """
    Reads the relevant MAL files and builds a dictionary with each step as well as one with distributions assigned to steps
    """
    ttc_dict = {}
    objects_dict = {}

    if from_mal:
        for fileName in {"Resources/coreLang.mal",
                         "Resources/VulnerabilityAutomatic.mal",
                         "Resources/VulnerabilityManual.mal"}:
            with open(fileName) as file:
                searchIsActive = False
                activeAsset = ""

                for line in file.readlines():
                    if searchIsActive:
                        if "}" in line and "{" not in line:
                            searchIsActive = False
                            continue

                        stepMatch = re.match("^\s*[|#&]\s(\w+).*$", line)
                        if stepMatch:
                            objects_dict[activeAsset][stepMatch.group(1)] = 0

                            ttc_match = re.match("^\s+[|#&]\s(\w+)\s*(?:@hidden)?\s*\[(.*)\].*$", line)
                            if ttc_match:
                                ttc_dict[activeAsset][ttc_match.group(1)] = ttc_match.group(2)
                            else:
                                ttc_dict[activeAsset][stepMatch.group(1)] = ""

                    assetMatch = re.match("^.*asset\s(\w+)(\sextends\s(\w+))?\s*$", line)
                    if assetMatch and not searchIsActive:
                        searchIsActive = True
                        activeAsset = assetMatch.group(1)
                        objects_dict[activeAsset] = {}
                        ttc_dict[activeAsset] = {}

                        if assetMatch.group(3):
                            if assetMatch.group(3) not in ["Vulnerability", "Exploit"]:
                                objects_dict[activeAsset] = objects_dict[assetMatch.group(3)].copy()

        with open("ValueApproximator/Resources/objectSpecification_out.json", "w") as f:
            json.dump(objects_dict, f)
        with open("ValueApproximator/Resources/ttc_specification_out.json", "w") as f:
            json.dump(ttc_dict, f)
    else:
        with open("ValueApproximator/Resources/objectSpecification_out.json", "r") as f:
            objects_dict = json.load(f)
        with open("ValueApproximator/Resources/ttc_specification_out.json", "r") as f:
            ttc_dict = json.load(f)
    return [objects_dict, ttc_dict]
