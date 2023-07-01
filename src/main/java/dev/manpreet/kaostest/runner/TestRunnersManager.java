package dev.manpreet.kaostest.runner;

import dev.manpreet.kaostest.providers.DurationProvider;
import dev.manpreet.kaostest.providers.PollingProvider;
import dev.manpreet.kaostest.providers.ThreadCountProvider;
import dev.manpreet.kaostest.util.SuiteXMLUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Main test executor
 */
@Slf4j
public class TestRunnersManager {

    private final ExecutorService executorService;
    private final ThreadCountProvider threadCountProvider;
    private final DurationProvider durationProvider;
    private final List<TestRunner> testRunners;
    //private final List<Class<?>> inputListeners;
    private final boolean isPollThreadCount;
    private final int waitFreqSecs;

    /**
     * Init the test executor
     * @param threadCountProvider - Instance of thread count provider
     * @param durationProvider - Instance of duration provider
     * @param inputListeners - List of listeners defined in suite
     */
    public TestRunnersManager(ThreadCountProvider threadCountProvider, DurationProvider durationProvider, List<String> inputListeners) {
        this.threadCountProvider = threadCountProvider;
        this.durationProvider = durationProvider;
        //If the thread count provider is a polling provider, we want to use a cached thread pool and set main thread to wake up
        //every poll # of seconds as defined in the provider.
        if (threadCountProvider instanceof PollingProvider) {
            isPollThreadCount = true;
            this.executorService = Executors.newCachedThreadPool();
            waitFreqSecs = ((PollingProvider) threadCountProvider).getPollSeconds();
        } else {
            isPollThreadCount = false;
            this.executorService = Executors.newFixedThreadPool(threadCountProvider.getThreadCount());
            waitFreqSecs = 60;
        }
        //this.inputListeners = SuiteXMLUtils.getClassFromName(inputListeners);
        testRunners = new ArrayList<>();
    }

    public void runTests() {
       // IntStream.range(0, threadCountProvider.getThreadCount()).forEach(x -> scheduleRunner());

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
            //IntStream.range(0, difference).forEach(x -> scheduleRunner());
        }
    }

    private boolean areTestsRunning() {
        return testRunners.stream().anyMatch(runner -> !runner.isFinished());
    }

//    private void scheduleRunner() {
//        TestRunner testRunner = new TestRunner(inputListeners);
//        testRunners.add(testRunner);
//        executorService.submit(testRunner);
//    }

    private void removeRunner() {
        if (testRunners.size() > 0) {
            testRunners.get(0).stop();
            testRunners.remove(0);
        }
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException interruptedException) {
            log.error("Exception occurred while waiting for the test runner threads", interruptedException);
        }
    }
}
