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

public class Identity extends Asset {
  public TwoFactorAuthentication twoFactorAuthentication;

  public AttemptAssume attemptAssume;

  public SuccessfulAssume successfulAssume;

  public Assume assume;

  public Set<Credentials> credentials = new HashSet<>();

  public Set<User> users = new HashSet<>();

  public Set<Identity> childId = new HashSet<>();

  public Set<Identity> parentId = new HashSet<>();

  public Set<Group> memberOf = new HashSet<>();

  public Set<System> highPrivManagedSystems = new HashSet<>();

  public Set<System> lowPrivManagedSystems = new HashSet<>();

  public Set<Application> execPrivApps = new HashSet<>();

  public Set<Application> highPrivApps = new HashSet<>();

  public Set<Application> lowPrivApps = new HashSet<>();

  public Set<Data> readPrivData = new HashSet<>();

  public Set<Data> writePrivData = new HashSet<>();

  public Set<Data> deletePrivData = new HashSet<>();

  public Identity(String name, boolean isTwoFactorAuthenticationEnabled) {
    super(name);
    assetClassName = "Identity";
    if (twoFactorAuthentication != null) {
      AttackStep.allAttackSteps.remove(twoFactorAuthentication.disable);
    }
    Defense.allDefenses.remove(twoFactorAuthentication);
    twoFactorAuthentication = new TwoFactorAuthentication(name, isTwoFactorAuthenticationEnabled);
    AttackStep.allAttackSteps.remove(attemptAssume);
    attemptAssume = new AttemptAssume(name);
    AttackStep.allAttackSteps.remove(successfulAssume);
    successfulAssume = new SuccessfulAssume(name);
    AttackStep.allAttackSteps.remove(assume);
    assume = new Assume(name);
  }

  public Identity(String name) {
    super(name);
    assetClassName = "Identity";
    if (twoFactorAuthentication != null) {
      AttackStep.allAttackSteps.remove(twoFactorAuthentication.disable);
    }
    Defense.allDefenses.remove(twoFactorAuthentication);
    twoFactorAuthentication = new TwoFactorAuthentication(name, false);
    AttackStep.allAttackSteps.remove(attemptAssume);
    attemptAssume = new AttemptAssume(name);
    AttackStep.allAttackSteps.remove(successfulAssume);
    successfulAssume = new SuccessfulAssume(name);
    AttackStep.allAttackSteps.remove(assume);
    assume = new Assume(name);
  }

  public Identity(boolean isTwoFactorAuthenticationEnabled) {
    this("Anonymous", isTwoFactorAuthenticationEnabled);
  }

  public Identity() {
    this("Anonymous");
  }

  public void addCredentials(Credentials credentials) {
    this.credentials.add(credentials);
    credentials.identities.add(this);
  }

  public void addUsers(User users) {
    this.users.add(users);
    users.userIds.add(this);
  }

  public void addChildId(Identity childId) {
    this.childId.add(childId);
    childId.parentId.add(this);
  }

  public void addParentId(Identity parentId) {
    this.parentId.add(parentId);
    parentId.childId.add(this);
  }

  public void addMemberOf(Group memberOf) {
    this.memberOf.add(memberOf);
    memberOf.groupIds.add(this);
  }

  public void addHighPrivManagedSystems(System highPrivManagedSystems) {
    this.highPrivManagedSystems.add(highPrivManagedSystems);
    highPrivManagedSystems.highPrivSysIds.add(this);
  }

  public void addLowPrivManagedSystems(System lowPrivManagedSystems) {
    this.lowPrivManagedSystems.add(lowPrivManagedSystems);
    lowPrivManagedSystems.lowPrivSysIds.add(this);
  }

  public void addExecPrivApps(Application execPrivApps) {
    this.execPrivApps.add(execPrivApps);
    execPrivApps.executionPrivIds.add(this);
  }

  public void addHighPrivApps(Application highPrivApps) {
    this.highPrivApps.add(highPrivApps);
    highPrivApps.highPrivAppIds.add(this);
  }

  public void addLowPrivApps(Application lowPrivApps) {
    this.lowPrivApps.add(lowPrivApps);
    lowPrivApps.lowPrivAppIds.add(this);
  }

  public void addReadPrivData(Data readPrivData) {
    this.readPrivData.add(readPrivData);
    readPrivData.readingIds.add(this);
  }

  public void addWritePrivData(Data writePrivData) {
    this.writePrivData.add(writePrivData);
    writePrivData.writingIds.add(this);
  }

  public void addDeletePrivData(Data deletePrivData) {
    this.deletePrivData.add(deletePrivData);
    deletePrivData.deletingIds.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("credentials")) {
      return Credentials.class.getName();
    } else if (field.equals("users")) {
      return User.class.getName();
    } else if (field.equals("childId")) {
      return Identity.class.getName();
    } else if (field.equals("parentId")) {
      return Identity.class.getName();
    } else if (field.equals("memberOf")) {
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
    if (field.equals("credentials")) {
      assets.addAll(credentials);
    } else if (field.equals("users")) {
      assets.addAll(users);
    } else if (field.equals("childId")) {
      assets.addAll(childId);
    } else if (field.equals("parentId")) {
      assets.addAll(parentId);
    } else if (field.equals("memberOf")) {
      assets.addAll(memberOf);
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
    assets.addAll(credentials);
    assets.addAll(users);
    assets.addAll(childId);
    assets.addAll(parentId);
    assets.addAll(memberOf);
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

  public class TwoFactorAuthentication extends Defense {
    public TwoFactorAuthentication(String name) {
      this(name, false);
    }

    public TwoFactorAuthentication(String name, Boolean isEnabled) {
      super(name);
      defaultValue = isEnabled;
      disable = new Disable(name);
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenTwoFactorAuthentication;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenTwoFactorAuthentication == null) {
          _cacheChildrenTwoFactorAuthentication = new HashSet<>();
          _cacheChildrenTwoFactorAuthentication.add(successfulAssume);
        }
        for (AttackStep attackStep : _cacheChildrenTwoFactorAuthentication) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "Identity.twoFactorAuthentication";
      }
    }
  }

  public class AttemptAssume extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptAssume;

    private Set<AttackStep> _cacheParentAttemptAssume;

    public AttemptAssume(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptAssume == null) {
        _cacheChildrenAttemptAssume = new HashSet<>();
        _cacheChildrenAttemptAssume.add(successfulAssume);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptAssume) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptAssume == null) {
        _cacheParentAttemptAssume = new HashSet<>();
        for (Credentials _0 : credentials) {
          _cacheParentAttemptAssume.add(_0.use);
        }
        for (User _1 : users) {
          _cacheParentAttemptAssume.add(_1.compromise);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptAssume) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Identity.attemptAssume");
    }
  }

  public class SuccessfulAssume extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenSuccessfulAssume;

    private Set<AttackStep> _cacheParentSuccessfulAssume;

    public SuccessfulAssume(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenSuccessfulAssume == null) {
        _cacheChildrenSuccessfulAssume = new HashSet<>();
        _cacheChildrenSuccessfulAssume.add(assume);
      }
      for (AttackStep attackStep : _cacheChildrenSuccessfulAssume) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentSuccessfulAssume == null) {
        _cacheParentSuccessfulAssume = new HashSet<>();
        _cacheParentSuccessfulAssume.add(twoFactorAuthentication.disable);
        _cacheParentSuccessfulAssume.add(attemptAssume);
      }
      for (AttackStep attackStep : _cacheParentSuccessfulAssume) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Identity.successfulAssume");
    }
  }

  public class Assume extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAssume;

    private Set<AttackStep> _cacheParentAssume;

    public Assume(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAssume == null) {
        _cacheChildrenAssume = new HashSet<>();
        for (Identity _0 : parentId) {
          _cacheChildrenAssume.add(_0.assume);
        }
        for (Group _1 : memberOf) {
          _cacheChildrenAssume.add(_1.compromiseGroup);
        }
        for (System _2 : lowPrivManagedSystems) {
          _cacheChildrenAssume.add(_2.individualPrivilegeAuthenticate);
        }
        for (System _3 : highPrivManagedSystems) {
          _cacheChildrenAssume.add(_3.allPrivilegeAuthenticate);
        }
        for (Application _4 : execPrivApps) {
          _cacheChildrenAssume.add(_4.authenticate);
        }
        for (Application _5 : highPrivApps) {
          _cacheChildrenAssume.add(_5.authenticate);
        }
        for (Application _6 : lowPrivApps) {
          _cacheChildrenAssume.add(_6.specificAccessAuthenticate);
        }
        for (Data _7 : readPrivData) {
          _cacheChildrenAssume.add(_7.identityAttemptRead);
        }
        for (Data _8 : writePrivData) {
          _cacheChildrenAssume.add(_8.identityAttemptWrite);
        }
        for (Data _9 : deletePrivData) {
          _cacheChildrenAssume.add(_9.identityAttemptDelete);
        }
      }
      for (AttackStep attackStep : _cacheChildrenAssume) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAssume == null) {
        _cacheParentAssume = new HashSet<>();
        for (System _a : highPrivManagedSystems) {
          _cacheParentAssume.add(_a.fullAccess);
        }
        for (System _b : lowPrivManagedSystems) {
          _cacheParentAssume.add(_b.fullAccess);
        }
        for (Application _c : execPrivApps) {
          _cacheParentAssume.add(_c.fullAccess);
        }
        _cacheParentAssume.add(successfulAssume);
        for (Identity _d : childId) {
          _cacheParentAssume.add(_d.assume);
        }
        for (User _e : users) {
          _cacheParentAssume.add(_e.steal2FAtoken);
        }
      }
      for (AttackStep attackStep : _cacheParentAssume) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Identity.assume");
    }
  }
}
