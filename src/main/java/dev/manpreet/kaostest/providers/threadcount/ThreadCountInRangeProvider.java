package dev.manpreet.kaostest.providers.threadcount;

import dev.manpreet.kaostest.providers.PollingProvider;
import dev.manpreet.kaostest.providers.ThreadCountProvider;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation for the thread count provider to set the number of test runner threads. This basic implementation takes
 * a minCount and maxCount number and provides a random number between/equal to those two to set as the thread count.
 */
public class ThreadCountInRangeProvider extends PollingProvider implements ThreadCountProvider {

    private final int minCount;
    private final int maxCount;

    /**
     * Init the polling thread count provider
     * @param pollFrequencySeconds - Number of seconds after which test runner will ask the provider for the new thread count
     * @param minCount - Minimum number of threads (inclusive)
     * @param maxCount - Maximum number of threads (inclusive)
     */
    public ThreadCountInRangeProvider(int pollFrequencySeconds, int minCount, int maxCount) {
        super(pollFrequencySeconds);
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    /**
     * Called by the test executor to determine the number of threads. Since this is a polling provider, this method will be called
     * every pollFrequencySeconds.
     * @return thread count
     */
    @Override
    public int getThreadCount() {
        return ThreadLocalRandom.current().nextInt(minCount, maxCount+1);
    }
}
