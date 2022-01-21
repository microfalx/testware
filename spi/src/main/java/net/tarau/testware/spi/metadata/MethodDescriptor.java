package net.tarau.testware.spi.metadata;

import net.tarau.binserde.utils.ArgumentUtils;

import java.lang.reflect.Method;

public class MethodDescriptor extends AnnotationDescriptor implements net.tarau.testware.api.metadata.MethodDescriptor {

    private Method testMethod;

    public static Builder create(Method method) {
        return new Builder(method);
    }

    @Override
    public Method getTestMethod() {
        return testMethod;
    }

    public static class Builder extends AnnotationDescriptor.Builder<Builder, MethodDescriptor> {

        private final Method testMethod;

        public Builder(Method testMethod) {
            ArgumentUtils.requireNonNull(testMethod);
            this.testMethod = testMethod;
        }

        @Override
        protected MethodDescriptor createDescriptor() {
            return new MethodDescriptor();
        }

        @Override
        protected void updateDescriptor(MethodDescriptor descriptor) {
            descriptor.testMethod = testMethod;
        }
    }
}
