package net.tarau.testware.api;

/**
 * An enum with possible status for a test
 */
public enum Status {

    /**
     * The status is not known.
     */
    NA,

    /**
     * The test failed.
     */
    FAILED,

    /**
     * The test failed.
     */
    PASSED,

    /**
     * The test was skipped (not executed)
     */
    SKIPPED;
}
