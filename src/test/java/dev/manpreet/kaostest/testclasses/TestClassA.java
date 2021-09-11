package dev.manpreet.kaostest.testclasses;

import lombok.extern.slf4j.Slf4j;
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
    public void runEmptyTest1() {
        sleep();
        log.info("Finished test 1");
    }

    @Test
    public void runEmptyTest2() {
        sleep();
        log.info("Finished test 2");
    }
}
