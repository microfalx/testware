package net.microfalx.testware.api.annotation;

import java.lang.annotation.*;

/**
 * An annotation used to assign a category to a test.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Category {

    /**
     * Returns the category name
     *
     * @return a non-null String
     */
    String value();
}
