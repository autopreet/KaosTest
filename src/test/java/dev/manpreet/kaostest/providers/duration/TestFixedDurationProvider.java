package dev.manpreet.kaostest.providers.duration;

import dev.manpreet.kaostest.providers.DurationProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestFixedDurationProvider {

    private DurationProvider durationProvider;

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFixedDurationProviderWithInvalidMicroSecTimeUnits() {
        durationProvider = new FixedDurationProvider(1, TimeUnit.MICROSECONDS);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFixedDurationProviderWithInvalidMilliSecTimeUnits() {
        durationProvider = new FixedDurationProvider(1, TimeUnit.MILLISECONDS);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFixedDurationProviderWithInvalidNanoSecTimeUnits() {
        durationProvider = new FixedDurationProvider(1, TimeUnit.NANOSECONDS);
    }

    @Test
    public void testFixedDurationProviderWithSecs() throws InterruptedException {
        durationProvider = new FixedDurationProvider(10, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(5);
        Assert.assertFalse(durationProvider.stopTests());
        TimeUnit.SECONDS.sleep(5);
        Assert.assertTrue(durationProvider.stopTests());
    }
}
