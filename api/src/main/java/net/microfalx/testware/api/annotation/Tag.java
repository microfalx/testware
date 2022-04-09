package net.microfalx.testware.api.annotation;

import java.lang.annotation.*;

/**
 * An annotation used to tag tests.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Tag {

    /**
     * Returns a list of tags.
     *
     * @return a non-null instance
     */
    String[] value();
}
