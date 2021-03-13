package test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.lang.Override;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;

public class SoftwareProduct extends Asset {
  public CompromiseApplication compromiseApplication;

  public Set<Vulnerability> softProductVulnerabilities = new HashSet<>();

  public Set<Application> softApplications = new HashSet<>();

  public Data originData = null;

  public SoftwareProduct(String name) {
    super(name);
    assetClassName = "SoftwareProduct";
    AttackStep.allAttackSteps.remove(compromiseApplication);
    compromiseApplication = new CompromiseApplication(name);
  }

  public SoftwareProduct() {
    this("Anonymous");
  }

  public void addSoftProductVulnerabilities(Vulnerability softProductVulnerabilities) {
    this.softProductVulnerabilities.add(softProductVulnerabilities);
    softProductVulnerabilities.softwareProduct = this;
  }

  public void addSoftApplications(Application softApplications) {
    this.softApplications.add(softApplications);
    softApplications.appSoftProduct = this;
  }

  public void addOriginData(Data originData) {
    this.originData = originData;
    originData.originSoftwareProduct = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("softProductVulnerabilities")) {
      return Vulnerability.class.getName();
    } else if (field.equals("softApplications")) {
      return Application.class.getName();
    } else if (field.equals("originData")) {
      return Data.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("softProductVulnerabilities")) {
      assets.addAll(softProductVulnerabilities);
    } else if (field.equals("softApplications")) {
      assets.addAll(softApplications);
    } else if (field.equals("originData")) {
      if (originData != null) {
        assets.add(originData);
      }
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(softProductVulnerabilities);
    assets.addAll(softApplications);
    if (originData != null) {
      assets.add(originData);
    }
    return assets;
  }

  public class CompromiseApplication extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCompromiseApplication;

    private Set<AttackStep> _cacheParentCompromiseApplication;

    public CompromiseApplication(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCompromiseApplication == null) {
        _cacheChildrenCompromiseApplication = new HashSet<>();
        for (Application _0 : softApplications) {
          _cacheChildrenCompromiseApplication.add(_0.fullAccess);
        }
      }
      for (AttackStep attackStep : _cacheChildrenCompromiseApplication) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentCompromiseApplication == null) {
        _cacheParentCompromiseApplication = new HashSet<>();
        if (originData != null) {
          _cacheParentCompromiseApplication.add(originData.compromiseAppOrigin);
        }
      }
      for (AttackStep attackStep : _cacheParentCompromiseApplication) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("SoftwareProduct.compromiseApplication");
    }
  }
}
