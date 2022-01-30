package net.tarau.testware.api;

import net.tarau.testware.api.metadata.MethodDescriptor;

public interface Hook extends Result {

    /**
     * Returns the descriptor of this hook.
     *
     * @return a non-null instance
     */
    MethodDescriptor getDescriptor();

    /**
     * Returns the type of the hook.
     *
     * @return a non-null instance
     */
    Type getType();

    /**
     * An enum for types of tests
     */
    enum Type {

        /**
         * The constructor of the test class.
         */
        CONSTRUCTOR,

        /**
         * A hook called before all tests in a class.
         */
        BEFORE_ALL,

        /**
         * A hook called before each test.
         */
        BEFORE_EACH,

        /**
         * A hook called after each test.
         */
        AFTER_EACH,

        /**
         * A hook called after all tests in a class.
         */
        AFTER_ALL,

        /**
         * End-to-end tests replicates a user behavior with the software in a complete application
         * environment. It verifies that various user flows work as expected.
         */
        E2E,

        /**
         * Any test except from above
         */
        OTHER
    }
}
