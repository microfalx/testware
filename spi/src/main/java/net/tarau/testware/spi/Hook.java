package net.tarau.testware.spi;

import net.tarau.testware.api.metadata.MethodDescriptor;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public class Hook extends Result implements net.tarau.testware.api.Hook {

    private MethodDescriptor descriptor;
    private Type type;

    public static Builder create(MethodDescriptor descriptor) {
        return new Builder(descriptor);
    }

    @Override
    public MethodDescriptor getDescriptor() {
        return descriptor;
    }

    @Override
    public Type getType() {
        return type;
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
