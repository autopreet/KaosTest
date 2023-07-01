package dev.manpreet.demotests.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

@Slf4j
public class AnotherListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        long durationMilliSeconds = result.getEndMillis() - result.getStartMillis();
        log.info("Test (" + testName + ") failed in " + durationMilliSeconds + " ms.");
    }
}
