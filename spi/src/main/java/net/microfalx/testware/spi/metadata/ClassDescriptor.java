package net.microfalx.testware.spi.metadata;

import net.microfalx.testware.api.annotation.Category;
import net.microfalx.testware.api.annotation.Description;
import net.microfalx.testware.api.annotation.Name;
import net.microfalx.testware.api.annotation.Tag;

import java.lang.annotation.Annotation;

import static net.microfalx.binserde.utils.ArgumentUtils.requireNonNull;

public final class ClassDescriptor extends BaseDescriptor implements net.microfalx.testware.api.metadata.ClassDescriptor {

    private Class<?> testClass;

    public static Builder create(Class<?> testClass) {
        return new Builder(testClass);
    }

    @Override
    public Class<?> getTestClass() {
        return testClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassDescriptor that = (ClassDescriptor) o;

        return testClass.equals(that.testClass);
    }

    @Override
    public int hashCode() {
        return testClass.hashCode();
    }

    @Override
    public String toString() {
        return "ClassDescriptor{" +
                "testClass=" + testClass +
                "} " + super.toString();
    }

    public static class Builder extends BaseDescriptor.Builder<Builder, ClassDescriptor> {

        private final Class<?> testClass;

        public Builder(Class<?> testClass) {
            requireNonNull(testClass);
            this.testClass = testClass;
        }

        @Override
        protected ClassDescriptor createDescriptor() {
            return new ClassDescriptor();
        }

        private void updateFromAnnotations() {
            displayName(testClass.getName());
            Class<?> currentClass = testClass;
            String displayName = null;
            String description = null;
            String category = null;
            while (currentClass != Object.class) {
                Annotation[] annotations = currentClass.getAnnotations();
                for (Annotation annotation : annotations) {
                    annotation(annotation);
                    if (annotation.annotationType() == Tag.class) {
                        tags(((Tag) annotation).value());
                    }
                }
                Name nameAnnotation = currentClass.getAnnotation(Name.class);
                if (nameAnnotation != null && displayName == null) displayName = nameAnnotation.value();
                Description descriptionAnnotation = currentClass.getAnnotation(Description.class);
                if (descriptionAnnotation != null && description == null) description = descriptionAnnotation.value();
                Category categoryAnnotation = currentClass.getAnnotation(Category.class);
                if (categoryAnnotation != null && category == null) category = categoryAnnotation.value();
                currentClass = currentClass.getSuperclass();
            }
            if (displayName != null) displayName(displayName);
            if (description != null) description(description);
            if (category != null) category(category);
        }

        @Override
        protected void updateDescriptor(ClassDescriptor descriptor) {
            descriptor.testClass = testClass;
            updateFromAnnotations();
            super.updateDescriptor(descriptor);

        }
    }

}
