package net.tarau.testware.api.metadata;

import java.lang.reflect.Method;
import java.util.Optional;

public interface TestDescriptor extends AnnotationAwareDescriptor, MetadataAwareDescriptor {

    /**
     * Get the {@link Class} associated with the current test or container, if available.
     */
    Optional<ClassDescriptor> getClassDescriptor();

    /**
     * Get the {@link Method} associated with the current test or container, if available.
     */
    Optional<MethodDescriptor> getMethodDescriptor();
}
