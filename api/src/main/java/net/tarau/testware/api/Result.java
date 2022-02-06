package net.tarau.testware.api;

import net.tarau.testware.api.metadata.ClassDescriptor;
import net.tarau.testware.api.metadata.MethodDescriptor;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Optional;

/**
 * Base interface for all results.
 */
public interface Result {

    /**
     * Get the {@link Class} associated with the current test or container, if available.
     */
    Optional<ClassDescriptor> getClassDescriptor();

    /**
     * Get the {@link Method} associated with the current test or container, if available.
     */
    Optional<MethodDescriptor> getMethodDescriptor();

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
