package net.tarau.testware.api.annotation;

import java.lang.annotation.*;

/**
 * An annotation
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Repeatable(Issues.class)
public @interface Issue {

    /**
     * Returns the issue identifier.
     * @return a non-empty String
     */
    String value();
}
