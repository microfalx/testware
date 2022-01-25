package net.tarau.testware.spi.metadata;

import net.tarau.testware.api.metadata.AnnotationAwareDescriptor;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public abstract class AnnotationDescriptor implements AnnotationAwareDescriptor {

    private Map<Class<? extends Annotation>, Collection<Annotation>> annotations = new HashMap<>();

    public final boolean isAnnotated(Class<? extends Annotation> type) {
        requireNonNull(type);
        return annotations.containsKey(type);
    }

    @SuppressWarnings("unchecked")
    public final <A extends Annotation> Optional<A> findAnnotation(Class<A> type) {
        requireNonNull(type);
        Collection<A> annotations = getAnnotations(type);
        return annotations.isEmpty() ? Optional.empty() : Optional.of(annotations.iterator().next());
    }

    @SuppressWarnings("unchecked")
    public final <A extends Annotation> Collection<A> getAnnotations(Class<A> type) {
        requireNonNull(type);
        return (Collection<A>) annotations.getOrDefault(type, Collections.emptyList());
    }

    @Override
    public Collection<? extends Annotation> getAnnotations() {
        return annotations.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AnnotationDescriptor.class.getSimpleName() + "[", "]")
                .add("annotations=" + annotations)
                .toString();
    }

    public static abstract class Builder<B extends Builder, D extends AnnotationDescriptor> {

        private Map<Class<? extends Annotation>, Collection<Annotation>> annotations = new HashMap<>();

        protected abstract D createDescriptor();

        protected abstract void updateDescriptor(D descriptor);

        public <A extends Annotation> B annotation(A annotation) {
            requireNonNull(annotation);
            annotations.computeIfAbsent(annotation.annotationType(), c -> new ArrayList<>()).add(annotation);
            return (B) this;
        }

        protected D build() {
            D descriptor = createDescriptor();
            AnnotationDescriptor annotationDescriptor = descriptor;
            updateDescriptor(descriptor);
            annotationDescriptor.annotations = annotations;
            return descriptor;
        }
    }
}
