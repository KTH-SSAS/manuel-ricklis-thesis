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

public class Data extends Asset {
  public AttemptAccess attemptAccess;

  public AttemptAccessFromIdentity attemptAccessFromIdentity;

  public Access access;

  public DataEncrypted dataEncrypted;

  public Authenticated authenticated;

  public AccessUnencryptedData accessUnencryptedData;

  public AccessDecryptedData accessDecryptedData;

  public DataNotPresent dataNotPresent;

  public ReadContainedInformationAndData readContainedInformationAndData;

  public AttemptApplicationRespondConnect attemptApplicationRespondConnect;

  public ApplicationRespondConnect applicationRespondConnect;

  public AttemptRead attemptRead;

  public IdentityAttemptRead identityAttemptRead;

  public IdentityRead identityRead;

  public AttemptWrite attemptWrite;

  public IdentityAttemptWrite identityAttemptWrite;

  public IdentityWrite identityWrite;

  public AttemptDelete attemptDelete;

  public IdentityAttemptDelete identityAttemptDelete;

  public IdentityDelete identityDelete;

  public Read read;

  public Write write;

  public Delete delete;

  public AttemptDeny attemptDeny;

  public Deny deny;

  public AttemptEavesdrop attemptEavesdrop;

  public AttemptManInTheMiddle attemptManInTheMiddle;

  public Eavesdrop eavesdrop;

  public ManInTheMiddle manInTheMiddle;

  public CompromiseAppOrigin compromiseAppOrigin;

  public Set<Data> containedData = new HashSet<>();

  public Set<Data> containingData = new HashSet<>();

  public Set<Application> containingApp = new HashSet<>();

  public Set<Application> transitApp = new HashSet<>();

  public Set<Network> transitNetwork = new HashSet<>();

  public System system = null;

  public Set<Information> information = new HashSet<>();

  public Credentials encryptCreds = null;

  public SoftwareProduct originSoftwareProduct = null;

  public Set<Identity> readingIds = new HashSet<>();

  public Set<Identity> writingIds = new HashSet<>();

  public Set<Identity> deletingIds = new HashSet<>();

  public Set<Group> readingGroups = new HashSet<>();

  public Set<Group> writingGroups = new HashSet<>();

  public Set<Group> deletingGroups = new HashSet<>();

  public Data(String name, boolean isAuthenticatedEnabled, boolean isDataNotPresentEnabled) {
    super(name);
    assetClassName = "Data";
    AttackStep.allAttackSteps.remove(attemptAccess);
    attemptAccess = new AttemptAccess(name);
    AttackStep.allAttackSteps.remove(attemptAccessFromIdentity);
    attemptAccessFromIdentity = new AttemptAccessFromIdentity(name);
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
    if (dataEncrypted != null) {
      AttackStep.allAttackSteps.remove(dataEncrypted.disable);
    }
    Defense.allDefenses.remove(dataEncrypted);
    dataEncrypted = new DataEncrypted(name);
    if (authenticated != null) {
      AttackStep.allAttackSteps.remove(authenticated.disable);
    }
    Defense.allDefenses.remove(authenticated);
    authenticated = new Authenticated(name, isAuthenticatedEnabled);
    AttackStep.allAttackSteps.remove(accessUnencryptedData);
    accessUnencryptedData = new AccessUnencryptedData(name);
    AttackStep.allAttackSteps.remove(accessDecryptedData);
    accessDecryptedData = new AccessDecryptedData(name);
    if (dataNotPresent != null) {
      AttackStep.allAttackSteps.remove(dataNotPresent.disable);
    }
    Defense.allDefenses.remove(dataNotPresent);
    dataNotPresent = new DataNotPresent(name, isDataNotPresentEnabled);
    AttackStep.allAttackSteps.remove(readContainedInformationAndData);
    readContainedInformationAndData = new ReadContainedInformationAndData(name);
    AttackStep.allAttackSteps.remove(attemptApplicationRespondConnect);
    attemptApplicationRespondConnect = new AttemptApplicationRespondConnect(name);
    AttackStep.allAttackSteps.remove(applicationRespondConnect);
    applicationRespondConnect = new ApplicationRespondConnect(name);
    AttackStep.allAttackSteps.remove(attemptRead);
    attemptRead = new AttemptRead(name);
    AttackStep.allAttackSteps.remove(identityAttemptRead);
    identityAttemptRead = new IdentityAttemptRead(name);
    AttackStep.allAttackSteps.remove(identityRead);
    identityRead = new IdentityRead(name);
    AttackStep.allAttackSteps.remove(attemptWrite);
    attemptWrite = new AttemptWrite(name);
    AttackStep.allAttackSteps.remove(identityAttemptWrite);
    identityAttemptWrite = new IdentityAttemptWrite(name);
    AttackStep.allAttackSteps.remove(identityWrite);
    identityWrite = new IdentityWrite(name);
    AttackStep.allAttackSteps.remove(attemptDelete);
    attemptDelete = new AttemptDelete(name);
    AttackStep.allAttackSteps.remove(identityAttemptDelete);
    identityAttemptDelete = new IdentityAttemptDelete(name);
    AttackStep.allAttackSteps.remove(identityDelete);
    identityDelete = new IdentityDelete(name);
    AttackStep.allAttackSteps.remove(read);
    read = new Read(name);
    AttackStep.allAttackSteps.remove(write);
    write = new Write(name);
    AttackStep.allAttackSteps.remove(delete);
    delete = new Delete(name);
    AttackStep.allAttackSteps.remove(attemptDeny);
    attemptDeny = new AttemptDeny(name);
    AttackStep.allAttackSteps.remove(deny);
    deny = new Deny(name);
    AttackStep.allAttackSteps.remove(attemptEavesdrop);
    attemptEavesdrop = new AttemptEavesdrop(name);
    AttackStep.allAttackSteps.remove(attemptManInTheMiddle);
    attemptManInTheMiddle = new AttemptManInTheMiddle(name);
    AttackStep.allAttackSteps.remove(eavesdrop);
    eavesdrop = new Eavesdrop(name);
    AttackStep.allAttackSteps.remove(manInTheMiddle);
    manInTheMiddle = new ManInTheMiddle(name);
    AttackStep.allAttackSteps.remove(compromiseAppOrigin);
    compromiseAppOrigin = new CompromiseAppOrigin(name);
  }

  public Data(String name) {
    super(name);
    assetClassName = "Data";
    AttackStep.allAttackSteps.remove(attemptAccess);
    attemptAccess = new AttemptAccess(name);
    AttackStep.allAttackSteps.remove(attemptAccessFromIdentity);
    attemptAccessFromIdentity = new AttemptAccessFromIdentity(name);
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
    if (dataEncrypted != null) {
      AttackStep.allAttackSteps.remove(dataEncrypted.disable);
    }
    Defense.allDefenses.remove(dataEncrypted);
    dataEncrypted = new DataEncrypted(name);
    if (authenticated != null) {
      AttackStep.allAttackSteps.remove(authenticated.disable);
    }
    Defense.allDefenses.remove(authenticated);
    authenticated = new Authenticated(name, false);
    AttackStep.allAttackSteps.remove(accessUnencryptedData);
    accessUnencryptedData = new AccessUnencryptedData(name);
    AttackStep.allAttackSteps.remove(accessDecryptedData);
    accessDecryptedData = new AccessDecryptedData(name);
    if (dataNotPresent != null) {
      AttackStep.allAttackSteps.remove(dataNotPresent.disable);
    }
    Defense.allDefenses.remove(dataNotPresent);
    dataNotPresent = new DataNotPresent(name, false);
    AttackStep.allAttackSteps.remove(readContainedInformationAndData);
    readContainedInformationAndData = new ReadContainedInformationAndData(name);
    AttackStep.allAttackSteps.remove(attemptApplicationRespondConnect);
    attemptApplicationRespondConnect = new AttemptApplicationRespondConnect(name);
    AttackStep.allAttackSteps.remove(applicationRespondConnect);
    applicationRespondConnect = new ApplicationRespondConnect(name);
    AttackStep.allAttackSteps.remove(attemptRead);
    attemptRead = new AttemptRead(name);
    AttackStep.allAttackSteps.remove(identityAttemptRead);
    identityAttemptRead = new IdentityAttemptRead(name);
    AttackStep.allAttackSteps.remove(identityRead);
    identityRead = new IdentityRead(name);
    AttackStep.allAttackSteps.remove(attemptWrite);
    attemptWrite = new AttemptWrite(name);
    AttackStep.allAttackSteps.remove(identityAttemptWrite);
    identityAttemptWrite = new IdentityAttemptWrite(name);
    AttackStep.allAttackSteps.remove(identityWrite);
    identityWrite = new IdentityWrite(name);
    AttackStep.allAttackSteps.remove(attemptDelete);
    attemptDelete = new AttemptDelete(name);
    AttackStep.allAttackSteps.remove(identityAttemptDelete);
    identityAttemptDelete = new IdentityAttemptDelete(name);
    AttackStep.allAttackSteps.remove(identityDelete);
    identityDelete = new IdentityDelete(name);
    AttackStep.allAttackSteps.remove(read);
    read = new Read(name);
    AttackStep.allAttackSteps.remove(write);
    write = new Write(name);
    AttackStep.allAttackSteps.remove(delete);
    delete = new Delete(name);
    AttackStep.allAttackSteps.remove(attemptDeny);
    attemptDeny = new AttemptDeny(name);
    AttackStep.allAttackSteps.remove(deny);
    deny = new Deny(name);
    AttackStep.allAttackSteps.remove(attemptEavesdrop);
    attemptEavesdrop = new AttemptEavesdrop(name);
    AttackStep.allAttackSteps.remove(attemptManInTheMiddle);
    attemptManInTheMiddle = new AttemptManInTheMiddle(name);
    AttackStep.allAttackSteps.remove(eavesdrop);
    eavesdrop = new Eavesdrop(name);
    AttackStep.allAttackSteps.remove(manInTheMiddle);
    manInTheMiddle = new ManInTheMiddle(name);
    AttackStep.allAttackSteps.remove(compromiseAppOrigin);
    compromiseAppOrigin = new CompromiseAppOrigin(name);
  }

  public Data(boolean isAuthenticatedEnabled, boolean isDataNotPresentEnabled) {
    this("Anonymous", isAuthenticatedEnabled, isDataNotPresentEnabled);
  }

  public Data() {
    this("Anonymous");
  }

  public void addContainedData(Data containedData) {
    this.containedData.add(containedData);
    containedData.containingData.add(this);
  }

  public void addContainingData(Data containingData) {
    this.containingData.add(containingData);
    containingData.containedData.add(this);
  }

  public void addContainingApp(Application containingApp) {
    this.containingApp.add(containingApp);
    containingApp.containedData.add(this);
  }

  public void addTransitApp(Application transitApp) {
    this.transitApp.add(transitApp);
    transitApp.transitData.add(this);
  }

  public void addTransitNetwork(Network transitNetwork) {
    this.transitNetwork.add(transitNetwork);
    transitNetwork.transitData.add(this);
  }

  public void addSystem(System system) {
    this.system = system;
    system.sysData.add(this);
  }

  public void addInformation(Information information) {
    this.information.add(information);
    information.containerData.add(this);
  }

  public void addEncryptCreds(Credentials encryptCreds) {
    this.encryptCreds = encryptCreds;
    encryptCreds.encryptedData.add(this);
  }

  public void addOriginSoftwareProduct(SoftwareProduct originSoftwareProduct) {
    this.originSoftwareProduct = originSoftwareProduct;
    originSoftwareProduct.originData = this;
  }

  public void addReadingIds(Identity readingIds) {
    this.readingIds.add(readingIds);
    readingIds.readPrivData.add(this);
  }

  public void addWritingIds(Identity writingIds) {
    this.writingIds.add(writingIds);
    writingIds.writePrivData.add(this);
  }

  public void addDeletingIds(Identity deletingIds) {
    this.deletingIds.add(deletingIds);
    deletingIds.deletePrivData.add(this);
  }

  public void addReadingGroups(Group readingGroups) {
    this.readingGroups.add(readingGroups);
    readingGroups.readPrivData.add(this);
  }

  public void addWritingGroups(Group writingGroups) {
    this.writingGroups.add(writingGroups);
    writingGroups.writePrivData.add(this);
  }

  public void addDeletingGroups(Group deletingGroups) {
    this.deletingGroups.add(deletingGroups);
    deletingGroups.deletePrivData.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("containedData")) {
      return Data.class.getName();
    } else if (field.equals("containingData")) {
      return Data.class.getName();
    } else if (field.equals("containingApp")) {
      return Application.class.getName();
    } else if (field.equals("transitApp")) {
      return Application.class.getName();
    } else if (field.equals("transitNetwork")) {
      return Network.class.getName();
    } else if (field.equals("system")) {
      return System.class.getName();
    } else if (field.equals("information")) {
      return Information.class.getName();
    } else if (field.equals("encryptCreds")) {
      return Credentials.class.getName();
    } else if (field.equals("originSoftwareProduct")) {
      return SoftwareProduct.class.getName();
    } else if (field.equals("readingIds")) {
      return Identity.class.getName();
    } else if (field.equals("writingIds")) {
      return Identity.class.getName();
    } else if (field.equals("deletingIds")) {
      return Identity.class.getName();
    } else if (field.equals("readingGroups")) {
      return Group.class.getName();
    } else if (field.equals("writingGroups")) {
      return Group.class.getName();
    } else if (field.equals("deletingGroups")) {
      return Group.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("containedData")) {
      assets.addAll(containedData);
    } else if (field.equals("containingData")) {
      assets.addAll(containingData);
    } else if (field.equals("containingApp")) {
      assets.addAll(containingApp);
    } else if (field.equals("transitApp")) {
      assets.addAll(transitApp);
    } else if (field.equals("transitNetwork")) {
      assets.addAll(transitNetwork);
    } else if (field.equals("system")) {
      if (system != null) {
        assets.add(system);
      }
    } else if (field.equals("information")) {
      assets.addAll(information);
    } else if (field.equals("encryptCreds")) {
      if (encryptCreds != null) {
        assets.add(encryptCreds);
      }
    } else if (field.equals("originSoftwareProduct")) {
      if (originSoftwareProduct != null) {
        assets.add(originSoftwareProduct);
      }
    } else if (field.equals("readingIds")) {
      assets.addAll(readingIds);
    } else if (field.equals("writingIds")) {
      assets.addAll(writingIds);
    } else if (field.equals("deletingIds")) {
      assets.addAll(deletingIds);
    } else if (field.equals("readingGroups")) {
      assets.addAll(readingGroups);
    } else if (field.equals("writingGroups")) {
      assets.addAll(writingGroups);
    } else if (field.equals("deletingGroups")) {
      assets.addAll(deletingGroups);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(containedData);
    assets.addAll(containingData);
    assets.addAll(containingApp);
    assets.addAll(transitApp);
    assets.addAll(transitNetwork);
    if (system != null) {
      assets.add(system);
    }
    assets.addAll(information);
    if (encryptCreds != null) {
      assets.add(encryptCreds);
    }
    if (originSoftwareProduct != null) {
      assets.add(originSoftwareProduct);
    }
    assets.addAll(readingIds);
    assets.addAll(writingIds);
    assets.addAll(deletingIds);
    assets.addAll(readingGroups);
    assets.addAll(writingGroups);
    assets.addAll(deletingGroups);
    return assets;
  }

  public class AttemptAccess extends AttackStepMin {
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
        if (system != null) {
          _cacheParentAttemptAccess.add(system.fullAccess);
        }
        for (Application _0 : containingApp) {
          _cacheParentAttemptAccess.add(_0.fullAccess);
        }
        for (Application _1 : transitApp) {
          _cacheParentAttemptAccess.add(_1.fullAccess);
        }
        for (Application _2 : containingApp) {
          _cacheParentAttemptAccess.add(_2.modify);
        }
        for (Data _3 : containingData) {
          _cacheParentAttemptAccess.add(_3.readContainedInformationAndData);
        }
        for (Network _4 : transitNetwork) {
          _cacheParentAttemptAccess.add(_4.accessNetworkData);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.attemptAccess");
    }
  }

  public class AttemptAccessFromIdentity extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptAccessFromIdentity;

    private Set<AttackStep> _cacheParentAttemptAccessFromIdentity;

    public AttemptAccessFromIdentity(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptAccessFromIdentity == null) {
        _cacheChildrenAttemptAccessFromIdentity = new HashSet<>();
        _cacheChildrenAttemptAccessFromIdentity.add(identityRead);
        _cacheChildrenAttemptAccessFromIdentity.add(identityWrite);
        _cacheChildrenAttemptAccessFromIdentity.add(identityDelete);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptAccessFromIdentity) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptAccessFromIdentity == null) {
        _cacheParentAttemptAccessFromIdentity = new HashSet<>();
        for (Application _0 : containingApp) {
          _cacheParentAttemptAccessFromIdentity.add(_0.specificAccess);
        }
        for (Application _1 : transitApp) {
          _cacheParentAttemptAccessFromIdentity.add(_1.specificAccess);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptAccessFromIdentity) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.attemptAccessFromIdentity");
    }
  }

  public class Access extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenAccess;

    private Set<AttackStep> _cacheParentAccess;

    public Access(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAccess == null) {
        _cacheChildrenAccess = new HashSet<>();
        _cacheChildrenAccess.add(attemptRead);
        _cacheChildrenAccess.add(attemptWrite);
        _cacheChildrenAccess.add(attemptDelete);
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
        _cacheParentAccess.add(attemptAccess);
        _cacheParentAccess.add(accessDecryptedData);
      }
      for (AttackStep attackStep : _cacheParentAccess) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.access");
    }
  }

  public class DataEncrypted extends Defense {
    public DataEncrypted(String name) {
      super(name);
      disable = new Disable(name);
    }

    @Override
    public boolean isEnabled() {
      int count = 1;
      if (encryptCreds != null) {
        count--;
      }
      return count == 0;
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenDataEncrypted;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenDataEncrypted == null) {
          _cacheChildrenDataEncrypted = new HashSet<>();
          _cacheChildrenDataEncrypted.add(accessUnencryptedData);
        }
        for (AttackStep attackStep : _cacheChildrenDataEncrypted) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "Data.dataEncrypted";
      }
    }
  }

  public class Authenticated extends Defense {
    public Authenticated(String name) {
      this(name, false);
    }

    public Authenticated(String name, Boolean isEnabled) {
      super(name);
      defaultValue = isEnabled;
      disable = new Disable(name);
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenAuthenticated;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenAuthenticated == null) {
          _cacheChildrenAuthenticated = new HashSet<>();
          _cacheChildrenAuthenticated.add(applicationRespondConnect);
          _cacheChildrenAuthenticated.add(write);
          _cacheChildrenAuthenticated.add(manInTheMiddle);
        }
        for (AttackStep attackStep : _cacheChildrenAuthenticated) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "Data.authenticated";
      }
    }
  }

  public class AccessUnencryptedData extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenAccessUnencryptedData;

    private Set<AttackStep> _cacheParentAccessUnencryptedData;

    public AccessUnencryptedData(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAccessUnencryptedData == null) {
        _cacheChildrenAccessUnencryptedData = new HashSet<>();
        _cacheChildrenAccessUnencryptedData.add(accessDecryptedData);
      }
      for (AttackStep attackStep : _cacheChildrenAccessUnencryptedData) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAccessUnencryptedData == null) {
        _cacheParentAccessUnencryptedData = new HashSet<>();
        _cacheParentAccessUnencryptedData.add(dataEncrypted.disable);
        _cacheParentAccessUnencryptedData.add(dataNotPresent.disable);
      }
      for (AttackStep attackStep : _cacheParentAccessUnencryptedData) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.accessUnencryptedData");
    }
  }

  public class AccessDecryptedData extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAccessDecryptedData;

    private Set<AttackStep> _cacheParentAccessDecryptedData;

    public AccessDecryptedData(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAccessDecryptedData == null) {
        _cacheChildrenAccessDecryptedData = new HashSet<>();
        _cacheChildrenAccessDecryptedData.add(access);
        _cacheChildrenAccessDecryptedData.add(readContainedInformationAndData);
        _cacheChildrenAccessDecryptedData.add(applicationRespondConnect);
        _cacheChildrenAccessDecryptedData.add(eavesdrop);
        _cacheChildrenAccessDecryptedData.add(manInTheMiddle);
        _cacheChildrenAccessDecryptedData.add(read);
        _cacheChildrenAccessDecryptedData.add(write);
        _cacheChildrenAccessDecryptedData.add(delete);
      }
      for (AttackStep attackStep : _cacheChildrenAccessDecryptedData) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAccessDecryptedData == null) {
        _cacheParentAccessDecryptedData = new HashSet<>();
        _cacheParentAccessDecryptedData.add(accessUnencryptedData);
        if (encryptCreds != null) {
          _cacheParentAccessDecryptedData.add(encryptCreds.use);
        }
      }
      for (AttackStep attackStep : _cacheParentAccessDecryptedData) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.accessDecryptedData");
    }
  }

  public class DataNotPresent extends Defense {
    public DataNotPresent(String name) {
      this(name, false);
    }

    public DataNotPresent(String name, Boolean isEnabled) {
      super(name);
      defaultValue = isEnabled;
      disable = new Disable(name);
    }

    public class Disable extends AttackStepMin {
      private Set<AttackStep> _cacheChildrenDataNotPresent;

      public Disable(String name) {
        super(name);
      }

      @Override
      public void updateChildren(Set<AttackStep> attackSteps) {
        if (_cacheChildrenDataNotPresent == null) {
          _cacheChildrenDataNotPresent = new HashSet<>();
          _cacheChildrenDataNotPresent.add(accessUnencryptedData);
          _cacheChildrenDataNotPresent.add(deny);
        }
        for (AttackStep attackStep : _cacheChildrenDataNotPresent) {
          attackStep.updateTtc(this, ttc, attackSteps);
        }
      }

      @Override
      public String fullName() {
        return "Data.dataNotPresent";
      }
    }
  }

  public class ReadContainedInformationAndData extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenReadContainedInformationAndData;

    private Set<AttackStep> _cacheParentReadContainedInformationAndData;

    public ReadContainedInformationAndData(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenReadContainedInformationAndData == null) {
        _cacheChildrenReadContainedInformationAndData = new HashSet<>();
        for (Information _0 : information) {
          _cacheChildrenReadContainedInformationAndData.add(_0.attemptAccess);
        }
        for (Data _1 : containedData) {
          _cacheChildrenReadContainedInformationAndData.add(_1.attemptAccess);
        }
      }
      for (AttackStep attackStep : _cacheChildrenReadContainedInformationAndData) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentReadContainedInformationAndData == null) {
        _cacheParentReadContainedInformationAndData = new HashSet<>();
        _cacheParentReadContainedInformationAndData.add(accessDecryptedData);
        _cacheParentReadContainedInformationAndData.add(read);
      }
      for (AttackStep attackStep : _cacheParentReadContainedInformationAndData) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.readContainedInformationAndData");
    }
  }

  public class AttemptApplicationRespondConnect extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptApplicationRespondConnect;

    private Set<AttackStep> _cacheParentAttemptApplicationRespondConnect;

    public AttemptApplicationRespondConnect(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptApplicationRespondConnect == null) {
        _cacheChildrenAttemptApplicationRespondConnect = new HashSet<>();
        _cacheChildrenAttemptApplicationRespondConnect.add(applicationRespondConnect);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptApplicationRespondConnect) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptApplicationRespondConnect == null) {
        _cacheParentAttemptApplicationRespondConnect = new HashSet<>();
        for (Application _0 : transitApp) {
          _cacheParentAttemptApplicationRespondConnect.add(_0.attemptApplicationRespondConnectThroughData);
        }
        for (Application _1 : containingApp) {
          _cacheParentAttemptApplicationRespondConnect.add(_1.attemptApplicationRespondConnectThroughData);
        }
        _cacheParentAttemptApplicationRespondConnect.add(manInTheMiddle);
      }
      for (AttackStep attackStep : _cacheParentAttemptApplicationRespondConnect) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.attemptApplicationRespondConnect");
    }
  }

  public class ApplicationRespondConnect extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenApplicationRespondConnect;

    private Set<AttackStep> _cacheParentApplicationRespondConnect;

    public ApplicationRespondConnect(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenApplicationRespondConnect == null) {
        _cacheChildrenApplicationRespondConnect = new HashSet<>();
        for (Application _0 : transitApp) {
          _cacheChildrenApplicationRespondConnect.add(_0.networkRespondConnect);
        }
      }
      for (AttackStep attackStep : _cacheChildrenApplicationRespondConnect) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentApplicationRespondConnect == null) {
        _cacheParentApplicationRespondConnect = new HashSet<>();
        _cacheParentApplicationRespondConnect.add(authenticated.disable);
        _cacheParentApplicationRespondConnect.add(accessDecryptedData);
        _cacheParentApplicationRespondConnect.add(attemptApplicationRespondConnect);
      }
      for (AttackStep attackStep : _cacheParentApplicationRespondConnect) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.applicationRespondConnect");
    }
  }

  public class AttemptRead extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptRead;

    private Set<AttackStep> _cacheParentAttemptRead;

    public AttemptRead(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptRead == null) {
        _cacheChildrenAttemptRead = new HashSet<>();
        _cacheChildrenAttemptRead.add(read);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptRead) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptRead == null) {
        _cacheParentAttemptRead = new HashSet<>();
        for (Application _0 : containingApp) {
          _cacheParentAttemptRead.add(_0.read);
        }
        _cacheParentAttemptRead.add(access);
        _cacheParentAttemptRead.add(identityRead);
        for (Data _1 : containingData) {
          _cacheParentAttemptRead.add(_1.read);
        }
        _cacheParentAttemptRead.add(eavesdrop);
        _cacheParentAttemptRead.add(manInTheMiddle);
      }
      for (AttackStep attackStep : _cacheParentAttemptRead) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.attemptRead");
    }
  }

  public class IdentityAttemptRead extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenIdentityAttemptRead;

    private Set<AttackStep> _cacheParentIdentityAttemptRead;

    public IdentityAttemptRead(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenIdentityAttemptRead == null) {
        _cacheChildrenIdentityAttemptRead = new HashSet<>();
        _cacheChildrenIdentityAttemptRead.add(identityRead);
      }
      for (AttackStep attackStep : _cacheChildrenIdentityAttemptRead) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentIdentityAttemptRead == null) {
        _cacheParentIdentityAttemptRead = new HashSet<>();
        for (Identity _0 : readingIds) {
          _cacheParentIdentityAttemptRead.add(_0.assume);
        }
        for (Group _1 : readingGroups) {
          _cacheParentIdentityAttemptRead.add(_1.compromiseGroup);
        }
      }
      for (AttackStep attackStep : _cacheParentIdentityAttemptRead) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.identityAttemptRead");
    }
  }

  public class IdentityRead extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenIdentityRead;

    private Set<AttackStep> _cacheParentIdentityRead;

    public IdentityRead(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenIdentityRead == null) {
        _cacheChildrenIdentityRead = new HashSet<>();
        _cacheChildrenIdentityRead.add(attemptRead);
      }
      for (AttackStep attackStep : _cacheChildrenIdentityRead) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentIdentityRead == null) {
        _cacheParentIdentityRead = new HashSet<>();
        _cacheParentIdentityRead.add(attemptAccessFromIdentity);
        _cacheParentIdentityRead.add(identityAttemptRead);
      }
      for (AttackStep attackStep : _cacheParentIdentityRead) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.identityRead");
    }
  }

  public class AttemptWrite extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptWrite;

    private Set<AttackStep> _cacheParentAttemptWrite;

    public AttemptWrite(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptWrite == null) {
        _cacheChildrenAttemptWrite = new HashSet<>();
        _cacheChildrenAttemptWrite.add(write);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptWrite) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptWrite == null) {
        _cacheParentAttemptWrite = new HashSet<>();
        _cacheParentAttemptWrite.add(access);
        _cacheParentAttemptWrite.add(identityWrite);
        for (Data _0 : containingData) {
          _cacheParentAttemptWrite.add(_0.write);
        }
        _cacheParentAttemptWrite.add(manInTheMiddle);
      }
      for (AttackStep attackStep : _cacheParentAttemptWrite) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.attemptWrite");
    }
  }

  public class IdentityAttemptWrite extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenIdentityAttemptWrite;

    private Set<AttackStep> _cacheParentIdentityAttemptWrite;

    public IdentityAttemptWrite(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenIdentityAttemptWrite == null) {
        _cacheChildrenIdentityAttemptWrite = new HashSet<>();
        _cacheChildrenIdentityAttemptWrite.add(identityWrite);
      }
      for (AttackStep attackStep : _cacheChildrenIdentityAttemptWrite) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentIdentityAttemptWrite == null) {
        _cacheParentIdentityAttemptWrite = new HashSet<>();
        for (Identity _0 : writingIds) {
          _cacheParentIdentityAttemptWrite.add(_0.assume);
        }
        for (Group _1 : writingGroups) {
          _cacheParentIdentityAttemptWrite.add(_1.compromiseGroup);
        }
      }
      for (AttackStep attackStep : _cacheParentIdentityAttemptWrite) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.identityAttemptWrite");
    }
  }

  public class IdentityWrite extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenIdentityWrite;

    private Set<AttackStep> _cacheParentIdentityWrite;

    public IdentityWrite(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenIdentityWrite == null) {
        _cacheChildrenIdentityWrite = new HashSet<>();
        _cacheChildrenIdentityWrite.add(attemptWrite);
      }
      for (AttackStep attackStep : _cacheChildrenIdentityWrite) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentIdentityWrite == null) {
        _cacheParentIdentityWrite = new HashSet<>();
        _cacheParentIdentityWrite.add(attemptAccessFromIdentity);
        _cacheParentIdentityWrite.add(identityAttemptWrite);
      }
      for (AttackStep attackStep : _cacheParentIdentityWrite) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.identityWrite");
    }
  }

  public class AttemptDelete extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptDelete;

    private Set<AttackStep> _cacheParentAttemptDelete;

    public AttemptDelete(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptDelete == null) {
        _cacheChildrenAttemptDelete = new HashSet<>();
        _cacheChildrenAttemptDelete.add(delete);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptDelete) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptDelete == null) {
        _cacheParentAttemptDelete = new HashSet<>();
        _cacheParentAttemptDelete.add(access);
        _cacheParentAttemptDelete.add(identityDelete);
        _cacheParentAttemptDelete.add(write);
        for (Data _0 : containingData) {
          _cacheParentAttemptDelete.add(_0.delete);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptDelete) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.attemptDelete");
    }
  }

  public class IdentityAttemptDelete extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenIdentityAttemptDelete;

    private Set<AttackStep> _cacheParentIdentityAttemptDelete;

    public IdentityAttemptDelete(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenIdentityAttemptDelete == null) {
        _cacheChildrenIdentityAttemptDelete = new HashSet<>();
        _cacheChildrenIdentityAttemptDelete.add(identityDelete);
      }
      for (AttackStep attackStep : _cacheChildrenIdentityAttemptDelete) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentIdentityAttemptDelete == null) {
        _cacheParentIdentityAttemptDelete = new HashSet<>();
        for (Identity _0 : deletingIds) {
          _cacheParentIdentityAttemptDelete.add(_0.assume);
        }
        for (Group _1 : deletingGroups) {
          _cacheParentIdentityAttemptDelete.add(_1.compromiseGroup);
        }
      }
      for (AttackStep attackStep : _cacheParentIdentityAttemptDelete) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.identityAttemptDelete");
    }
  }

  public class IdentityDelete extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenIdentityDelete;

    private Set<AttackStep> _cacheParentIdentityDelete;

    public IdentityDelete(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenIdentityDelete == null) {
        _cacheChildrenIdentityDelete = new HashSet<>();
        _cacheChildrenIdentityDelete.add(attemptDelete);
      }
      for (AttackStep attackStep : _cacheChildrenIdentityDelete) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentIdentityDelete == null) {
        _cacheParentIdentityDelete = new HashSet<>();
        _cacheParentIdentityDelete.add(attemptAccessFromIdentity);
        _cacheParentIdentityDelete.add(identityAttemptDelete);
      }
      for (AttackStep attackStep : _cacheParentIdentityDelete) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.identityDelete");
    }
  }

  public class Read extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenRead;

    private Set<AttackStep> _cacheParentRead;

    public Read(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenRead == null) {
        _cacheChildrenRead = new HashSet<>();
        for (Data _0 : containedData) {
          _cacheChildrenRead.add(_0.attemptRead);
        }
        _cacheChildrenRead.add(readContainedInformationAndData);
      }
      for (AttackStep attackStep : _cacheChildrenRead) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentRead == null) {
        _cacheParentRead = new HashSet<>();
        _cacheParentRead.add(accessDecryptedData);
        _cacheParentRead.add(attemptRead);
      }
      for (AttackStep attackStep : _cacheParentRead) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.read");
    }
  }

  public class Write extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenWrite;

    private Set<AttackStep> _cacheParentWrite;

    public Write(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenWrite == null) {
        _cacheChildrenWrite = new HashSet<>();
        for (Data _0 : containedData) {
          _cacheChildrenWrite.add(_0.attemptWrite);
        }
        _cacheChildrenWrite.add(attemptDelete);
        _cacheChildrenWrite.add(compromiseAppOrigin);
      }
      for (AttackStep attackStep : _cacheChildrenWrite) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentWrite == null) {
        _cacheParentWrite = new HashSet<>();
        _cacheParentWrite.add(authenticated.disable);
        _cacheParentWrite.add(accessDecryptedData);
        _cacheParentWrite.add(attemptWrite);
      }
      for (AttackStep attackStep : _cacheParentWrite) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.write");
    }
  }

  public class Delete extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenDelete;

    private Set<AttackStep> _cacheParentDelete;

    public Delete(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenDelete == null) {
        _cacheChildrenDelete = new HashSet<>();
        for (Data _0 : containedData) {
          _cacheChildrenDelete.add(_0.attemptDelete);
        }
      }
      for (AttackStep attackStep : _cacheChildrenDelete) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentDelete == null) {
        _cacheParentDelete = new HashSet<>();
        _cacheParentDelete.add(accessDecryptedData);
        _cacheParentDelete.add(attemptDelete);
      }
      for (AttackStep attackStep : _cacheParentDelete) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.delete");
    }
  }

  public class AttemptDeny extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptDeny;

    private Set<AttackStep> _cacheParentAttemptDeny;

    public AttemptDeny(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptDeny == null) {
        _cacheChildrenAttemptDeny = new HashSet<>();
        _cacheChildrenAttemptDeny.add(deny);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptDeny) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptDeny == null) {
        _cacheParentAttemptDeny = new HashSet<>();
        for (Application _0 : containingApp) {
          _cacheParentAttemptDeny.add(_0.deny);
        }
        for (Application _1 : transitApp) {
          _cacheParentAttemptDeny.add(_1.deny);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptDeny) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.attemptDeny");
    }
  }

  public class Deny extends AttackStepMax {
    private Set<AttackStep> _cacheChildrenDeny;

    private Set<AttackStep> _cacheParentDeny;

    public Deny(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenDeny == null) {
        _cacheChildrenDeny = new HashSet<>();
        for (Data _0 : containedData) {
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
        _cacheParentDeny.add(dataNotPresent.disable);
        _cacheParentDeny.add(attemptDeny);
        for (Data _1 : containingData) {
          _cacheParentDeny.add(_1.deny);
        }
      }
      for (AttackStep attackStep : _cacheParentDeny) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.deny");
    }
  }

  public class AttemptEavesdrop extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptEavesdrop;

    private Set<AttackStep> _cacheParentAttemptEavesdrop;

    public AttemptEavesdrop(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptEavesdrop == null) {
        _cacheChildrenAttemptEavesdrop = new HashSet<>();
        _cacheChildrenAttemptEavesdrop.add(eavesdrop);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptEavesdrop) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptEavesdrop == null) {
        _cacheParentAttemptEavesdrop = new HashSet<>();
        for (Network _0 : transitNetwork) {
          _cacheParentAttemptEavesdrop.add(_0.accessNetworkData);
        }
        for (Application _1 : transitApp) {
          for (var _2 : _1._reverseallowedApplicationConnectionsApplicationsNetwork()) {
            _cacheParentAttemptEavesdrop.add(_2.eavesdrop);
          }
        }
        for (Application _3 : transitApp) {
          for (var _4 : _3._reverseallowedApplicationConnectionsApplicationsNetwork()) {
            _cacheParentAttemptEavesdrop.add(_4.bypassEavesdropProtection);
          }
        }
        for (Application _5 : transitApp) {
          for (var _6 : _5._reverseallowedApplicationConnectionsApplicationsNetwork()) {
            _cacheParentAttemptEavesdrop.add(_6.eavesdropAfterPhysicalAccess);
          }
        }
        for (Network _7 : transitNetwork) {
          _cacheParentAttemptEavesdrop.add(_7.eavesdropAfterPhysicalAccess);
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptEavesdrop) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.attemptEavesdrop");
    }
  }

  public class AttemptManInTheMiddle extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenAttemptManInTheMiddle;

    private Set<AttackStep> _cacheParentAttemptManInTheMiddle;

    public AttemptManInTheMiddle(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenAttemptManInTheMiddle == null) {
        _cacheChildrenAttemptManInTheMiddle = new HashSet<>();
        _cacheChildrenAttemptManInTheMiddle.add(manInTheMiddle);
      }
      for (AttackStep attackStep : _cacheChildrenAttemptManInTheMiddle) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentAttemptManInTheMiddle == null) {
        _cacheParentAttemptManInTheMiddle = new HashSet<>();
        for (Network _0 : transitNetwork) {
          _cacheParentAttemptManInTheMiddle.add(_0.accessNetworkData);
        }
        for (Application _1 : transitApp) {
          for (var _2 : _1._reverseallowedApplicationConnectionsApplicationsNetwork()) {
            _cacheParentAttemptManInTheMiddle.add(_2.manInTheMiddle);
          }
        }
        for (Application _3 : transitApp) {
          for (var _4 : _3._reverseallowedApplicationConnectionsApplicationsNetwork()) {
            _cacheParentAttemptManInTheMiddle.add(_4.bypassMitMProtection);
          }
        }
      }
      for (AttackStep attackStep : _cacheParentAttemptManInTheMiddle) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.attemptManInTheMiddle");
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
        _cacheChildrenEavesdrop.add(attemptRead);
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
        _cacheParentEavesdrop.add(accessDecryptedData);
        _cacheParentEavesdrop.add(attemptEavesdrop);
      }
      for (AttackStep attackStep : _cacheParentEavesdrop) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.eavesdrop");
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
        _cacheChildrenManInTheMiddle.add(attemptRead);
        _cacheChildrenManInTheMiddle.add(attemptWrite);
        _cacheChildrenManInTheMiddle.add(attemptApplicationRespondConnect);
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
        _cacheParentManInTheMiddle.add(authenticated.disable);
        _cacheParentManInTheMiddle.add(accessDecryptedData);
        _cacheParentManInTheMiddle.add(attemptManInTheMiddle);
      }
      for (AttackStep attackStep : _cacheParentManInTheMiddle) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.manInTheMiddle");
    }
  }

  public class CompromiseAppOrigin extends AttackStepMin {
    private Set<AttackStep> _cacheChildrenCompromiseAppOrigin;

    private Set<AttackStep> _cacheParentCompromiseAppOrigin;

    public CompromiseAppOrigin(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (_cacheChildrenCompromiseAppOrigin == null) {
        _cacheChildrenCompromiseAppOrigin = new HashSet<>();
        if (originSoftwareProduct != null) {
          _cacheChildrenCompromiseAppOrigin.add(originSoftwareProduct.compromiseApplication);
        }
      }
      for (AttackStep attackStep : _cacheChildrenCompromiseAppOrigin) {
        attackStep.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (_cacheParentCompromiseAppOrigin == null) {
        _cacheParentCompromiseAppOrigin = new HashSet<>();
        _cacheParentCompromiseAppOrigin.add(write);
      }
      for (AttackStep attackStep : _cacheParentCompromiseAppOrigin) {
        addExpectedParent(attackStep);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Data.compromiseAppOrigin");
    }
  }
}
