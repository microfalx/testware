package net.tarau.testware.api;

import java.time.Duration;

/**
 * Base interface for all results.
 */
public interface Result {

    /**
     * Returns the amount of time spent to run a test.
     *
     * @return a non-null instance
     */
    Duration getDuration();

    /**
     * Returns the status associated with a test.
     *
     * @return a non-null instance
     */
    Status getStatus();

    /**
     * Returns the failure message.
     *
     * @return the message, null if successful
     */
    String getFailureMessage();

    /**
     * Returns the exception thrown by a failed test.
     *
     * @return the exception, null if successful
     */
    Throwable getThrowable();
}
