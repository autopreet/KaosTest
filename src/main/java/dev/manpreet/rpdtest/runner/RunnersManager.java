package dev.manpreet.rpdtest.runner;

import dev.manpreet.rpdtest.providers.DurationProvider;
import dev.manpreet.rpdtest.providers.ThreadCountProvider;

public class RunnersManager {

    private final ThreadCountProvider threadCountProvider;
    private final DurationProvider durationProvider;

    public RunnersManager(ThreadCountProvider threadCountProvider, DurationProvider durationProvider) {
        this.threadCountProvider = threadCountProvider;
        this.durationProvider = durationProvider;
    }
}
