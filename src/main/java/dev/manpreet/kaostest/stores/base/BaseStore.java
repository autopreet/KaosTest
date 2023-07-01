package dev.manpreet.kaostest.stores.base;

import lombok.Getter;
import lombok.Synchronized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class BaseStore {

    private final String name;
    private int totalCount = 0;
    private int passCount = 0;
    private int failCount = 0;
    private int skipCount = 0;
    private double avgRuntimeMillis = 0;
    private double minRuntimeMillis = 0;
    private double maxRuntimeMillis = 0;
    private double totalRuntimeMillis = 0;
    private double medianRuntimeMillis = 0;
    private final List<Double> runtimes;

    public BaseStore(String name) {
        this.name = name;
        runtimes = new ArrayList<>();
    }

    @Synchronized
    public void addNewInstance(Double runtime, Status status) {
        totalCount++;
        runtimes.add(runtime);
        switch (status) {
            case PASS -> passCount++;
            case FAIL -> failCount++;
            case SKIP -> skipCount++;
        }
        avgRuntimeMillis = runtimes.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        minRuntimeMillis = runtimes.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        maxRuntimeMillis = runtimes.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        totalRuntimeMillis = runtimes.stream().mapToDouble(Double::doubleValue).sum();
        List<Double> sortedRuntimes = new ArrayList<>(runtimes);
        Collections.sort(sortedRuntimes);
        double midVal = sortedRuntimes.get(totalCount/2);
        medianRuntimeMillis = totalCount % 2 == 0 ? (midVal + sortedRuntimes.get(totalCount/2 -1)) / 2 : midVal;
    }

}
