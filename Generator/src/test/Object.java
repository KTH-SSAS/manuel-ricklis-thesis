package test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public abstract class Object extends Asset {
  public AttemptUseVulnerability attemptUseVulnerability;

  public Deny deny;

  public BypassAccessControl bypassAccessControl;

  public Set<Vulnerability> objvulnerabilities = new HashSet<>();

  public Object(String name) {
    super(name);
    assetClassName = "Object";
    AttackStep.allAttackSteps.remove(attemptUseVulnerability);
    attemptUseVulnerability = new AttemptUseVulnerability(name);
    AttackStep.allAttackSteps.remove(deny);
    deny = new Deny(name);
    AttackStep.allAttackSteps.remove(bypassAccessControl);
    bypassAccessControl = new BypassAccessControl(name);
  }

  public Object() {
    this("Anonymous");
  }

  public void addObjvulnerabilities(Vulnerability objvulnerabilities) {
    this.objvulnerabilities.add(objvulnerabilities);
    objvulnerabilities.object = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("objvulnerabilities")) {
      return Vulnerability.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("objvulnerabilities")) {
      assets.addAll(objvulnerabilities);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(objvulnerabilities);
    return assets;
  }

  public class AttemptUseVulnerability extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptUseVulnerability;

    private Set<AttackStep> _cacheParentAttemptUseVulnerability;

    public AttemptUseVulnerability(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptUseVulnerability == null) {
        _cacheChildrenAttemptUseVulnerability = new HashSet<>();
        for (Vulnerability _0 : objvulnerabilities) {
          _cacheChildrenAttemptUseVulnerability.add(_0.attemptAbuse);
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
        if (Object.this instanceof System) {
          _cacheParentAttemptUseVulnerability.add(((test.System) Object.this).connect);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptUseVulnerability) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Object.attemptUseVulnerability");
    }
  }

  public class Deny extends AttackStepMin {
    private Set<AttackStep> _cacheParentDeny;

    public Deny(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentDeny == null) {
        _cacheParentDeny = new HashSet<>();
        for (Vulnerability _0 : objvulnerabilities) {
          if (_0 instanceof UnknownVulnerability) {
            _cacheParentDeny.add(((test.UnknownVulnerability) _0).deny);
          }
        }
      }
      for (AttackStep attackStep : _cacheParentDeny) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Object.deny");
    }
  }

  public class BypassAccessControl extends AttackStepMin {
    private Set<AttackStep> _cacheParentBypassAccessControl;

    public BypassAccessControl(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentBypassAccessControl == null) {
        _cacheParentBypassAccessControl = new HashSet<>();
        for (Vulnerability _0 : objvulnerabilities) {
          if (_0 instanceof UnknownVulnerability) {
            _cacheParentBypassAccessControl.add(((test.UnknownVulnerability) _0).attemptObjectAccess);
          }
        }
      }
      for (AttackStep attackStep : _cacheParentBypassAccessControl) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Object.bypassAccessControl");
    }
  }
}
