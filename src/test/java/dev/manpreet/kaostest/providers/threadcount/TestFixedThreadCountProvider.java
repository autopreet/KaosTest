package dev.manpreet.kaostest.providers.threadcount;

import dev.manpreet.kaostest.providers.ThreadCountProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestFixedThreadCountProvider {

    private ThreadCountProvider threadCountProvider;

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFixedThreadCountInvalidValue() {
        threadCountProvider = new FixedThreadCountProvider(-1);
    }

    @Test
    public void testFixedThreadCountValue() {
        int threadCounts = 10;
        threadCountProvider = new FixedThreadCountProvider(threadCounts);
        Assert.assertEquals(threadCountProvider.getThreadCount(), threadCounts);
    }
}
