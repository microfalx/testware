package net.tarau.testware.api.metadata;

/**
 * Holds information about a test class.
 */
public interface ClassDescriptor extends AnnotationAwareDescriptor, MetadataAwareDescriptor {

    /**
     * Returns the test class for this descriptor.
     *
     * @return a non-null instance
     */
    Class<?> getTestClass();

}
