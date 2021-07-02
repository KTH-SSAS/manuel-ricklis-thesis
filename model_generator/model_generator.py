import json
import inspect
from statistics import NormalDist


class Instance(object):
    def __init__(self, inst_type, inst_name):
        self.type = inst_type
        self.name = inst_name
        self.attack_steps = {}
        self.load_attack_steps()
        # Creating intra-asset attack step connection
        if self.type == "System":
            self.attack_steps["attemptGainFullAccess"].type = "AND"
            self.attack_steps["attemptGainFullAccess"].children = self.attack_steps["fullAccess"]
            self.attack_steps["specificAccess"].type = "AND"
            self.attack_steps["individualPrivilegeAuthenticate"].children = self.attack_steps["specificAccess"]
            self.attack_steps["physicalAccess"].children = self.attack_steps["attemptUsePhysicalVulnerability"]
            self.attack_steps["bypassAccessControl"].children = self.attack_steps["fullAccess"]

        elif self.type == "Application":
            # add arbitrary parent to two related steps to create a common entry point
            self.attack_steps["networkRespondConnect"].children["networkConnect"] = self.attack_steps["networkConnect"]
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
        self.model = list()
        return

    def generate_model_based_on_random_parameters(self, networks_mean, networks_sd, services_mean, services_sd,
                                                  data_mean, data_sd, id_data_mean, id_data_sd, service_id_mean,
                                                  service_id_sd):
        number_of_networks = int(NormalDist(mu=networks_mean, sigma=networks_sd).inv_cdf(0.95))
        number_of_services = int(NormalDist(mu=services_mean, sigma=services_sd).inv_cdf(0.95))
        number_of_data_per_service = int(NormalDist(mu=data_mean, sigma=data_sd).inv_cdf(0.95))
        number_of_data_per_identity = int(NormalDist(mu=id_data_mean, sigma=id_data_sd).inv_cdf(0.95))
        number_of_identities_per_service = int(NormalDist(mu=service_id_mean, sigma=service_id_sd).inv_cdf(0.95))

        return self.generate_model(number_of_networks, number_of_services, number_of_data_per_service,
                                   number_of_data_per_identity, number_of_identities_per_service)

    def generate_model(self, number_of_networks=2, number_of_services=5, number_of_data_per_service=2,
                       number_of_data_per_identity=2, number_of_identities_per_service=3):
        for networks in range(1, number_of_networks + 1):
            network = Instance("Network", "Network" + str(networks))
            self.model.append(network)
            for services in range(1, number_of_services + 1):
                service = Instance("Application", "Service" + str(services))
                self.model.append(service)
                vulnerability = Instance("ManualHighImpactVulnerability", "Vulnerability")
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
        instance_a.attack_steps["fullAccess"].children = instance_b.attack_steps["fullAccess"]
        instance_a.attack_steps["deny"].children["deny"] = instance_b.attack_steps["deny"]
        instance_a.attack_steps["specificAccess"].children = instance_b.attack_steps["localConnect"]

    def add_appExecution(self, instance_a, instance_b):
        if instance_a.type != "Application" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["fullAccess"].children = instance_b.attack_steps["fullAccess"]
        instance_a.attack_steps["deny"].children["deny"] = instance_b.attack_steps["deny"]
        instance_a.attack_steps["specificAccess"].children = instance_b.attack_steps["localConnect"]
        instance_b.attack_steps["fullAccess"].children = instance_a.attack_steps["localConnect"]

    def add_networkExposure(self, instance_a, instance_b):
        if instance_a.type != "Network" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        # Getting fullAccess and specificAccess (after authenticate) from Netwrok connect
        instance_a.attack_steps["successfulAccess"].children = instance_b.attack_steps["networkAccess"]
        instance_a.attack_steps["successfulAccess"].children = instance_b.attack_steps[
            "specificAccessFromNetworkConnection"]
        instance_a.attack_steps["successfulAccess"].children = instance_b.attack_steps["networkConnect"]
        instance_a.attack_steps["successfulAccess"].children = instance_b.attack_steps["networkRequestConnect"]
        instance_b.attack_steps["specificAccess"].children = instance_a.attack_steps["access"]
        instance_b.attack_steps["fullAccess"].children = instance_a.attack_steps["access"]

    def add_clientAccess(self, instance_a, instance_b):
        if instance_a.type != "Network" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["successfulAccess"].children = instance_b.attack_steps["networkRespondConnect"]
        instance_b.attack_steps["specificAccess"].children = instance_a.attack_steps["access"]
        instance_b.attack_steps["fullAccess"].children = instance_a.attack_steps["access"]

    def add_appDataContainment(self, instance_a, instance_b):
        if instance_a.type != "Data" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        # The 'attemptApplicationRespondConnectThroughData' is not included for simplicity
        instance_b.attack_steps["fullAccess"].children = instance_a.attack_steps["read"]
        instance_b.attack_steps["fullAccess"].children = instance_a.attack_steps["access"]
        instance_b.attack_steps["fullAccess"].children = instance_a.attack_steps["deny"]

    def add_highPrivilegeAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "System":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children = instance_b.attack_steps["attemptGainFullAccess"]
        instance_b.attack_steps["fullAccess"].children = instance_a.attack_steps["assume"]

    def add_lowPrivilegeAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "System":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children = instance_b.attack_steps["individualPrivilegeAuthenticate"]
        instance_b.attack_steps["fullAccess"].children = instance_a.attack_steps["assume"]

    def add_executionPrivilegeAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children = instance_b.attack_steps["authenticate"]
        instance_b.attack_steps["fullAccess"].children = instance_a.attack_steps["assume"]

    def add_highPrivilegeApplicationAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children = instance_b.attack_steps["authenticate"]
        instance_b.attack_steps["fullAccess"].children = instance_a.attack_steps["assume"]

    def add_lowPrivilegeApplicationAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children = instance_b.attack_steps["specificAccessAuthenticate"]
        instance_b.attack_steps["fullAccess"].children = instance_a.attack_steps["assume"]

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
        instance_a.attack_steps["assume"].children = instance_b.attack_steps["write"]

    def add_deletePrivileges(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Data":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children = instance_b.attack_steps["delete"]

    def add_physicalVulnerability(self, instance_a, instance_b):
        if instance_a.type != "System" or instance_b.type != "ManualHighImpactVulnerability":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["attemptUsePhysicalVulnerability"].children = instance_b.attack_steps["impact"]
        instance_b.attack_steps["impact"].children = instance_a.attack_steps["bypassAccessControl"]

    def add_applicationVulnerability(self, instance_a, instance_b):
        if instance_a.type != "Application" or instance_b.type != "ManualHighImpactVulnerability":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["attemptUseVulnerability"].children = instance_b.attack_steps["impact"]
        instance_b.attack_steps["impact"].children = instance_a.attack_steps["fullAccess"]

    def add_identityAdmin(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Identity":
            print("ERROR: Wrong instances specified on '" + str(
                inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps["assume"].children = instance_b.attack_steps["assume"]


def getModel():
    model_generator = ModelGenerator()
    model = model_generator.generate_model()
    return model
