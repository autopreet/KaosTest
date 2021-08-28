package dev.manpreet.rpdtest.dto.internal;

public class TestStore extends BaseStore {

    private final String testName;

    public TestStore(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }
}
