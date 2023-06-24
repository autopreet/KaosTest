package dev.manpreet.kaostest.stores;

import dev.manpreet.kaostest.stores.base.BaseStore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestMethodData extends BaseStore {

    private List<TestInstanceData> instancesData;
    private TestClassData subscriber;

    public TestMethodData(String name) {
        super(name);
        instancesData = new ArrayList<>();
    }

    public void addNewInstance(TestInstanceData testInstanceData) {
        instancesData.add(testInstanceData);
        double runtimeMillis = TimeUnit.NANOSECONDS
                .toMillis(testInstanceData.getEndTimeMillis() - testInstanceData.getStartTimeMillis());
        addNewInstance(runtimeMillis, testInstanceData.getStatus());
        subscriber.addNewInstance(runtimeMillis, testInstanceData.getStatus());
        super.addNewInstance(runtimeMillis, testInstanceData.getStatus());
    }

    public void setSubscriber(TestClassData subscriber) {
        this.subscriber = subscriber;
    }
}
