package dev.manpreet.rpdtest.providers.threadcount;

import dev.manpreet.rpdtest.providers.ThreadCountProvider;

public class FixedThreadCountProvider implements ThreadCountProvider {

    private final int pollFrequencySeconds;
    private final int threadCount;

    public FixedThreadCountProvider(int pollFrequencySeconds, int threadCount) {
        this.pollFrequencySeconds = pollFrequencySeconds;
        this.threadCount = threadCount;
    }

    @Override
    public int getThreadCount() {
        return threadCount;
    }

    @Override
    public int getPollFrequencySeconds() {
        return pollFrequencySeconds;
    }
}
