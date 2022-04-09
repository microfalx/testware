package net.microfalx.testware.api.annotation;

import java.lang.annotation.*;

/**
 * An annotation used to mark tests as @{code flaky} and reduce (and categorize) the importance of tests which randomly
 * fail (timing, dependency on external systems, etc).
 * <p>
 * The annotation should be used temporary until the test is refactored to eliminate these failures
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Flaky {
}
