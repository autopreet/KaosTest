package dev.manpreet.rpdtest.runner;

import dev.manpreet.rpdtest.dto.internal.RunnerStore;
import lombok.SneakyThrows;
import org.testng.TestNG;

public class TestRunner implements Runnable {

    private final RunnerStore runnerStore;
    private final TestNG testNG;
    private boolean isRun;
    private boolean isFinished;

    public TestRunner(TestNG testNG) {
        this.runnerStore = RunnerStore.getRunnerStore();
        this.testNG = testNG;
        isRun = true;
        isFinished = false;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (isRun) {
            testNG.setTestClasses(new Class[]{runnerStore.getRandomTest()});
            testNG.run();
        }
        isFinished = true;
    }

    public void stop() {
        isRun = false;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
