package net.tarau.testware.api.annotation;

import java.lang.annotation.*;

/**
 * An annotationn to support multiple {@link Issue Issues}.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Issues {

    /**
     * Returns a collection of {@link Issue}
     *
     * @return a non-null instance
     */
    Issue[] value();
}
