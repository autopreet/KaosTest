package dev.manpreet.kaostest.providers;

/**
 * A specialization class for providers which makes the test executor get the latest provided value every pollSeconds.
 * For example, see {@link dev.manpreet.kaostest.providers.threadcount.ThreadCountInRangeProvider} which would be
 * called by the test executor every pollSeconds and will return a new thread count. The test executor would start/stop
 * the test threads as needed to adjust to the newly provided thread count.
 */
public class PollingProvider {

    protected final int pollSeconds;

    protected PollingProvider(int pollSeconds) {
        if (pollSeconds < 5) {
            throw new IllegalArgumentException("Poll seconds cannot be less than 5");
        }
        this.pollSeconds = pollSeconds;
    }

    public int getPollSeconds() {
        return pollSeconds;
    }
}
