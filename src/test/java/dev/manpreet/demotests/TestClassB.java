package dev.manpreet.demotests;

import dev.manpreet.demotests.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class TestClassB {

    @Test(groups = {"setup"})
    public void runPassingTest1() {
        int code = TestUtils.sendHTTPGETRequest("https://postman-echo.com/get?foo1=bar1&foo2=bar2", "application/json");
        Assert.assertEquals(code, 200, "Expected HTTP 200");
        log.info("Finished test TestClassB.runPassingTest1");
    }

    @Test(groups = {"setup"})
    public void runFailingTest2() {
        int code = TestUtils.sendHTTPGETRequest("https://api.spotify.com/v1/search?q=classical&type=track&market=US",
                "application/json");
        Assert.assertEquals(code, 200, "Intentional 401 here");
        log.info("Finished test TestClassB.runFailingTest2");
    }

    @Test(dependsOnGroups = {"setup"})
    public void runSkippingTest3() {
        log.info("WILL NEVER LOG - Finished test TestClassB.runSkippingTest3");
    }
}