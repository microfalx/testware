package net.tarau.testware.api.annotation;

import java.lang.annotation.*;

/**
 * An interface used to give a description to a test or a group of tests.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Description {

    /**
     * Returns the description.
     *
     * @return a non-null instance
     */
    String value();
}
