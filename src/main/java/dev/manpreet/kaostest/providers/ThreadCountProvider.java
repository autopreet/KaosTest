package dev.manpreet.kaostest.providers;

/**
 * Interface that all thread count providers must implement. Used by the test executor to determine the number of test
 * runner threads.
 */
public interface ThreadCountProvider {

    int getThreadCount();
}
