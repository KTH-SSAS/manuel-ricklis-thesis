package test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Group extends Asset {
  public CompromiseGroup compromiseGroup;

  public Set<Identity> groupIds = new HashSet<>();

  public Set<Group> childGroups = new HashSet<>();

  public Set<Group> parentGroup = new HashSet<>();

  public Set<System> highPrivManagedSystems = new HashSet<>();

  public Set<System> lowPrivManagedSystems = new HashSet<>();

  public Set<Application> execPrivApps = new HashSet<>();

  public Set<Application> highPrivApps = new HashSet<>();

  public Set<Application> lowPrivApps = new HashSet<>();

  public Set<Data> readPrivData = new HashSet<>();

  public Set<Data> writePrivData = new HashSet<>();

  public Set<Data> deletePrivData = new HashSet<>();

  public Group(String name) {
    super(name);
    assetClassName = "Group";
    AttackStep.allAttackSteps.remove(compromiseGroup);
    compromiseGroup = new CompromiseGroup(name);
  }

  public Group() {
    this("Anonymous");
  }

  public void addGroupIds(Identity groupIds) {
    this.groupIds.add(groupIds);
    groupIds.memberOf.add(this);
  }

  public void addChildGroups(Group childGroups) {
    this.childGroups.add(childGroups);
    childGroups.parentGroup.add(this);
  }

  public void addParentGroup(Group parentGroup) {
    this.parentGroup.add(parentGroup);
    parentGroup.childGroups.add(this);
  }

  public void addHighPrivManagedSystems(System highPrivManagedSystems) {
    this.highPrivManagedSystems.add(highPrivManagedSystems);
    highPrivManagedSystems.highPrivSysGroups.add(this);
  }

  public void addLowPrivManagedSystems(System lowPrivManagedSystems) {
    this.lowPrivManagedSystems.add(lowPrivManagedSystems);
    lowPrivManagedSystems.lowPrivSysGroups.add(this);
  }

  public void addExecPrivApps(Application execPrivApps) {
    this.execPrivApps.add(execPrivApps);
    execPrivApps.executionPrivGroups.add(this);
  }

  public void addHighPrivApps(Application highPrivApps) {
    this.highPrivApps.add(highPrivApps);
    highPrivApps.highPrivAppGroups.add(this);
  }

  public void addLowPrivApps(Application lowPrivApps) {
    this.lowPrivApps.add(lowPrivApps);
    lowPrivApps.lowPrivAppGroups.add(this);
  }

  public void addReadPrivData(Data readPrivData) {
    this.readPrivData.add(readPrivData);
    readPrivData.readingGroups.add(this);
  }

  public void addWritePrivData(Data writePrivData) {
    this.writePrivData.add(writePrivData);
    writePrivData.writingGroups.add(this);
  }

  public void addDeletePrivData(Data deletePrivData) {
    this.deletePrivData.add(deletePrivData);
    deletePrivData.deletingGroups.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("groupIds")) {
      return Identity.class.getName();
    } else if (field.equals("childGroups")) {
      return Group.class.getName();
    } else if (field.equals("parentGroup")) {
      return Group.class.getName();
    } else if (field.equals("highPrivManagedSystems")) {
      return System.class.getName();
    } else if (field.equals("lowPrivManagedSystems")) {
      return System.class.getName();
    } else if (field.equals("execPrivApps")) {
      return Application.class.getName();
    } else if (field.equals("highPrivApps")) {
      return Application.class.getName();
    } else if (field.equals("lowPrivApps")) {
      return Application.class.getName();
    } else if (field.equals("readPrivData")) {
      return Data.class.getName();
    } else if (field.equals("writePrivData")) {
      return Data.class.getName();
    } else if (field.equals("deletePrivData")) {
      return Data.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("groupIds")) {
      assets.addAll(groupIds);
    } else if (field.equals("childGroups")) {
      assets.addAll(childGroups);
    } else if (field.equals("parentGroup")) {
      assets.addAll(parentGroup);
    } else if (field.equals("highPrivManagedSystems")) {
      assets.addAll(highPrivManagedSystems);
    } else if (field.equals("lowPrivManagedSystems")) {
      assets.addAll(lowPrivManagedSystems);
    } else if (field.equals("execPrivApps")) {
      assets.addAll(execPrivApps);
    } else if (field.equals("highPrivApps")) {
      assets.addAll(highPrivApps);
    } else if (field.equals("lowPrivApps")) {
      assets.addAll(lowPrivApps);
    } else if (field.equals("readPrivData")) {
      assets.addAll(readPrivData);
    } else if (field.equals("writePrivData")) {
      assets.addAll(writePrivData);
    } else if (field.equals("deletePrivData")) {
      assets.addAll(deletePrivData);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(groupIds);
    assets.addAll(childGroups);
    assets.addAll(parentGroup);
    assets.addAll(highPrivManagedSystems);
    assets.addAll(lowPrivManagedSystems);
    assets.addAll(execPrivApps);
    assets.addAll(highPrivApps);
    assets.addAll(lowPrivApps);
    assets.addAll(readPrivData);
    assets.addAll(writePrivData);
    assets.addAll(deletePrivData);
    return assets;
  }

  public class CompromiseGroup extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCompromiseGroup;

    private Set<AttackStep> _cacheParentCompromiseGroup;

    public CompromiseGroup(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCompromiseGroup == null) {
        _cacheChildrenCompromiseGroup = new HashSet<>();
        for (Group _0 : parentGroup) {
          _cacheChildrenCompromiseGroup.add(_0.compromiseGroup);
        }
        for (System _1 : lowPrivManagedSystems) {
          _cacheChildrenCompromiseGroup.add(_1.individualPrivilegeAuthenticate);
        }
        for (System _2 : highPrivManagedSystems) {
          _cacheChildrenCompromiseGroup.add(_2.allPrivilegeAuthenticate);
        }
        for (Application _3 : execPrivApps) {
          _cacheChildrenCompromiseGroup.add(_3.authenticate);
        }
        for (Application _4 : highPrivApps) {
          _cacheChildrenCompromiseGroup.add(_4.authenticate);
        }
        for (Application _5 : lowPrivApps) {
          _cacheChildrenCompromiseGroup.add(_5.specificAccessAuthenticate);
        }
        for (Data _6 : readPrivData) {
          _cacheChildrenCompromiseGroup.add(_6.identityAttemptRead);
        }
        for (Data _7 : writePrivData) {
          _cacheChildrenCompromiseGroup.add(_7.identityAttemptWrite);
        }
        for (Data _8 : deletePrivData) {
          _cacheChildrenCompromiseGroup.add(_8.identityAttemptDelete);
        }
      }
      for (AttackStep attackStep : _cacheChildrenCompromiseGroup) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentCompromiseGroup == null) {
        _cacheParentCompromiseGroup = new HashSet<>();
        for (System _9 : highPrivManagedSystems) {
          _cacheParentCompromiseGroup.add(_9.fullAccess);
        }
        for (System _a : lowPrivManagedSystems) {
          _cacheParentCompromiseGroup.add(_a.fullAccess);
        }
        for (Application _b : execPrivApps) {
          _cacheParentCompromiseGroup.add(_b.fullAccess);
        }
        for (Identity _c : groupIds) {
          _cacheParentCompromiseGroup.add(_c.assume);
        }
        for (Group _d : childGroups) {
          _cacheParentCompromiseGroup.add(_d.compromiseGroup);
        }
      }
      for (AttackStep attackStep : _cacheParentCompromiseGroup) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Group.compromiseGroup");
    }
  }
}
