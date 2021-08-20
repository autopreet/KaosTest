package dev.manpreet.rpdtest.providers;

public class PollingProvider {

    protected final int pollSeconds;

    public PollingProvider(int pollSeconds) {
        this.pollSeconds = pollSeconds;
    }

    public int getPollSeconds() {
        return pollSeconds;
    }
}
