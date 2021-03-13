package test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class PhysicalZone extends Asset {
  public GainPhysicalAccess gainPhysicalAccess;

  public Set<System> systems = new HashSet<>();

  public PhysicalZone(String name) {
    super(name);
    assetClassName = "PhysicalZone";
    AttackStep.allAttackSteps.remove(gainPhysicalAccess);
    gainPhysicalAccess = new GainPhysicalAccess(name);
  }

  public PhysicalZone() {
    this("Anonymous");
  }

  public void addSystems(System systems) {
    this.systems.add(systems);
    systems.physicalZone = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("systems")) {
      return System.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("systems")) {
      assets.addAll(systems);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(systems);
    return assets;
  }

  public class GainPhysicalAccess extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenGainPhysicalAccess;

    public GainPhysicalAccess(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenGainPhysicalAccess == null) {
        _cacheChildrenGainPhysicalAccess = new HashSet<>();
        for (System _0 : systems) {
          _cacheChildrenGainPhysicalAccess.add(_0.physicalAccess);
        }
      }
      for (AttackStep attackStep : _cacheChildrenGainPhysicalAccess) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("PhysicalZone.gainPhysicalAccess");
    }
  }
}
