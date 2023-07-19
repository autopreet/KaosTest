package dev.manpreet.kaostest.providers.testorder;

import dev.manpreet.kaostest.providers.TestOrderProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class TestRandomTestOrderProvider {

    private final List<String> inputTestClasses = new ArrayList<>(Arrays.asList("dev.manpreet.demotests.TestClassA",
            "dev.manpreet.demotests.TestClassB", "dev.manpreet.demotests.moretests.TestClassC"));
    private final Set<Class> expectedTestClasses = new HashSet<>(Arrays.asList(
            Class.forName("dev.manpreet.demotests.TestClassA"),
            Class.forName("dev.manpreet.demotests.TestClassB"),
            Class.forName("dev.manpreet.demotests.moretests.TestClassC")
    ));

    public TestRandomTestOrderProvider() throws ClassNotFoundException {}

    @Test
    public void testRandomOrderProvider() {
        TestOrderProvider testOrderProvider = new RandomTestOrderProvider(inputTestClasses);
        for (int i=0; i<inputTestClasses.size() * 2; i++) {
            Assert.assertTrue(expectedTestClasses.contains(testOrderProvider.getNextTest()));
        }
    }
}
