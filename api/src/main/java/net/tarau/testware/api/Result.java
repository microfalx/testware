package net.tarau.testware.api;

/**
 * A result of a hook or test execution.
 */
public interface Result {

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
