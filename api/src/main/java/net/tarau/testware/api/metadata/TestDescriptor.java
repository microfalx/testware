package net.tarau.testware.api.metadata;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

public interface TestDescriptor {

    /**
     * Returns the display name of the current test or container.
     *
     * @return a non-empty String
     */
    String getDisplayName();

    /**
     * Returns the set of all tags for the current test or container.
     *
     * <p>Tags may be declared directly on the test element or <em>inherited</em>
     * from an outer context.
     */
    Set<String> getTags();

    /**
     * Get the {@link Class} associated with the current test or container, if available.
     */
    Optional<ClassDescriptor> getClassDescriptor();

    /**
     * Get the {@link Method} associated with the current test or container, if available.
     */
    Optional<MethodDescriptor> getMethodDescriptor();
}
