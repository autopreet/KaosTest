package dev.manpreet.rpdtest;

import dev.manpreet.rpdtest.dto.internal.RunnerStore;
import dev.manpreet.rpdtest.dto.xml.Suite;
import dev.manpreet.rpdtest.providers.DurationProvider;
import dev.manpreet.rpdtest.providers.ThreadCountProvider;
import dev.manpreet.rpdtest.providers.duration.FixedDurationProvider;
import dev.manpreet.rpdtest.providers.threadcount.FixedThreadCountProvider;
import dev.manpreet.rpdtest.runner.RunnersManager;
import dev.manpreet.rpdtest.util.SuiteXMLUtils;

import java.util.concurrent.TimeUnit;

public class Runner {

    public void runTests(String suiteXmlPath) {

        ThreadCountProvider threadCountProvider = new FixedThreadCountProvider(10);
        DurationProvider durationProvider = new FixedDurationProvider(5, TimeUnit.MINUTES);
        runTests(suiteXmlPath, threadCountProvider, durationProvider);
    }

    public void runTests(String suiteXmlPath, ThreadCountProvider threadCountProvider, DurationProvider durationProvider) {

        Suite suite = SuiteXMLUtils.deserializeSuiteXML(suiteXmlPath);
        if (!SuiteXMLUtils.isSuiteValid(suite)) {
            throw new RPDException("Validation of suite XML failed. Please check logs");
        }
        RunnerStore runnerStore = new RunnerStore(SuiteXMLUtils.getAllTestClasses(suite));

        RunnersManager testRunnersManager = new RunnersManager(threadCountProvider, durationProvider);
    }
}
