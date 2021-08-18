package dev.manpreet.rpdtest.providers.threadcount;

import dev.manpreet.rpdtest.providers.ThreadCountProvider;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadCountInRangeProvider implements ThreadCountProvider {

    private final int pollFrequencySeconds;
    private final int minCount;
    private final int maxCount;

    public ThreadCountInRangeProvider(int pollFrequencySeconds, int minCount, int maxCount) {
        this.pollFrequencySeconds = pollFrequencySeconds;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public int getThreadCount() {
        return ThreadLocalRandom.current().nextInt(minCount, maxCount+1);
    }

    @Override
    public int getPollFrequencySeconds() {
        return pollFrequencySeconds;
    }
}
