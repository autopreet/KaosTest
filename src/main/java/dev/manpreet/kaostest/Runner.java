package dev.manpreet.kaostest;

import dev.manpreet.kaostest.dto.testng.suite.Suite;
import dev.manpreet.kaostest.dto.testng.suite.SuiteClass;
import dev.manpreet.kaostest.dto.testng.suite.SuiteListener;
import dev.manpreet.kaostest.dto.testng.suite.SuitePackage;
import dev.manpreet.kaostest.preprocessing.SetupStores;
import dev.manpreet.kaostest.providers.DurationProvider;
import dev.manpreet.kaostest.providers.ThreadCountProvider;
import dev.manpreet.kaostest.providers.duration.FixedDurationProvider;
import dev.manpreet.kaostest.providers.threadcount.FixedThreadCountProvider;
import dev.manpreet.kaostest.runner.TestRunnersManager;
import dev.manpreet.kaostest.stores.Store;
import dev.manpreet.kaostest.util.SuiteXMLUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Our main API class that should be used.
 */
@Slf4j
public class Runner {

    /**
     * Run the tests in the defined suite XML for 5 minutes in 10 threads
     * @param suiteXmlPath - TestNG XML suite file
     */
    public void runTests(String suiteXmlPath) {
        ThreadCountProvider threadCountProvider = new FixedThreadCountProvider(10);
        DurationProvider durationProvider = new FixedDurationProvider(5, TimeUnit.MINUTES);
        runTests(suiteXmlPath, threadCountProvider, durationProvider);
    }

    /**
     * Run the tests defined in the suite XML as per the provider configurations.
     * @param suiteXmlPath - TestNG XML suite file
     * @param threadCountProvider - Instance of thread count provider
     * @param durationProvider - Instance of duration provider
     * @return Store - Holds statistics about the complete execution
     */
    public Store runTests(String suiteXmlPath, ThreadCountProvider threadCountProvider,
                          DurationProvider durationProvider) {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(suiteXmlPath);
        SuiteXMLUtils.validateSuite(suite);
        //Initialize runner store
        Store store = Store.getInstance();
        SetupStores setupStores = new SetupStores();
        if (!suite.getAllTestPackages().isEmpty()) {
            suite.getAllTestPackages().stream().map(SuitePackage::getName)
                    .forEach(eachPackage -> setupStores.addTests(eachPackage, false));
        }
        if (!suite.getAllTestPackages().isEmpty()) {
            suite.getAllTestClasses().stream().map(SuiteClass::getName)
                    .forEach(eachClass -> setupStores.addTests(eachClass, true));
        }
        TestRunnersManager testRunnersManager = new TestRunnersManager(threadCountProvider, durationProvider,
                suite.getListener().stream().map(SuiteListener::getClassName).toList());
        testRunnersManager.runTests();
        //return getPrintResults();
        return store;
    }

    //private Store getPrintResults() {
    //    Store runnerStore = Store.getInstance();
    //    StringBuilder testResult = new StringBuilder("\n");
    //    for (TestStore eachTest: runnerStore.getTestStoreMap().values()) {
    //        testResult.append(eachTest.getTestName());
    //        testResult.append("\n");
    //        testResult.append(eachTest.toString());
    //        testResult.append("\n");
    //    }
    //    testResult.append("\nOverall Summary:\n");
    //    testResult.append(runnerStore.toString());
    //    log.info(testResult.toString());
    //    return runnerStore;
    //}
}
