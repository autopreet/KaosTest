package dev.manpreet.kaostest.dto.internal;

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

    @Override
    public String toString() {
        float passPct, failPct, skipPct;
        passPct = (passCount * 100.0f) / totalCount;
        failPct = (failCount * 100.0f) / totalCount;
        skipPct = (skipCount * 100.0f) / totalCount;
        StringBuilder result = new StringBuilder();
        result.append("Total runs: " + totalCount + "\n");
        result.append("Passed: " + passCount + " (" + String.format("%.2f", passPct) + "%)");
        result.append("\t\nFailed: " + failCount + " (" + String.format("%.2f", failPct) + "%)");
        result.append("\t\nSkipped: " + skipCount + " (" + String.format("%.2f", skipPct) + "%)");
        result.append("\t\nTotal execution time: " + String.format("%.2f", (execTimeMillis)/1000D) + " seconds");
        result.append("\t\nAverage execution time: " + String.format("%.2f", (execTimeMillis/totalCount)/1000D) + " seconds");
        result.append("\t\nAverage passed execution time: " + String.format("%.2f", (execTimeMillis/passCount)/1000D) + " seconds");
        return result.toString();
    }
}
