package net.microfalx.testware.api.annotation;

import java.lang.annotation.*;

/**
 * An annotation used to mark tests written with the purpose of fixing bug.
 * <p>
 * This annotation is usually paired with {@link Issue}.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Bug {
}
