package dev.manpreet.rpdtest.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

@Slf4j
public class TestListener extends TestListenerAdapter {

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        long durationMilliSeconds = result.getEndMillis() - result.getStartMillis();
        log.info("Test (" + testName + ") passed in " + durationMilliSeconds + " ms.");
    }
}
