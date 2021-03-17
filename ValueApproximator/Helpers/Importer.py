import json
import re


def readJSON() -> dict:
    with open("../Generator/GraphFiles/1615897177789-test.json") as f:
        graphData = json.load(f)
    return graphData


def readSpecification() -> dict:
    ttc_dict = {}
    with open("../Generator/src/main/mal/VulnerabilityAutomatic.mal") as vulnAutomatic:
        searchIsActive = False
        activeName = ""

        for line in vulnAutomatic.readlines():
            if searchIsActive:
                if "}" in line:
                    searchIsActive = False
                    continue
                ttcMatch = re.match("^\s*[|#&]\s(\w+).*\[(\w+)\((.*)\)\].*$", line)
                if ttcMatch:
                    ttc_dict[activeName] = [ttcMatch.group(1), ttcMatch.group(2), float(ttcMatch.group(3))]
                    continue

            nameMatch = re.match("^.*asset\s(\w+).*$", line)
            if nameMatch:
                searchIsActive = True
                activeName = nameMatch.group(1)
                ttc_dict[activeName] = ["", "", 0]

    return ttc_dict
