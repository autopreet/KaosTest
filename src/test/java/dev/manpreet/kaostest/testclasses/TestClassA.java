package dev.manpreet.kaostest.testclasses;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestClassA {

    public static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

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
