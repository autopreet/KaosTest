package dev.manpreet.rpdtest.runner.listeners;

import dev.manpreet.rpdtest.dto.internal.RunnerStore;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class StoreIncrementListener extends TestListenerAdapter {

    private final RunnerStore runnerStore;

    public StoreIncrementListener() {
        runnerStore = RunnerStore.getRunnerStore();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String className = result.getTestClass().getName();
        long durationMilliSeconds = result.getEndMillis() - result.getStartMillis();
        runnerStore.addFailedTest(className, durationMilliSeconds);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String className = result.getTestClass().getName();
        long durationMilliSeconds = result.getEndMillis() - result.getStartMillis();
        runnerStore.addPassedTest(className, durationMilliSeconds);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String className = result.getTestClass().getName();
        long durationMilliSeconds = result.getEndMillis() - result.getStartMillis();
        runnerStore.addSkippedTest(className, durationMilliSeconds);
    }
}
