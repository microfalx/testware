package net.tarau.testware.spi.metadata;

import net.tarau.binserde.utils.ArgumentUtils;
import net.tarau.testware.api.annotation.Description;
import net.tarau.testware.api.annotation.Name;
import net.tarau.testware.api.annotation.Tag;

import java.lang.reflect.Executable;
import java.util.Arrays;

public final class MethodDescriptor extends BaseDescriptor implements net.tarau.testware.api.metadata.MethodDescriptor {

    private net.tarau.testware.api.metadata.ClassDescriptor classDescriptor;
    private Executable testMethod;

    public static Builder create(ClassDescriptor classDescriptor, Executable method) {
        return new Builder(classDescriptor, method);
    }

    @Override
    public net.tarau.testware.api.metadata.ClassDescriptor getClassDescriptor() {
        return classDescriptor;
    }

    @Override
    public Executable getTestMethod() {
        return testMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodDescriptor that = (MethodDescriptor) o;

        return testMethod.equals(that.testMethod);
    }

    @Override
    public int hashCode() {
        return testMethod.hashCode();
    }

    @Override
    public String toString() {
        return "MethodDescriptor{" +
                "testMethod=" + testMethod +
                "} " + super.toString();
    }

    public static class Builder extends BaseDescriptor.Builder<Builder, MethodDescriptor> {

        private final net.tarau.testware.api.metadata.ClassDescriptor classDescriptor;
        private final Executable testMethod;

        public Builder(net.tarau.testware.api.metadata.ClassDescriptor classDescriptor, Executable testMethod) {
            ArgumentUtils.requireNonNull(classDescriptor);
            ArgumentUtils.requireNonNull(testMethod);
            this.classDescriptor = classDescriptor;
            this.testMethod = testMethod;
        }

        @Override
        protected MethodDescriptor createDescriptor() {
            return new MethodDescriptor();
        }

        private void updateFromAnnotations() {
            displayName(testMethod.getName());
            Name nameAnnotation = testMethod.getAnnotation(Name.class);
            if (nameAnnotation != null) displayName(nameAnnotation.value());
            Description descriptionAnnotation = testMethod.getAnnotation(Description.class);
            if (descriptionAnnotation != null) description(descriptionAnnotation.value());
            Arrays.asList(testMethod.getAnnotations()).forEach(annotation -> {
                annotation(annotation);
                if (annotation.annotationType() == Tag.class) {
                    tags(((Tag) annotation).value());
                }
            });
        }

        @Override
        protected void updateDescriptor(MethodDescriptor descriptor) {
            descriptor.classDescriptor = classDescriptor;
            descriptor.testMethod = testMethod;
            updateFromAnnotations();
            super.updateDescriptor(descriptor);
        }
    }
}
