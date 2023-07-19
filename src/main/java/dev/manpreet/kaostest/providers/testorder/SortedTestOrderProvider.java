package dev.manpreet.kaostest.providers.testorder;

import dev.manpreet.kaostest.KaosException;
import dev.manpreet.kaostest.providers.TestOrderProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SortedTestOrderProvider implements TestOrderProvider {

    private final List<Class> testClasses;
    private int nextIndex;

    public SortedTestOrderProvider(List<String> testClasses) {
        List<String> inputClasses = new ArrayList<>(testClasses);
        Collections.sort(inputClasses);
        this.testClasses = inputClasses.stream().map(eachClass -> {
            try {
                return Class.forName(eachClass);
            } catch (ClassNotFoundException e) {
                throw new KaosException("Invalid class " + eachClass + ". Unable to initialize.");
            }
        }).distinct().collect(Collectors.toList());
        nextIndex = -1;
    }

    @Override
    public Class getNextTest() {
        nextIndex = (nextIndex + 1 == testClasses.size()) ? 0 : nextIndex + 1;
        return testClasses.get(nextIndex);
    }
}
