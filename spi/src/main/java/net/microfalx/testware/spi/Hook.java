package net.microfalx.testware.spi;

import net.microfalx.testware.api.metadata.ClassDescriptor;
import net.microfalx.testware.api.metadata.MethodDescriptor;

import java.util.Optional;

import static net.microfalx.binserde.utils.ArgumentUtils.requireNonNull;

public class Hook extends Result implements net.microfalx.testware.api.Hook {

    private MethodDescriptor descriptor;
    private Type type;

    public static Builder create(MethodDescriptor descriptor) {
        return new Builder(descriptor);
    }

    @Override
    public Optional<ClassDescriptor> getClassDescriptor() {
        return Optional.of(descriptor.getClassDescriptor());
    }

    @Override
    public Optional<MethodDescriptor> getMethodDescriptor() {
        return Optional.of(descriptor);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Hook{" +
                "descriptor=" + descriptor +
                ", type=" + type +
                "} " + super.toString();
    }

    public static class Builder extends Result.Builder<Builder, Hook> {

        private final MethodDescriptor descriptor;
        private Type type = Type.BEFORE_EACH;

        public Builder(MethodDescriptor descriptor) {
            requireNonNull(descriptor);
            this.descriptor = descriptor;
        }

        public Builder type(Type type) {
            requireNonNull(type);
            this.type = type;
            return this;
        }

        @Override
        protected Hook createResult() {
            return new Hook();
        }

        @Override
        protected void updateResult(Hook result) {
            result.descriptor = descriptor;
            result.type = type;
        }
    }
}
