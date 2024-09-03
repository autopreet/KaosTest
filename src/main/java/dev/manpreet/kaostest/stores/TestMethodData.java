package dev.manpreet.kaostest.stores;

import dev.manpreet.kaostest.stores.base.BaseStore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
public class TestMethodData extends BaseStore {

    private final List<TestInstanceData> instancesData;
    @Setter
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

}
