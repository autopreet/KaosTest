package dev.manpreet.kaostest.stores;

import dev.manpreet.kaostest.stores.base.BaseStore;
import dev.manpreet.kaostest.stores.base.Status;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class TestPackageData extends BaseStore {

    @Getter
    private Map<String, TestClassData> testClassesData;
    private Store subscriber;

    public TestPackageData(String name) {
        super(name);
        testClassesData = new HashMap<>();
    }

    public void addTestClass(TestClassData testClassData) {
        if (!testClassesData.containsKey(testClassData.getName())) {
            testClassesData.put(testClassData.getName(), testClassData);
            testClassData.setSubscriber(this);
        }
    }

    public void setSubscriber(Store subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void addNewInstance(Double runtime, Status status) {
        subscriber.addNewInstance(runtime, status);
        super.addNewInstance(runtime, status);
    }
}
