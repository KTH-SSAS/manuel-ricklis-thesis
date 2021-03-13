package test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class User extends Asset {
  public AttemptSocialEngineering attemptSocialEngineering;

  public AttemptCredentialTheft attemptCredentialTheft;

  public CredentialTheft credentialTheft;

  public AttemptReverseTakeover attemptReverseTakeover;

  public ReverseTakeover reverseTakeover;

  public PhishUser phishUser;

  public AttemptSteal2FAtoken attemptSteal2FAtoken;

  public Steal2FAtoken steal2FAtoken;

  public Compromise compromise;

  public Set<Identity> userIds = new HashSet<>();

  public User(String name) {
    super(name);
    assetClassName = "User";
    AttackStep.allAttackSteps.remove(attemptSocialEngineering);
    attemptSocialEngineering = new AttemptSocialEngineering(name);
    AttackStep.allAttackSteps.remove(attemptCredentialTheft);
    attemptCredentialTheft = new AttemptCredentialTheft(name);
    AttackStep.allAttackSteps.remove(credentialTheft);
    credentialTheft = new CredentialTheft(name);
    AttackStep.allAttackSteps.remove(attemptReverseTakeover);
    attemptReverseTakeover = new AttemptReverseTakeover(name);
    AttackStep.allAttackSteps.remove(reverseTakeover);
    reverseTakeover = new ReverseTakeover(name);
    AttackStep.allAttackSteps.remove(phishUser);
    phishUser = new PhishUser(name);
    AttackStep.allAttackSteps.remove(attemptSteal2FAtoken);
    attemptSteal2FAtoken = new AttemptSteal2FAtoken(name);
    AttackStep.allAttackSteps.remove(steal2FAtoken);
    steal2FAtoken = new Steal2FAtoken(name);
    AttackStep.allAttackSteps.remove(compromise);
    compromise = new Compromise(name);
  }

  public User() {
    this("Anonymous");
  }

  public void addUserIds(Identity userIds) {
    this.userIds.add(userIds);
    userIds.users.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("userIds")) {
      return Identity.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("userIds")) {
      assets.addAll(userIds);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(userIds);
    return assets;
  }

  public class AttemptSocialEngineering extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptSocialEngineering;

    public AttemptSocialEngineering(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptSocialEngineering == null) {
        _cacheChildrenAttemptSocialEngineering = new HashSet<>();
        _cacheChildrenAttemptSocialEngineering.add(phishUser);
        _cacheChildrenAttemptSocialEngineering.add(steal2FAtoken);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptSocialEngineering) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.attemptSocialEngineering");
    }
  }

  public class AttemptCredentialTheft extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptCredentialTheft;

    private Set<AttackStep> _cacheParentAttemptCredentialTheft;

    public AttemptCredentialTheft(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptCredentialTheft == null) {
        _cacheChildrenAttemptCredentialTheft = new HashSet<>();
        _cacheChildrenAttemptCredentialTheft.add(credentialTheft);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptCredentialTheft) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptCredentialTheft == null) {
        _cacheParentAttemptCredentialTheft = new HashSet<>();
        _cacheParentAttemptCredentialTheft.add(phishUser);
      }
      for (AttackStep attackStep : _cacheParentAttemptCredentialTheft) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.attemptCredentialTheft");
    }
  }

  public class CredentialTheft extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCredentialTheft;

    private Set<AttackStep> _cacheParentCredentialTheft;

    public CredentialTheft(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCredentialTheft == null) {
        _cacheChildrenCredentialTheft = new HashSet<>();
        for (Identity _0 : userIds) {
          for (Credentials _1 : _0.credentials) {
            _cacheChildrenCredentialTheft.add(_1.credentialTheft);
          }
        }
      }
      for (AttackStep attackStep : _cacheChildrenCredentialTheft) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentCredentialTheft == null) {
        _cacheParentCredentialTheft = new HashSet<>();
        _cacheParentCredentialTheft.add(attemptCredentialTheft);
      }
      for (AttackStep attackStep : _cacheParentCredentialTheft) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.credentialTheft");
    }
  }

  public class AttemptReverseTakeover extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptReverseTakeover;

    private Set<AttackStep> _cacheParentAttemptReverseTakeover;

    public AttemptReverseTakeover(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptReverseTakeover == null) {
        _cacheChildrenAttemptReverseTakeover = new HashSet<>();
        _cacheChildrenAttemptReverseTakeover.add(reverseTakeover);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptReverseTakeover) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptReverseTakeover == null) {
        _cacheParentAttemptReverseTakeover = new HashSet<>();
        _cacheParentAttemptReverseTakeover.add(phishUser);
      }
      for (AttackStep attackStep : _cacheParentAttemptReverseTakeover) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.attemptReverseTakeover");
    }
  }

  public class ReverseTakeover extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenReverseTakeover;

    private Set<AttackStep> _cacheParentReverseTakeover;

    public ReverseTakeover(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenReverseTakeover == null) {
        _cacheChildrenReverseTakeover = new HashSet<>();
        for (Identity _0 : userIds) {
          for (Application _1 : _0.execPrivApps) {
            _cacheChildrenReverseTakeover.add(_1.networkConnect);
          }
        }
      }
      for (AttackStep attackStep : _cacheChildrenReverseTakeover) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentReverseTakeover == null) {
        _cacheParentReverseTakeover = new HashSet<>();
        _cacheParentReverseTakeover.add(attemptReverseTakeover);
      }
      for (AttackStep attackStep : _cacheParentReverseTakeover) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.reverseTakeover");
    }
  }

  public class PhishUser extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenPhishUser;

    private Set<AttackStep> _cacheParentPhishUser;

    public PhishUser(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenPhishUser == null) {
        _cacheChildrenPhishUser = new HashSet<>();
        _cacheChildrenPhishUser.add(attemptCredentialTheft);
        _cacheChildrenPhishUser.add(attemptReverseTakeover);
      }
      for (AttackStep attackStep : _cacheChildrenPhishUser) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentPhishUser == null) {
        _cacheParentPhishUser = new HashSet<>();
        _cacheParentPhishUser.add(attemptSocialEngineering);
      }
      for (AttackStep attackStep : _cacheParentPhishUser) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.phishUser");
    }
  }

  public class AttemptSteal2FAtoken extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptSteal2FAtoken;

    private Set<AttackStep> _cacheParentAttemptSteal2FAtoken;

    public AttemptSteal2FAtoken(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptSteal2FAtoken == null) {
        _cacheChildrenAttemptSteal2FAtoken = new HashSet<>();
        _cacheChildrenAttemptSteal2FAtoken.add(steal2FAtoken);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptSteal2FAtoken) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptSteal2FAtoken == null) {
        _cacheParentAttemptSteal2FAtoken = new HashSet<>();
        for (Identity _0 : userIds) {
          for (Credentials _1 : _0.credentials) {
            _cacheParentAttemptSteal2FAtoken.add(_1.use);
          }
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptSteal2FAtoken) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.attemptSteal2FAtoken");
    }
  }

  public class Steal2FAtoken extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenSteal2FAtoken;

    private Set<AttackStep> _cacheParentSteal2FAtoken;

    public Steal2FAtoken(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenSteal2FAtoken == null) {
        _cacheChildrenSteal2FAtoken = new HashSet<>();
        for (Identity _0 : userIds) {
          _cacheChildrenSteal2FAtoken.add(_0.assume);
        }
      }
      for (AttackStep attackStep : _cacheChildrenSteal2FAtoken) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentSteal2FAtoken == null) {
        _cacheParentSteal2FAtoken = new HashSet<>();
        _cacheParentSteal2FAtoken.add(attemptSocialEngineering);
        _cacheParentSteal2FAtoken.add(attemptSteal2FAtoken);
      }
      for (AttackStep attackStep : _cacheParentSteal2FAtoken) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.steal2FAtoken");
    }
  }

  public class Compromise extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCompromise;

    public Compromise(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCompromise == null) {
        _cacheChildrenCompromise = new HashSet<>();
        for (Identity _0 : userIds) {
          _cacheChildrenCompromise.add(_0.attemptAssume);
        }
      }
      for (AttackStep attackStep : _cacheChildrenCompromise) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.compromise");
    }
  }
}
