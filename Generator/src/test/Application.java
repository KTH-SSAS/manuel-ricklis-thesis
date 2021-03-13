package test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Application extends Object {
  public LocalConnect localConnect;

  public AttemptLocalConnectVuln attemptLocalConnectVuln;

  public AttemptNetworkRequestRespondConnectVuln attemptNetworkRequestRespondConnectVuln;

  public NetworkConnect networkConnect;

  public AccessNetworkAndConnections accessNetworkAndConnections;

  public NetworkRequestConnect networkRequestConnect;

  public NetworkRespondConnect networkRespondConnect;

  public SpecificAccessFromLocalConnection specificAccessFromLocalConnection;

  public SpecificAccessFromNetworkConnection specificAccessFromNetworkConnection;

  public SpecificAccess specificAccess;

  public AttemptLocalConnectVulnOnHost attemptLocalConnectVulnOnHost;

  public Authenticate authenticate;

  public SpecificAccessAuthenticate specificAccessAuthenticate;

  public LocalAccess localAccess;

  public NetworkAccess networkAccess;

  public FullAccess fullAccess;

  public AttemptApplicationRespondConnectThroughData attemptApplicationRespondConnectThroughData;

  public CodeExecutionAfterVulnerability codeExecutionAfterVulnerability;

  public Read read;

  public Modify modify;

  public DenyFromConnectionRule denyFromConnectionRule;

  private Set<ManuallyModeledVulnerability> _cacheallManualVulnerabilitiesApplication;

  private Set<UnknownVulnerability> _cacheallUnknownVulnerabilitiesApplication;

  private Set<Vulnerability> _cacheallAutomaticVulnerabilitiesApplication;

  private Set<ConnectionRule> _cacheclientApplicationConnectionsApplication;

  private Set<ConnectionRule> _cacheserverApplicationConnectionsApplication;

  private Set<ConnectionRule> _cacheallApplicationConnectionsApplication;

  private Set<Vulnerability> _cachereverseallVulnerableSoftwareVulnerability;

  private Set<AutomaticExploit> _cachereverseallVulnerableSoftwareAutomaticExploit;

  private Set<Network> _cachereverseallowedApplicationConnectionsApplicationsNetwork;

  private Set<ConnectionRule> _cachereverseserverApplicationsConnectionRule;

  private Set<ConnectionRule> _cachereverseclientApplicationsConnectionRule;

  private Set<ConnectionRule> _cachereverseallApplicationsConnectionRule;

  public Set<Vulnerability> vulnerabilities = new HashSet<>();

  public System hostSystem = null;

  public Set<Application> appExecutedApps = new HashSet<>();

  public Application hostApp = null;

  public SoftwareProduct appSoftProduct = null;

  public Set<RoutingFirewall> managedRoutingFw = new HashSet<>();

  public Set<Network> networks = new HashSet<>();

  public Set<Network> clientAccessNetworks = new HashSet<>();

  public Set<ConnectionRule> appConnections = new HashSet<>();

  public Set<ConnectionRule> ingoingAppConnections = new HashSet<>();

  public Set<ConnectionRule> outgoingAppConnections = new HashSet<>();

  public Set<Data> containedData = new HashSet<>();

  public Set<Data> transitData = new HashSet<>();

  public Set<Identity> executionPrivIds = new HashSet<>();

  public Set<Identity> highPrivAppIds = new HashSet<>();

  public Set<Identity> lowPrivAppIds = new HashSet<>();

  public Set<Group> executionPrivGroups = new HashSet<>();

  public Set<Group> highPrivAppGroups = new HashSet<>();

  public Set<Group> lowPrivAppGroups = new HashSet<>();

  public Application(String name) {
    super(name);
    assetClassName = "Application";
    AttackStep.allAttackSteps.remove(localConnect);
    localConnect = new LocalConnect(name);
    AttackStep.allAttackSteps.remove(attemptUseVulnerability);
    attemptUseVulnerability = new AttemptUseVulnerability(name);
    AttackStep.allAttackSteps.remove(attemptLocalConnectVuln);
    attemptLocalConnectVuln = new AttemptLocalConnectVuln(name);
    AttackStep.allAttackSteps.remove(attemptNetworkRequestRespondConnectVuln);
    attemptNetworkRequestRespondConnectVuln = new AttemptNetworkRequestRespondConnectVuln(name);
    AttackStep.allAttackSteps.remove(networkConnect);
    networkConnect = new NetworkConnect(name);
    AttackStep.allAttackSteps.remove(accessNetworkAndConnections);
    accessNetworkAndConnections = new AccessNetworkAndConnections(name);
    AttackStep.allAttackSteps.remove(networkRequestConnect);
    networkRequestConnect = new NetworkRequestConnect(name);
    AttackStep.allAttackSteps.remove(networkRespondConnect);
    networkRespondConnect = new NetworkRespondConnect(name);
    AttackStep.allAttackSteps.remove(specificAccessFromLocalConnection);
    specificAccessFromLocalConnection = new SpecificAccessFromLocalConnection(name);
    AttackStep.allAttackSteps.remove(specificAccessFromNetworkConnection);
    specificAccessFromNetworkConnection = new SpecificAccessFromNetworkConnection(name);
    AttackStep.allAttackSteps.remove(specificAccess);
    specificAccess = new SpecificAccess(name);
    AttackStep.allAttackSteps.remove(attemptLocalConnectVulnOnHost);
    attemptLocalConnectVulnOnHost = new AttemptLocalConnectVulnOnHost(name);
    AttackStep.allAttackSteps.remove(authenticate);
    authenticate = new Authenticate(name);
    AttackStep.allAttackSteps.remove(specificAccessAuthenticate);
    specificAccessAuthenticate = new SpecificAccessAuthenticate(name);
    AttackStep.allAttackSteps.remove(localAccess);
    localAccess = new LocalAccess(name);
    AttackStep.allAttackSteps.remove(networkAccess);
    networkAccess = new NetworkAccess(name);
    AttackStep.allAttackSteps.remove(fullAccess);
    fullAccess = new FullAccess(name);
    AttackStep.allAttackSteps.remove(attemptApplicationRespondConnectThroughData);
    attemptApplicationRespondConnectThroughData = new AttemptApplicationRespondConnectThroughData(name);
    AttackStep.allAttackSteps.remove(codeExecutionAfterVulnerability);
    codeExecutionAfterVulnerability = new CodeExecutionAfterVulnerability(name);
    AttackStep.allAttackSteps.remove(read);
    read = new Read(name);
    AttackStep.allAttackSteps.remove(modify);
    modify = new Modify(name);
    AttackStep.allAttackSteps.remove(deny);
    deny = new Deny(name);
    AttackStep.allAttackSteps.remove(denyFromConnectionRule);
    denyFromConnectionRule = new DenyFromConnectionRule(name);
  }

  public Application() {
    this("Anonymous");
  }

  protected Set<ManuallyModeledVulnerability> _allManualVulnerabilitiesApplication() {
    if (_cacheallManualVulnerabilitiesApplication == null) {
      _cacheallManualVulnerabilitiesApplication = new HashSet<>();
      Set<ManuallyModeledVulnerability> _2 = new HashSet<>();
      Set<ManuallyModeledVulnerability> _3 = new HashSet<>();
      for (Vulnerability _4 : vulnerabilities) {
        if (_4 instanceof ManuallyModeledVulnerability) {
          _2.add(((test.ManuallyModeledVulnerability) _4));
        }
      }
      if (appSoftProduct != null) {
        for (Vulnerability _5 : appSoftProduct.softProductVulnerabilities) {
          if (_5 instanceof ManuallyModeledVulnerability) {
            _3.add(((test.ManuallyModeledVulnerability) _5));
          }
        }
      }
      _2.addAll(_3);
      for (ManuallyModeledVulnerability _6 : _2) {
        _cacheallManualVulnerabilitiesApplication.add(_6);
      }
    }
    return _cacheallManualVulnerabilitiesApplication;
  }

  protected Set<UnknownVulnerability> _allUnknownVulnerabilitiesApplication() {
    if (_cacheallUnknownVulnerabilitiesApplication == null) {
      _cacheallUnknownVulnerabilitiesApplication = new HashSet<>();
      Set<UnknownVulnerability> _7 = new HashSet<>();
      Set<UnknownVulnerability> _8 = new HashSet<>();
      for (Vulnerability _9 : vulnerabilities) {
        if (_9 instanceof UnknownVulnerability) {
          _7.add(((test.UnknownVulnerability) _9));
        }
      }
      if (appSoftProduct != null) {
        for (Vulnerability _a : appSoftProduct.softProductVulnerabilities) {
          if (_a instanceof UnknownVulnerability) {
            _8.add(((test.UnknownVulnerability) _a));
          }
        }
      }
      _7.addAll(_8);
      for (UnknownVulnerability _b : _7) {
        _cacheallUnknownVulnerabilitiesApplication.add(_b);
      }
    }
    return _cacheallUnknownVulnerabilitiesApplication;
  }

  protected Set<Vulnerability> _allAutomaticVulnerabilitiesApplication() {
    if (_cacheallAutomaticVulnerabilitiesApplication == null) {
      _cacheallAutomaticVulnerabilitiesApplication = new HashSet<>();
      Set<Vulnerability> _c = new HashSet<>();
      Set<Vulnerability> _d = new HashSet<>();
      Set<Vulnerability> _e = new HashSet<>();
      Set<Vulnerability> _f = new HashSet<>();
      Set<Vulnerability> _10 = new HashSet<>();
      Set<Vulnerability> _11 = new HashSet<>();
      for (Vulnerability _12 : vulnerabilities) {
        _10.add(_12);
      }
      if (appSoftProduct != null) {
        for (Vulnerability _13 : appSoftProduct.softProductVulnerabilities) {
          _11.add(_13);
        }
      }
      _10.addAll(_11);
      for (Vulnerability _14 : _10) {
        _e.add(_14);
      }
      for (var _15 : _allManualVulnerabilitiesApplication()) {
        _f.add(_15);
      }
      _e.removeAll(_f);
      for (Vulnerability _16 : _e) {
        _c.add(_16);
      }
      for (var _17 : _allUnknownVulnerabilitiesApplication()) {
        _d.add(_17);
      }
      _c.removeAll(_d);
      for (Vulnerability _18 : _c) {
        _cacheallAutomaticVulnerabilitiesApplication.add(_18);
      }
    }
    return _cacheallAutomaticVulnerabilitiesApplication;
  }

  protected Set<ConnectionRule> _clientApplicationConnectionsApplication() {
    if (_cacheclientApplicationConnectionsApplication == null) {
      _cacheclientApplicationConnectionsApplication = new HashSet<>();
      Set<ConnectionRule> _19 = new HashSet<>();
      Set<ConnectionRule> _1a = new HashSet<>();
      for (ConnectionRule _1b : appConnections) {
        _19.add(_1b);
      }
      for (ConnectionRule _1c : outgoingAppConnections) {
        _1a.add(_1c);
      }
      _19.addAll(_1a);
      for (ConnectionRule _1d : _19) {
        _cacheclientApplicationConnectionsApplication.add(_1d);
      }
    }
    return _cacheclientApplicationConnectionsApplication;
  }

  protected Set<ConnectionRule> _serverApplicationConnectionsApplication() {
    if (_cacheserverApplicationConnectionsApplication == null) {
      _cacheserverApplicationConnectionsApplication = new HashSet<>();
      Set<ConnectionRule> _1e = new HashSet<>();
      Set<ConnectionRule> _1f = new HashSet<>();
      for (ConnectionRule _20 : appConnections) {
        _1e.add(_20);
      }
      for (ConnectionRule _21 : ingoingAppConnections) {
        _1f.add(_21);
      }
      _1e.addAll(_1f);
      for (ConnectionRule _22 : _1e) {
        _cacheserverApplicationConnectionsApplication.add(_22);
      }
    }
    return _cacheserverApplicationConnectionsApplication;
  }

  protected Set<ConnectionRule> _allApplicationConnectionsApplication() {
    if (_cacheallApplicationConnectionsApplication == null) {
      _cacheallApplicationConnectionsApplication = new HashSet<>();
      Set<ConnectionRule> _23 = new HashSet<>();
      Set<ConnectionRule> _24 = new HashSet<>();
      Set<ConnectionRule> _25 = new HashSet<>();
      Set<ConnectionRule> _26 = new HashSet<>();
      for (ConnectionRule _27 : appConnections) {
        _25.add(_27);
      }
      for (ConnectionRule _28 : outgoingAppConnections) {
        _26.add(_28);
      }
      _25.addAll(_26);
      for (ConnectionRule _29 : _25) {
        _23.add(_29);
      }
      for (ConnectionRule _2a : ingoingAppConnections) {
        _24.add(_2a);
      }
      _23.addAll(_24);
      for (ConnectionRule _2b : _23) {
        _cacheallApplicationConnectionsApplication.add(_2b);
      }
    }
    return _cacheallApplicationConnectionsApplication;
  }

  protected Set<Vulnerability> _reverseallVulnerableSoftwareVulnerability() {
    if (_cachereverseallVulnerableSoftwareVulnerability == null) {
      _cachereverseallVulnerableSoftwareVulnerability = new HashSet<>();
      Set<Vulnerability> _2c = new HashSet<>();
      Set<Vulnerability> _2d = new HashSet<>();
      if (appSoftProduct != null) {
        for (Vulnerability _2e : appSoftProduct.softProductVulnerabilities) {
          _2c.add(_2e);
        }
      }
      for (Vulnerability _2f : vulnerabilities) {
        _2d.add(_2f);
      }
      _2c.addAll(_2d);
      for (Vulnerability _30 : _2c) {
        _cachereverseallVulnerableSoftwareVulnerability.add(_30);
      }
    }
    return _cachereverseallVulnerableSoftwareVulnerability;
  }

  protected Set<AutomaticExploit> _reverseallVulnerableSoftwareAutomaticExploit() {
    if (_cachereverseallVulnerableSoftwareAutomaticExploit == null) {
      _cachereverseallVulnerableSoftwareAutomaticExploit = new HashSet<>();
      Set<AutomaticExploit> _31 = new HashSet<>();
      Set<AutomaticExploit> _32 = new HashSet<>();
      if (appSoftProduct != null) {
        for (Vulnerability _33 : appSoftProduct.softProductVulnerabilities) {
          for (Exploit _34 : _33.exploits) {
            if (_34 instanceof AutomaticExploit) {
              _31.add(((test.AutomaticExploit) _34));
            }
          }
        }
      }
      for (Vulnerability _35 : vulnerabilities) {
        for (Exploit _36 : _35.exploits) {
          if (_36 instanceof AutomaticExploit) {
            _32.add(((test.AutomaticExploit) _36));
          }
        }
      }
      _31.addAll(_32);
      for (AutomaticExploit _37 : _31) {
        _cachereverseallVulnerableSoftwareAutomaticExploit.add(_37);
      }
    }
    return _cachereverseallVulnerableSoftwareAutomaticExploit;
  }

  protected Set<Network> _reverseallowedApplicationConnectionsApplicationsNetwork() {
    if (_cachereverseallowedApplicationConnectionsApplicationsNetwork == null) {
      _cachereverseallowedApplicationConnectionsApplicationsNetwork = new HashSet<>();
      Set<Network> _38 = new HashSet<>();
      Set<Network> _39 = new HashSet<>();
      for (Network _3a : networks) {
        _38.add(_3a);
      }
      Set<Network> _3b = new HashSet<>();
      Set<Network> _3c = new HashSet<>();
      for (ConnectionRule _3d : appConnections) {
        for (Network _3e : _3d.outNetworks) {
          _3b.add(_3e);
        }
      }
      for (ConnectionRule _3f : appConnections) {
        for (Network _40 : _3f.networks) {
          _3c.add(_40);
        }
      }
      _3b.addAll(_3c);
      for (Network _41 : _3b) {
        _39.add(_41);
      }
      _38.addAll(_39);
      for (Network _42 : _38) {
        _cachereverseallowedApplicationConnectionsApplicationsNetwork.add(_42);
      }
    }
    return _cachereverseallowedApplicationConnectionsApplicationsNetwork;
  }

  protected Set<ConnectionRule> _reverseserverApplicationsConnectionRule() {
    if (_cachereverseserverApplicationsConnectionRule == null) {
      _cachereverseserverApplicationsConnectionRule = new HashSet<>();
      Set<ConnectionRule> _43 = new HashSet<>();
      Set<ConnectionRule> _44 = new HashSet<>();
      for (ConnectionRule _45 : ingoingAppConnections) {
        _43.add(_45);
      }
      for (ConnectionRule _46 : appConnections) {
        _44.add(_46);
      }
      _43.addAll(_44);
      for (ConnectionRule _47 : _43) {
        _cachereverseserverApplicationsConnectionRule.add(_47);
      }
    }
    return _cachereverseserverApplicationsConnectionRule;
  }

  protected Set<ConnectionRule> _reverseclientApplicationsConnectionRule() {
    if (_cachereverseclientApplicationsConnectionRule == null) {
      _cachereverseclientApplicationsConnectionRule = new HashSet<>();
      Set<ConnectionRule> _48 = new HashSet<>();
      Set<ConnectionRule> _49 = new HashSet<>();
      for (ConnectionRule _4a : outgoingAppConnections) {
        _48.add(_4a);
      }
      for (ConnectionRule _4b : appConnections) {
        _49.add(_4b);
      }
      _48.addAll(_49);
      for (ConnectionRule _4c : _48) {
        _cachereverseclientApplicationsConnectionRule.add(_4c);
      }
    }
    return _cachereverseclientApplicationsConnectionRule;
  }

  protected Set<ConnectionRule> _reverseallApplicationsConnectionRule() {
    if (_cachereverseallApplicationsConnectionRule == null) {
      _cachereverseallApplicationsConnectionRule = new HashSet<>();
      Set<ConnectionRule> _4d = new HashSet<>();
      Set<ConnectionRule> _4e = new HashSet<>();
      for (ConnectionRule _4f : outgoingAppConnections) {
        _4d.add(_4f);
      }
      Set<ConnectionRule> _50 = new HashSet<>();
      Set<ConnectionRule> _51 = new HashSet<>();
      for (ConnectionRule _52 : ingoingAppConnections) {
        _50.add(_52);
      }
      for (ConnectionRule _53 : appConnections) {
        _51.add(_53);
      }
      _50.addAll(_51);
      for (ConnectionRule _54 : _50) {
        _4e.add(_54);
      }
      _4d.addAll(_4e);
      for (ConnectionRule _55 : _4d) {
        _cachereverseallApplicationsConnectionRule.add(_55);
      }
    }
    return _cachereverseallApplicationsConnectionRule;
  }

  public void addVulnerabilities(Vulnerability vulnerabilities) {
    this.vulnerabilities.add(vulnerabilities);
    vulnerabilities.application = this;
  }

  public void addHostSystem(System hostSystem) {
    this.hostSystem = hostSystem;
    hostSystem.sysExecutedApps.add(this);
  }

  public void addAppExecutedApps(Application appExecutedApps) {
    this.appExecutedApps.add(appExecutedApps);
    appExecutedApps.hostApp = this;
  }

  public void addHostApp(Application hostApp) {
    this.hostApp = hostApp;
    hostApp.appExecutedApps.add(this);
  }

  public void addAppSoftProduct(SoftwareProduct appSoftProduct) {
    this.appSoftProduct = appSoftProduct;
    appSoftProduct.softApplications.add(this);
  }

  public void addManagedRoutingFw(RoutingFirewall managedRoutingFw) {
    this.managedRoutingFw.add(managedRoutingFw);
    managedRoutingFw.managerApp = this;
  }

  public void addNetworks(Network networks) {
    this.networks.add(networks);
    networks.applications.add(this);
  }

  public void addClientAccessNetworks(Network clientAccessNetworks) {
    this.clientAccessNetworks.add(clientAccessNetworks);
    clientAccessNetworks.clientApplications.add(this);
  }

  public void addAppConnections(ConnectionRule appConnections) {
    this.appConnections.add(appConnections);
    appConnections.applications.add(this);
  }

  public void addIngoingAppConnections(ConnectionRule ingoingAppConnections) {
    this.ingoingAppConnections.add(ingoingAppConnections);
    ingoingAppConnections.inApplications.add(this);
  }

  public void addOutgoingAppConnections(ConnectionRule outgoingAppConnections) {
    this.outgoingAppConnections.add(outgoingAppConnections);
    outgoingAppConnections.outApplications.add(this);
  }

  public void addContainedData(Data containedData) {
    this.containedData.add(containedData);
    containedData.containingApp.add(this);
  }

  public void addTransitData(Data transitData) {
    this.transitData.add(transitData);
    transitData.transitApp.add(this);
  }

  public void addExecutionPrivIds(Identity executionPrivIds) {
    this.executionPrivIds.add(executionPrivIds);
    executionPrivIds.execPrivApps.add(this);
  }

  public void addHighPrivAppIds(Identity highPrivAppIds) {
    this.highPrivAppIds.add(highPrivAppIds);
    highPrivAppIds.highPrivApps.add(this);
  }

  public void addLowPrivAppIds(Identity lowPrivAppIds) {
    this.lowPrivAppIds.add(lowPrivAppIds);
    lowPrivAppIds.lowPrivApps.add(this);
  }

  public void addExecutionPrivGroups(Group executionPrivGroups) {
    this.executionPrivGroups.add(executionPrivGroups);
    executionPrivGroups.execPrivApps.add(this);
  }

  public void addHighPrivAppGroups(Group highPrivAppGroups) {
    this.highPrivAppGroups.add(highPrivAppGroups);
    highPrivAppGroups.highPrivApps.add(this);
  }

  public void addLowPrivAppGroups(Group lowPrivAppGroups) {
    this.lowPrivAppGroups.add(lowPrivAppGroups);
    lowPrivAppGroups.lowPrivApps.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("vulnerabilities")) {
      return Vulnerability.class.getName();
    } else if (field.equals("hostSystem")) {
      return System.class.getName();
    } else if (field.equals("appExecutedApps")) {
      return Application.class.getName();
    } else if (field.equals("hostApp")) {
      return Application.class.getName();
    } else if (field.equals("appSoftProduct")) {
      return SoftwareProduct.class.getName();
    } else if (field.equals("managedRoutingFw")) {
      return RoutingFirewall.class.getName();
    } else if (field.equals("networks")) {
      return Network.class.getName();
    } else if (field.equals("clientAccessNetworks")) {
      return Network.class.getName();
    } else if (field.equals("appConnections")) {
      return ConnectionRule.class.getName();
    } else if (field.equals("ingoingAppConnections")) {
      return ConnectionRule.class.getName();
    } else if (field.equals("outgoingAppConnections")) {
      return ConnectionRule.class.getName();
    } else if (field.equals("containedData")) {
      return Data.class.getName();
    } else if (field.equals("transitData")) {
      return Data.class.getName();
    } else if (field.equals("executionPrivIds")) {
      return Identity.class.getName();
    } else if (field.equals("highPrivAppIds")) {
      return Identity.class.getName();
    } else if (field.equals("lowPrivAppIds")) {
      return Identity.class.getName();
    } else if (field.equals("executionPrivGroups")) {
      return Group.class.getName();
    } else if (field.equals("highPrivAppGroups")) {
      return Group.class.getName();
    } else if (field.equals("lowPrivAppGroups")) {
      return Group.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("vulnerabilities")) {
      assets.addAll(vulnerabilities);
    } else if (field.equals("hostSystem")) {
      if (hostSystem != null) {
        assets.add(hostSystem);
      }
    } else if (field.equals("appExecutedApps")) {
      assets.addAll(appExecutedApps);
    } else if (field.equals("hostApp")) {
      if (hostApp != null) {
        assets.add(hostApp);
      }
    } else if (field.equals("appSoftProduct")) {
      if (appSoftProduct != null) {
        assets.add(appSoftProduct);
      }
    } else if (field.equals("managedRoutingFw")) {
      assets.addAll(managedRoutingFw);
    } else if (field.equals("networks")) {
      assets.addAll(networks);
    } else if (field.equals("clientAccessNetworks")) {
      assets.addAll(clientAccessNetworks);
    } else if (field.equals("appConnections")) {
      assets.addAll(appConnections);
    } else if (field.equals("ingoingAppConnections")) {
      assets.addAll(ingoingAppConnections);
    } else if (field.equals("outgoingAppConnections")) {
      assets.addAll(outgoingAppConnections);
    } else if (field.equals("containedData")) {
      assets.addAll(containedData);
    } else if (field.equals("transitData")) {
      assets.addAll(transitData);
    } else if (field.equals("executionPrivIds")) {
      assets.addAll(executionPrivIds);
    } else if (field.equals("highPrivAppIds")) {
      assets.addAll(highPrivAppIds);
    } else if (field.equals("lowPrivAppIds")) {
      assets.addAll(lowPrivAppIds);
    } else if (field.equals("executionPrivGroups")) {
      assets.addAll(executionPrivGroups);
    } else if (field.equals("highPrivAppGroups")) {
      assets.addAll(highPrivAppGroups);
    } else if (field.equals("lowPrivAppGroups")) {
      assets.addAll(lowPrivAppGroups);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(vulnerabilities);
    if (hostSystem != null) {
      assets.add(hostSystem);
    }
    assets.addAll(appExecutedApps);
    if (hostApp != null) {
      assets.add(hostApp);
    }
    if (appSoftProduct != null) {
      assets.add(appSoftProduct);
    }
    assets.addAll(managedRoutingFw);
    assets.addAll(networks);
    assets.addAll(clientAccessNetworks);
    assets.addAll(appConnections);
    assets.addAll(ingoingAppConnections);
    assets.addAll(outgoingAppConnections);
    assets.addAll(containedData);
    assets.addAll(transitData);
    assets.addAll(executionPrivIds);
    assets.addAll(highPrivAppIds);
    assets.addAll(lowPrivAppIds);
    assets.addAll(executionPrivGroups);
    assets.addAll(highPrivAppGroups);
    assets.addAll(lowPrivAppGroups);
    return assets;
  }

  public class LocalConnect extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenLocalConnect;

    private Set<AttackStep> _cacheParentLocalConnect;

    public LocalConnect(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenLocalConnect == null) {
        _cacheChildrenLocalConnect = new HashSet<>();
        _cacheChildrenLocalConnect.add(localAccess);
        _cacheChildrenLocalConnect.add(specificAccessFromLocalConnection);
        _cacheChildrenLocalConnect.add(attemptUseVulnerability);
        _cacheChildrenLocalConnect.add(attemptLocalConnectVuln);
      }
      for (AttackStep attackStep : _cacheChildrenLocalConnect) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentLocalConnect == null) {
        _cacheParentLocalConnect = new HashSet<>();
        if (hostSystem != null) {
          _cacheParentLocalConnect.add(hostSystem.specificAccess);
        }
        if (hostApp != null) {
          _cacheParentLocalConnect.add(hostApp.specificAccess);
        }
        for (Application _0 : appExecutedApps) {
          _cacheParentLocalConnect.add(_0.fullAccess);
        }
      }
      for (AttackStep attackStep : _cacheParentLocalConnect) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.localConnect");
    }
  }

  public class AttemptUseVulnerability extends Object.AttemptUseVulnerability {
    private Set<AttackStep> _cacheChildrenAttemptUseVulnerability;

    private Set<AttackStep> _cacheParentAttemptUseVulnerability;

    public AttemptUseVulnerability(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptUseVulnerability == null) {
        _cacheChildrenAttemptUseVulnerability = new HashSet<>();
        for (var _0 : _allManualVulnerabilitiesApplication()) {
          _cacheChildrenAttemptUseVulnerability.add(_0.attemptAbuse);
        }
        for (var _1 : _allUnknownVulnerabilitiesApplication()) {
          _cacheChildrenAttemptUseVulnerability.add(_1.attemptAbuse);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAttemptUseVulnerability) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptUseVulnerability == null) {
        _cacheParentAttemptUseVulnerability = new HashSet<>();
        _cacheParentAttemptUseVulnerability.add(localConnect);
        _cacheParentAttemptUseVulnerability.add(networkConnect);
        _cacheParentAttemptUseVulnerability.add(specificAccess);
        for (Application _2 : appExecutedApps) {
          _cacheParentAttemptUseVulnerability.add(_2.attemptLocalConnectVulnOnHost);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptUseVulnerability) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.attemptUseVulnerability");
    }
  }

  public class AttemptLocalConnectVuln extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptLocalConnectVuln;

    private Set<AttackStep> _cacheParentAttemptLocalConnectVuln;

    public AttemptLocalConnectVuln(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptLocalConnectVuln == null) {
        _cacheChildrenAttemptLocalConnectVuln = new HashSet<>();
        for (var _0 : _allAutomaticVulnerabilitiesApplication()) {
          if (_0 instanceof LLNNVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LLNNVulnerability) _0).attemptAbuse);
          }
        }
        for (var _1 : _allAutomaticVulnerabilitiesApplication()) {
          if (_1 instanceof LLNRVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LLNRVulnerability) _1).attemptAbuse);
          }
        }
        for (var _2 : _allAutomaticVulnerabilitiesApplication()) {
          if (_2 instanceof LLLNVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LLLNVulnerability) _2).attemptAbuse);
          }
        }
        for (var _3 : _allAutomaticVulnerabilitiesApplication()) {
          if (_3 instanceof LLLRVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LLLRVulnerability) _3).attemptAbuse);
          }
        }
        for (var _4 : _allAutomaticVulnerabilitiesApplication()) {
          if (_4 instanceof LLHNVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LLHNVulnerability) _4).attemptAbuse);
          }
        }
        for (var _5 : _allAutomaticVulnerabilitiesApplication()) {
          if (_5 instanceof LLHRVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LLHRVulnerability) _5).attemptAbuse);
          }
        }
        for (var _6 : _allAutomaticVulnerabilitiesApplication()) {
          if (_6 instanceof LHNNVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LHNNVulnerability) _6).attemptAbuse);
          }
        }
        for (var _7 : _allAutomaticVulnerabilitiesApplication()) {
          if (_7 instanceof LHNRVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LHNRVulnerability) _7).attemptAbuse);
          }
        }
        for (var _8 : _allAutomaticVulnerabilitiesApplication()) {
          if (_8 instanceof LHLNVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LHLNVulnerability) _8).attemptAbuse);
          }
        }
        for (var _9 : _allAutomaticVulnerabilitiesApplication()) {
          if (_9 instanceof LHLRVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LHLRVulnerability) _9).attemptAbuse);
          }
        }
        for (var _a : _allAutomaticVulnerabilitiesApplication()) {
          if (_a instanceof LHHNVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LHHNVulnerability) _a).attemptAbuse);
          }
        }
        for (var _b : _allAutomaticVulnerabilitiesApplication()) {
          if (_b instanceof LHHRVulnerability) {
            _cacheChildrenAttemptLocalConnectVuln.add(((test.LHHRVulnerability) _b).attemptAbuse);
          }
        }
      }
      for (AttackStep attackStep : _cacheChildrenAttemptLocalConnectVuln) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptLocalConnectVuln == null) {
        _cacheParentAttemptLocalConnectVuln = new HashSet<>();
        _cacheParentAttemptLocalConnectVuln.add(localConnect);
        _cacheParentAttemptLocalConnectVuln.add(specificAccess);
        for (Application _c : appExecutedApps) {
          _cacheParentAttemptLocalConnectVuln.add(_c.attemptLocalConnectVulnOnHost);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptLocalConnectVuln) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.attemptLocalConnectVuln");
    }
  }

  public class AttemptNetworkRequestRespondConnectVuln extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptNetworkRequestRespondConnectVuln;

    private Set<AttackStep> _cacheParentAttemptNetworkRequestRespondConnectVuln;

    public AttemptNetworkRequestRespondConnectVuln(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptNetworkRequestRespondConnectVuln == null) {
        _cacheChildrenAttemptNetworkRequestRespondConnectVuln = new HashSet<>();
        for (var _0 : _allAutomaticVulnerabilitiesApplication()) {
          if (_0 instanceof NLNNVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NLNNVulnerability) _0).attemptAbuse);
          }
        }
        for (var _1 : _allAutomaticVulnerabilitiesApplication()) {
          if (_1 instanceof NLNRVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NLNRVulnerability) _1).attemptAbuse);
          }
        }
        for (var _2 : _allAutomaticVulnerabilitiesApplication()) {
          if (_2 instanceof NLLNVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NLLNVulnerability) _2).attemptAbuse);
          }
        }
        for (var _3 : _allAutomaticVulnerabilitiesApplication()) {
          if (_3 instanceof NLLRVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NLLRVulnerability) _3).attemptAbuse);
          }
        }
        for (var _4 : _allAutomaticVulnerabilitiesApplication()) {
          if (_4 instanceof NLHNVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NLHNVulnerability) _4).attemptAbuse);
          }
        }
        for (var _5 : _allAutomaticVulnerabilitiesApplication()) {
          if (_5 instanceof NLHRVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NLHRVulnerability) _5).attemptAbuse);
          }
        }
        for (var _6 : _allAutomaticVulnerabilitiesApplication()) {
          if (_6 instanceof NHNNVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NHNNVulnerability) _6).attemptAbuse);
          }
        }
        for (var _7 : _allAutomaticVulnerabilitiesApplication()) {
          if (_7 instanceof NHNRVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NHNRVulnerability) _7).attemptAbuse);
          }
        }
        for (var _8 : _allAutomaticVulnerabilitiesApplication()) {
          if (_8 instanceof NHLNVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NHLNVulnerability) _8).attemptAbuse);
          }
        }
        for (var _9 : _allAutomaticVulnerabilitiesApplication()) {
          if (_9 instanceof NHLRVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NHLRVulnerability) _9).attemptAbuse);
          }
        }
        for (var _a : _allAutomaticVulnerabilitiesApplication()) {
          if (_a instanceof NHHNVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NHHNVulnerability) _a).attemptAbuse);
          }
        }
        for (var _b : _allAutomaticVulnerabilitiesApplication()) {
          if (_b instanceof NHHRVulnerability) {
            _cacheChildrenAttemptNetworkRequestRespondConnectVuln.add(((test.NHHRVulnerability) _b).attemptAbuse);
          }
        }
      }
      for (AttackStep attackStep : _cacheChildrenAttemptNetworkRequestRespondConnectVuln) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptNetworkRequestRespondConnectVuln == null) {
        _cacheParentAttemptNetworkRequestRespondConnectVuln = new HashSet<>();
        _cacheParentAttemptNetworkRequestRespondConnectVuln.add(networkRequestConnect);
        _cacheParentAttemptNetworkRequestRespondConnectVuln.add(networkRespondConnect);
      }
      for (AttackStep attackStep : _cacheParentAttemptNetworkRequestRespondConnectVuln) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.attemptNetworkRequestRespondConnectVuln");
    }
  }

  public class NetworkConnect extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenNetworkConnect;

    private Set<AttackStep> _cacheParentNetworkConnect;

    public NetworkConnect(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenNetworkConnect == null) {
        _cacheChildrenNetworkConnect = new HashSet<>();
        _cacheChildrenNetworkConnect.add(networkAccess);
        _cacheChildrenNetworkConnect.add(specificAccessFromNetworkConnection);
        _cacheChildrenNetworkConnect.add(attemptUseVulnerability);
      }
      for (AttackStep attackStep : _cacheChildrenNetworkConnect) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentNetworkConnect == null) {
        _cacheParentNetworkConnect = new HashSet<>();
        _cacheParentNetworkConnect.add(networkRespondConnect);
        for (Identity _0 : executionPrivIds) {
          for (User _1 : _0.users) {
            _cacheParentNetworkConnect.add(_1.reverseTakeover);
          }
        }
        for (var _2 : _reverseallowedApplicationConnectionsApplicationsNetwork()) {
          _cacheParentNetworkConnect.add(_2.successfulAccess);
        }
        for (ConnectionRule _3 : appConnections) {
          if (_3.routingFirewalls != null) {
            _cacheParentNetworkConnect.add(_3.routingFirewalls.fullAccess);
          }
        }
        for (var _4 : _reverseserverApplicationsConnectionRule()) {
          _cacheParentNetworkConnect.add(_4.connectToApplications);
        }
      }
      for (AttackStep attackStep : _cacheParentNetworkConnect) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.networkConnect");
    }
  }

  public class AccessNetworkAndConnections extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAccessNetworkAndConnections;

    private Set<AttackStep> _cacheParentAccessNetworkAndConnections;

    public AccessNetworkAndConnections(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAccessNetworkAndConnections == null) {
        _cacheChildrenAccessNetworkAndConnections = new HashSet<>();
        for (Network _0 : networks) {
          _cacheChildrenAccessNetworkAndConnections.add(_0.access);
        }
        for (var _1 : _clientApplicationConnectionsApplication()) {
          _cacheChildrenAccessNetworkAndConnections.add(_1.attemptConnectToApplications);
        }
        for (var _2 : _clientApplicationConnectionsApplication()) {
          _cacheChildrenAccessNetworkAndConnections.add(_2.attemptTransmit);
        }
        for (var _3 : _serverApplicationConnectionsApplication()) {
          _cacheChildrenAccessNetworkAndConnections.add(_3.attemptTransmitResponse);
        }
        for (var _4 : _allApplicationConnectionsApplication()) {
          _cacheChildrenAccessNetworkAndConnections.add(_4.attemptAccessNetworks);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAccessNetworkAndConnections) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAccessNetworkAndConnections == null) {
        _cacheParentAccessNetworkAndConnections = new HashSet<>();
        _cacheParentAccessNetworkAndConnections.add(specificAccess);
        _cacheParentAccessNetworkAndConnections.add(fullAccess);
      }
      for (AttackStep attackStep : _cacheParentAccessNetworkAndConnections) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.accessNetworkAndConnections");
    }
  }

  public class NetworkRequestConnect extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenNetworkRequestConnect;

    private Set<AttackStep> _cacheParentNetworkRequestConnect;

    public NetworkRequestConnect(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenNetworkRequestConnect == null) {
        _cacheChildrenNetworkRequestConnect = new HashSet<>();
        _cacheChildrenNetworkRequestConnect.add(attemptNetworkRequestRespondConnectVuln);
      }
      for (AttackStep attackStep : _cacheChildrenNetworkRequestConnect) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentNetworkRequestConnect == null) {
        _cacheParentNetworkRequestConnect = new HashSet<>();
        for (var _0 : _reverseallowedApplicationConnectionsApplicationsNetwork()) {
          _cacheParentNetworkRequestConnect.add(_0.successfulAccess);
        }
        for (var _1 : _reverseclientApplicationsConnectionRule()) {
          _cacheParentNetworkRequestConnect.add(_1.transmit);
        }
      }
      for (AttackStep attackStep : _cacheParentNetworkRequestConnect) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.networkRequestConnect");
    }
  }

  public class NetworkRespondConnect extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenNetworkRespondConnect;

    private Set<AttackStep> _cacheParentNetworkRespondConnect;

    public NetworkRespondConnect(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenNetworkRespondConnect == null) {
        _cacheChildrenNetworkRespondConnect = new HashSet<>();
        _cacheChildrenNetworkRespondConnect.add(attemptNetworkRequestRespondConnectVuln);
        _cacheChildrenNetworkRespondConnect.add(networkConnect);
      }
      for (AttackStep attackStep : _cacheChildrenNetworkRespondConnect) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentNetworkRespondConnect == null) {
        _cacheParentNetworkRespondConnect = new HashSet<>();
        for (Data _0 : transitData) {
          _cacheParentNetworkRespondConnect.add(_0.applicationRespondConnect);
        }
        for (Network _1 : clientAccessNetworks) {
          _cacheParentNetworkRespondConnect.add(_1.successfulAccess);
        }
        for (var _2 : _reverseclientApplicationsConnectionRule()) {
          _cacheParentNetworkRespondConnect.add(_2.transmitResponse);
        }
      }
      for (AttackStep attackStep : _cacheParentNetworkRespondConnect) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.networkRespondConnect");
    }
  }

  public class SpecificAccessFromLocalConnection extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenSpecificAccessFromLocalConnection;

    private Set<AttackStep> _cacheParentSpecificAccessFromLocalConnection;

    public SpecificAccessFromLocalConnection(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenSpecificAccessFromLocalConnection == null) {
        _cacheChildrenSpecificAccessFromLocalConnection = new HashSet<>();
        _cacheChildrenSpecificAccessFromLocalConnection.add(specificAccess);
      }
      for (AttackStep attackStep : _cacheChildrenSpecificAccessFromLocalConnection) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentSpecificAccessFromLocalConnection == null) {
        _cacheParentSpecificAccessFromLocalConnection = new HashSet<>();
        _cacheParentSpecificAccessFromLocalConnection.add(localConnect);
        _cacheParentSpecificAccessFromLocalConnection.add(specificAccessAuthenticate);
      }
      for (AttackStep attackStep : _cacheParentSpecificAccessFromLocalConnection) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.specificAccessFromLocalConnection");
    }
  }

  public class SpecificAccessFromNetworkConnection extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenSpecificAccessFromNetworkConnection;

    private Set<AttackStep> _cacheParentSpecificAccessFromNetworkConnection;

    public SpecificAccessFromNetworkConnection(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenSpecificAccessFromNetworkConnection == null) {
        _cacheChildrenSpecificAccessFromNetworkConnection = new HashSet<>();
        _cacheChildrenSpecificAccessFromNetworkConnection.add(specificAccess);
      }
      for (AttackStep attackStep : _cacheChildrenSpecificAccessFromNetworkConnection) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentSpecificAccessFromNetworkConnection == null) {
        _cacheParentSpecificAccessFromNetworkConnection = new HashSet<>();
        _cacheParentSpecificAccessFromNetworkConnection.add(networkConnect);
        _cacheParentSpecificAccessFromNetworkConnection.add(specificAccessAuthenticate);
      }
      for (AttackStep attackStep : _cacheParentSpecificAccessFromNetworkConnection) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.specificAccessFromNetworkConnection");
    }
  }

  public class SpecificAccess extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenSpecificAccess;

    private Set<AttackStep> _cacheParentSpecificAccess;

    public SpecificAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenSpecificAccess == null) {
        _cacheChildrenSpecificAccess = new HashSet<>();
        for (Application _0 : appExecutedApps) {
          _cacheChildrenSpecificAccess.add(_0.localConnect);
        }
        _cacheChildrenSpecificAccess.add(attemptLocalConnectVulnOnHost);
        _cacheChildrenSpecificAccess.add(attemptUseVulnerability);
        _cacheChildrenSpecificAccess.add(attemptLocalConnectVuln);
        for (Data _1 : containedData) {
          _cacheChildrenSpecificAccess.add(_1.attemptAccessFromIdentity);
        }
        for (Data _2 : transitData) {
          _cacheChildrenSpecificAccess.add(_2.attemptAccessFromIdentity);
        }
        _cacheChildrenSpecificAccess.add(attemptApplicationRespondConnectThroughData);
        _cacheChildrenSpecificAccess.add(accessNetworkAndConnections);
      }
      for (AttackStep attackStep : _cacheChildrenSpecificAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentSpecificAccess == null) {
        _cacheParentSpecificAccess = new HashSet<>();
        _cacheParentSpecificAccess.add(specificAccessFromLocalConnection);
        _cacheParentSpecificAccess.add(specificAccessFromNetworkConnection);
        _cacheParentSpecificAccess.add(fullAccess);
      }
      for (AttackStep attackStep : _cacheParentSpecificAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.specificAccess");
    }
  }

  public class AttemptLocalConnectVulnOnHost extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptLocalConnectVulnOnHost;

    private Set<AttackStep> _cacheParentAttemptLocalConnectVulnOnHost;

    public AttemptLocalConnectVulnOnHost(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptLocalConnectVulnOnHost == null) {
        _cacheChildrenAttemptLocalConnectVulnOnHost = new HashSet<>();
        if (hostApp != null) {
          _cacheChildrenAttemptLocalConnectVulnOnHost.add(hostApp.attemptLocalConnectVuln);
        }
        if (hostApp != null) {
          _cacheChildrenAttemptLocalConnectVulnOnHost.add(hostApp.attemptUseVulnerability);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAttemptLocalConnectVulnOnHost) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptLocalConnectVulnOnHost == null) {
        _cacheParentAttemptLocalConnectVulnOnHost = new HashSet<>();
        _cacheParentAttemptLocalConnectVulnOnHost.add(specificAccess);
      }
      for (AttackStep attackStep : _cacheParentAttemptLocalConnectVulnOnHost) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.attemptLocalConnectVulnOnHost");
    }
  }

  public class Authenticate extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAuthenticate;

    private Set<AttackStep> _cacheParentAuthenticate;

    public Authenticate(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAuthenticate == null) {
        _cacheChildrenAuthenticate = new HashSet<>();
        _cacheChildrenAuthenticate.add(localAccess);
        _cacheChildrenAuthenticate.add(networkAccess);
      }
      for (AttackStep attackStep : _cacheChildrenAuthenticate) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAuthenticate == null) {
        _cacheParentAuthenticate = new HashSet<>();
        for (Identity _0 : executionPrivIds) {
          _cacheParentAuthenticate.add(_0.assume);
        }
        for (Identity _1 : highPrivAppIds) {
          _cacheParentAuthenticate.add(_1.assume);
        }
        for (Group _2 : executionPrivGroups) {
          _cacheParentAuthenticate.add(_2.compromiseGroup);
        }
        for (Group _3 : highPrivAppGroups) {
          _cacheParentAuthenticate.add(_3.compromiseGroup);
        }
      }
      for (AttackStep attackStep : _cacheParentAuthenticate) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.authenticate");
    }
  }

  public class SpecificAccessAuthenticate extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenSpecificAccessAuthenticate;

    private Set<AttackStep> _cacheParentSpecificAccessAuthenticate;

    public SpecificAccessAuthenticate(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenSpecificAccessAuthenticate == null) {
        _cacheChildrenSpecificAccessAuthenticate = new HashSet<>();
        _cacheChildrenSpecificAccessAuthenticate.add(specificAccessFromLocalConnection);
        _cacheChildrenSpecificAccessAuthenticate.add(specificAccessFromNetworkConnection);
      }
      for (AttackStep attackStep : _cacheChildrenSpecificAccessAuthenticate) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentSpecificAccessAuthenticate == null) {
        _cacheParentSpecificAccessAuthenticate = new HashSet<>();
        for (Identity _0 : lowPrivAppIds) {
          _cacheParentSpecificAccessAuthenticate.add(_0.assume);
        }
        for (Group _1 : lowPrivAppGroups) {
          _cacheParentSpecificAccessAuthenticate.add(_1.compromiseGroup);
        }
      }
      for (AttackStep attackStep : _cacheParentSpecificAccessAuthenticate) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.specificAccessAuthenticate");
    }
  }

  public class LocalAccess extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenLocalAccess;

    private Set<AttackStep> _cacheParentLocalAccess;

    public LocalAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenLocalAccess == null) {
        _cacheChildrenLocalAccess = new HashSet<>();
        _cacheChildrenLocalAccess.add(fullAccess);
      }
      for (AttackStep attackStep : _cacheChildrenLocalAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentLocalAccess == null) {
        _cacheParentLocalAccess = new HashSet<>();
        _cacheParentLocalAccess.add(localConnect);
        _cacheParentLocalAccess.add(authenticate);
      }
      for (AttackStep attackStep : _cacheParentLocalAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.localAccess");
    }
  }

  public class NetworkAccess extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenNetworkAccess;

    private Set<AttackStep> _cacheParentNetworkAccess;

    public NetworkAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenNetworkAccess == null) {
        _cacheChildrenNetworkAccess = new HashSet<>();
        _cacheChildrenNetworkAccess.add(fullAccess);
      }
      for (AttackStep attackStep : _cacheChildrenNetworkAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentNetworkAccess == null) {
        _cacheParentNetworkAccess = new HashSet<>();
        _cacheParentNetworkAccess.add(networkConnect);
        _cacheParentNetworkAccess.add(authenticate);
      }
      for (AttackStep attackStep : _cacheParentNetworkAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.networkAccess");
    }
  }

  public class FullAccess extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenFullAccess;

    private Set<AttackStep> _cacheParentFullAccess;

    public FullAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenFullAccess == null) {
        _cacheChildrenFullAccess = new HashSet<>();
        _cacheChildrenFullAccess.add(read);
        _cacheChildrenFullAccess.add(modify);
        _cacheChildrenFullAccess.add(deny);
        for (Application _0 : appExecutedApps) {
          _cacheChildrenFullAccess.add(_0.fullAccess);
        }
        for (Identity _1 : executionPrivIds) {
          _cacheChildrenFullAccess.add(_1.assume);
        }
        for (Group _2 : executionPrivGroups) {
          _cacheChildrenFullAccess.add(_2.compromiseGroup);
        }
        for (Data _3 : containedData) {
          _cacheChildrenFullAccess.add(_3.attemptAccess);
        }
        for (Data _4 : transitData) {
          _cacheChildrenFullAccess.add(_4.attemptAccess);
        }
        _cacheChildrenFullAccess.add(attemptApplicationRespondConnectThroughData);
        _cacheChildrenFullAccess.add(accessNetworkAndConnections);
        if (hostApp != null) {
          _cacheChildrenFullAccess.add(hostApp.localConnect);
        }
        for (RoutingFirewall _5 : managedRoutingFw) {
          _cacheChildrenFullAccess.add(_5.fullAccess);
        }
        _cacheChildrenFullAccess.add(specificAccess);
      }
      for (AttackStep attackStep : _cacheChildrenFullAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentFullAccess == null) {
        _cacheParentFullAccess = new HashSet<>();
        if (hostSystem != null) {
          _cacheParentFullAccess.add(hostSystem.fullAccess);
        }
        if (appSoftProduct != null) {
          _cacheParentFullAccess.add(appSoftProduct.compromiseApplication);
        }
        _cacheParentFullAccess.add(localAccess);
        _cacheParentFullAccess.add(networkAccess);
        if (hostApp != null) {
          _cacheParentFullAccess.add(hostApp.fullAccess);
        }
        _cacheParentFullAccess.add(codeExecutionAfterVulnerability);
      }
      for (AttackStep attackStep : _cacheParentFullAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.fullAccess");
    }
  }

  public class AttemptApplicationRespondConnectThroughData extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptApplicationRespondConnectThroughData;

    private Set<AttackStep> _cacheParentAttemptApplicationRespondConnectThroughData;

    public AttemptApplicationRespondConnectThroughData(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptApplicationRespondConnectThroughData == null) {
        _cacheChildrenAttemptApplicationRespondConnectThroughData = new HashSet<>();
        for (Data _0 : transitData) {
          _cacheChildrenAttemptApplicationRespondConnectThroughData.add(_0.attemptApplicationRespondConnect);
        }
        for (Data _1 : containedData) {
          _cacheChildrenAttemptApplicationRespondConnectThroughData.add(_1.attemptApplicationRespondConnect);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAttemptApplicationRespondConnectThroughData) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptApplicationRespondConnectThroughData == null) {
        _cacheParentAttemptApplicationRespondConnectThroughData = new HashSet<>();
        _cacheParentAttemptApplicationRespondConnectThroughData.add(specificAccess);
        _cacheParentAttemptApplicationRespondConnectThroughData.add(fullAccess);
      }
      for (AttackStep attackStep : _cacheParentAttemptApplicationRespondConnectThroughData) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.attemptApplicationRespondConnectThroughData");
    }
  }

  public class CodeExecutionAfterVulnerability extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCodeExecutionAfterVulnerability;

    private Set<AttackStep> _cacheParentCodeExecutionAfterVulnerability;

    public CodeExecutionAfterVulnerability(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCodeExecutionAfterVulnerability == null) {
        _cacheChildrenCodeExecutionAfterVulnerability = new HashSet<>();
        _cacheChildrenCodeExecutionAfterVulnerability.add(fullAccess);
      }
      for (AttackStep attackStep : _cacheChildrenCodeExecutionAfterVulnerability) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentCodeExecutionAfterVulnerability == null) {
        _cacheParentCodeExecutionAfterVulnerability = new HashSet<>();
        for (var _0 : _reverseallVulnerableSoftwareVulnerability()) {
          if (_0 instanceof UnknownVulnerability) {
            _cacheParentCodeExecutionAfterVulnerability.add(((test.UnknownVulnerability) _0).codeExecution);
          }
        }
        for (var _1 : _reverseallVulnerableSoftwareVulnerability()) {
          if (_1 instanceof ManualHighImpactVulnerability) {
            _cacheParentCodeExecutionAfterVulnerability.add(((test.ManualHighImpactVulnerability) _1).impact);
          }
        }
        for (var _2 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_2 instanceof HHHExploit) {
            _cacheParentCodeExecutionAfterVulnerability.add(((test.HHHExploit) _2).impact);
          }
        }
      }
      for (AttackStep attackStep : _cacheParentCodeExecutionAfterVulnerability) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.codeExecutionAfterVulnerability");
    }
  }

  public class Read extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenRead;

    private Set<AttackStep> _cacheParentRead;

    public Read(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenRead == null) {
        _cacheChildrenRead = new HashSet<>();
        for (Data _0 : containedData) {
          _cacheChildrenRead.add(_0.attemptRead);
        }
      }
      for (AttackStep attackStep : _cacheChildrenRead) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentRead == null) {
        _cacheParentRead = new HashSet<>();
        for (var _1 : _reverseallVulnerableSoftwareVulnerability()) {
          if (_1 instanceof UnknownVulnerability) {
            _cacheParentRead.add(((test.UnknownVulnerability) _1).read);
          }
        }
        for (var _2 : _reverseallVulnerableSoftwareVulnerability()) {
          if (_2 instanceof ManualLowImpactVulnerability) {
            _cacheParentRead.add(((test.ManualLowImpactVulnerability) _2).lowImpact);
          }
        }
        for (var _3 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_3 instanceof HHLExploit) {
            _cacheParentRead.add(((test.HHLExploit) _3).impact);
          }
        }
        for (var _4 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_4 instanceof HHNExploit) {
            _cacheParentRead.add(((test.HHNExploit) _4).impact);
          }
        }
        for (var _5 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_5 instanceof HLHExploit) {
            _cacheParentRead.add(((test.HLHExploit) _5).impact);
          }
        }
        for (var _6 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_6 instanceof HLLExploit) {
            _cacheParentRead.add(((test.HLLExploit) _6).impact);
          }
        }
        for (var _7 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_7 instanceof HLNExploit) {
            _cacheParentRead.add(((test.HLNExploit) _7).impact);
          }
        }
        for (var _8 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_8 instanceof HNHExploit) {
            _cacheParentRead.add(((test.HNHExploit) _8).impact);
          }
        }
        for (var _9 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_9 instanceof HNLExploit) {
            _cacheParentRead.add(((test.HNLExploit) _9).impact);
          }
        }
        for (var _a : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_a instanceof HNNExploit) {
            _cacheParentRead.add(((test.HNNExploit) _a).impact);
          }
        }
        for (var _b : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_b instanceof LHHExploit) {
            _cacheParentRead.add(((test.LHHExploit) _b).lowImpact);
          }
        }
        for (var _c : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_c instanceof LHLExploit) {
            _cacheParentRead.add(((test.LHLExploit) _c).lowImpact);
          }
        }
        for (var _d : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_d instanceof LHNExploit) {
            _cacheParentRead.add(((test.LHNExploit) _d).lowImpact);
          }
        }
        for (var _e : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_e instanceof LLHExploit) {
            _cacheParentRead.add(((test.LLHExploit) _e).lowImpact);
          }
        }
        for (var _f : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_f instanceof LLLExploit) {
            _cacheParentRead.add(((test.LLLExploit) _f).lowImpact);
          }
        }
        for (var _10 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_10 instanceof LLNExploit) {
            _cacheParentRead.add(((test.LLNExploit) _10).lowImpact);
          }
        }
        for (var _11 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_11 instanceof LNHExploit) {
            _cacheParentRead.add(((test.LNHExploit) _11).lowImpact);
          }
        }
        for (var _12 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_12 instanceof LNLExploit) {
            _cacheParentRead.add(((test.LNLExploit) _12).lowImpact);
          }
        }
        for (var _13 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_13 instanceof LNNExploit) {
            _cacheParentRead.add(((test.LNNExploit) _13).lowImpact);
          }
        }
        _cacheParentRead.add(fullAccess);
      }
      for (AttackStep attackStep : _cacheParentRead) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.read");
    }
  }

  public class Modify extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenModify;

    private Set<AttackStep> _cacheParentModify;

    public Modify(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenModify == null) {
        _cacheChildrenModify = new HashSet<>();
        for (Data _0 : containedData) {
          _cacheChildrenModify.add(_0.attemptAccess);
        }
      }
      for (AttackStep attackStep : _cacheChildrenModify) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentModify == null) {
        _cacheParentModify = new HashSet<>();
        for (var _1 : _reverseallVulnerableSoftwareVulnerability()) {
          if (_1 instanceof UnknownVulnerability) {
            _cacheParentModify.add(((test.UnknownVulnerability) _1).modify);
          }
        }
        for (var _2 : _reverseallVulnerableSoftwareVulnerability()) {
          if (_2 instanceof ManualLowImpactVulnerability) {
            _cacheParentModify.add(((test.ManualLowImpactVulnerability) _2).lowImpact);
          }
        }
        for (var _3 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_3 instanceof HHLExploit) {
            _cacheParentModify.add(((test.HHLExploit) _3).impact);
          }
        }
        for (var _4 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_4 instanceof HHNExploit) {
            _cacheParentModify.add(((test.HHNExploit) _4).impact);
          }
        }
        for (var _5 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_5 instanceof HLHExploit) {
            _cacheParentModify.add(((test.HLHExploit) _5).lowImpact);
          }
        }
        for (var _6 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_6 instanceof HLLExploit) {
            _cacheParentModify.add(((test.HLLExploit) _6).lowImpact);
          }
        }
        for (var _7 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_7 instanceof HLNExploit) {
            _cacheParentModify.add(((test.HLNExploit) _7).lowImpact);
          }
        }
        for (var _8 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_8 instanceof LHHExploit) {
            _cacheParentModify.add(((test.LHHExploit) _8).impact);
          }
        }
        for (var _9 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_9 instanceof LHLExploit) {
            _cacheParentModify.add(((test.LHLExploit) _9).impact);
          }
        }
        for (var _a : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_a instanceof LHNExploit) {
            _cacheParentModify.add(((test.LHNExploit) _a).impact);
          }
        }
        for (var _b : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_b instanceof LLHExploit) {
            _cacheParentModify.add(((test.LLHExploit) _b).lowImpact);
          }
        }
        for (var _c : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_c instanceof LLLExploit) {
            _cacheParentModify.add(((test.LLLExploit) _c).lowImpact);
          }
        }
        for (var _d : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_d instanceof LLNExploit) {
            _cacheParentModify.add(((test.LLNExploit) _d).lowImpact);
          }
        }
        for (var _e : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_e instanceof NHHExploit) {
            _cacheParentModify.add(((test.NHHExploit) _e).impact);
          }
        }
        for (var _f : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_f instanceof NHLExploit) {
            _cacheParentModify.add(((test.NHLExploit) _f).impact);
          }
        }
        for (var _10 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_10 instanceof NHNExploit) {
            _cacheParentModify.add(((test.NHNExploit) _10).impact);
          }
        }
        for (var _11 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_11 instanceof NLHExploit) {
            _cacheParentModify.add(((test.NLHExploit) _11).lowImpact);
          }
        }
        for (var _12 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_12 instanceof NLLExploit) {
            _cacheParentModify.add(((test.NLLExploit) _12).lowImpact);
          }
        }
        for (var _13 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_13 instanceof NLNExploit) {
            _cacheParentModify.add(((test.NLNExploit) _13).lowImpact);
          }
        }
        _cacheParentModify.add(fullAccess);
      }
      for (AttackStep attackStep : _cacheParentModify) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.modify");
    }
  }

  public class Deny extends Object.Deny {
    private Set<AttackStep> _cacheChildrenDeny;

    private Set<AttackStep> _cacheParentDeny;

    public Deny(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenDeny == null) {
        _cacheChildrenDeny = new HashSet<>();
        for (Data _0 : containedData) {
          _cacheChildrenDeny.add(_0.attemptDeny);
        }
        for (Data _1 : transitData) {
          _cacheChildrenDeny.add(_1.attemptDeny);
        }
        for (Application _2 : appExecutedApps) {
          _cacheChildrenDeny.add(_2.deny);
        }
      }
      for (AttackStep attackStep : _cacheChildrenDeny) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentDeny == null) {
        _cacheParentDeny = new HashSet<>();
        for (var _3 : _reverseallVulnerableSoftwareVulnerability()) {
          if (_3 instanceof UnknownVulnerability) {
            _cacheParentDeny.add(((test.UnknownVulnerability) _3).deny);
          }
        }
        for (var _4 : _reverseallVulnerableSoftwareVulnerability()) {
          if (_4 instanceof ManualLowImpactVulnerability) {
            _cacheParentDeny.add(((test.ManualLowImpactVulnerability) _4).lowImpact);
          }
        }
        for (var _5 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_5 instanceof HHLExploit) {
            _cacheParentDeny.add(((test.HHLExploit) _5).lowImpact);
          }
        }
        for (var _6 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_6 instanceof HLHExploit) {
            _cacheParentDeny.add(((test.HLHExploit) _6).impact);
          }
        }
        for (var _7 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_7 instanceof HLLExploit) {
            _cacheParentDeny.add(((test.HLLExploit) _7).lowImpact);
          }
        }
        for (var _8 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_8 instanceof HNHExploit) {
            _cacheParentDeny.add(((test.HNHExploit) _8).impact);
          }
        }
        for (var _9 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_9 instanceof HNLExploit) {
            _cacheParentDeny.add(((test.HNLExploit) _9).lowImpact);
          }
        }
        for (var _a : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_a instanceof LHHExploit) {
            _cacheParentDeny.add(((test.LHHExploit) _a).impact);
          }
        }
        for (var _b : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_b instanceof LHLExploit) {
            _cacheParentDeny.add(((test.LHLExploit) _b).lowImpact);
          }
        }
        for (var _c : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_c instanceof LLHExploit) {
            _cacheParentDeny.add(((test.LLHExploit) _c).impact);
          }
        }
        for (var _d : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_d instanceof LLLExploit) {
            _cacheParentDeny.add(((test.LLLExploit) _d).lowImpact);
          }
        }
        for (var _e : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_e instanceof LNHExploit) {
            _cacheParentDeny.add(((test.LNHExploit) _e).impact);
          }
        }
        for (var _f : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_f instanceof LNLExploit) {
            _cacheParentDeny.add(((test.LNLExploit) _f).lowImpact);
          }
        }
        for (var _10 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_10 instanceof NHHExploit) {
            _cacheParentDeny.add(((test.NHHExploit) _10).impact);
          }
        }
        for (var _11 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_11 instanceof NHLExploit) {
            _cacheParentDeny.add(((test.NHLExploit) _11).lowImpact);
          }
        }
        for (var _12 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_12 instanceof NLHExploit) {
            _cacheParentDeny.add(((test.NLHExploit) _12).impact);
          }
        }
        for (var _13 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_13 instanceof NLLExploit) {
            _cacheParentDeny.add(((test.NLLExploit) _13).lowImpact);
          }
        }
        for (var _14 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_14 instanceof NNHExploit) {
            _cacheParentDeny.add(((test.NNHExploit) _14).impact);
          }
        }
        for (var _15 : _reverseallVulnerableSoftwareAutomaticExploit()) {
          if (_15 instanceof NNLExploit) {
            _cacheParentDeny.add(((test.NNLExploit) _15).lowImpact);
          }
        }
        if (hostSystem != null) {
          _cacheParentDeny.add(hostSystem.deny);
        }
        _cacheParentDeny.add(fullAccess);
        if (hostApp != null) {
          _cacheParentDeny.add(hostApp.deny);
        }
        _cacheParentDeny.add(denyFromConnectionRule);
      }
      for (AttackStep attackStep : _cacheParentDeny) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.deny");
    }
  }

  public class DenyFromConnectionRule extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenDenyFromConnectionRule;

    private Set<AttackStep> _cacheParentDenyFromConnectionRule;

    public DenyFromConnectionRule(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenDenyFromConnectionRule == null) {
        _cacheChildrenDenyFromConnectionRule = new HashSet<>();
        _cacheChildrenDenyFromConnectionRule.add(deny);
      }
      for (AttackStep attackStep : _cacheChildrenDenyFromConnectionRule) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentDenyFromConnectionRule == null) {
        _cacheParentDenyFromConnectionRule = new HashSet<>();
        for (var _0 : _reverseallowedApplicationConnectionsApplicationsNetwork()) {
          _cacheParentDenyFromConnectionRule.add(_0.denialOfService);
        }
        for (var _1 : _reverseallApplicationsConnectionRule()) {
          _cacheParentDenyFromConnectionRule.add(_1.denialOfService);
        }
      }
      for (AttackStep attackStep : _cacheParentDenyFromConnectionRule) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Application.denyFromConnectionRule");
    }
  }
}
