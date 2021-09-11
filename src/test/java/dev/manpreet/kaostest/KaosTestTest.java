package dev.manpreet.kaostest;

import dev.manpreet.kaostest.dto.internal.RunnerStore;
import dev.manpreet.kaostest.providers.DurationProvider;
import dev.manpreet.kaostest.providers.ThreadCountProvider;
import dev.manpreet.kaostest.providers.duration.FixedDurationProvider;
import dev.manpreet.kaostest.providers.threadcount.FixedThreadCountProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class KaosTestTest {

    private final String RESOURCES = Paths.get(System.getProperty("user.dir"), "src", "test", "resources").toString();

    @Test
    public void testKaosFixedThreads() {
        String inputXML = Paths.get(RESOURCES, "testsuite.xml").toString();
        ThreadCountProvider threadCountProvider = new FixedThreadCountProvider(5);
        DurationProvider durationProvider = new FixedDurationProvider(2, TimeUnit.MINUTES);

        Runner runner = new Runner();
        RunnerStore runnerStore = runner.runTests(inputXML, threadCountProvider, durationProvider);
        Assert.assertNotNull(runnerStore, "Expected runner store");
        Assert.assertTrue(runnerStore.getTestStoreMap()!=null && !runnerStore.getTestStoreMap().isEmpty(), "Expected test store map");
        Assert.assertEquals(runnerStore.getTestStoreMap().size(), 3, "Expected 3 test classes in test store");
    }
}
