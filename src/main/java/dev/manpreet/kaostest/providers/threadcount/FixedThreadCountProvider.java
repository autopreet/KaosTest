package dev.manpreet.kaostest.providers.threadcount;

import dev.manpreet.kaostest.providers.ThreadCountProvider;

/**
 * Implementation for the thread count provider to set the number of test runner threads. This basic implementation
 * takes a fixed number and provides the same to the test runner. The runner will continue the run the tests in the
 * provided number of threads until execution stops.
 */
public class FixedThreadCountProvider implements ThreadCountProvider {

    private final int threadCount;

    /**
     * Init the thread count provider
     * @param threadCount - number of threads in which to run the tests in parallel
     */
    public FixedThreadCountProvider(int threadCount) {
        if (threadCount < 0) {
            throw new IllegalArgumentException("Thread count cannot be less than 0");
        }
        this.threadCount = threadCount;
    }

    /**
     * Called by the test executor to determine the number of threads.
     * @return thread count
     */
    @Override
    public int getThreadCount() {
        return threadCount;
    }
}
