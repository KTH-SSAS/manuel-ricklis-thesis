package test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;
import core.Defense;
import java.lang.Boolean;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class ConnectionRule extends Asset {
  public Disabled disabled;

  public AttemptAccessNetworks attemptAccessNetworks;

  public AccessNetworks accessNetworks;

  public AttemptConnectToApplications attemptConnectToApplications;

  public ConnectToApplications connectToApplications;

  public AttemptTransmitResponse attemptTransmitResponse;

  public TransmitResponse transmitResponse;

  public AttemptTransmit attemptTransmit;

  public Transmit transmit;

  public AttemptDenialOfService attemptDenialOfService;

  public DenialOfService denialOfService;

  private Set<Application> _cacheserverApplicationsConnectionRule;

  private Set<Application> _cacheclientApplicationsConnectionRule;

  private Set<Application> _cacheallApplicationsConnectionRule;

  private Set<Application> _cachereverseclientApplicationConnectionsApplication;

  private Set<Application> _cachereverseserverApplicationConnectionsApplication;

  private Set<Application> _cachereverseallApplicationConnectionsApplication;

  private Set<Network> _cachereverseallowedNetworkConnectionsNetwork;

  private Set<Network> _cachereverseallNetConnectionsNetwork;

  public RoutingFirewall routingFirewalls = null;

  public Set<Application> applications = new HashSet<>();

  public Set<Application> inApplications = new HashSet<>();

  public Set<Application> outApplications = new HashSet<>();

  public Set<Network> networks = new HashSet<>();

  public Set<Network> outNetworks = new HashSet<>();

  public Set<Network> inNetworks = new HashSet<>();

  public Set<Network> diodeInNetworks = new HashSet<>();

  public ConnectionRule(String name, boolean isDisabledEnabled) {
    super(name);
    assetClassName = "ConnectionRule";
    if (disabled != null) {
      AttackStep.allAttackSteps.remove(disabled.disable);
    }
    Defense.allDefenses.remove(disabled);
    disabled = new Disabled(name, isDisabledEnabled);
    AttackStep.allAttackSteps.remove(attemptAccessNetworks);
    attemptAccessNetworks = new AttemptAccessNetworks(name);
    AttackStep.allAttackSteps.remove(accessNetworks);
    accessNetworks = new AccessNetworks(name);
    AttackStep.allAttackSteps.remove(attemptConnectToApplications);
    attemptConnectToApplications = new AttemptConnectToApplications(name);
    AttackStep.allAttackSteps.remove(connectToApplications);
    connectToApplications = new ConnectToApplications(name);
    AttackStep.allAttackSteps.remove(attemptTransmitResponse);
    attemptTransmitResponse = new AttemptTransmitResponse(name);
    AttackStep.allAttackSteps.remove(transmitResponse);
    transmitResponse = new TransmitResponse(name);
    AttackStep.allAttackSteps.remove(attemptTransmit);
    attemptTransmit = new AttemptTransmit(name);
    AttackStep.allAttackSteps.remove(transmit);
    transmit = new Transmit(name);
    AttackStep.allAttackSteps.remove(attemptDenialOfService);
    attemptDenialOfService = new AttemptDenialOfService(name);
    AttackStep.allAttackSteps.remove(denialOfService);
    denialOfService = new DenialOfService(name);
  }

  public ConnectionRule(String name) {
    super(name);
    assetClassName = "ConnectionRule";
    if (disabled != null) {
      AttackStep.allAttackSteps.remove(disabled.disable);
    }
    Defense.allDefenses.remove(disabled);
    disabled = new Disabled(name, false);
    AttackStep.allAttackSteps.remove(attemptAccessNetworks);
    attemptAccessNetworks = new AttemptAccessNetworks(name);
    AttackStep.allAttackSteps.remove(accessNetworks);
    accessNetworks = new AccessNetworks(name);
    AttackStep.allAttackSteps.remove(attemptConnectToApplications);
    attemptConnectToApplications = new AttemptConnectToApplications(name);
    AttackStep.allAttackSteps.remove(connectToApplications);
    connectToApplications = new ConnectToApplications(name);
    AttackStep.allAttackSteps.remove(attemptTransmitResponse);
    attemptTransmitResponse = new AttemptTransmitResponse(name);
    AttackStep.allAttackSteps.remove(transmitResponse);
    transmitResponse = new TransmitResponse(name);
    AttackStep.allAttackSteps.remove(attemptTransmit);
    attemptTransmit = new AttemptTransmit(name);
    AttackStep.allAttackSteps.remove(transmit);
    transmit = new Transmit(name);
    AttackStep.allAttackSteps.remove(attemptDenialOfService);
    attemptDenialOfService = new AttemptDenialOfService(name);
    AttackStep.allAttackSteps.remove(denialOfService);
    denialOfService = new DenialOfService(name);
  }

  public ConnectionRule(boolean isDisabledEnabled) {
    this("Anonymous", isDisabledEnabled);
  }

  public ConnectionRule() {
    this("Anonymous");
  }

  protected Set<Application> _serverApplicationsConnectionRule() {
    if (_cacheserverApplicationsConnectionRule == null) {
      _cacheserverApplicationsConnectionRule = new HashSet<>();
      Set<Application> _1 = new HashSet<>();
      Set<Application> _2 = new HashSet<>();
      for (Application _3 : applications) {
        _1.add(_3);
      }
      for (Application _4 : inApplications) {
        _2.add(_4);
      }
      _1.addAll(_2);
      for (Application _5 : _1) {
        _cacheserverApplicationsConnectionRule.add(_5);
      }
    }
    return _cacheserverApplicationsConnectionRule;
  }

  protected Set<Application> _clientApplicationsConnectionRule() {
    if (_cacheclientApplicationsConnectionRule == null) {
      _cacheclientApplicationsConnectionRule = new HashSet<>();
      Set<Application> _6 = new HashSet<>();
      Set<Application> _7 = new HashSet<>();
      for (Application _8 : applications) {
        _6.add(_8);
      }
      for (Application _9 : outApplications) {
        _7.add(_9);
      }
      _6.addAll(_7);
      for (Application _a : _6) {
        _cacheclientApplicationsConnectionRule.add(_a);
      }
    }
    return _cacheclientApplicationsConnectionRule;
  }

  protected Set<Application> _allApplicationsConnectionRule() {
    if (_cacheallApplicationsConnectionRule == null) {
      _cacheallApplicationsConnectionRule = new HashSet<>();
      Set<Application> _b = new HashSet<>();
      Set<Application> _c = new HashSet<>();
      Set<Application> _d = new HashSet<>();
      Set<Application> _e = new HashSet<>();
      for (Application _f : applications) {
        _d.add(_f);
      }
      for (Application _10 : inApplications) {
        _e.add(_10);
      }
      _d.addAll(_e);
      for (Application _11 : _d) {
        _b.add(_11);
      }
      for (Application _12 : outApplications) {
        _c.add(_12);
      }
      _b.addAll(_c);
      for (Application _13 : _b) {
        _cacheallApplicationsConnectionRule.add(_13);
      }
    }
    return _cacheallApplicationsConnectionRule;
  }

  protected Set<Application> _reverseclientApplicationConnectionsApplication() {
    if (_cachereverseclientApplicationConnectionsApplication == null) {
      _cachereverseclientApplicationConnectionsApplication = new HashSet<>();
      Set<Application> _14 = new HashSet<>();
      Set<Application> _15 = new HashSet<>();
      for (Application _16 : outApplications) {
        _14.add(_16);
      }
      for (Application _17 : applications) {
        _15.add(_17);
      }
      _14.addAll(_15);
      for (Application _18 : _14) {
        _cachereverseclientApplicationConnectionsApplication.add(_18);
      }
    }
    return _cachereverseclientApplicationConnectionsApplication;
  }

  protected Set<Application> _reverseserverApplicationConnectionsApplication() {
    if (_cachereverseserverApplicationConnectionsApplication == null) {
      _cachereverseserverApplicationConnectionsApplication = new HashSet<>();
      Set<Application> _19 = new HashSet<>();
      Set<Application> _1a = new HashSet<>();
      for (Application _1b : inApplications) {
        _19.add(_1b);
      }
      for (Application _1c : applications) {
        _1a.add(_1c);
      }
      _19.addAll(_1a);
      for (Application _1d : _19) {
        _cachereverseserverApplicationConnectionsApplication.add(_1d);
      }
    }
    return _cachereverseserverApplicationConnectionsApplication;
  }

  protected Set<Application> _reverseallApplicationConnectionsApplication() {
    if (_cachereverseallApplicationConnectionsApplication == null) {
      _cachereverseallApplicationConnectionsApplication = new HashSet<>();
      Set<Application> _1e = new HashSet<>();
      Set<Application> _1f = new HashSet<>();
      for (Application _20 : inApplications) {
        _1e.add(_20);
      }
      Set<Application> _21 = new HashSet<>();
      Set<Application> _22 = new HashSet<>();
      for (Application _23 : outApplications) {
        _21.add(_23);
      }
      for (Application _24 : applications) {
        _22.add(_24);
      }
      _21.addAll(_22);
      for (Application _25 : _21) {
        _1f.add(_25);
      }
      _1e.addAll(_1f);
      for (Application _26 : _1e) {
        _cachereverseallApplicationConnectionsApplication.add(_26);
      }
    }
    return _cachereverseallApplicationConnectionsApplication;
  }

  protected Set<Network> _reverseallowedNetworkConnectionsNetwork() {
    if (_cachereverseallowedNetworkConnectionsNetwork == null) {
      _cachereverseallowedNetworkConnectionsNetwork = new HashSet<>();
      Set<Network> _27 = new HashSet<>();
      Set<Network> _28 = new HashSet<>();
      for (Network _29 : outNetworks) {
        _27.add(_29);
      }
      for (Network _2a : networks) {
        _28.add(_2a);
      }
      _27.addAll(_28);
      for (Network _2b : _27) {
        _cachereverseallowedNetworkConnectionsNetwork.add(_2b);
      }
    }
    return _cachereverseallowedNetworkConnectionsNetwork;
  }

  protected Set<Network> _reverseallNetConnectionsNetwork() {
    if (_cachereverseallNetConnectionsNetwork == null) {
      _cachereverseallNetConnectionsNetwork = new HashSet<>();
      Set<Network> _2c = new HashSet<>();
      Set<Network> _2d = new HashSet<>();
      for (Network _2e : diodeInNetworks) {
        _2c.add(_2e);
      }
      Set<Network> _2f = new HashSet<>();
      Set<Network> _30 = new HashSet<>();
      for (Network _31 : outNetworks) {
        _2f.add(_31);
      }
      Set<Network> _32 = new HashSet<>();
      Set<Network> _33 = new HashSet<>();
      for (Network _34 : inNetworks) {
        _32.add(_34);
      }
      for (Network _35 : networks) {
        _33.add(_35);
      }
      _32.addAll(_33);
      for (Network _36 : _32) {
        _30.add(_36);
      }
      _2f.addAll(_30);
      for (Network _37 : _2f) {
        _2d.add(_37);
      }
      _2c.addAll(_2d);
      for (Network _38 : _2c) {
        _cachereverseallNetConnectionsNetwork.add(_38);
      }
    }
    return _cachereverseallNetConnectionsNetwork;
  }

  public void addRoutingFirewalls(RoutingFirewall routingFirewalls) {
    this.routingFirewalls = routingFirewalls;
    routingFirewalls.connectionRules.add(this);
  }

  public void addApplications(Application applications) {
    this.applications.add(applications);
    applications.appConnections.add(this);
  }

  public void addInApplications(Application inApplications) {
    this.inApplications.add(inApplications);
    inApplications.ingoingAppConnections.add(this);
  }

  public void addOutApplications(Application outApplications) {
    this.outApplications.add(outApplications);
    outApplications.outgoingAppConnections.add(this);
  }

  public void addNetworks(Network networks) {
    this.networks.add(networks);
    networks.netConnections.add(this);
  }

  public void addOutNetworks(Network outNetworks) {
    this.outNetworks.add(outNetworks);
    outNetworks.outgoingNetConnections.add(this);
  }

  public void addInNetworks(Network inNetworks) {
    this.inNetworks.add(inNetworks);
    inNetworks.ingoingNetConnections.add(this);
  }

  public void addDiodeInNetworks(Network diodeInNetworks) {
    this.diodeInNetworks.add(diodeInNetworks);
    diodeInNetworks.diodeIngoingNetConnections.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("routingFirewalls")) {
      return RoutingFirewall.class.getName();
    } else if (field.equals("applications")) {
      return Application.class.getName();
    } else if (field.equals("inApplications")) {
      return Application.class.getName();
    } else if (field.equals("outApplications")) {
      return Application.class.getName();
    } else if (field.equals("networks")) {
      return Network.class.getName();
    } else if (field.equals("outNetworks")) {
      return Network.class.getName();
    } else if (field.equals("inNetworks")) {
      return Network.class.getName();
    } else if (field.equals("diodeInNetworks")) {
      return Network.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("routingFirewalls")) {
      if (routingFirewalls != null) {
        assets.add(routingFirewalls);
      }
    } else if (field.equals("applications")) {
      assets.addAll(applications);
    } else if (field.equals("inApplications")) {
      assets.addAll(inApplications);
    } else if (field.equals("outApplications")) {
      assets.addAll(outApplications);
    } else if (field.equals("networks")) {
      assets.addAll(networks);
    } else if (field.equals("outNetworks")) {
      assets.addAll(outNetworks);
    } else if (field.equals("inNetworks")) {
      assets.addAll(inNetworks);
    } else if (field.equals("diodeInNetworks")) {
      assets.addAll(diodeInNetworks);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (routingFirewalls != null) {
      assets.add(routingFirewalls);
    }
    assets.addAll(applications);
    assets.addAll(inApplications);
    assets.addAll(outApplications);
    assets.addAll(networks);
    assets.addAll(outNetworks);
    assets.addAll(inNetworks);
    assets.addAll(diodeInNetworks);
    return assets;
  }

  public class Disabled extends Defense {
    public Disabled(String name) {
      this(name, false);
    }

    public Disabled(String name, Boolean isEnabled) {
      super(name);
      defaultValue = isEnabled;
      disable = new Disable(name);
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenDisabled;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenDisabled == null) {
          _cacheChildrenDisabled = new HashSet<>();
          _cacheChildrenDisabled.add(accessNetworks);
          _cacheChildrenDisabled.add(connectToApplications);
          _cacheChildrenDisabled.add(transmit);
          _cacheChildrenDisabled.add(transmitResponse);
          _cacheChildrenDisabled.add(denialOfService);
        }
        for (AttackStep attackStep : _cacheChildrenDisabled) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "ConnectionRule.disabled";
      }
    }
  }

  public class AttemptAccessNetworks extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptAccessNetworks;

    private Set<AttackStep> _cacheParentAttemptAccessNetworks;

    public AttemptAccessNetworks(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptAccessNetworks == null) {
        _cacheChildrenAttemptAccessNetworks = new HashSet<>();
        _cacheChildrenAttemptAccessNetworks.add(accessNetworks);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptAccessNetworks) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptAccessNetworks == null) {
        _cacheParentAttemptAccessNetworks = new HashSet<>();
        for (var _0 : _reverseallApplicationConnectionsApplication()) {
          _cacheParentAttemptAccessNetworks.add(_0.accessNetworkAndConnections);
        }
        for (var _1 : _reverseallowedNetworkConnectionsNetwork()) {
          _cacheParentAttemptAccessNetworks.add(_1.networkForwarding);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptAccessNetworks) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("ConnectionRule.attemptAccessNetworks");
    }
  }

  public class AccessNetworks extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenAccessNetworks;

    private Set<AttackStep> _cacheParentAccessNetworks;

    public AccessNetworks(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAccessNetworks == null) {
        _cacheChildrenAccessNetworks = new HashSet<>();
        Set<Network> _0 = new HashSet<>();
        Set<Network> _1 = new HashSet<>();
        Set<Network> _2 = new HashSet<>();
        Set<Network> _3 = new HashSet<>();
        for (Network _4 : networks) {
          _2.add(_4);
        }
        for (Network _5 : inNetworks) {
          _3.add(_5);
        }
        _2.addAll(_3);
        for (Network _6 : _2) {
          _0.add(_6);
        }
        for (Network _7 : diodeInNetworks) {
          _1.add(_7);
        }
        _0.addAll(_1);
        for (Network _8 : _0) {
          _cacheChildrenAccessNetworks.add(_8.access);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAccessNetworks) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAccessNetworks == null) {
        _cacheParentAccessNetworks = new HashSet<>();
        _cacheParentAccessNetworks.add(disabled.disable);
        _cacheParentAccessNetworks.add(attemptAccessNetworks);
      }
      for (AttackStep attackStep : _cacheParentAccessNetworks) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("ConnectionRule.accessNetworks");
    }
  }

  public class AttemptConnectToApplications extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptConnectToApplications;

    private Set<AttackStep> _cacheParentAttemptConnectToApplications;

    public AttemptConnectToApplications(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptConnectToApplications == null) {
        _cacheChildrenAttemptConnectToApplications = new HashSet<>();
        _cacheChildrenAttemptConnectToApplications.add(connectToApplications);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptConnectToApplications) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptConnectToApplications == null) {
        _cacheParentAttemptConnectToApplications = new HashSet<>();
        for (var _0 : _reverseclientApplicationConnectionsApplication()) {
          _cacheParentAttemptConnectToApplications.add(_0.accessNetworkAndConnections);
        }
        for (var _1 : _reverseallowedNetworkConnectionsNetwork()) {
          _cacheParentAttemptConnectToApplications.add(_1.networkForwarding);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptConnectToApplications) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("ConnectionRule.attemptConnectToApplications");
    }
  }

  public class ConnectToApplications extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenConnectToApplications;

    private Set<AttackStep> _cacheParentConnectToApplications;

    public ConnectToApplications(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenConnectToApplications == null) {
        _cacheChildrenConnectToApplications = new HashSet<>();
        for (var _0 : _serverApplicationsConnectionRule()) {
          _cacheChildrenConnectToApplications.add(_0.networkConnect);
        }
      }
      for (AttackStep attackStep : _cacheChildrenConnectToApplications) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentConnectToApplications == null) {
        _cacheParentConnectToApplications = new HashSet<>();
        _cacheParentConnectToApplications.add(disabled.disable);
        _cacheParentConnectToApplications.add(attemptConnectToApplications);
      }
      for (AttackStep attackStep : _cacheParentConnectToApplications) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("ConnectionRule.connectToApplications");
    }
  }

  public class AttemptTransmitResponse extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptTransmitResponse;

    private Set<AttackStep> _cacheParentAttemptTransmitResponse;

    public AttemptTransmitResponse(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptTransmitResponse == null) {
        _cacheChildrenAttemptTransmitResponse = new HashSet<>();
        _cacheChildrenAttemptTransmitResponse.add(transmitResponse);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptTransmitResponse) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptTransmitResponse == null) {
        _cacheParentAttemptTransmitResponse = new HashSet<>();
        for (var _0 : _reverseserverApplicationConnectionsApplication()) {
          _cacheParentAttemptTransmitResponse.add(_0.accessNetworkAndConnections);
        }
        for (var _1 : _reverseallowedNetworkConnectionsNetwork()) {
          _cacheParentAttemptTransmitResponse.add(_1.networkForwarding);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptTransmitResponse) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("ConnectionRule.attemptTransmitResponse");
    }
  }

  public class TransmitResponse extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenTransmitResponse;

    private Set<AttackStep> _cacheParentTransmitResponse;

    public TransmitResponse(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenTransmitResponse == null) {
        _cacheChildrenTransmitResponse = new HashSet<>();
        for (var _0 : _clientApplicationsConnectionRule()) {
          _cacheChildrenTransmitResponse.add(_0.networkRespondConnect);
        }
      }
      for (AttackStep attackStep : _cacheChildrenTransmitResponse) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentTransmitResponse == null) {
        _cacheParentTransmitResponse = new HashSet<>();
        for (Application _1 : applications) {
          for (Vulnerability _2 : _1.vulnerabilities) {
            if (_2 instanceof UnknownVulnerability) {
              _cacheParentTransmitResponse.add(((test.UnknownVulnerability) _2).requestForgery);
            }
          }
        }
        _cacheParentTransmitResponse.add(disabled.disable);
        _cacheParentTransmitResponse.add(attemptTransmitResponse);
      }
      for (AttackStep attackStep : _cacheParentTransmitResponse) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("ConnectionRule.transmitResponse");
    }
  }

  public class AttemptTransmit extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptTransmit;

    private Set<AttackStep> _cacheParentAttemptTransmit;

    public AttemptTransmit(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptTransmit == null) {
        _cacheChildrenAttemptTransmit = new HashSet<>();
        _cacheChildrenAttemptTransmit.add(transmit);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptTransmit) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptTransmit == null) {
        _cacheParentAttemptTransmit = new HashSet<>();
        for (var _0 : _reverseclientApplicationConnectionsApplication()) {
          _cacheParentAttemptTransmit.add(_0.accessNetworkAndConnections);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptTransmit) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("ConnectionRule.attemptTransmit");
    }
  }

  public class Transmit extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenTransmit;

    private Set<AttackStep> _cacheParentTransmit;

    public Transmit(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenTransmit == null) {
        _cacheChildrenTransmit = new HashSet<>();
        for (var _0 : _clientApplicationsConnectionRule()) {
          _cacheChildrenTransmit.add(_0.networkRequestConnect);
        }
      }
      for (AttackStep attackStep : _cacheChildrenTransmit) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentTransmit == null) {
        _cacheParentTransmit = new HashSet<>();
        for (Application _1 : applications) {
          for (Vulnerability _2 : _1.vulnerabilities) {
            if (_2 instanceof UnknownVulnerability) {
              _cacheParentTransmit.add(((test.UnknownVulnerability) _2).requestForgery);
            }
          }
        }
        _cacheParentTransmit.add(disabled.disable);
        _cacheParentTransmit.add(attemptTransmit);
      }
      for (AttackStep attackStep : _cacheParentTransmit) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("ConnectionRule.transmit");
    }
  }

  public class AttemptDenialOfService extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptDenialOfService;

    private Set<AttackStep> _cacheParentAttemptDenialOfService;

    public AttemptDenialOfService(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptDenialOfService == null) {
        _cacheChildrenAttemptDenialOfService = new HashSet<>();
        _cacheChildrenAttemptDenialOfService.add(denialOfService);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptDenialOfService) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptDenialOfService == null) {
        _cacheParentAttemptDenialOfService = new HashSet<>();
        for (var _0 : _reverseallNetConnectionsNetwork()) {
          _cacheParentAttemptDenialOfService.add(_0.denialOfService);
        }
        if (routingFirewalls != null) {
          _cacheParentAttemptDenialOfService.add(routingFirewalls.denialOfService);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptDenialOfService) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("ConnectionRule.attemptDenialOfService");
    }
  }

  public class DenialOfService extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenDenialOfService;

    private Set<AttackStep> _cacheParentDenialOfService;

    public DenialOfService(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenDenialOfService == null) {
        _cacheChildrenDenialOfService = new HashSet<>();
        for (var _0 : _allApplicationsConnectionRule()) {
          _cacheChildrenDenialOfService.add(_0.denyFromConnectionRule);
        }
      }
      for (AttackStep attackStep : _cacheChildrenDenialOfService) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentDenialOfService == null) {
        _cacheParentDenialOfService = new HashSet<>();
        _cacheParentDenialOfService.add(disabled.disable);
        _cacheParentDenialOfService.add(attemptDenialOfService);
      }
      for (AttackStep attackStep : _cacheParentDenialOfService) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("ConnectionRule.denialOfService");
    }
  }
}
