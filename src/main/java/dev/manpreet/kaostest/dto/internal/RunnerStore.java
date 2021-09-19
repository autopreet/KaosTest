package dev.manpreet.kaostest.dto.internal;

import dev.manpreet.kaostest.KaosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Maintains test statistics of the complete run and extends BaseStore to maintain these statistics at the top level.
 * This is incremented by our built-in test listener and therefore is only available as a Singleton.
 */
@Slf4j
public class RunnerStore extends BaseStore {

    private Map<String, TestStore> testStoreMap;
    private final int storeSize;
    private static RunnerStore runnerStore;

    /**
     * Get an instance of the runner store. In case this is already initialized, the parameter won't have any effect.
     * @param tests - the list of test classes that are going to be run
     * @return RunnerStore
     */
    public static RunnerStore getRunnerStore(List<String> tests) {
        if (runnerStore == null) {
            runnerStore = new RunnerStore(tests);
        }
        return runnerStore;
    }

    /**
     * Get an instance of the runner store when it is already initialized.
     * @return
     */
    public static RunnerStore getRunnerStore() {
        return runnerStore;
    }

    private RunnerStore(List<String> tests) {
        testStoreMap = new HashMap<>();
        initTestStore(tests);
        storeSize = testStoreMap.size();
    }

    /**
     * Initialize the test store map with the list of test classes.
     * @param tests - the list of test classes that are going to be run
     */
    private void initTestStore(List<String> tests) {
        if (tests == null || tests.isEmpty()) {
            throw new KaosException("Tests list cannot be empty");
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
            throw new KaosException("Invalid test name (" + testName + ") not found in store: " + StringUtils.join(testStoreMap.keySet()));
        }
    }

    public Map<String, TestStore> getTestStoreMap() {
        return testStoreMap;
    }
}
