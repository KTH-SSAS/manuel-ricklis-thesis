import json, inspect

class Instance (object):
    def __init__(self, inst_type, inst_name):
        self.type = inst_type
        self.name = inst_name
        self.attack_steps = []
        self.load_attack_steps()
        # Creating intra-asset attack step connections
        if self.type == "System":
            self.attack_steps[self.attack_steps.index("attemptGainFullAccess")].type = "AND"
            self.attack_steps[self.attack_steps.index("attemptGainFullAccess")].children.append(self.name + "." + "fullAccess")
            self.attack_steps[self.attack_steps.index("specificAccess")].type = "AND"
            self.attack_steps[self.attack_steps.index("individualPrivilegeAuthenticate")].children.append(self.name + "." + "specificAccess")
            self.attack_steps[self.attack_steps.index("physicalAccess")].children.append(self.name + "." + "attemptUsePhysicalVulnerability")
            self.attack_steps[self.attack_steps.index("bypassAccessControl")].children.append(self.name + "." + "fullAccess")

        elif self.type == "Application":
            self.attack_steps[self.attack_steps.index("localAccess")].type = "AND"
            self.attack_steps[self.attack_steps.index("networkAccess")].type = "AND"
            self.attack_steps[self.attack_steps.index("authenticate")].children.append(self.name + "." + "localAccess")
            self.attack_steps[self.attack_steps.index("authenticate")].children.append(self.name + "." + "networkAccess")
            self.attack_steps[self.attack_steps.index("localAccess")].children.append(self.name + "." + "fullAccess")
            self.attack_steps[self.attack_steps.index("networkAccess")].children.append(self.name + "." + "fullAccess")
            self.attack_steps[self.attack_steps.index("localConnect")].children.append(self.name + "." + "localAccess")
            self.attack_steps[self.attack_steps.index("networkConnect")].children.append(self.name + "." + "networkAccess")
            self.attack_steps[self.attack_steps.index("specificAccessFromLocalConnection")].type = "AND"
            self.attack_steps[self.attack_steps.index("specificAccessFromNetworkConnection")].type = "AND"
            self.attack_steps[self.attack_steps.index("specificAccessAuthenticate")].children.append(self.name + "." + "specificAccessFromLocalConnection")
            self.attack_steps[self.attack_steps.index("specificAccessAuthenticate")].children.append(self.name + "." + "specificAccessFromNetworkConnection")
            self.attack_steps[self.attack_steps.index("specificAccessFromLocalConnection")].children.append(self.name + "." + "specificAccess")
            self.attack_steps[self.attack_steps.index("specificAccessFromNetworkConnection")].children.append(self.name + "." + "specificAccess")
            self.attack_steps[self.attack_steps.index("localAccess")].children.append(self.name + "." + "specificAccessFromLocalConnection")
            self.attack_steps[self.attack_steps.index("specificAccessAuthenticate")].children.append(self.name + "." + "specificAccessFromLocalConnection")
            self.attack_steps[self.attack_steps.index("networkConnect")].children.append(self.name + "." + "attemptUseVulnerability")
            self.attack_steps[self.attack_steps.index("networkRequestConnect")].children.append(self.name + "." + "attemptUseVulnerability")
            self.attack_steps[self.attack_steps.index("networkRespondConnect")].children.append(self.name + "." + "attemptUseVulnerability")

    def __str__(self):
        return self.name
    
    def __hash__(self):
        return "Instance of type: %s, with name: %s" % (self.type, self.name)

    def __eq__(self, other):
        return self.name == other

    def load_attack_steps(self):
        with open('objectSpecification.json') as f:
            data = json.load(f)
        attack_step_list = data.get(self.type)
        if attack_step_list is not None:
            for key, value in attack_step_list.items():
                self.attack_steps.append(AttackStep(name=key,reward_distribution=value))
        else:
            print("Asset type not found for: " + self.type)

    def get_connected_attack_steps(self):
        conn_attack_steps = dict()
        for attack in self.attack_steps:
            if attack.has_children():
                parent_attack_step = self.name + "." + attack.name
                conn_attack_steps[parent_attack_step] = attack.children
        return conn_attack_steps

class AttackStep (object):
    def __init__(self, name, reward_distribution, attack_step_type="OR", ttc_distribution=None, children=None):
        self.name = name
        self.ttc_distribution = ttc_distribution
        self.type = attack_step_type
        self.reward_distribution = reward_distribution
        self.children = []

    def __eq__(self, other):
        return self.name == other

    def has_children(self):
        return bool(self.children)

class ModelGenerator (object):
    # Supported assets (so far): System, Application, Network, Data, Identity, Vulnerability
    def __init__(self):
        self.model = list()
        return

    def generate_model(self, number_of_networks=2, number_of_services=5, number_of_data_per_service=2, number_of_data_per_identity=2, number_of_identities_per_service=3):
        print("Running...")

        for networks in range(1,number_of_networks+1):
            network = Instance("Network", "Network" + str(networks))
            self.model.append(network)
            for services in range(1,number_of_services+1):
                service = Instance("Application", "Service" + str(services))
                self.model.append(service)
                vulnerability = Instance("ManualHighImpactVulnerability", "Vulnerability")
                self.model.append(vulnerability)
                self.add_applicationVulnerability(service,vulnerability)
                if services in range (1, int((number_of_services+1)/2)):
                    self.add_networkExposure(network,service)
                else:
                    self.add_clientAccess(network,service)
                for data in range(1,number_of_data_per_service+1):
                    data = Instance("Data","Data" + str(data))
                    self.model.append(data)
                    self.add_appDataContainment(data,service)
                admin_identity = Instance("Identity", "AdminId" + str(services))
                identity0 = Instance("Identity", "Executor" + str(services))
                self.model.append(admin_identity)
                self.model.append(identity0)
                self.add_executionPrivilegeAccess(identity0, service)
                self.add_identityAdmin(admin_identity,identity0)
                for ids in range(1,number_of_identities_per_service+1):
                    identity = Instance("Identity", "HighPrivId" + str(ids) + "Srvc" + str(service))
                    self.model.append(identity)
                    self.add_highPrivilegeApplicationAccess(identity,service)
                    self.add_identityAdmin(admin_identity,identity)
                    identity2 = Instance("Identity", "LowPrivId" + str(ids) + "Srvc" + str(service))
                    self.model.append(identity2)
                    self.add_lowPrivilegeApplicationAccess(identity,service)
                    self.add_identityAdmin(admin_identity,identity2)
                    for data in range(1,number_of_data_per_identity+1):
                        data = Instance("Data","IdData" + str(data))
                        self.model.append(data)
                        self.add_readPrivileges(identity2,data)
                        data2 = Instance("Data","IdData" + str(data))
                        self.model.append(data2)
                        self.add_writePrivileges(identity,data2)

        # print(instance1.get_connected_attack_steps())
        
        print("Finished.")
        return self.model

    def add_sysExecution(self, instance_a, instance_b):
        if instance_a.type != "System" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        # FullAccess on Applications after fullAccess on System
        instance_a.attack_steps[instance_a.attack_steps.index("fullAccess")].children.append(instance_b.name + "." + "fullAccess")
        instance_a.attack_steps[instance_a.attack_steps.index("deny")].children.append(instance_b.name + "." + "deny")
        instance_a.attack_steps[instance_a.attack_steps.index("specificAccess")].children.append(instance_b.name + "." + "localConnect")

    def add_appExecution(self, instance_a, instance_b):
        if instance_a.type != "Application" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("fullAccess")].children.append(instance_b.name + "." + "fullAccess")
        instance_a.attack_steps[instance_a.attack_steps.index("deny")].children.append(instance_b.name + "." + "deny")
        instance_a.attack_steps[instance_a.attack_steps.index("specificAccess")].children.append(instance_b.name + "." + "localConnect")
        instance_b.attack_steps[instance_b.attack_steps.index("fullAccess")].children.append(instance_a.name + "." + "localConnect")

    def add_networkExposure(self, instance_a, instance_b):
        if instance_a.type != "Network" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        # Getting fullAccess and specificAccess (after authenticate) from Netwrok connect
        instance_a.attack_steps[instance_a.attack_steps.index("successfulAccess")].children.append(instance_b.name + "." + "networkAccess")
        instance_a.attack_steps[instance_a.attack_steps.index("successfulAccess")].children.append(instance_b.name + "." + "specificAccessFromNetworkConnection")
        instance_a.attack_steps[instance_a.attack_steps.index("successfulAccess")].children.append(instance_b.name + "." + "networkConnect")
        instance_a.attack_steps[instance_a.attack_steps.index("successfulAccess")].children.append(instance_b.name + "." + "networkRequestConnect")
        instance_b.attack_steps[instance_b.attack_steps.index("specificAccess")].children.append(instance_a.name + "." + "access")
        instance_b.attack_steps[instance_b.attack_steps.index("fullAccess")].children.append(instance_a.name + "." + "access")

    def add_clientAccess(self, instance_a, instance_b):
        if instance_a.type != "Network" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("successfulAccess")].children.append(instance_b.name + "." + "networkRespondConnect")
        instance_b.attack_steps[instance_b.attack_steps.index("specificAccess")].children.append(instance_a.name + "." + "access")
        instance_b.attack_steps[instance_b.attack_steps.index("fullAccess")].children.append(instance_a.name + "." + "access")

    def add_appDataContainment(self, instance_a, instance_b):
        if instance_a.type != "Data" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        # The 'attemptApplicationRespondConnectThroughData' is not included for simplicity
        instance_b.attack_steps[instance_b.attack_steps.index("fullAccess")].children.append(instance_a.name + "." + "read")
        instance_b.attack_steps[instance_b.attack_steps.index("fullAccess")].children.append(instance_a.name + "." + "access")
        instance_b.attack_steps[instance_b.attack_steps.index("fullAccess")].children.append(instance_a.name + "." + "deny")

    def add_highPrivilegeAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "System":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("assume")].children.append(instance_b.name + "." + "attemptGainFullAccess")
        instance_b.attack_steps[instance_b.attack_steps.index("fullAccess")].children.append(instance_a.name + "." + "assume")

    def add_lowPrivilegeAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "System":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("assume")].children.append(instance_b.name + "." + "individualPrivilegeAuthenticate")
        instance_b.attack_steps[instance_b.attack_steps.index("fullAccess")].children.append(instance_a.name + "." + "assume")

    def add_executionPrivilegeAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("assume")].children.append(instance_b.name + "." + "authenticate")
        instance_b.attack_steps[instance_b.attack_steps.index("fullAccess")].children.append(instance_a.name + "." + "assume")

    def add_highPrivilegeApplicationAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("assume")].children.append(instance_b.name + "." + "authenticate")
        instance_b.attack_steps[instance_b.attack_steps.index("fullAccess")].children.append(instance_a.name + "." + "assume")

    def add_lowPrivilegeApplicationAccess(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Application":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("assume")].children.append(instance_b.name + "." + "specificAccessAuthenticate")
        instance_b.attack_steps[instance_b.attack_steps.index("fullAccess")].children.append(instance_a.name + "." + "assume")

    def add_readPrivileges(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Data":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("assume")].children.append(instance_b.name + "." + "read")

    def add_writePrivileges(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Data":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("assume")].children.append(instance_b.name + "." + "write")

    def add_deletePrivileges(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Data":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("assume")].children.append(instance_b.name + "." + "delete")

    def add_physicalVulnerability(self, instance_a, instance_b):
        if instance_a.type != "System" or instance_b.type != "ManualHighImpactVulnerability":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("attemptUsePhysicalVulnerability")].children.append(instance_b.name + "." + "impact")
        instance_b.attack_steps[instance_b.attack_steps.index("impact")].children.append(instance_a.name + "." + "bypassAccessControl")
        
    def add_applicationVulnerability(self, instance_a, instance_b):
        if instance_a.type != "Application" or instance_b.type != "ManualHighImpactVulnerability":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("attemptUseVulnerability")].children.append(instance_b.name + "." + "impact")
        instance_b.attack_steps[instance_b.attack_steps.index("impact")].children.append(instance_a.name + "." + "fullAccess")

    def add_identityAdmin(self, instance_a, instance_b):
        if instance_a.type != "Identity" or instance_b.type != "Identity":
            print("ERROR: Wrong instances specified on '" + str(inspect.currentframe().f_code.co_name) + "', returning...")
            return
        instance_a.attack_steps[instance_a.attack_steps.index("assume")].children.append(instance_b.name + "." + "assume")

model_generator = ModelGenerator()
model = model_generator.generate_model()
# print(model)

# One dictionary of attack step connections for each asset instance (if those dicts are concatenated, then the full graph is produced!)
for instance in model:
    print("# Instance: " + str(instance))
    print(instance.get_connected_attack_steps())