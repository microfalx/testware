package net.tarau.testware.junit5.annotation;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

/**
 * An annotation used to mark unit tests.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Tag("unit")
public @interface UnitTest {
}
