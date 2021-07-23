import json
import inspect
from statistics import NormalDist
from scipy.stats import norm
import random


class Instance(object):
    def __init__(self, inst_type, inst_name):
        self.type = inst_type
        self.name = inst_name
        self.attack_steps = {}
        self.load_attack_steps()
        # Creating intra-asset attack step connection
        if self.type == "System":
            self.attack_steps["attemptGainFullAccess"].type = "AND"
            self.attack_steps["attemptGainFullAccess"].children["fullAccess"] = self.attack_steps["fullAccess"]
            self.attack_steps["specificAccess"].type = "AND"
            self.attack_steps["individualPrivilegeAuthenticate"].children["specificAccess"] = \
                self.attack_steps["specificAccess"]
            self.attack_steps["physicalAccess"].children["attemptUsePhysicalVulnerability"] = \
                self.attack_steps["attemptUsePhysicalVulnerability"]
            self.attack_steps["bypassAccessControl"].children["fullAccess"] = self.attack_steps["fullAccess"]

        elif self.type == "Application":
            # add arbitrary parent to two related steps to create a common entry point
            self.attack_steps["networkRespondConnect"].children["networkConnect"] = \
                self.attack_steps["networkConnect"]
            self.attack_steps["networkRespondConnect"].children["networkRequestConnect"] = self.attack_steps[
                "networkRequestConnect"]

            self.attack_steps["localAccess"].type = "AND"
            self.attack_steps["networkAccess"].type = "AND"
            self.attack_steps["authenticate"].children["localAccess"] = self.attack_steps["localAccess"]
            self.attack_steps["authenticate"].children["networkAccess"] = self.attack_steps["networkAccess"]
            self.attack_steps["localAccess"].children["fullAccess"] = self.attack_steps["fullAccess"]
            self.attack_steps["networkAccess"].children["fullAccess"] = self.attack_steps["fullAccess"]
            self.attack_steps["localConnect"].children["localAccess"] = self.attack_steps["localAccess"]
            self.attack_steps["networkConnect"].children["networkAccess"] = self.attack_steps["networkAccess"]
            self.attack_steps["specificAccessFromLocalConnection"].type = "AND"
            self.attack_steps["specificAccessFromNetworkConnection"].type = "AND"
            self.attack_steps["specificAccessAuthenticate"].children["specificAccessFromLocalConnection"] = \
                self.attack_steps["specificAccessFromLocalConnection"]
            self.attack_steps["specificAccessAuthenticate"].children["specificAccessFromNetworkConnection"] = \
                self.attack_steps["specificAccessFromNetworkConnection"]
            self.attack_steps["specificAccessFromLocalConnection"].children["specificAccess"] = self.attack_steps[
                "specificAccess"]
            self.attack_steps["specificAccessFromNetworkConnection"].children["specificAccess"] = self.attack_steps[
                "specificAccess"]
            self.attack_steps["localAccess"].children["specificAccessFromLocalConnection"] = self.attack_steps[
                "specificAccessFromLocalConnection"]
            self.attack_steps["specificAccessAuthenticate"].children["specificAccessFromLocalConnection"] = \
                self.attack_steps["specificAccessFromLocalConnection"]
            self.attack_steps["networkConnect"].children["attemptUseVulnerability"] = self.attack_steps[
                "attemptUseVulnerability"]
            self.attack_steps["networkRequestConnect"].children["attemptUseVulnerability"] = self.attack_steps[
                "attemptUseVulnerability"]
            self.attack_steps["networkRespondConnect"].children["attemptUseVulnerability"] = self.attack_steps[
                "attemptUseVulnerability"]

    def __str__(self):
        return self.name

    def __hash__(self):
        return "Instance of type: %s, with name: %s" % (self.type, self.name)

    def __eq__(self, other):
        return self.name == other

    def load_attack_steps(self):
        with open('value_approximator/Resources/objectSpecification_out.json') as f:
            data = json.load(f)
        attack_step_list = data.get(self.type)
        if attack_step_list is not None:
            for key, value in attack_step_list.items():
                self.attack_steps[key] = AttackStep(name=key, reward_distribution=value, instance=self)
        else:
            print("Asset type not found for: " + self.type)

    def get_connected_attack_steps(self):
        conn_attack_steps = dict()
        for key, attack in self.attack_steps.items():
            if attack.has_children():
                parent_attack_step = self.attack_steps[attack.name]
                conn_attack_steps[parent_attack_step.name] = attack.children
        return conn_attack_steps


class AttackStep(object):
    def __init__(self, name, reward_distribution, attack_step_type="OR", ttc_distribution=None, children=None,
                 instance=None):
        self.name = name
        self.instance = instance
        self.ttc_distribution = ttc_distribution
        self.type = attack_step_type
        self.reward_distribution = reward_distribution
        self.children = {}

    def __eq__(self, other):
        return self.name == other

    def has_children(self):
        return bool(self.children)


class ModelGenerator(object):
    # Supported assets (so far): System, Application, Network, Data, Identity, Vulnerability
    def __init__(self):
        self.graph = {}
        self.graph_and_parents = {}
        self.model = list()

    def add_graph(self, graph):
        self.graph = graph

    def add_graph_and_parents(self, graph_and_parents):
        self.graph_and_parents = graph_and_parents

    def generate_model_based_on_random_parameters(self, networks_mean, networks_sd, services_mean, services_sd,
                                                  data_mean, data_sd, id_data_mean, id_data_sd, service_id_mean,
                                                  service_id_sd):
        number_of_networks = int(norm.ppf(random.randint(10, 95) / 100, loc=networks_mean, scale=networks_sd))
        number_of_services = int(norm.ppf(random.randint(10, 95) / 100, loc=services_mean, scale=services_sd))
        number_of_data_per_service = int(norm.ppf(random.randint(10, 95) / 100, loc=data_mean, scale=data_sd))
        number_of_data_per_identity = int(norm.ppf(random.randint(10, 95) / 100, loc=id_data_mean, scale=id_data_sd))
        number_of_identities_per_service = int(norm.ppf(random.randint(10, 95) / 100, loc=service_id_mean, scale=service_id_sd))

        return self.generate_model(number_of_networks, number_of_services, number_of_data_per_service,
                                   number_of_data_per_identity, number_of_identities_per_service)

    def generate_model(self, number_of_networks=2, number_of_services=5, number_of_data_per_service=2,
                       number_of_data_per_identity=2, number_of_identities_per_service=3):
        for networks in range(1, number_of_networks + 1):
            network = Instance("Network", "Network" + str(networks))
            self.model.append(network)
            IAMservice = Instance("Application", "IAM" + str(networks))
            self.model.append(IAMservice)
            vulnerability = Instance(
                "ManualHighImpactVulnerabilityHighComplexity" if random.random() > 0.5
                else "ManualHighImpactVulnerabilityLowComplexity",
                "Vulnerability")
            self.model.append(vulnerability)
            self.add_applicationVulnerability(IAMservice, vulnerability)
            self.add_networkExposure(network, IAMservice)
            for services in range(1, number_of_services + 1):
                service = Instance("Application", "Service" + str(services))
                self.model.append(service)
                vulnerability = Instance(
                    "ManualHighImpactVulnerabilityHighComplexity" if random.random() > 0.5
                    else "ManualHighImpactVulnerabilityLowComplexity",
                    "Vulnerability")
                self.model.append(vulnerability)
                self.add_applicationVulnerability(service, vulnerability)
                if services in range(1, int((number_of_services + 1) / 2)):
                    self.add_networkExposure(network, service)
                else:
                    self.add_clientAccess(network, service)
                for data in range(1, number_of_data_per_service + 1):
                    data = Instance("Data", "Data" + str(data))
                    self.model.append(data)
                    self.add_appDataContainment(data, service)
                admin_identity = Instance("Identity", "AdminId" + str(services))
                # Add the admin identity as an executor of the IAM service
                self.add_executionPrivilegeAccess(admin_identity, IAMservice)
                identity0 = Instance("Identity", "Executor" + str(services))
                self.model.append(admin_identity)
                self.model.append(identity0)
                self.add_executionPrivilegeAccess(identity0, service)
                self.add_identityAdmin(admin_identity, identity0)
                for ids in range(1, number_of_identities_per_service + 1):
                    identity = Instance("Identity", "HighPrivId" + str(ids) + "Srvc" + str(service))
                    self.model.append(identity)
                    self.add_highPrivilegeApplicationAccess(identity, service)
                    self.add_identityAdmin(admin_identity, identity)
                    identity2 = Instance("Identity", "LowPrivId" + str(ids) + "Srvc" + str(service))
                    self.model.append(identity2)
                    self.add_lowPrivilegeApplicationAccess(identity, service)
                    self.add_identityAdmin(admin_identity, identity2)
                    for data in range(1, number_of_data_per_identity + 1):
                        data = Instance("Data", "IdData" + str(data))
                        self.model.append(data)
                        self.add_readPrivileges(identity2, data)
                        data2 = Instance("Data", "IdData" + str(data))
                        self.model.append(data2)
                        self.add_writePrivileges(identity, data2)
        return self.model

    def add_sysExecution(self, instance_a, instance_b):
        if instance_a.type != "System" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        # FullAccess on Applications after fullAccess on System
        instance_a.attack_steps["fullAccess"].children["fullAccess"] = instance_b.attack_steps["fullAccess"]
        instance_a.attack_steps["deny"].children["deny"] = instance_b.attack_steps["deny"]
        instance_a.attack_steps["specificAccess"].children["localConnect"] = instance_b.attack_steps["localConnect"]

    def add_appExecution(self, instance_a, instance_b):
        if instance_a.type != "Application" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["fullAccess"].children["fullAccess"] = instance_b.attack_steps["fullAccess"]
        instance_a.attack_steps["deny"].children["deny"] = instance_b.attack_steps["deny"]
        instance_a.attack_steps["specificAccess"].children["localConnect"] = instance_b.attack_steps["localConnect"]
        instance_b.attack_steps["fullAccess"].children["localConnect"] = instance_a.attack_steps["localConnect"]

    def add_networkExposure(self, instance_a, instance_b):
        if instance_a.type != "Network" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        # Getting fullAccess and specificAccess (after authenticate) from Netwrok connect
        instance_a.attack_steps["successfulAccess"].children["networkAccess"] = instance_b.attack_steps["networkAccess"]
        instance_a.attack_steps["successfulAccess"].children["specificAccessFromNetworkConnection"] = \
            instance_b.attack_steps[
                "specificAccessFromNetworkConnection"]
        instance_a.attack_steps["successfulAccess"].children["networkConnect"] = instance_b.attack_steps[
            "networkConnect"]
        instance_a.attack_steps["successfulAccess"].children["networkRequestConnect"] = instance_b.attack_steps[
            "networkRequestConnect"]
        instance_b.attack_steps["specificAccess"].children["access"] = instance_a.attack_steps["access"]
        instance_b.attack_steps["fullAccess"].children["access"] = instance_a.attack_steps["access"]

    def add_clientAccess(self, instance_a, instance_b):
        if instance_a.type != "Network" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["successfulAccess"].children["networkRespondConnect"] = instance_b.attack_steps[
            "networkRespondConnect"]
        instance_b.attack_steps["specificAccess"].children["access"] = instance_a.attack_steps["access"]
        instance_b.attack_steps["fullAccess"].children["access"] = instance_a.attack_steps["access"]

    def add_appDataContainment(self, instance_a, instance_b):
        if instance_a.type != "Data" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        # The 'attemptApplicationRespondConnectThroughData' is not included for simplicity
        instance_b.attack_steps["fullAccess"].children["read"] = instance_a.attack_steps["read"]
        instance_b.attack_steps["fullAccess"].children["access"] = instance_a.attack_steps["access"]
        instance_b.attack_steps["fullAccess"].children["deny"] = instance_a.attack_steps["deny"]

    def add_highPrivilegeAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "System":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children["attemptGainFullAccess"] = instance_b.attack_steps[
            "attemptGainFullAccess"]
        instance_b.attack_steps["fullAccess"].children["assume"] = instance_a.attack_steps["assume"]

    def add_lowPrivilegeAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "System":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children["individualPrivilegeAuthenticate"] = instance_b.attack_steps[
            "individualPrivilegeAuthenticate"]
        instance_b.attack_steps["fullAccess"].children["assume"] = instance_a.attack_steps["assume"]

    def add_executionPrivilegeAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children["authenticate"] = instance_b.attack_steps["authenticate"]
        instance_b.attack_steps["fullAccess"].children["assume"] = instance_a.attack_steps["assume"]

    def add_highPrivilegeApplicationAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children["authenticate"] = instance_b.attack_steps["authenticate"]
        instance_b.attack_steps["fullAccess"].children["assume"] = instance_a.attack_steps["assume"]

    def add_lowPrivilegeApplicationAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children["specificAccessAuthenticate"] = instance_b.attack_steps[
            "specificAccessAuthenticate"]
        instance_b.attack_steps["fullAccess"].children["assume"] = instance_a.attack_steps["assume"]

    def add_readPrivileges(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Data":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children["read"] = instance_b.attack_steps["read"]

    def add_writePrivileges(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Data":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children["write"] = instance_b.attack_steps["write"]

    def add_deletePrivileges(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Data":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children["delete"] = instance_b.attack_steps["delete"]

    def add_physicalVulnerability(self, instance_a, instance_b):
        if instance_a.type != "System" or instance_b.type != "ManualHighImpactVulnerability":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["attemptUsePhysicalVulnerability"].children["impact"] = instance_b.attack_steps[
            "impact"]
        instance_b.attack_steps["impact"].children["bypassAccessControl"] = instance_a.attack_steps[
            "bypassAccessControl"]

    def add_applicationVulnerability(self, instance_a, instance_b):
        if instance_a.type != "Application" or "ManualHighImpactVulnerability" not in instance_b.type:
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["attemptUseVulnerability"].children["impact"] = instance_b.attack_steps["impact"]
        instance_b.attack_steps["impact"].children["fullAccess"] = instance_a.attack_steps["fullAccess"]

    def add_identityAdmin(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Identity":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children["assume"] = instance_b.attack_steps["assume"]


def getModel():
    model_generator = ModelGenerator()
    model = model_generator.generate_model()
    return model
