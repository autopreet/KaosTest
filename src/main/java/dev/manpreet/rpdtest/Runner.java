package dev.manpreet.rpdtest;

import dev.manpreet.rpdtest.dto.internal.RunnerStore;
import dev.manpreet.rpdtest.dto.internal.TestStore;
import dev.manpreet.rpdtest.dto.xml.Suite;
import dev.manpreet.rpdtest.providers.DurationProvider;
import dev.manpreet.rpdtest.providers.ThreadCountProvider;
import dev.manpreet.rpdtest.providers.duration.FixedDurationProvider;
import dev.manpreet.rpdtest.providers.threadcount.FixedThreadCountProvider;
import dev.manpreet.rpdtest.runner.TestRunnersManager;
import dev.manpreet.rpdtest.util.SuiteXMLUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Runner {

    public void runTests(String suiteXmlPath) {
        ThreadCountProvider threadCountProvider = new FixedThreadCountProvider(10);
        DurationProvider durationProvider = new FixedDurationProvider(5, TimeUnit.MINUTES);
        runTests(suiteXmlPath, threadCountProvider, durationProvider);
    }

    public RunnerStore runTests(String suiteXmlPath, ThreadCountProvider threadCountProvider, DurationProvider durationProvider) {

        Suite suite = SuiteXMLUtils.deserializeSuiteXML(suiteXmlPath);
        if (!SuiteXMLUtils.isSuiteValid(suite)) {
            throw new RPDException("Validation of suite XML failed. Please check logs");
        }
        //Initialize runner store
        RunnerStore.getRunnerStore(SuiteXMLUtils.getAllTestClasses(suite));
        TestRunnersManager testRunnersManager = new TestRunnersManager(threadCountProvider, durationProvider,
                SuiteXMLUtils.getAllListenerClasses(suite));
        testRunnersManager.runTests();
        return getPrintResults();
    }

    private RunnerStore getPrintResults() {
        RunnerStore runnerStore = RunnerStore.getRunnerStore();
        StringBuilder testResult = new StringBuilder("\n");
        for (TestStore eachTest: runnerStore.getTestStoreMap().values()) {
            testResult.append(eachTest.getTestName());
            testResult.append("\n");
            testResult.append(eachTest.toString());
            testResult.append("\n");
        }
        testResult.append("\nOverall Summary:\n");
        testResult.append(runnerStore.toString());
        log.info(testResult.toString());
        return runnerStore;
    }
}
