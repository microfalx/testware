package net.tarau.testware.spi.metadata;

import net.tarau.binserde.utils.ArgumentUtils;

public class ClassDescriptor extends AnnotationDescriptor implements net.tarau.testware.api.metadata.ClassDescriptor {

    private Class<?> testClass;

    public static Builder create(Class<?> testClass) {
        return new Builder(testClass);
    }

    @Override
    public Class<?> getTestClass() {
        return testClass;
    }

    public static class Builder extends AnnotationDescriptor.Builder<Builder, ClassDescriptor> {

        private final Class<?> testClass;

        public Builder(Class<?> testClass) {
            ArgumentUtils.requireNonNull(testClass);
            this.testClass = testClass;
        }

        @Override
        protected ClassDescriptor createDescriptor() {
            return new ClassDescriptor();
        }

        @Override
        protected void updateDescriptor(ClassDescriptor descriptor) {
            descriptor.testClass = testClass;
        }
    }

}
