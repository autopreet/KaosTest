package dev.manpreet.rpdtest.testclasses.more;

import dev.manpreet.rpdtest.testclasses.TestClassA;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class TestClassC {

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
}
