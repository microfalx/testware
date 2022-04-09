package net.microfalx.testware.api.annotation;

import java.lang.annotation.*;

/**
 * An annotation to tag tests with issues.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Issue {

    /**
     * Returns a list of  issue identifiers.
     *
     * @return a non-empty String
     */
    String[] value();
}
