package net.tarau.testware.spi.metadata;

import net.tarau.testware.api.metadata.ClassDescriptor;
import net.tarau.testware.api.metadata.MethodDescriptor;

import java.util.Optional;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public class TestDescriptor extends BaseDescriptor implements net.tarau.testware.api.metadata.TestDescriptor {

    private ClassDescriptor classDescriptor;
    private MethodDescriptor methodDescriptor;

    public static Builder create(ClassDescriptor classDescriptor, MethodDescriptor methodDescriptor) {
        return new Builder(classDescriptor, methodDescriptor);
    }

    @Override
    public Optional<ClassDescriptor> getClassDescriptor() {
        return classDescriptor != null ? Optional.of(classDescriptor) : Optional.empty();
    }

    @Override
    public Optional<MethodDescriptor> getMethodDescriptor() {
        return methodDescriptor != null ? Optional.of(methodDescriptor) : Optional.empty();
    }


    @Override
    public String toString() {
        return "TestDescriptor{" +
                "classDescriptor=" + classDescriptor +
                ", methodDescriptor=" + methodDescriptor +
                "} " + super.toString();
    }

    public static class Builder extends BaseDescriptor.Builder<Builder, TestDescriptor> {

        private final ClassDescriptor classDescriptor;
        private final MethodDescriptor methodDescriptor;

        public Builder(ClassDescriptor classDescriptor, MethodDescriptor methodDescriptor) {
            requireNonNull(classDescriptor);
            requireNonNull(methodDescriptor);

            this.classDescriptor = classDescriptor;
            this.methodDescriptor = methodDescriptor;
        }

        private void updateFromAnnotations() {
            displayName(methodDescriptor.getDisplayName());
            description(methodDescriptor.getDescription());
            tags(methodDescriptor.getTags());
            tags(classDescriptor.getTags());
        }

        @Override
        protected TestDescriptor createDescriptor() {
            return new TestDescriptor();
        }

        @Override
        protected void updateDescriptor(TestDescriptor descriptor) {
            updateFromAnnotations();
            super.updateDescriptor(descriptor);
            descriptor.classDescriptor = classDescriptor;
            descriptor.methodDescriptor = methodDescriptor;
        }

    }
}
