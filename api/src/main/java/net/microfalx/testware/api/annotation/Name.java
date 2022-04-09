package net.microfalx.testware.api.annotation;

import java.lang.annotation.*;

/**
 * An interface used to give a friendly name to a test or a group of tests.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Name {

    /**
     * Returns the name.
     * @return a non-null instance
     */
    String value();
}
