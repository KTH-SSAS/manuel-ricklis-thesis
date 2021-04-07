import json
import re
from typing import Dict, List

def readTTCSpecifications() -> dict:
    ttc_dict = {}

    for fileName in {"Resources/VulnerabilityAutomatic.mal",
                     "Resources/VulnerabilityManual.mal"}:
        with open(fileName) as file:
            searchIsActive = False
            activeName = ""

            for line in file.readlines():
                if searchIsActive:
                    if "}" in line:
                        searchIsActive = False
                        continue

                    noDistrMatch = re.match("^\s*[|#&]\s(\w+)(\s@hidden)?\n$", line)
                    if noDistrMatch:
                        ttc_dict[activeName][noDistrMatch.group(1)] = ["", 0]
                        continue

                    ttcMatch = re.match("^\s*[|#&]\s(\w+).*\[(\w+)\((.*)\)\].*$", line)
                    if ttcMatch:
                        ttc_dict[activeName][ttcMatch.group(1)] = [ttcMatch.group(2), float(ttcMatch.group(3))]
                        continue

                nameMatch = re.match("^.*asset\s(\w+).*$", line)
                if nameMatch and not searchIsActive:
                    searchIsActive = True
                    activeName = nameMatch.group(1)
                    ttc_dict[activeName] = {}

    return ttc_dict


def readObjectSpecifications(fromMal=False) -> dict:
    objects_dict = {}
    if fromMal:
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
                            continue

                    assetMatch = re.match("^.*asset\s(\w+)(\sextends\s(\w+))?.*$", line)
                    if assetMatch and not searchIsActive:
                        searchIsActive = True
                        activeAsset = assetMatch.group(1)
                        objects_dict[activeAsset] = {}

                        if assetMatch.group(3):
                            if assetMatch.group(3) not in ["Vulnerability", "Exploit"]:
                                objects_dict[activeAsset] = objects_dict[assetMatch.group(3)].copy()

        # with open("Resources/objectSpecification_out.json", "w") as f:
        #     json.dump(objects_dict, f)
    else:
        with open("Resources/objectSpecification.json", "r") as f:
            objects_dict = json.load(f)

    return objects_dict


def getExampleGraph():
    graph_dict: Dict[str, List[str]] = {
        "Identity.Identity.attemptAssume": ["Identity.Identity.successfulAssume"],
        "Identity.Identity.successfulAssume": ["System.System.fullAccess"],
        "System.System.fullAccess": ["Network.LAN1.successfulAccess", "Network.LAN2.successfulAccess"],
        "Network.LAN1.successfulAccess": ["AutomaticLowComplexityVulnerability.AppVulnerability.abuse"],
        "Network.LAN2.successfulAccess": ["AutomaticHighComplexityVulnerability.AppVulnerability.abuse"],
        "AutomaticLowComplexityVulnerability.AppVulnerability.abuse": ["Application.App.denyAfterSoftProdVulnerability"],
        "AutomaticHighComplexityVulnerability.AppVulnerability.abuse": ["Application.App.readAfterSoftProdVulnerability"],
        "Application.App.denyAfterSoftProdVulnerability": ["Data.Data.deny"],
        "Application.App.readAfterSoftProdVulnerability": ["Data.Data.read"],
        "Data.Data.deny": [],
        "Data.Data.read": []
    }

    return graph_dict
