package dev.manpreet.kaostest.providers.duration;

import dev.manpreet.kaostest.providers.DurationProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Implementation for duration provider to set the end time for the execution. This basic implementation takes a fixed
 * time duration as input and once that time has passed, signals to the test executor to stop.
 */
@Slf4j
public class FixedDurationProvider implements DurationProvider {

    private long durationSecs, startTime;

    /**
     * Init the duration provider.
     * @param time - Value for the time duration
     * @param timeUnit - Unit for the time duration
     */
    public FixedDurationProvider(int time, TimeUnit timeUnit) {
        if (timeUnit.equals(TimeUnit.MICROSECONDS) || timeUnit.equals(TimeUnit.MILLISECONDS) ||
                timeUnit.equals(TimeUnit.NANOSECONDS)) {
            throw new IllegalArgumentException("Time unit must be in seconds or a higher unit");
        }
        setStartTime(time, timeUnit);
    }

    /**
     * Called by the test executor to determine whether to stop the tests.
     * @return boolean - stop tests?
     */
    @Override
    public boolean stopTests() {
        long secondsElapsed = (System.currentTimeMillis() - startTime) / 1000;
        long secondsRemaining = durationSecs - secondsElapsed;
        log.info("Seconds elapsed: " + secondsElapsed);
        log.info("Remaining seconds: " + secondsRemaining);
        return secondsRemaining <= 0;
    }

    private void setStartTime(int time, TimeUnit timeUnit) {
        log.info("Setting start time");
        switch (timeUnit) {
            case SECONDS -> durationSecs = time;
            case MINUTES -> durationSecs = time * 60L;
            case HOURS -> durationSecs = time * 60L * 60L;
            default -> durationSecs = time * 24L * 60L * 60L;
        }
        startTime = System.currentTimeMillis();
        log.info("Start epoch: " + startTime);
        log.info("Remaining seconds: " + durationSecs);
    }
}
