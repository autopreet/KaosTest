package dev.manpreet.demotests;

import dev.manpreet.demotests.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class TestClassA {

    @Test
    public void runPassingTest1() {
        int code = TestUtils.sendHTTPGETRequest("https://reqbin.com", "text/html");
        Assert.assertEquals(code, 200, "Expected HTTP 200");
        log.info("Finished test TestClassA.runPassingTest1");
    }

    @Test(dependsOnMethods = "runPassingTest1")
    public void runPassingTest2() {
        int code = TestUtils.sendHTTPGETRequest("https://reqbin.com/echo", "text/html");
        Assert.assertEquals(code, 200, "Expected HTTP 200");
        log.info("Finished test TestClassA.runPassingTest2");
    }
}