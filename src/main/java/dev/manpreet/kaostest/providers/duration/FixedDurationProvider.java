package dev.manpreet.kaostest.providers.duration;

import dev.manpreet.kaostest.providers.DurationProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class FixedDurationProvider implements DurationProvider {

    private long remainingSecs = -1;
    private long prevTime;

    public FixedDurationProvider(int time, TimeUnit timeUnit) {
        if (timeUnit.equals(TimeUnit.MICROSECONDS) || timeUnit.equals(TimeUnit.MILLISECONDS) || timeUnit.equals(TimeUnit.NANOSECONDS)) {
            throw new IllegalArgumentException("Time unit must be in seconds or a higher unit");
        }
        setStartTime(time, timeUnit);
    }

    @Override
    public boolean stopTests() {
        remainingSecs = remainingSecs - ((System.currentTimeMillis() - prevTime) / 1000);
        prevTime = System.currentTimeMillis();
        log.info("Previous time: " + prevTime);
        log.info("Remaining seconds: " + remainingSecs);
        return remainingSecs <= 0;
    }

    public void setStartTime(int time, TimeUnit timeUnit) {
        if (remainingSecs < 0) {
            log.info("Setting start time");
            switch (timeUnit) {
                case SECONDS:
                    remainingSecs = time;
                    break;
                case MINUTES:
                    remainingSecs = time * 60L;
                    break;
                case HOURS:
                    remainingSecs = time * 60L * 60L;
                    break;
                default:
                    remainingSecs = time * 24L * 60L * 60L;
            }
            prevTime = System.currentTimeMillis();
            log.info("Previous time: " + prevTime);
            log.info("Remaining seconds: " + remainingSecs);
        }
    }
}
