package corelang.test;

import core.AttackStep;
import core.Attacker;
import graph.AttackNode;
import org.junit.jupiter.api.Test;
import test.*;
import test.System;
import graph.AttackGraph;

public class StandardIntegrationTest extends CoreLangTest {
    public class StandardIntegrationTestModel {
        public final System workstn1 = new System("workstn1");
        public final System workstn2 = new System("workstn2");
        public final System workstn3 = new System("workstn3");
        public final System workstn4 = new System("workstn4");
        public final System workstn5 = new System("workstn5");
        public final Application wos1 = new Application("WinOS1");
        public final Application wos2 = new Application("WinOS2");
        public final Application wos3 = new Application("WinOS3");
        public final Application linuxos = new Application("LinuxOS");
        public final Application os5 = new Application("OS5");
        public final SoftwareProduct WinOS = new SoftwareProduct("Windows OS");
        public final Identity domainAdmin = new Identity("DomainAdmin");
        public final Identity id1 = new Identity("ID1");
        public final Identity id2 = new Identity("ID2");
        public final Identity id3 = new Identity("ID3");
        public final Group workstnGroup = new Group("WorkstationGroup");
        public final Application localFS = new Application("LocalFileServer");
        public final Application mailClient = new Application("MailClient");
        public final Application fileClient = new Application("FileClient");
        public final Data fs1 = new Data("FileShare1");
        public final Data sharedData = new Data("SharedData");
        public final Data db = new Data("DB");
        public final Data fs3 = new Data("FileShare3");
        public final Network lan1 = new Network("LAN1");
        public final ConnectionRule cn1 = new ConnectionRule("ConnectionRule1");
        public final Application publicMail = new Application("PublicMailServer");
        public final Network wan = new Network("WAN");
        public final ConnectionRule wanAccess = new ConnectionRule("WANaccess");
        public final ConnectionRule cn2 = new ConnectionRule("ConnectionRule2");
        public final Network lan2 = new Network("LAN2");
        public final Application webBrowser = new Application("WebBrowser");
        public final Application localFS2 = new Application("LocalFileServer2");
        public final Application intranetMail = new Application("IntranetMailServer");
        public final Data mailDb = new Data("MailDB");
        public final Data db2 = new Data("DB2");


        public StandardIntegrationTestModel() {
            // Create associations
            workstn1.addSysExecutedApps(wos1);
            workstn2.addSysExecutedApps(wos2);
            workstn3.addSysExecutedApps(wos3);
            workstn4.addSysExecutedApps(linuxos);
            workstn5.addSysExecutedApps(os5);
            wos1.addAppSoftProduct(WinOS);
            wos2.addAppSoftProduct(WinOS);
            wos3.addAppSoftProduct(WinOS);
            domainAdmin.addChildId(id1);
            domainAdmin.addChildId(id2);
            domainAdmin.addChildId(id3);
            workstnGroup.addGroupIds(id1);
            workstnGroup.addGroupIds(id2);
            workstnGroup.addGroupIds(id3);
            wos1.addAppExecutedApps(localFS);
            wos2.addAppExecutedApps(mailClient);
            linuxos.addAppExecutedApps(fileClient);
            localFS.addContainedData(sharedData);
            wos2.addContainedData(fs1);
            wos3.addContainedData(fs3);
            linuxos.addContainedData(fs3);
            domainAdmin.addHighPrivApps(wos1);
            domainAdmin.addExecPrivApps(localFS);
            domainAdmin.addWritePrivData(sharedData);
            id2.addHighPrivApps(wos2);
            id2.addExecPrivApps(mailClient);
            id3.addReadPrivData(sharedData);
            id3.addHighPrivApps(wos3);

            lan1.addApplications(localFS);
            lan1.addClientApplications(fileClient);
            cn1.addApplications(publicMail);
            cn1.addApplications(mailClient);
            wan.addApplications(publicMail);
            wanAccess.addNetworks(wan);
            cn2.addNetworks(lan1);
            cn2.addNetworks(lan2);

            id1.addHighPrivApps(os5);
            id1.addExecPrivApps(webBrowser);
            os5.addAppExecutedApps(webBrowser);
            wanAccess.addOutApplications(webBrowser);
            lan2.addApplications(localFS2);
            lan2.addApplications(intranetMail);
            intranetMail.addContainedData(mailDb);
            localFS2.addContainedData(db2);

        }

        public void addAttacker(Attacker attacker, AttackStep entryPoint) {
            attacker.addAttackPoint(entryPoint);
        }

        public void assertModel(AttackStep target) {
            target.assertCompromisedInstantaneously();
        }
    }

    @Test
    public void attackGraphGeneratorTest() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());

        var model = new StandardIntegrationTestModel();

//        AttackStep entryPoint = model.workstn2.fullAccess;
//        AttackStep target = model.mailClient.read;

        AttackStep entryPoint = model.lan1.access;
        AttackStep target = model.mailDb.deny;

        var atk = new Attacker();
        model.addAttacker(atk, entryPoint);
        atk.attack();

        // the attack graph is built here with all relevant steps concerning the target asset
        AttackGraph attackGraph = new AttackGraph(entryPoint, target);
        attackGraph.expandGraph();
        attackGraph.printGraph();

        model.assertModel(target);
    }
}
