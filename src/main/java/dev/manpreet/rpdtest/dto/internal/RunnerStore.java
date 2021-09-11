package dev.manpreet.rpdtest.dto.internal;

import dev.manpreet.rpdtest.RPDException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class RunnerStore extends BaseStore {

    private Map<String, TestStore> testStoreMap;
    private final int storeSize;
    private static RunnerStore runnerStore;

    public static RunnerStore getRunnerStore(List<String> tests) {
        if (runnerStore == null) {
            runnerStore = new RunnerStore(tests);
        }
        return runnerStore;
    }

    public static RunnerStore getRunnerStore() {
        return runnerStore;
    }

    private RunnerStore(List<String> tests) {
        testStoreMap = new HashMap<>();
        initTestStore(tests);
        storeSize = testStoreMap.size();
    }

    private void initTestStore(List<String> tests) {
        if (tests == null || tests.isEmpty()) {
            throw new RPDException("Tests list cannot be empty");
        }
        tests.forEach(eachTest -> testStoreMap.put(eachTest, new TestStore(eachTest)));
    }

    public Class getRandomTest() throws ClassNotFoundException {
        int randomIndex = ThreadLocalRandom.current().nextInt(storeSize);
        String className = new ArrayList<>(testStoreMap.keySet()).get(randomIndex);
        log.info("Returning random test class (" + className + ") at index (" + randomIndex + ")");
        return Class.forName(className);
    }

    public void addPassedTest(String testName, long addedExecTime) {
        validateTest(testName);
        testStoreMap.get(testName).incrementPassCount(addedExecTime);
        incrementPassCount(addedExecTime);
    }

    public void addFailedTest(String testName, long addedExecTime) {
        validateTest(testName);
        testStoreMap.get(testName).incrementFailCount(addedExecTime);
        incrementFailCount(addedExecTime);
    }

    public void addSkippedTest(String testName, long addedExecTime) {
        validateTest(testName);
        testStoreMap.get(testName).incrementSkipCount(addedExecTime);
        incrementSkipCount(addedExecTime);
    }

    private void validateTest(String testName) {
        if (!testStoreMap.containsKey(testName)) {
            throw new RPDException("Invalid test name (" + testName + ") not found in store: " + StringUtils.join(testStoreMap.keySet()));
        }
    }

    public Map<String, TestStore> getTestStoreMap() {
        return testStoreMap;
    }
}
