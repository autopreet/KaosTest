package dev.manpreet.kaostest.providers;

/**
 * Interface that all duration providers must implement. Used by the test executor to determine when to stop the execution.
 */
public interface DurationProvider {

    boolean stopTests();
}
