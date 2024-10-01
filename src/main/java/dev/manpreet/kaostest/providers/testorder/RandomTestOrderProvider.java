package dev.manpreet.kaostest.providers.testorder;

import dev.manpreet.kaostest.exception.KaosException;
import dev.manpreet.kaostest.providers.TestOrderProvider;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RandomTestOrderProvider implements TestOrderProvider {

    private final List<Class> testClasses;

    public RandomTestOrderProvider(List<String> testClasses) {
        List<String> inputClasses = new ArrayList<>(testClasses);
        Collections.sort(inputClasses);
        this.testClasses = inputClasses.stream().map(eachClass -> {
            try {
                return Class.forName(eachClass);
            } catch (ClassNotFoundException e) {
                throw new KaosException("Invalid class " + eachClass + ". Unable to initialize.");
            }
        }).distinct().collect(Collectors.toList());
    }

    @Override
    public Class getNextTest() {
        int randomIndex = ThreadLocalRandom.current().nextInt(testClasses.size());
        return testClasses.get(randomIndex);
    }
}
