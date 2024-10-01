package dev.manpreet.kaostest.providers.testorder;

import dev.manpreet.kaostest.providers.TestOrderProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSortedTestOrderProvider {

    private final List<String> inputTestClasses = new ArrayList<>(Arrays.asList("dev.manpreet.demotests.TestClassA",
            "dev.manpreet.demotests.TestClassB", "dev.manpreet.demotests.moretests.TestClassC"));
    private final List<Class> expectedTestClasses = new ArrayList<>(Arrays.asList(
            Class.forName("dev.manpreet.demotests.TestClassA"),
            Class.forName("dev.manpreet.demotests.TestClassB"),
            Class.forName("dev.manpreet.demotests.moretests.TestClassC")
    ));

    public TestSortedTestOrderProvider() throws ClassNotFoundException {}

    @Test
    public void testSortedOrderProvider() {
        TestOrderProvider testOrderProvider = new SortedTestOrderProvider(inputTestClasses);
        int index = 0;
        for (int i=0; i<2; i++) {
            Assert.assertEquals(testOrderProvider.getNextTest(), expectedTestClasses.get(index));
            index++;
            if (index == inputTestClasses.size()) {
                index = 0;
            }
        }
    }
}
