package dev.manpreet.kaostest.runner.listeners;

import dev.manpreet.kaostest.dto.internal.RunnerStore;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

@Slf4j
public class StoreIncrementListener extends TestListenerAdapter {

    private final RunnerStore runnerStore;

    public StoreIncrementListener() {
        runnerStore = RunnerStore.getRunnerStore();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String className = result.getTestClass().getName();
        long durationMilliSeconds = result.getEndMillis() - result.getStartMillis();
        log.info("Adding failed test for class (" + className + ") with (" + durationMilliSeconds + " ms) duration");
        runnerStore.addFailedTest(className, durationMilliSeconds);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String className = result.getTestClass().getName();
        long durationMilliSeconds = result.getEndMillis() - result.getStartMillis();
        log.info("Adding passed test for class (" + className + ") with (" + durationMilliSeconds + " ms) duration");
        runnerStore.addPassedTest(className, durationMilliSeconds);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String className = result.getTestClass().getName();
        long durationMilliSeconds = result.getEndMillis() - result.getStartMillis();
        log.info("Adding skipped test for class (" + className + ") with (" + durationMilliSeconds + " ms) duration");
        runnerStore.addSkippedTest(className, durationMilliSeconds);
    }
}
