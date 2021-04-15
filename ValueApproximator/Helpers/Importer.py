import json
import re
from typing import Dict, List


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
        file.close()

        with open("Resources/objectSpecification_out.json", "w") as f:
            json.dump(objects_dict, f)
            f.close()
        with open("Resources/ttc_specification_out.json", "w") as f:
            json.dump(ttc_dict, f)
            f.close()
    else:
        with open("Resources/objectSpecification_out.json", "r") as f:
            objects_dict = json.load(f)
            f.close()
        with open("Resources/ttc_specification_out.json", "r") as f:
            ttc_dict = json.load(f)
            f.close()
    return [objects_dict, ttc_dict]


def getExampleGraph():
    graph_dict: Dict[str, List[str]] = {
        "Identity.Identity.attemptAssume": ["Identity.Identity.successfulAssume"],
        "Identity.Identity.successfulAssume": ["System.System.fullAccess"],
        "System.System.fullAccess": ["Network.LAN1.successfulAccess", "Network.LAN2.successfulAccess"],
        "Network.LAN1.successfulAccess": ["AutomaticLowComplexityVulnerability.AppVulnerability.abuse"],
        "Network.LAN2.successfulAccess": ["AutomaticHighComplexityVulnerability.AppVulnerability.abuse"],
        "AutomaticLowComplexityVulnerability.AppVulnerability.abuse": [
            "Application.App.denyAfterSoftProdVulnerability"],
        "AutomaticHighComplexityVulnerability.AppVulnerability.abuse": [
            "Application.App.readAfterSoftProdVulnerability"],
        "Application.App.denyAfterSoftProdVulnerability": ["Data.Data.deny"],
        "Application.App.readAfterSoftProdVulnerability": ["Data.Data.read"],
        "Data.Data.deny": [],
        "Data.Data.read": []
    }

    return graph_dict
