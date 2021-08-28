package dev.manpreet.rpdtest.dto.internal;

public class BaseStore {

    private long execTimeMillis = 0;
    private int totalCount = 0;
    private int passCount = 0;
    private int failCount = 0;
    private int skipCount = 0;

    public long getExecTimeMillis() {
        return execTimeMillis;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPassCount() {
        return passCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public int getSkipCount() {
        return skipCount;
    }

    private void incrementTotalCount(long addedExecTime) {
        totalCount++;
        execTimeMillis = execTimeMillis + addedExecTime;
    }

    public void incrementPassCount(long addedExecTime) {
        passCount++;
        incrementTotalCount(addedExecTime);
    }

    public void incrementFailCount(long addedExecTime) {
        failCount++;
        incrementTotalCount(addedExecTime);
    }

    public void incrementSkipCount(long addedExecTime) {
        skipCount++;
        incrementTotalCount(addedExecTime);
    }
}
