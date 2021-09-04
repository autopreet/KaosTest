package dev.manpreet.rpdtest.runner;

import dev.manpreet.rpdtest.RPDException;
import dev.manpreet.rpdtest.providers.DurationProvider;
import dev.manpreet.rpdtest.providers.PollingProvider;
import dev.manpreet.rpdtest.providers.ThreadCountProvider;
import dev.manpreet.rpdtest.runner.listeners.StoreIncrementListener;
import dev.manpreet.rpdtest.util.SuiteXMLUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestNGListener;
import org.testng.TestNG;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class TestRunnersManager {

    private final ExecutorService executorService;
    private final ThreadCountProvider threadCountProvider;
    private final DurationProvider durationProvider;
    private final List<String> inputListeners;
    private final List<TestRunner> testRunners;
    private final boolean isPollThreadCount;
    private final TestNG baseTestNG;
    private final int waitFreqSecs;

    public TestRunnersManager(ThreadCountProvider threadCountProvider, DurationProvider durationProvider, List<String> inputListeners) {
        this.threadCountProvider = threadCountProvider;
        this.durationProvider = durationProvider;
        this.inputListeners = inputListeners;
        if (threadCountProvider instanceof PollingProvider) {
            isPollThreadCount = true;
            this.executorService = Executors.newCachedThreadPool();
            waitFreqSecs = ((PollingProvider) threadCountProvider).getPollSeconds();
        } else {
            isPollThreadCount = false;
            this.executorService = Executors.newFixedThreadPool(threadCountProvider.getThreadCount());
            waitFreqSecs = 60;
        }
        baseTestNG = getTestNGInstance();
        testRunners = new ArrayList<>();
    }

    public void runTests() {
        IntStream.range(0, threadCountProvider.getThreadCount()).forEach(x -> scheduleRunner());

        while (!durationProvider.stopTests()) {
            sleep(waitFreqSecs);
            if (isPollThreadCount) {
                updateThreadCount();
            }
        }

        testRunners.forEach(TestRunner::stop);
        //Wait another 3 cycles
        int remCycles = 3;
        while (remCycles > 0 && areTestsRunning()) {
            sleep(waitFreqSecs);
            remCycles--;
        }
        executorService.shutdown();
    }

    private void updateThreadCount() {
        int newThreadCount = threadCountProvider.getThreadCount();
        int currentCount = testRunners.size();
        if (currentCount > newThreadCount) {
            int difference = currentCount - newThreadCount;
            IntStream.range(0, difference).forEach(x -> removeRunner());
        } else if (currentCount < newThreadCount) {
            int difference = newThreadCount - currentCount;
            IntStream.range(0, difference).forEach(x -> scheduleRunner());
        }
    }

    private boolean areTestsRunning() {
        return testRunners.stream().anyMatch(runner -> !runner.isFinished());
    }

    private TestNG getTestNGInstance() {
        TestNG testNG = new TestNG();
        //Configure listeners
        List<Class> inputListenerClasses = SuiteXMLUtils.getClassFromName(inputListeners);
        for (Class listener : inputListenerClasses) {
            if (ITestNGListener.class.isAssignableFrom(listener)) {
                try {
                    testNG.addListener((ITestNGListener) listener.getConstructor().newInstance());
                } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throwExceptionForListener(e, listener.getName());
                }
            } else {
                log.error("Provided listener class (" + listener.getName() + ") is not a valid subtype of ITestNGListener");
            }
        }
        testNG.addListener(new StoreIncrementListener());
        testNG.alwaysRunListeners(true);

        testNG.setVerbose(-1);
        return testNG;
    }

    private void scheduleRunner() {
        TestRunner testRunner = new TestRunner(baseTestNG);
        testRunners.add(testRunner);
        executorService.submit(testRunner);
    }

    private void removeRunner() {
        if (testRunners.size() > 0) {
            testRunners.get(0).stop();
            testRunners.remove(0);
        }
    }

    private void throwExceptionForListener(Exception e, String className) {
        log.error("Failed to instantiate provided listener class: " + className, e);
        throw new RPDException("Must have a public non-parameterized constructor for listener: " + className);
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException interruptedException) {
            log.error("Exception occurred while waiting for the test runner threads", interruptedException);
        }
    }
}
