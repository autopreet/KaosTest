//package dev.manpreet.kaostest;
//
//import dev.manpreet.kaostest.dto.internal.RunnerStore;
//import dev.manpreet.kaostest.dto.internal.TestStore;
//import dev.manpreet.kaostest.providers.DurationProvider;
//import dev.manpreet.kaostest.providers.ThreadCountProvider;
//import dev.manpreet.kaostest.providers.duration.FixedDurationProvider;
//import dev.manpreet.kaostest.providers.threadcount.FixedThreadCountProvider;
//import dev.manpreet.kaostest.providers.threadcount.ThreadCountInRangeProvider;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import java.nio.file.Paths;
//import java.util.concurrent.TimeUnit;
//
//public class KaosTestTest {
//
//    private final String RESOURCES = Paths.get(System.getProperty("user.dir"), "src", "test", "resources").toString();
//    private final String TESTCLASS_A = "dev.manpreet.kaostest.testclasses.TestClassA";
//    private final String TESTCLASS_B = "dev.manpreet.kaostest.testclasses.TestClassB";
//    private final String TESTCLASS_C = "dev.manpreet.kaostest.testclasses.more.TestClassC";
//
//    @Test
//    public void testKaosFixedThreads() {
//        String inputXML = Paths.get(RESOURCES, "testsuite.xml").toString();
//        ThreadCountProvider threadCountProvider = new FixedThreadCountProvider(5);
//        DurationProvider durationProvider = new FixedDurationProvider(2, TimeUnit.MINUTES);
//
//        Runner runner = new Runner();
//        RunnerStore runnerStore = runner.runTests(inputXML, threadCountProvider, durationProvider);
//        Assert.assertNotNull(runnerStore, "Expected runner store");
//        Assert.assertTrue(runnerStore.getTestStoreMap()!=null && !runnerStore.getTestStoreMap().isEmpty(), "Expected test store map");
//        Assert.assertEquals(runnerStore.getTestStoreMap().size(), 3, "Expected 3 test classes in test store");
//        validateStore(runnerStore);
//    }
//
//    @Test
//    public void testKaosRangeThreads() {
//        String inputXML = Paths.get(RESOURCES, "testsuite.xml").toString();
//        ThreadCountProvider threadCountProvider = new ThreadCountInRangeProvider(30, 1, 3);
//        DurationProvider durationProvider = new FixedDurationProvider(3, TimeUnit.MINUTES);
//
//        Runner runner = new Runner();
//        RunnerStore runnerStore = runner.runTests(inputXML, threadCountProvider, durationProvider);
//        Assert.assertNotNull(runnerStore, "Expected runner store");
//        Assert.assertTrue(runnerStore.getTestStoreMap()!=null && !runnerStore.getTestStoreMap().isEmpty(), "Expected test store map");
//        Assert.assertEquals(runnerStore.getTestStoreMap().size(), 3, "Expected 3 test classes in test store");
//        validateStore(runnerStore);
//    }
//
//    private void validateStore(RunnerStore runnerStore) {
//        Assert.assertTrue(runnerStore.getTotalCount() > 0, "Expected some tests to run");
//        Assert.assertTrue(runnerStore.getPassCount() > 0, "Expected some tests to pass");
//        Assert.assertTrue(runnerStore.getSkipCount() > 0, "Expected some tests to skip");
//
//        TestStore testStore = runnerStore.getTestStoreMap().get(TESTCLASS_A);
//        Assert.assertTrue(testStore.getTotalCount() > 0, "Expected some tests to run from TestClassA");
//        Assert.assertTrue(testStore.getPassCount() > 0, "Expected some tests to pass from TestClassA");
//        Assert.assertEquals(testStore.getFailCount(), 0, "Expected NO tests to fail from TestClassA");
//        Assert.assertEquals(testStore.getSkipCount(), 0, "Expected NO tests to skip from TestClassA");
//        testStore = runnerStore.getTestStoreMap().get(TESTCLASS_B);
//        Assert.assertTrue(testStore.getTotalCount() > 0, "Expected some tests to run from TestClassB");
//        Assert.assertTrue(testStore.getPassCount() > 0, "Expected some tests to pass from TestClassB");
//        Assert.assertTrue(testStore.getFailCount() > 0, "Expected some tests to fail from TestClassB");
//        Assert.assertTrue(testStore.getSkipCount() > 0, "Expected some tests to skip from TestClassB");
//        testStore = runnerStore.getTestStoreMap().get(TESTCLASS_C);
//        Assert.assertTrue(testStore.getTotalCount() > 0, "Expected some tests to run from TestClassC");
//        Assert.assertTrue(testStore.getPassCount() > 0, "Expected some tests to pass from TestClassC");
//        Assert.assertEquals(testStore.getFailCount(), 0, "Expected NO tests to fail from TestClassC");
//        Assert.assertEquals(testStore.getSkipCount(), 0, "Expected NO tests to skip from TestClassC");
//    }
//}
