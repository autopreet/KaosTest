package dev.manpreet.rpdtest.util;

import dev.manpreet.rpdtest.runner.listeners.StoreIncrementListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.ITestNGListener;
import org.testng.TestNG;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
public class TestNGUtils {

    public static TestNG getTestNGInstance(List<Class<?>> inputListeners) {
        TestNG testNG = new TestNG();
        //Configure listeners
        for (Class<?> listener : inputListeners) {
            if (ITestNGListener.class.isAssignableFrom(listener)) {
                try {
                    testNG.addListener((ITestNGListener) listener.getConstructor().newInstance());
                } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    log.error("Failed to instantiate provided listener class (" + listener.getName() + ") which will be ignored. Ensure that the class " +
                            "has a public non-parameterized constructor", e);
                }
            } else {
                log.error("Provided listener class (" + listener.getName() + ") is not a valid subtype of ITestNGListener");
            }
        }
        testNG.setDefaultSuiteName(RandomStringUtils.randomAlphabetic(12));
        testNG.addListener(new StoreIncrementListener());
        testNG.alwaysRunListeners(true);
        testNG.setVerbose(-1);
        return testNG;
    }
}
