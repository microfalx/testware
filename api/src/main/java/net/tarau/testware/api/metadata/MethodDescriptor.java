package net.tarau.testware.api.metadata;

import java.lang.reflect.Executable;

/**
 * Holds information about a test method.
 */
public interface MethodDescriptor extends AnnotationAwareDescriptor, MetadataAwareDescriptor {

    /**
     * Returns the test method (method or constructor) for this descriptor.
     *
     * @return a non-null instance
     */
    Executable getTestMethod();

}
