package net.tarau.testware.api;

import net.tarau.testware.api.metadata.MethodDescriptor;

/**
 * A test hooks (before all, before each, after each, after all)
 */
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
        CONSTRUCTOR(true),

        /**
         * A hook called before all tests in a class.
         */
        BEFORE_ALL(true),

        /**
         * A hook called before each test.
         */
        BEFORE_EACH(true),

        /**
         * A hook called after each test.
         */
        AFTER_EACH(false),

        /**
         * A hook called after all tests in a class.
         */
        AFTER_ALL(false);

        boolean before;

        Type(boolean before) {
            this.before = before;
        }

        /**
         * Returns whether the hook type identifies a hook executed before the test.
         *
         * @return {@code true} if executed before test, {@code false} otherwise
         */
        public boolean isBefore() {
            return before;
        }
    }
}
