package net.tarau.testware.api.metadata;

import java.lang.reflect.Method;

/**
 * Holds information about a test method.
 */
public interface MethodDescriptor extends AnnotationAwareDescriptor, MetadataAwareDescriptor {

    /**
     * Returns the test method for this descriptor.
     *
     * @return a non-null instance
     */
    Method getTestMethod();

}
