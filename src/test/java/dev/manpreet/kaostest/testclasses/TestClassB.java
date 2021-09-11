package dev.manpreet.kaostest.testclasses;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class TestClassB {

    @Test
    public void runEmptyTest1() {
        TestClassA.sleep();
        log.info("Finished test 1");
    }

    @Test
    public void runEmptyTest2() {
        TestClassA.sleep();
        log.info("Finished test 2");
    }

    @Test
    public void runEmptyTest3() {
        TestClassA.sleep();
        log.info("Finished test 3");
    }
}
