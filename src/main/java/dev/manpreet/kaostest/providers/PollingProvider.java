package dev.manpreet.kaostest.providers;

/**
 * A specialization class for providers which makes the test executor get the latest provided value every pollSeconds number of seconds.
 * Consider ThreadCountInRangeProvider which would be called by the test executor every pollSeconds and get a new thread count.
 * The test executor would adjust the start/stop the test threads as needed to adjust to the newly provided thread count.
 */
public class PollingProvider {

    protected final int pollSeconds;

    public PollingProvider(int pollSeconds) {
        this.pollSeconds = pollSeconds;
    }

    public int getPollSeconds() {
        return pollSeconds;
    }
}
