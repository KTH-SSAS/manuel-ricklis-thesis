package test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class Credentials extends Information {
  public Use use;

  public CredentialTheft credentialTheft;

  public Set<Data> encryptedData = new HashSet<>();

  public Set<Identity> identities = new HashSet<>();

  public Credentials(String name) {
    super(name);
    assetClassName = "Credentials";
    AttackStep.allAttackSteps.remove(use);
    use = new Use(name);
    AttackStep.allAttackSteps.remove(attemptAccess);
    attemptAccess = new AttemptAccess(name);
    AttackStep.allAttackSteps.remove(credentialTheft);
    credentialTheft = new CredentialTheft(name);
  }

  public Credentials() {
    this("Anonymous");
  }

  public void addEncryptedData(Data encryptedData) {
    this.encryptedData.add(encryptedData);
    encryptedData.encryptCreds = this;
  }

  public void addIdentities(Identity identities) {
    this.identities.add(identities);
    identities.credentials.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("encryptedData")) {
      return Data.class.getName();
    } else if (field.equals("identities")) {
      return Identity.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("encryptedData")) {
      assets.addAll(encryptedData);
    } else if (field.equals("identities")) {
      assets.addAll(identities);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(encryptedData);
    assets.addAll(identities);
    return assets;
  }

  public class Use extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenUse;

    private Set<AttackStep> _cacheParentUse;

    public Use(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenUse == null) {
        _cacheChildrenUse = new HashSet<>();
        for (Identity _0 : identities) {
          _cacheChildrenUse.add(_0.attemptAssume);
        }
        for (Data _1 : encryptedData) {
          _cacheChildrenUse.add(_1.accessDecryptedData);
        }
        for (Identity _2 : identities) {
          for (User _3 : _2.users) {
            _cacheChildrenUse.add(_3.attemptSteal2FAtoken);
          }
        }
      }
      for (AttackStep attackStep : _cacheChildrenUse) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentUse == null) {
        _cacheParentUse = new HashSet<>();
        _cacheParentUse.add(attemptAccess);
        _cacheParentUse.add(credentialTheft);
      }
      for (AttackStep attackStep : _cacheParentUse) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Credentials.use");
    }
  }

  public class AttemptAccess extends Information.AttemptAccess {
    private Set<AttackStep> _cacheChildrenAttemptAccess;

    public AttemptAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptAccess == null) {
        _cacheChildrenAttemptAccess = new HashSet<>();
        _cacheChildrenAttemptAccess.add(use);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Credentials.attemptAccess");
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
        _cacheChildrenCredentialTheft.add(use);
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
        for (Identity _0 : identities) {
          for (User _1 : _0.users) {
            _cacheParentCredentialTheft.add(_1.credentialTheft);
          }
        }
      }
      for (AttackStep attackStep : _cacheParentCredentialTheft) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Credentials.credentialTheft");
    }
  }
}
