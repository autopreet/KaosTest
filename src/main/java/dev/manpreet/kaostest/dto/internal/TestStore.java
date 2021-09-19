package dev.manpreet.kaostest.dto.internal;

/**
 * Maintains test statistics for a single test class. Extends BaseStore to do this.
 */
public class TestStore extends BaseStore {

    private final String testName;

    public TestStore(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }
}
