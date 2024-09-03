package dev.manpreet.kaostest.stores;

import dev.manpreet.kaostest.stores.base.BaseStore;
import dev.manpreet.kaostest.stores.base.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TestPackageData extends BaseStore {

    private final Map<String, TestClassData> testClassesData;
    @Setter
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

    @Override
    public void addNewInstance(Double runtime, Status status) {
        subscriber.addNewInstance(runtime, status);
        super.addNewInstance(runtime, status);
    }
}
