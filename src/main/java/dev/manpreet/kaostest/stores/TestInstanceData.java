package dev.manpreet.kaostest.stores;

import dev.manpreet.kaostest.stores.base.Status;
import lombok.Getter;

@Getter
public class TestInstanceData {

    private final String threadName;
    private final Status status;
    private final long startTimeMillis;
    private final long endTimeMillis;

    public TestInstanceData(Status status, long startTimeMillis, long endTimeMillis) {
        this.threadName = Thread.currentThread().getName();
        this.status = status;
        this.startTimeMillis = startTimeMillis;
        this.endTimeMillis = endTimeMillis;
    }
}
