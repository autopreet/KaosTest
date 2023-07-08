package dev.manpreet.kaostest.providers.threadcount;

import dev.manpreet.kaostest.providers.ThreadCountProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestThreadCountInRangeProvider {

    private ThreadCountProvider threadCountProvider;

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testThreadCountInRangeInvalidPollSeconds() {
        threadCountProvider = new ThreadCountInRangeProvider(3, 5, 10);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testThreadCountInRangeInvalidMinThreads() {
        threadCountProvider = new ThreadCountInRangeProvider(5, -1, 10);
    }

    @Test
    public void testThreadCountInRangeProvider() {
        int minCount = 5;
        int maxCount = 10;
        threadCountProvider = new ThreadCountInRangeProvider(5, minCount, maxCount);
        int actualThreadCount = threadCountProvider.getThreadCount();
        Assert.assertTrue(actualThreadCount >= minCount && actualThreadCount < maxCount);
        actualThreadCount = threadCountProvider.getThreadCount();
        Assert.assertTrue(actualThreadCount >= minCount && actualThreadCount < maxCount);
    }

}
