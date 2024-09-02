package dev.manpreet.kaostest.stores;

import dev.manpreet.kaostest.stores.base.BaseStore;
import dev.manpreet.kaostest.stores.base.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TestClassData extends BaseStore {

    private final Map<String, TestMethodData> testMethodsData;
    @Setter
    private TestPackageData subscriber;

    public TestClassData(String name) {
        super(name);
        testMethodsData = new HashMap<>();
    }

    public void addTestMethod(TestMethodData testMethodData) {
        if (!testMethodsData.containsKey(testMethodData.getName())) {
            testMethodsData.put(testMethodData.getName(), testMethodData);
            testMethodData.setSubscriber(this);
        }
    }

    @Override
    public void addNewInstance(Double runtime, Status status) {
        subscriber.addNewInstance(runtime, status);
        super.addNewInstance(runtime, status);
    }
}
