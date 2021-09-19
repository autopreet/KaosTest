package dev.manpreet.kaostest.runner;

import dev.manpreet.kaostest.dto.internal.RunnerStore;
import dev.manpreet.kaostest.util.TestNGUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.TestNG;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test runner thread which picks one test class at a time and runs it using the TestNG API.
 */
@Slf4j
public class TestRunner implements Runnable {

    private final RunnerStore runnerStore;
    private final List<Class<?>> inputListeners;
    private boolean isRun;
    private boolean isFinished;

    /**
     * Init the test runner
     * @param inputListeners - List of listener classes to add to the test run
     */
    public TestRunner(List<Class<?>> inputListeners) {
        this.inputListeners = inputListeners;
        this.runnerStore = RunnerStore.getRunnerStore();
        isRun = true;
        isFinished = false;
    }

    @Override
    public void run() {
        TestNG testNG;
        try {
            while (isRun) {
                testNG = TestNGUtils.getTestNGInstance(inputListeners);
                Class<?>[] classes = new Class[]{runnerStore.getRandomTest()};
                log.info("Setting test classes: " + StringUtils.join(Arrays.stream(classes).map(Class::getName).collect(Collectors.toList())));
                testNG.setTestClasses(classes);
                testNG.run();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        isFinished = true;
    }

    public void stop() {
        log.info("Stop received");
        isRun = false;
    }

    public boolean isFinished() {
        return isFinished;
    }

}
