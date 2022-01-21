package net.tarau.testware.api.metadata;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Optional;

/**
 * Holds information about a test class.
 */
public interface ClassDescriptor {

    /**
     * Returns the test class for this descriptor.
     *
     * @return a non-null instance
     */
    Class<?> getTestClass();

    /**
     * Returns the display name for the test class.
     *
     * @return a non-empty String (class name if no other information exists)
     */
    String getDisplayName();

    /**
     * Return whether an annotation is present on the test class or a super class
     *
     * @param type the annotation type to search for
     * @return {@code true} if the annotation is present, {@code false} otherwise
     */
    boolean isAnnotated(Class<? extends Annotation> type);

    /**
     * Find the first annotation of a given type on the test class or a super class.
     *
     * @param type the annotation type to search for
     * @param <A>  the annotation type
     * @return an {@code Optional} containing the annotation; never {@code null} but
     * potentially empty
     * @see #isAnnotated(Class)
     */
    <A extends Annotation> Optional<A> findAnnotation(Class<A> type);

    /**
     * Returns all annotations registered on test class and its subclasses.
     *
     * @param type the annotation type to search for
     * @param <A>  the annotation type
     * @return an {@code Optional} containing the annotation; never {@code null} but
     * potentially empty
     * @see #isAnnotated(Class)
     */
    <A extends Annotation> Collection<A> getAnnotations(Class<A> type);
}