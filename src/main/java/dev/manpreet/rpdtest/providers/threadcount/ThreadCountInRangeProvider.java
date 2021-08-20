package dev.manpreet.rpdtest.providers.threadcount;

import dev.manpreet.rpdtest.providers.PollingProvider;
import dev.manpreet.rpdtest.providers.ThreadCountProvider;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadCountInRangeProvider extends PollingProvider implements ThreadCountProvider {

    private final int minCount;
    private final int maxCount;

    public ThreadCountInRangeProvider(int pollFrequencySeconds, int minCount, int maxCount) {
        super(pollFrequencySeconds);
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public int getThreadCount() {
        return ThreadLocalRandom.current().nextInt(minCount, maxCount+1);
    }
}
