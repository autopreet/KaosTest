package dev.manpreet.kaostest.preprocessing;

import dev.manpreet.kaostest.dto.testng.suite.Suite;
import dev.manpreet.kaostest.dto.testng.suite.SuiteClass;
import dev.manpreet.kaostest.dto.testng.suite.SuitePackage;
import dev.manpreet.kaostest.stores.Store;
import dev.manpreet.kaostest.stores.TestClassData;
import dev.manpreet.kaostest.stores.TestMethodData;
import dev.manpreet.kaostest.stores.TestPackageData;
import dev.manpreet.kaostest.util.SuiteXMLUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class TestSetupStores {

    private final Store store = Store.getInstance();
    private final SetupStores setupStores = new SetupStores();
    private final String resourcesPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources")
            .toString();
    private Suite suite;

    @AfterMethod
    public void clearStore() {
        store.clear();
    }

    @Test
    public void testStoreSetupWithClassesAndPackagesInSuite() {
        updateStore(Paths.get(resourcesPath, "testsuite_classes_packages.xml").toString());
        assertListeners();
        assertStore();
    }

    @Test
    public void testStoreSetupWithClassesOnlyInSuite() {
        updateStore(Paths.get(resourcesPath, "testsuite_classes.xml").toString());
        assertListeners();
        assertStore();
    }

    @Test
    public void testStoreSetupWithPackagesOnlyInSuite() {
        updateStore(Paths.get(resourcesPath, "testsuite_packages.xml").toString());
        assertListeners();
        assertStore();
    }

    @Test
    public void testStoreSetupWithClassesOnlyInOneTestSuite() {
        updateStore(Paths.get(resourcesPath, "testsuite_singletest_classes.xml").toString());
        assertListeners();
        assertStore();
    }

    @Test
    public void testStoreSetupWithPackagesOnlyInOneTestSuite() {
        updateStore(Paths.get(resourcesPath, "testsuite_singletest_packages.xml").toString());
        assertListeners();
        assertStore();
    }

    private void updateStore(String suiteXMLPath) {
        suite = SuiteXMLUtils.deserializeSuiteXML(suiteXMLPath);
        SuiteXMLUtils.validateSuite(suite);
        List<SuiteClass> testClasses = suite.getAllTestClasses();
        List<SuitePackage> testPackages = suite.getAllTestPackages();
        testClasses.forEach(eachClass -> setupStores.addTests(eachClass.getName(), true));
        testPackages.forEach(eachPackage -> setupStores.addTests(eachPackage.getName(), false));
    }

    private void assertListeners() {
        assertNotNull(suite.getListener());
        assertEquals(suite.getListener().size(), 2);
        Set<String> expectedListeners = new HashSet<>();
        expectedListeners.add("dev.manpreet.demotests.listeners.TestListener");
        expectedListeners.add("dev.manpreet.demotests.listeners.AnotherListener");
        assertTrue(expectedListeners.contains(suite.getListener().get(0).getClassName()));
        assertTrue(expectedListeners.contains(suite.getListener().get(1).getClassName()));
    }

    private void assertStore() {
        assertEquals(store.getName(), "ALL");
        assertNotNull(store.getPackagesData());
        System.out.println(store.getPackagesData());
        //Reflections library ends up loading the current tests in this class also as it scans current classloader
        removeInternalPackages();
        System.out.println(store.getPackagesData().values().stream().map(TestPackageData::getName));
        assertEquals(store.getPackagesData().size(), 2);

        TestPackageData demoTestPkg = assertPackageInStore("dev.manpreet.demotests");
        assertNotNull(demoTestPkg.getTestClassesData());
        assertEquals(demoTestPkg.getTestClassesData().size(), 2);

        TestClassData tClassAData = assertClassInPackageData(demoTestPkg, "TestClassA");
        assertMethodInClassData(tClassAData, "runPassingTest1");
        assertMethodInClassData(tClassAData, "runPassingTest2");
        TestClassData tClassBData = assertClassInPackageData(demoTestPkg, "TestClassB");
        assertMethodInClassData(tClassBData, "runPassingTest1");
        assertMethodInClassData(tClassBData, "runFailingTest2");
        assertMethodInClassData(tClassBData, "runSkippingTest3");

        TestPackageData moreTestPkg = assertPackageInStore("dev.manpreet.demotests.moretests");
        assertNotNull(moreTestPkg.getTestClassesData());
        assertEquals(moreTestPkg.getTestClassesData().size(), 1);

        TestClassData tClassCData = assertClassInPackageData(moreTestPkg, "TestClassC");
        assertMethodInClassData(tClassCData, "runPassingTest1");
        assertMethodInClassData(tClassCData, "runPassingTest2");
    }

    private TestPackageData assertPackageInStore(String packageName) {
        TestPackageData testPackageData = store.getPackagesData().get(packageName);
        assertNotNull(testPackageData);
        assertEquals(testPackageData.getName(), packageName);
        assertNotNull(testPackageData.getSubscriber());
        assertEquals(testPackageData.getSubscriber(), store);
        return testPackageData;
    }

    private TestClassData assertClassInPackageData(TestPackageData testPackageData, String className) {
        TestClassData testClassData = testPackageData.getTestClassesData().get(className);
        assertNotNull(testClassData);
        assertEquals(testClassData.getName(), className);
        assertNotNull(testClassData.getSubscriber());
        assertEquals(testClassData.getSubscriber(), testPackageData);
        return testClassData;
    }

    private void assertMethodInClassData(TestClassData testClassData, String methodName) {
        TestMethodData testMethodData = testClassData.getTestMethodsData().get(methodName);
        assertNotNull(testMethodData);
        assertEquals(testMethodData.getName(), methodName);
        assertNotNull(testMethodData.getSubscriber());
        assertEquals(testMethodData.getSubscriber(), testClassData);
        assertNotNull(testMethodData.getInstancesData());
    }

    private void removeInternalPackages() {
        store.getPackagesData().remove("dev.manpreet.kaostest.preprocessing");
        store.getPackagesData().remove("dev.manpreet.kaostest.providers.threadcount");
        store.getPackagesData().remove("dev.manpreet.kaostest.providers.duration");
    }
}
