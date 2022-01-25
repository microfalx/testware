package net.tarau.testware.spi.metadata;

import net.tarau.binserde.utils.ArgumentUtils;
import net.tarau.testware.api.annotation.Description;
import net.tarau.testware.api.annotation.Name;
import net.tarau.testware.api.annotation.Tag;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class MethodDescriptor extends BaseDescriptor implements net.tarau.testware.api.metadata.MethodDescriptor {

    private Method testMethod;

    public static Builder create(Method method) {
        return new Builder(method);
    }

    @Override
    public Method getTestMethod() {
        return testMethod;
    }

    @Override
    public String toString() {
        return "MethodDescriptor{" +
                "testMethod=" + testMethod +
                "} " + super.toString();
    }

    public static class Builder extends BaseDescriptor.Builder<Builder, MethodDescriptor> {

        private final Method testMethod;

        public Builder(Method testMethod) {
            ArgumentUtils.requireNonNull(testMethod);
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
            descriptor.testMethod = testMethod;
            updateFromAnnotations();
            super.updateDescriptor(descriptor);
        }
    }
}
