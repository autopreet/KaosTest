package dev.manpreet.kaostest.providers.threadcount;

import dev.manpreet.kaostest.providers.ThreadCountProvider;

public class FixedThreadCountProvider implements ThreadCountProvider {

    private final int threadCount;

    public FixedThreadCountProvider(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public int getThreadCount() {
        return threadCount;
    }
}
