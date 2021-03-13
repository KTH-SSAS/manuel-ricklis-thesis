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

public class Network extends Asset {
  public PhysicalAccess physicalAccess;

  public NetworkAccessControl networkAccessControl;

  public BypassAccessControl bypassAccessControl;

  public AttemptAccess attemptAccess;

  public Access access;

  public SuccessfulAccess successfulAccess;

  public NetworkForwarding networkForwarding;

  public DenialOfService denialOfService;

  public AccessNetworkData accessNetworkData;

  public EavesdropDefense eavesdropDefense;

  public ManInTheMiddleDefense manInTheMiddleDefense;

  public Eavesdrop eavesdrop;

  public BypassEavesdropProtection bypassEavesdropProtection;

  public ManInTheMiddle manInTheMiddle;

  public BypassMitMProtection bypassMitMProtection;

  public EavesdropAfterPhysicalAccess eavesdropAfterPhysicalAccess;

  private Set<Application> _cacheallowedApplicationConnectionsApplicationsNetwork;

  private Set<ConnectionRule> _cacheallowedNetworkConnectionsNetwork;

  private Set<ConnectionRule> _cacheallNetConnectionsNetwork;

  public Set<Application> applications = new HashSet<>();

  public Set<Application> clientApplications = new HashSet<>();

  public Set<ConnectionRule> netConnections = new HashSet<>();

  public Set<ConnectionRule> outgoingNetConnections = new HashSet<>();

  public Set<ConnectionRule> ingoingNetConnections = new HashSet<>();

  public Set<ConnectionRule> diodeIngoingNetConnections = new HashSet<>();

  public Set<Data> transitData = new HashSet<>();

  public Network(String name, boolean isNetworkAccessControlEnabled,
      boolean isEavesdropDefenseEnabled, boolean isManInTheMiddleDefenseEnabled) {
    super(name);
    assetClassName = "Network";
    AttackStep.allAttackSteps.remove(physicalAccess);
    physicalAccess = new PhysicalAccess(name);
    if (networkAccessControl != null) {
      AttackStep.allAttackSteps.remove(networkAccessControl.disable);
    }
    Defense.allDefenses.remove(networkAccessControl);
    networkAccessControl = new NetworkAccessControl(name, isNetworkAccessControlEnabled);
    AttackStep.allAttackSteps.remove(bypassAccessControl);
    bypassAccessControl = new BypassAccessControl(name);
    AttackStep.allAttackSteps.remove(attemptAccess);
    attemptAccess = new AttemptAccess(name);
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
    AttackStep.allAttackSteps.remove(successfulAccess);
    successfulAccess = new SuccessfulAccess(name);
    AttackStep.allAttackSteps.remove(networkForwarding);
    networkForwarding = new NetworkForwarding(name);
    AttackStep.allAttackSteps.remove(denialOfService);
    denialOfService = new DenialOfService(name);
    AttackStep.allAttackSteps.remove(accessNetworkData);
    accessNetworkData = new AccessNetworkData(name);
    if (eavesdropDefense != null) {
      AttackStep.allAttackSteps.remove(eavesdropDefense.disable);
    }
    Defense.allDefenses.remove(eavesdropDefense);
    eavesdropDefense = new EavesdropDefense(name, isEavesdropDefenseEnabled);
    if (manInTheMiddleDefense != null) {
      AttackStep.allAttackSteps.remove(manInTheMiddleDefense.disable);
    }
    Defense.allDefenses.remove(manInTheMiddleDefense);
    manInTheMiddleDefense = new ManInTheMiddleDefense(name, isManInTheMiddleDefenseEnabled);
    AttackStep.allAttackSteps.remove(eavesdrop);
    eavesdrop = new Eavesdrop(name);
    AttackStep.allAttackSteps.remove(bypassEavesdropProtection);
    bypassEavesdropProtection = new BypassEavesdropProtection(name);
    AttackStep.allAttackSteps.remove(manInTheMiddle);
    manInTheMiddle = new ManInTheMiddle(name);
    AttackStep.allAttackSteps.remove(bypassMitMProtection);
    bypassMitMProtection = new BypassMitMProtection(name);
    AttackStep.allAttackSteps.remove(eavesdropAfterPhysicalAccess);
    eavesdropAfterPhysicalAccess = new EavesdropAfterPhysicalAccess(name);
  }

  public Network(String name) {
    super(name);
    assetClassName = "Network";
    AttackStep.allAttackSteps.remove(physicalAccess);
    physicalAccess = new PhysicalAccess(name);
    if (networkAccessControl != null) {
      AttackStep.allAttackSteps.remove(networkAccessControl.disable);
    }
    Defense.allDefenses.remove(networkAccessControl);
    networkAccessControl = new NetworkAccessControl(name, false);
    AttackStep.allAttackSteps.remove(bypassAccessControl);
    bypassAccessControl = new BypassAccessControl(name);
    AttackStep.allAttackSteps.remove(attemptAccess);
    attemptAccess = new AttemptAccess(name);
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
    AttackStep.allAttackSteps.remove(successfulAccess);
    successfulAccess = new SuccessfulAccess(name);
    AttackStep.allAttackSteps.remove(networkForwarding);
    networkForwarding = new NetworkForwarding(name);
    AttackStep.allAttackSteps.remove(denialOfService);
    denialOfService = new DenialOfService(name);
    AttackStep.allAttackSteps.remove(accessNetworkData);
    accessNetworkData = new AccessNetworkData(name);
    if (eavesdropDefense != null) {
      AttackStep.allAttackSteps.remove(eavesdropDefense.disable);
    }
    Defense.allDefenses.remove(eavesdropDefense);
    eavesdropDefense = new EavesdropDefense(name, false);
    if (manInTheMiddleDefense != null) {
      AttackStep.allAttackSteps.remove(manInTheMiddleDefense.disable);
    }
    Defense.allDefenses.remove(manInTheMiddleDefense);
    manInTheMiddleDefense = new ManInTheMiddleDefense(name, false);
    AttackStep.allAttackSteps.remove(eavesdrop);
    eavesdrop = new Eavesdrop(name);
    AttackStep.allAttackSteps.remove(bypassEavesdropProtection);
    bypassEavesdropProtection = new BypassEavesdropProtection(name);
    AttackStep.allAttackSteps.remove(manInTheMiddle);
    manInTheMiddle = new ManInTheMiddle(name);
    AttackStep.allAttackSteps.remove(bypassMitMProtection);
    bypassMitMProtection = new BypassMitMProtection(name);
    AttackStep.allAttackSteps.remove(eavesdropAfterPhysicalAccess);
    eavesdropAfterPhysicalAccess = new EavesdropAfterPhysicalAccess(name);
  }

  public Network(boolean isNetworkAccessControlEnabled, boolean isEavesdropDefenseEnabled,
      boolean isManInTheMiddleDefenseEnabled) {
    this("Anonymous", isNetworkAccessControlEnabled, isEavesdropDefenseEnabled, isManInTheMiddleDefenseEnabled);
  }

  public Network() {
    this("Anonymous");
  }

  protected Set<Application> _allowedApplicationConnectionsApplicationsNetwork() {
    if (_cacheallowedApplicationConnectionsApplicationsNetwork == null) {
      _cacheallowedApplicationConnectionsApplicationsNetwork = new HashSet<>();
      Set<Application> _3 = new HashSet<>();
      Set<Application> _4 = new HashSet<>();
      Set<Application> _5 = new HashSet<>();
      Set<Application> _6 = new HashSet<>();
      for (ConnectionRule _7 : netConnections) {
        for (Application _8 : _7.applications) {
          _5.add(_8);
        }
      }
      for (ConnectionRule _9 : outgoingNetConnections) {
        for (Application _a : _9.applications) {
          _6.add(_a);
        }
      }
      _5.addAll(_6);
      for (Application _b : _5) {
        _3.add(_b);
      }
      for (Application _c : applications) {
        _4.add(_c);
      }
      _3.addAll(_4);
      for (Application _d : _3) {
        _cacheallowedApplicationConnectionsApplicationsNetwork.add(_d);
      }
    }
    return _cacheallowedApplicationConnectionsApplicationsNetwork;
  }

  protected Set<ConnectionRule> _allowedNetworkConnectionsNetwork() {
    if (_cacheallowedNetworkConnectionsNetwork == null) {
      _cacheallowedNetworkConnectionsNetwork = new HashSet<>();
      Set<ConnectionRule> _e = new HashSet<>();
      Set<ConnectionRule> _f = new HashSet<>();
      for (ConnectionRule _10 : netConnections) {
        _e.add(_10);
      }
      for (ConnectionRule _11 : outgoingNetConnections) {
        _f.add(_11);
      }
      _e.addAll(_f);
      for (ConnectionRule _12 : _e) {
        _cacheallowedNetworkConnectionsNetwork.add(_12);
      }
    }
    return _cacheallowedNetworkConnectionsNetwork;
  }

  protected Set<ConnectionRule> _allNetConnectionsNetwork() {
    if (_cacheallNetConnectionsNetwork == null) {
      _cacheallNetConnectionsNetwork = new HashSet<>();
      Set<ConnectionRule> _13 = new HashSet<>();
      Set<ConnectionRule> _14 = new HashSet<>();
      Set<ConnectionRule> _15 = new HashSet<>();
      Set<ConnectionRule> _16 = new HashSet<>();
      Set<ConnectionRule> _17 = new HashSet<>();
      Set<ConnectionRule> _18 = new HashSet<>();
      for (ConnectionRule _19 : netConnections) {
        _17.add(_19);
      }
      for (ConnectionRule _1a : ingoingNetConnections) {
        _18.add(_1a);
      }
      _17.addAll(_18);
      for (ConnectionRule _1b : _17) {
        _15.add(_1b);
      }
      for (ConnectionRule _1c : outgoingNetConnections) {
        _16.add(_1c);
      }
      _15.addAll(_16);
      for (ConnectionRule _1d : _15) {
        _13.add(_1d);
      }
      for (ConnectionRule _1e : diodeIngoingNetConnections) {
        _14.add(_1e);
      }
      _13.addAll(_14);
      for (ConnectionRule _1f : _13) {
        _cacheallNetConnectionsNetwork.add(_1f);
      }
    }
    return _cacheallNetConnectionsNetwork;
  }

  public void addApplications(Application applications) {
    this.applications.add(applications);
    applications.networks.add(this);
  }

  public void addClientApplications(Application clientApplications) {
    this.clientApplications.add(clientApplications);
    clientApplications.clientAccessNetworks.add(this);
  }

  public void addNetConnections(ConnectionRule netConnections) {
    this.netConnections.add(netConnections);
    netConnections.networks.add(this);
  }

  public void addOutgoingNetConnections(ConnectionRule outgoingNetConnections) {
    this.outgoingNetConnections.add(outgoingNetConnections);
    outgoingNetConnections.outNetworks.add(this);
  }

  public void addIngoingNetConnections(ConnectionRule ingoingNetConnections) {
    this.ingoingNetConnections.add(ingoingNetConnections);
    ingoingNetConnections.inNetworks.add(this);
  }

  public void addDiodeIngoingNetConnections(ConnectionRule diodeIngoingNetConnections) {
    this.diodeIngoingNetConnections.add(diodeIngoingNetConnections);
    diodeIngoingNetConnections.diodeInNetworks.add(this);
  }

  public void addTransitData(Data transitData) {
    this.transitData.add(transitData);
    transitData.transitNetwork.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("applications")) {
      return Application.class.getName();
    } else if (field.equals("clientApplications")) {
      return Application.class.getName();
    } else if (field.equals("netConnections")) {
      return ConnectionRule.class.getName();
    } else if (field.equals("outgoingNetConnections")) {
      return ConnectionRule.class.getName();
    } else if (field.equals("ingoingNetConnections")) {
      return ConnectionRule.class.getName();
    } else if (field.equals("diodeIngoingNetConnections")) {
      return ConnectionRule.class.getName();
    } else if (field.equals("transitData")) {
      return Data.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("applications")) {
      assets.addAll(applications);
    } else if (field.equals("clientApplications")) {
      assets.addAll(clientApplications);
    } else if (field.equals("netConnections")) {
      assets.addAll(netConnections);
    } else if (field.equals("outgoingNetConnections")) {
      assets.addAll(outgoingNetConnections);
    } else if (field.equals("ingoingNetConnections")) {
      assets.addAll(ingoingNetConnections);
    } else if (field.equals("diodeIngoingNetConnections")) {
      assets.addAll(diodeIngoingNetConnections);
    } else if (field.equals("transitData")) {
      assets.addAll(transitData);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(applications);
    assets.addAll(clientApplications);
    assets.addAll(netConnections);
    assets.addAll(outgoingNetConnections);
    assets.addAll(ingoingNetConnections);
    assets.addAll(diodeIngoingNetConnections);
    assets.addAll(transitData);
    return assets;
  }

  public class PhysicalAccess extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenPhysicalAccess;

    public PhysicalAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenPhysicalAccess == null) {
        _cacheChildrenPhysicalAccess = new HashSet<>();
        _cacheChildrenPhysicalAccess.add(denialOfService);
        _cacheChildrenPhysicalAccess.add(eavesdropAfterPhysicalAccess);
        _cacheChildrenPhysicalAccess.add(attemptAccess);
        _cacheChildrenPhysicalAccess.add(bypassAccessControl);
      }
      for (AttackStep attackStep : _cacheChildrenPhysicalAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.physicalAccess");
    }
  }

  public class NetworkAccessControl extends Defense {
    public NetworkAccessControl(String name) {
      this(name, false);
    }

    public NetworkAccessControl(String name, Boolean isEnabled) {
      super(name);
      defaultValue = isEnabled;
      disable = new Disable(name);
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenNetworkAccessControl;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenNetworkAccessControl == null) {
          _cacheChildrenNetworkAccessControl = new HashSet<>();
          _cacheChildrenNetworkAccessControl.add(eavesdropAfterPhysicalAccess);
          _cacheChildrenNetworkAccessControl.add(attemptAccess);
        }
        for (AttackStep attackStep : _cacheChildrenNetworkAccessControl) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "Network.networkAccessControl";
      }
    }
  }

  public class BypassAccessControl extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenBypassAccessControl;

    private Set<AttackStep> _cacheParentBypassAccessControl;

    public BypassAccessControl(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenBypassAccessControl == null) {
        _cacheChildrenBypassAccessControl = new HashSet<>();
        _cacheChildrenBypassAccessControl.add(successfulAccess);
      }
      for (AttackStep attackStep : _cacheChildrenBypassAccessControl) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentBypassAccessControl == null) {
        _cacheParentBypassAccessControl = new HashSet<>();
        _cacheParentBypassAccessControl.add(physicalAccess);
      }
      for (AttackStep attackStep : _cacheParentBypassAccessControl) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.bypassAccessControl");
    }
  }

  public class AttemptAccess extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenAttemptAccess;

    private Set<AttackStep> _cacheParentAttemptAccess;

    public AttemptAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptAccess == null) {
        _cacheChildrenAttemptAccess = new HashSet<>();
        _cacheChildrenAttemptAccess.add(access);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptAccess == null) {
        _cacheParentAttemptAccess = new HashSet<>();
        _cacheParentAttemptAccess.add(physicalAccess);
        _cacheParentAttemptAccess.add(networkAccessControl.disable);
      }
      for (AttackStep attackStep : _cacheParentAttemptAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.attemptAccess");
    }
  }

  public class Access extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAccess;

    private Set<AttackStep> _cacheParentAccess;

    public Access(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAccess == null) {
        _cacheChildrenAccess = new HashSet<>();
        _cacheChildrenAccess.add(successfulAccess);
      }
      for (AttackStep attackStep : _cacheChildrenAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAccess == null) {
        _cacheParentAccess = new HashSet<>();
        for (Application _0 : applications) {
          _cacheParentAccess.add(_0.accessNetworkAndConnections);
        }
        _cacheParentAccess.add(attemptAccess);
        Set<RoutingFirewall> _1 = new HashSet<>();
        Set<RoutingFirewall> _2 = new HashSet<>();
        for (ConnectionRule _3 : diodeIngoingNetConnections) {
          if (_3.routingFirewalls != null) {
            _1.add(_3.routingFirewalls);
          }
        }
        Set<RoutingFirewall> _4 = new HashSet<>();
        Set<RoutingFirewall> _5 = new HashSet<>();
        for (ConnectionRule _6 : ingoingNetConnections) {
          if (_6.routingFirewalls != null) {
            _4.add(_6.routingFirewalls);
          }
        }
        Set<RoutingFirewall> _7 = new HashSet<>();
        Set<RoutingFirewall> _8 = new HashSet<>();
        for (ConnectionRule _9 : outgoingNetConnections) {
          if (_9.routingFirewalls != null) {
            _7.add(_9.routingFirewalls);
          }
        }
        for (ConnectionRule _a : netConnections) {
          if (_a.routingFirewalls != null) {
            _8.add(_a.routingFirewalls);
          }
        }
        _7.addAll(_8);
        for (RoutingFirewall _b : _7) {
          _5.add(_b);
        }
        _4.addAll(_5);
        for (RoutingFirewall _c : _4) {
          _2.add(_c);
        }
        _1.addAll(_2);
        for (RoutingFirewall _d : _1) {
          _cacheParentAccess.add(_d.fullAccess);
        }
        Set<ConnectionRule> _e = new HashSet<>();
        Set<ConnectionRule> _f = new HashSet<>();
        for (ConnectionRule _10 : diodeIngoingNetConnections) {
          _e.add(_10);
        }
        Set<ConnectionRule> _11 = new HashSet<>();
        Set<ConnectionRule> _12 = new HashSet<>();
        for (ConnectionRule _13 : ingoingNetConnections) {
          _11.add(_13);
        }
        for (ConnectionRule _14 : netConnections) {
          _12.add(_14);
        }
        _11.addAll(_12);
        for (ConnectionRule _15 : _11) {
          _f.add(_15);
        }
        _e.addAll(_f);
        for (ConnectionRule _16 : _e) {
          _cacheParentAccess.add(_16.accessNetworks);
        }
      }
      for (AttackStep attackStep : _cacheParentAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.access");
    }
  }

  public class SuccessfulAccess extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenSuccessfulAccess;

    private Set<AttackStep> _cacheParentSuccessfulAccess;

    public SuccessfulAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenSuccessfulAccess == null) {
        _cacheChildrenSuccessfulAccess = new HashSet<>();
        for (var _0 : _allowedApplicationConnectionsApplicationsNetwork()) {
          _cacheChildrenSuccessfulAccess.add(_0.networkConnect);
        }
        for (var _1 : _allowedApplicationConnectionsApplicationsNetwork()) {
          _cacheChildrenSuccessfulAccess.add(_1.networkRequestConnect);
        }
        for (Application _2 : clientApplications) {
          _cacheChildrenSuccessfulAccess.add(_2.networkRespondConnect);
        }
        _cacheChildrenSuccessfulAccess.add(accessNetworkData);
        _cacheChildrenSuccessfulAccess.add(networkForwarding);
        _cacheChildrenSuccessfulAccess.add(denialOfService);
        _cacheChildrenSuccessfulAccess.add(eavesdrop);
        _cacheChildrenSuccessfulAccess.add(bypassEavesdropProtection);
        _cacheChildrenSuccessfulAccess.add(manInTheMiddle);
        _cacheChildrenSuccessfulAccess.add(bypassMitMProtection);
      }
      for (AttackStep attackStep : _cacheChildrenSuccessfulAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentSuccessfulAccess == null) {
        _cacheParentSuccessfulAccess = new HashSet<>();
        _cacheParentSuccessfulAccess.add(bypassAccessControl);
        _cacheParentSuccessfulAccess.add(access);
      }
      for (AttackStep attackStep : _cacheParentSuccessfulAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.successfulAccess");
    }
  }

  public class NetworkForwarding extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenNetworkForwarding;

    private Set<AttackStep> _cacheParentNetworkForwarding;

    public NetworkForwarding(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenNetworkForwarding == null) {
        _cacheChildrenNetworkForwarding = new HashSet<>();
        for (var _0 : _allowedNetworkConnectionsNetwork()) {
          _cacheChildrenNetworkForwarding.add(_0.attemptAccessNetworks);
        }
        for (var _1 : _allowedNetworkConnectionsNetwork()) {
          _cacheChildrenNetworkForwarding.add(_1.attemptConnectToApplications);
        }
        for (var _2 : _allowedNetworkConnectionsNetwork()) {
          _cacheChildrenNetworkForwarding.add(_2.attemptTransmitResponse);
        }
      }
      for (AttackStep attackStep : _cacheChildrenNetworkForwarding) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentNetworkForwarding == null) {
        _cacheParentNetworkForwarding = new HashSet<>();
        _cacheParentNetworkForwarding.add(successfulAccess);
      }
      for (AttackStep attackStep : _cacheParentNetworkForwarding) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.networkForwarding");
    }
  }

  public class DenialOfService extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenDenialOfService;

    private Set<AttackStep> _cacheParentDenialOfService;

    public DenialOfService(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenDenialOfService == null) {
        _cacheChildrenDenialOfService = new HashSet<>();
        for (var _0 : _allNetConnectionsNetwork()) {
          _cacheChildrenDenialOfService.add(_0.attemptDenialOfService);
        }
        for (var _1 : _allowedApplicationConnectionsApplicationsNetwork()) {
          _cacheChildrenDenialOfService.add(_1.denyFromConnectionRule);
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
        _cacheParentDenialOfService.add(physicalAccess);
        _cacheParentDenialOfService.add(successfulAccess);
      }
      for (AttackStep attackStep : _cacheParentDenialOfService) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.denialOfService");
    }
  }

  public class AccessNetworkData extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAccessNetworkData;

    private Set<AttackStep> _cacheParentAccessNetworkData;

    public AccessNetworkData(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAccessNetworkData == null) {
        _cacheChildrenAccessNetworkData = new HashSet<>();
        for (Data _0 : transitData) {
          _cacheChildrenAccessNetworkData.add(_0.attemptAccess);
        }
        for (Data _1 : transitData) {
          _cacheChildrenAccessNetworkData.add(_1.attemptEavesdrop);
        }
        for (Data _2 : transitData) {
          _cacheChildrenAccessNetworkData.add(_2.attemptManInTheMiddle);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAccessNetworkData) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAccessNetworkData == null) {
        _cacheParentAccessNetworkData = new HashSet<>();
        _cacheParentAccessNetworkData.add(successfulAccess);
      }
      for (AttackStep attackStep : _cacheParentAccessNetworkData) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.accessNetworkData");
    }
  }

  public class EavesdropDefense extends Defense {
    public EavesdropDefense(String name) {
      this(name, false);
    }

    public EavesdropDefense(String name, Boolean isEnabled) {
      super(name);
      defaultValue = isEnabled;
      disable = new Disable(name);
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenEavesdropDefense;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenEavesdropDefense == null) {
          _cacheChildrenEavesdropDefense = new HashSet<>();
          _cacheChildrenEavesdropDefense.add(eavesdrop);
          _cacheChildrenEavesdropDefense.add(eavesdropAfterPhysicalAccess);
        }
        for (AttackStep attackStep : _cacheChildrenEavesdropDefense) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "Network.eavesdropDefense";
      }
    }
  }

  public class ManInTheMiddleDefense extends Defense {
    public ManInTheMiddleDefense(String name) {
      this(name, false);
    }

    public ManInTheMiddleDefense(String name, Boolean isEnabled) {
      super(name);
      defaultValue = isEnabled;
      disable = new Disable(name);
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenManInTheMiddleDefense;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenManInTheMiddleDefense == null) {
          _cacheChildrenManInTheMiddleDefense = new HashSet<>();
          _cacheChildrenManInTheMiddleDefense.add(manInTheMiddle);
        }
        for (AttackStep attackStep : _cacheChildrenManInTheMiddleDefense) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "Network.manInTheMiddleDefense";
      }
    }
  }

  public class Eavesdrop extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenEavesdrop;

    private Set<AttackStep> _cacheParentEavesdrop;

    public Eavesdrop(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenEavesdrop == null) {
        _cacheChildrenEavesdrop = new HashSet<>();
        for (var _0 : _allowedApplicationConnectionsApplicationsNetwork()) {
          for (Data _1 : _0.transitData) {
            _cacheChildrenEavesdrop.add(_1.attemptEavesdrop);
          }
        }
      }
      for (AttackStep attackStep : _cacheChildrenEavesdrop) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentEavesdrop == null) {
        _cacheParentEavesdrop = new HashSet<>();
        _cacheParentEavesdrop.add(successfulAccess);
        _cacheParentEavesdrop.add(eavesdropDefense.disable);
      }
      for (AttackStep attackStep : _cacheParentEavesdrop) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.eavesdrop");
    }
  }

  public class BypassEavesdropProtection extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenBypassEavesdropProtection;

    private Set<AttackStep> _cacheParentBypassEavesdropProtection;

    public BypassEavesdropProtection(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenBypassEavesdropProtection == null) {
        _cacheChildrenBypassEavesdropProtection = new HashSet<>();
        for (var _0 : _allowedApplicationConnectionsApplicationsNetwork()) {
          for (Data _1 : _0.transitData) {
            _cacheChildrenBypassEavesdropProtection.add(_1.attemptEavesdrop);
          }
        }
      }
      for (AttackStep attackStep : _cacheChildrenBypassEavesdropProtection) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentBypassEavesdropProtection == null) {
        _cacheParentBypassEavesdropProtection = new HashSet<>();
        _cacheParentBypassEavesdropProtection.add(successfulAccess);
      }
      for (AttackStep attackStep : _cacheParentBypassEavesdropProtection) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.bypassEavesdropProtection");
    }
  }

  public class ManInTheMiddle extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenManInTheMiddle;

    private Set<AttackStep> _cacheParentManInTheMiddle;

    public ManInTheMiddle(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenManInTheMiddle == null) {
        _cacheChildrenManInTheMiddle = new HashSet<>();
        for (var _0 : _allowedApplicationConnectionsApplicationsNetwork()) {
          for (Data _1 : _0.transitData) {
            _cacheChildrenManInTheMiddle.add(_1.attemptManInTheMiddle);
          }
        }
      }
      for (AttackStep attackStep : _cacheChildrenManInTheMiddle) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentManInTheMiddle == null) {
        _cacheParentManInTheMiddle = new HashSet<>();
        _cacheParentManInTheMiddle.add(successfulAccess);
        _cacheParentManInTheMiddle.add(manInTheMiddleDefense.disable);
      }
      for (AttackStep attackStep : _cacheParentManInTheMiddle) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.manInTheMiddle");
    }
  }

  public class BypassMitMProtection extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenBypassMitMProtection;

    private Set<AttackStep> _cacheParentBypassMitMProtection;

    public BypassMitMProtection(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenBypassMitMProtection == null) {
        _cacheChildrenBypassMitMProtection = new HashSet<>();
        for (var _0 : _allowedApplicationConnectionsApplicationsNetwork()) {
          for (Data _1 : _0.transitData) {
            _cacheChildrenBypassMitMProtection.add(_1.attemptManInTheMiddle);
          }
        }
      }
      for (AttackStep attackStep : _cacheChildrenBypassMitMProtection) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentBypassMitMProtection == null) {
        _cacheParentBypassMitMProtection = new HashSet<>();
        _cacheParentBypassMitMProtection.add(successfulAccess);
      }
      for (AttackStep attackStep : _cacheParentBypassMitMProtection) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.bypassMitMProtection");
    }
  }

  public class EavesdropAfterPhysicalAccess extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenEavesdropAfterPhysicalAccess;

    private Set<AttackStep> _cacheParentEavesdropAfterPhysicalAccess;

    public EavesdropAfterPhysicalAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenEavesdropAfterPhysicalAccess == null) {
        _cacheChildrenEavesdropAfterPhysicalAccess = new HashSet<>();
        for (var _0 : _allowedApplicationConnectionsApplicationsNetwork()) {
          for (Data _1 : _0.transitData) {
            _cacheChildrenEavesdropAfterPhysicalAccess.add(_1.attemptEavesdrop);
          }
        }
        for (Data _2 : transitData) {
          _cacheChildrenEavesdropAfterPhysicalAccess.add(_2.attemptEavesdrop);
        }
      }
      for (AttackStep attackStep : _cacheChildrenEavesdropAfterPhysicalAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentEavesdropAfterPhysicalAccess == null) {
        _cacheParentEavesdropAfterPhysicalAccess = new HashSet<>();
        _cacheParentEavesdropAfterPhysicalAccess.add(physicalAccess);
        _cacheParentEavesdropAfterPhysicalAccess.add(networkAccessControl.disable);
        _cacheParentEavesdropAfterPhysicalAccess.add(eavesdropDefense.disable);
      }
      for (AttackStep attackStep : _cacheParentEavesdropAfterPhysicalAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Network.eavesdropAfterPhysicalAccess");
    }
  }
}
