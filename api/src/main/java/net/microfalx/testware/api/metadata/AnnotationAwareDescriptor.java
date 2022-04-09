package net.microfalx.testware.api.metadata;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Optional;

/**
 * Base interface for all descriptors supporting annotations.
 */
public interface AnnotationAwareDescriptor {

    /**
     * Return whether an annotation is present in the context of the current descriptor.
     *
     * @param type the annotation type to search for
     * @return {@code true} if the annotation is present, {@code false} otherwise
     */
    boolean isAnnotated(Class<? extends Annotation> type);

    /**
     * Find the first annotation of a given type from the context of the current descriptor.
     *
     * @param type the annotation type to search for
     * @param <A>  the annotation type
     * @return an {@code Optional} containing the annotation; never {@code null} but
     * potentially empty
     * @see #isAnnotated(Class)
     */
    <A extends Annotation> Optional<A> findAnnotation(Class<A> type);

    /**
     * Returns all annotations registered in the context of the current descriptor.
     *
     * @param type the annotation type to search for
     * @param <A>  the annotation type
     * @return a collection containing the annotation
     */
    <A extends Annotation> Collection<A> getAnnotations(Class<A> type);

    /**
     * Returns all annotations registered in the context of the current descriptor.
     *
     * @return a collection containing the annotation
     */
    Collection<? extends Annotation> getAnnotations();
}
