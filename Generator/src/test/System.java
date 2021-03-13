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

public class System extends Object {
  public Connect connect;

  public AttemptConnect attemptConnect;

  public AttemptUsePhysicalVulnerability attemptUsePhysicalVulnerability;

  public AllPrivilegeAuthenticate allPrivilegeAuthenticate;

  public IndividualPrivilegeAuthenticate individualPrivilegeAuthenticate;

  public AttemptGainFullAccess attemptGainFullAccess;

  public FullAccess fullAccess;

  public SpecificAccess specificAccess;

  public PhysicalAccess physicalAccess;

  public HardwareModificationsProtection hardwareModificationsProtection;

  public Set<Vulnerability> sysvulnerabilities = new HashSet<>();

  public Set<Application> sysExecutedApps = new HashSet<>();

  public PhysicalZone physicalZone = null;

  public Set<Data> sysData = new HashSet<>();

  public Set<Identity> highPrivSysIds = new HashSet<>();

  public Set<Identity> lowPrivSysIds = new HashSet<>();

  public Set<Group> highPrivSysGroups = new HashSet<>();

  public Set<Group> lowPrivSysGroups = new HashSet<>();

  public System(String name, boolean isHardwareModificationsProtectionEnabled) {
    super(name);
    assetClassName = "System";
    AttackStep.allAttackSteps.remove(connect);
    connect = new Connect(name);
    AttackStep.allAttackSteps.remove(attemptConnect);
    attemptConnect = new AttemptConnect(name);
    AttackStep.allAttackSteps.remove(attemptUsePhysicalVulnerability);
    attemptUsePhysicalVulnerability = new AttemptUsePhysicalVulnerability(name);
    AttackStep.allAttackSteps.remove(allPrivilegeAuthenticate);
    allPrivilegeAuthenticate = new AllPrivilegeAuthenticate(name);
    AttackStep.allAttackSteps.remove(individualPrivilegeAuthenticate);
    individualPrivilegeAuthenticate = new IndividualPrivilegeAuthenticate(name);
    AttackStep.allAttackSteps.remove(attemptGainFullAccess);
    attemptGainFullAccess = new AttemptGainFullAccess(name);
    AttackStep.allAttackSteps.remove(fullAccess);
    fullAccess = new FullAccess(name);
    AttackStep.allAttackSteps.remove(specificAccess);
    specificAccess = new SpecificAccess(name);
    AttackStep.allAttackSteps.remove(physicalAccess);
    physicalAccess = new PhysicalAccess(name);
    if (hardwareModificationsProtection != null) {
      AttackStep.allAttackSteps.remove(hardwareModificationsProtection.disable);
    }
    Defense.allDefenses.remove(hardwareModificationsProtection);
    hardwareModificationsProtection = new HardwareModificationsProtection(name, isHardwareModificationsProtectionEnabled);
    AttackStep.allAttackSteps.remove(deny);
    deny = new Deny(name);
    AttackStep.allAttackSteps.remove(bypassAccessControl);
    bypassAccessControl = new BypassAccessControl(name);
  }

  public System(String name) {
    super(name);
    assetClassName = "System";
    AttackStep.allAttackSteps.remove(connect);
    connect = new Connect(name);
    AttackStep.allAttackSteps.remove(attemptConnect);
    attemptConnect = new AttemptConnect(name);
    AttackStep.allAttackSteps.remove(attemptUsePhysicalVulnerability);
    attemptUsePhysicalVulnerability = new AttemptUsePhysicalVulnerability(name);
    AttackStep.allAttackSteps.remove(allPrivilegeAuthenticate);
    allPrivilegeAuthenticate = new AllPrivilegeAuthenticate(name);
    AttackStep.allAttackSteps.remove(individualPrivilegeAuthenticate);
    individualPrivilegeAuthenticate = new IndividualPrivilegeAuthenticate(name);
    AttackStep.allAttackSteps.remove(attemptGainFullAccess);
    attemptGainFullAccess = new AttemptGainFullAccess(name);
    AttackStep.allAttackSteps.remove(fullAccess);
    fullAccess = new FullAccess(name);
    AttackStep.allAttackSteps.remove(specificAccess);
    specificAccess = new SpecificAccess(name);
    AttackStep.allAttackSteps.remove(physicalAccess);
    physicalAccess = new PhysicalAccess(name);
    if (hardwareModificationsProtection != null) {
      AttackStep.allAttackSteps.remove(hardwareModificationsProtection.disable);
    }
    Defense.allDefenses.remove(hardwareModificationsProtection);
    hardwareModificationsProtection = new HardwareModificationsProtection(name, false);
    AttackStep.allAttackSteps.remove(deny);
    deny = new Deny(name);
    AttackStep.allAttackSteps.remove(bypassAccessControl);
    bypassAccessControl = new BypassAccessControl(name);
  }

  public System(boolean isHardwareModificationsProtectionEnabled) {
    this("Anonymous", isHardwareModificationsProtectionEnabled);
  }

  public System() {
    this("Anonymous");
  }

  public void addSysvulnerabilities(Vulnerability sysvulnerabilities) {
    this.sysvulnerabilities.add(sysvulnerabilities);
    sysvulnerabilities.system = this;
  }

  public void addSysExecutedApps(Application sysExecutedApps) {
    this.sysExecutedApps.add(sysExecutedApps);
    sysExecutedApps.hostSystem = this;
  }

  public void addPhysicalZone(PhysicalZone physicalZone) {
    this.physicalZone = physicalZone;
    physicalZone.systems.add(this);
  }

  public void addSysData(Data sysData) {
    this.sysData.add(sysData);
    sysData.system = this;
  }

  public void addHighPrivSysIds(Identity highPrivSysIds) {
    this.highPrivSysIds.add(highPrivSysIds);
    highPrivSysIds.highPrivManagedSystems.add(this);
  }

  public void addLowPrivSysIds(Identity lowPrivSysIds) {
    this.lowPrivSysIds.add(lowPrivSysIds);
    lowPrivSysIds.lowPrivManagedSystems.add(this);
  }

  public void addHighPrivSysGroups(Group highPrivSysGroups) {
    this.highPrivSysGroups.add(highPrivSysGroups);
    highPrivSysGroups.highPrivManagedSystems.add(this);
  }

  public void addLowPrivSysGroups(Group lowPrivSysGroups) {
    this.lowPrivSysGroups.add(lowPrivSysGroups);
    lowPrivSysGroups.lowPrivManagedSystems.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("sysvulnerabilities")) {
      return Vulnerability.class.getName();
    } else if (field.equals("sysExecutedApps")) {
      return Application.class.getName();
    } else if (field.equals("physicalZone")) {
      return PhysicalZone.class.getName();
    } else if (field.equals("sysData")) {
      return Data.class.getName();
    } else if (field.equals("highPrivSysIds")) {
      return Identity.class.getName();
    } else if (field.equals("lowPrivSysIds")) {
      return Identity.class.getName();
    } else if (field.equals("highPrivSysGroups")) {
      return Group.class.getName();
    } else if (field.equals("lowPrivSysGroups")) {
      return Group.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("sysvulnerabilities")) {
      assets.addAll(sysvulnerabilities);
    } else if (field.equals("sysExecutedApps")) {
      assets.addAll(sysExecutedApps);
    } else if (field.equals("physicalZone")) {
      if (physicalZone != null) {
        assets.add(physicalZone);
      }
    } else if (field.equals("sysData")) {
      assets.addAll(sysData);
    } else if (field.equals("highPrivSysIds")) {
      assets.addAll(highPrivSysIds);
    } else if (field.equals("lowPrivSysIds")) {
      assets.addAll(lowPrivSysIds);
    } else if (field.equals("highPrivSysGroups")) {
      assets.addAll(highPrivSysGroups);
    } else if (field.equals("lowPrivSysGroups")) {
      assets.addAll(lowPrivSysGroups);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(sysvulnerabilities);
    assets.addAll(sysExecutedApps);
    if (physicalZone != null) {
      assets.add(physicalZone);
    }
    assets.addAll(sysData);
    assets.addAll(highPrivSysIds);
    assets.addAll(lowPrivSysIds);
    assets.addAll(highPrivSysGroups);
    assets.addAll(lowPrivSysGroups);
    return assets;
  }

  public class Connect extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenConnect;

    private Set<AttackStep> _cacheParentConnect;

    public Connect(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenConnect == null) {
        _cacheChildrenConnect = new HashSet<>();
        _cacheChildrenConnect.add(attemptGainFullAccess);
        _cacheChildrenConnect.add(specificAccess);
        if (System.this instanceof System) {
          _cacheChildrenConnect.add(((test.System) System.this).attemptUseVulnerability);
        }
      }
      for (AttackStep attackStep : _cacheChildrenConnect) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentConnect == null) {
        _cacheParentConnect = new HashSet<>();
        _cacheParentConnect.add(attemptConnect);
      }
      for (AttackStep attackStep : _cacheParentConnect) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("System.connect");
    }
  }

  public class AttemptConnect extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenAttemptConnect;

    private Set<AttackStep> _cacheParentAttemptConnect;

    public AttemptConnect(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptConnect == null) {
        _cacheChildrenAttemptConnect = new HashSet<>();
        _cacheChildrenAttemptConnect.add(connect);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptConnect) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptConnect == null) {
        _cacheParentAttemptConnect = new HashSet<>();
        _cacheParentAttemptConnect.add(physicalAccess);
        _cacheParentAttemptConnect.add(hardwareModificationsProtection.disable);
      }
      for (AttackStep attackStep : _cacheParentAttemptConnect) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("System.attemptConnect");
    }
  }

  public class AttemptUsePhysicalVulnerability extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenAttemptUsePhysicalVulnerability;

    private Set<AttackStep> _cacheParentAttemptUsePhysicalVulnerability;

    public AttemptUsePhysicalVulnerability(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptUsePhysicalVulnerability == null) {
        _cacheChildrenAttemptUsePhysicalVulnerability = new HashSet<>();
        for (Vulnerability _0 : sysvulnerabilities) {
          _cacheChildrenAttemptUsePhysicalVulnerability.add(_0.attemptAbuse);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAttemptUsePhysicalVulnerability) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptUsePhysicalVulnerability == null) {
        _cacheParentAttemptUsePhysicalVulnerability = new HashSet<>();
        _cacheParentAttemptUsePhysicalVulnerability.add(physicalAccess);
        _cacheParentAttemptUsePhysicalVulnerability.add(hardwareModificationsProtection.disable);
      }
      for (AttackStep attackStep : _cacheParentAttemptUsePhysicalVulnerability) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("System.attemptUsePhysicalVulnerability");
    }
  }

  public class AllPrivilegeAuthenticate extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAllPrivilegeAuthenticate;

    private Set<AttackStep> _cacheParentAllPrivilegeAuthenticate;

    public AllPrivilegeAuthenticate(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAllPrivilegeAuthenticate == null) {
        _cacheChildrenAllPrivilegeAuthenticate = new HashSet<>();
        _cacheChildrenAllPrivilegeAuthenticate.add(attemptGainFullAccess);
      }
      for (AttackStep attackStep : _cacheChildrenAllPrivilegeAuthenticate) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAllPrivilegeAuthenticate == null) {
        _cacheParentAllPrivilegeAuthenticate = new HashSet<>();
        for (Identity _0 : highPrivSysIds) {
          _cacheParentAllPrivilegeAuthenticate.add(_0.assume);
        }
        for (Group _1 : highPrivSysGroups) {
          _cacheParentAllPrivilegeAuthenticate.add(_1.compromiseGroup);
        }
      }
      for (AttackStep attackStep : _cacheParentAllPrivilegeAuthenticate) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("System.allPrivilegeAuthenticate");
    }
  }

  public class IndividualPrivilegeAuthenticate extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenIndividualPrivilegeAuthenticate;

    private Set<AttackStep> _cacheParentIndividualPrivilegeAuthenticate;

    public IndividualPrivilegeAuthenticate(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenIndividualPrivilegeAuthenticate == null) {
        _cacheChildrenIndividualPrivilegeAuthenticate = new HashSet<>();
        _cacheChildrenIndividualPrivilegeAuthenticate.add(specificAccess);
      }
      for (AttackStep attackStep : _cacheChildrenIndividualPrivilegeAuthenticate) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentIndividualPrivilegeAuthenticate == null) {
        _cacheParentIndividualPrivilegeAuthenticate = new HashSet<>();
        for (Identity _0 : lowPrivSysIds) {
          _cacheParentIndividualPrivilegeAuthenticate.add(_0.assume);
        }
        for (Group _1 : lowPrivSysGroups) {
          _cacheParentIndividualPrivilegeAuthenticate.add(_1.compromiseGroup);
        }
      }
      for (AttackStep attackStep : _cacheParentIndividualPrivilegeAuthenticate) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("System.individualPrivilegeAuthenticate");
    }
  }

  public class AttemptGainFullAccess extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenAttemptGainFullAccess;

    private Set<AttackStep> _cacheParentAttemptGainFullAccess;

    public AttemptGainFullAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptGainFullAccess == null) {
        _cacheChildrenAttemptGainFullAccess = new HashSet<>();
        _cacheChildrenAttemptGainFullAccess.add(fullAccess);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptGainFullAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptGainFullAccess == null) {
        _cacheParentAttemptGainFullAccess = new HashSet<>();
        _cacheParentAttemptGainFullAccess.add(connect);
        _cacheParentAttemptGainFullAccess.add(allPrivilegeAuthenticate);
      }
      for (AttackStep attackStep : _cacheParentAttemptGainFullAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("System.attemptGainFullAccess");
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
        for (Application _0 : sysExecutedApps) {
          _cacheChildrenFullAccess.add(_0.fullAccess);
        }
        for (Identity _1 : highPrivSysIds) {
          _cacheChildrenFullAccess.add(_1.assume);
        }
        for (Identity _2 : lowPrivSysIds) {
          _cacheChildrenFullAccess.add(_2.assume);
        }
        for (Group _3 : highPrivSysGroups) {
          _cacheChildrenFullAccess.add(_3.compromiseGroup);
        }
        for (Group _4 : lowPrivSysGroups) {
          _cacheChildrenFullAccess.add(_4.compromiseGroup);
        }
        for (Data _5 : sysData) {
          _cacheChildrenFullAccess.add(_5.attemptAccess);
        }
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
        _cacheParentFullAccess.add(attemptGainFullAccess);
        _cacheParentFullAccess.add(bypassAccessControl);
      }
      for (AttackStep attackStep : _cacheParentFullAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("System.fullAccess");
    }
  }

  public class SpecificAccess extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenSpecificAccess;

    private Set<AttackStep> _cacheParentSpecificAccess;

    public SpecificAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenSpecificAccess == null) {
        _cacheChildrenSpecificAccess = new HashSet<>();
        for (Application _0 : sysExecutedApps) {
          _cacheChildrenSpecificAccess.add(_0.localConnect);
        }
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
        _cacheParentSpecificAccess.add(connect);
        _cacheParentSpecificAccess.add(individualPrivilegeAuthenticate);
      }
      for (AttackStep attackStep : _cacheParentSpecificAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("System.specificAccess");
    }
  }

  public class PhysicalAccess extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenPhysicalAccess;

    private Set<AttackStep> _cacheParentPhysicalAccess;

    public PhysicalAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenPhysicalAccess == null) {
        _cacheChildrenPhysicalAccess = new HashSet<>();
        _cacheChildrenPhysicalAccess.add(deny);
        _cacheChildrenPhysicalAccess.add(attemptConnect);
        _cacheChildrenPhysicalAccess.add(attemptUsePhysicalVulnerability);
      }
      for (AttackStep attackStep : _cacheChildrenPhysicalAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentPhysicalAccess == null) {
        _cacheParentPhysicalAccess = new HashSet<>();
        if (physicalZone != null) {
          _cacheParentPhysicalAccess.add(physicalZone.gainPhysicalAccess);
        }
      }
      for (AttackStep attackStep : _cacheParentPhysicalAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("System.physicalAccess");
    }
  }

  public class HardwareModificationsProtection extends Defense {
    public HardwareModificationsProtection(String name) {
      this(name, false);
    }

    public HardwareModificationsProtection(String name, Boolean isEnabled) {
      super(name);
      defaultValue = isEnabled;
      disable = new Disable(name);
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenHardwareModificationsProtection;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenHardwareModificationsProtection == null) {
          _cacheChildrenHardwareModificationsProtection = new HashSet<>();
          _cacheChildrenHardwareModificationsProtection.add(attemptConnect);
          _cacheChildrenHardwareModificationsProtection.add(attemptUsePhysicalVulnerability);
        }
        for (AttackStep attackStep : _cacheChildrenHardwareModificationsProtection) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "System.hardwareModificationsProtection";
      }
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
        for (Application _0 : sysExecutedApps) {
          _cacheChildrenDeny.add(_0.deny);
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
        _cacheParentDeny.add(physicalAccess);
      }
      for (AttackStep attackStep : _cacheParentDeny) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("System.deny");
    }
  }

  public class BypassAccessControl extends Object.BypassAccessControl {
    private Set<AttackStep> _cacheChildrenBypassAccessControl;

    private Set<AttackStep> _cacheParentBypassAccessControl;

    public BypassAccessControl(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenBypassAccessControl == null) {
        _cacheChildrenBypassAccessControl = new HashSet<>();
        _cacheChildrenBypassAccessControl.add(fullAccess);
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
        for (Vulnerability _0 : sysvulnerabilities) {
          if (_0 instanceof UnknownVulnerability) {
            _cacheParentBypassAccessControl.add(((test.UnknownVulnerability) _0).attemptSystemAccess);
          }
        }
      }
      for (AttackStep attackStep : _cacheParentBypassAccessControl) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("System.bypassAccessControl");
    }
  }
}
