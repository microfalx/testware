package net.tarau.testware.api.metadata;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Holds information about a test hook (before all, before each, after each, after all).
 */
public interface HookDescriptor extends AnnotationAwareDescriptor, MetadataAwareDescriptor {

    /**
     * Get the {@link Class} associated with the current test or container, if available.
     */
    Optional<ClassDescriptor> getClassDescriptor();

    /**
     * Get the {@link Method} associated with the current test or container, if available.
     */
    Optional<MethodDescriptor> getMethodDescriptor();
}
