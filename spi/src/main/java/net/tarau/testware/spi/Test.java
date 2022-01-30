package net.tarau.testware.spi;

import net.tarau.testware.api.metadata.TestDescriptor;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public class Test extends Result implements net.tarau.testware.api.Test {

    private TestDescriptor descriptor;
    private Type type;

    public static Builder create(TestDescriptor descriptor) {
        return new Builder(descriptor);
    }

    @Override
    public TestDescriptor getDescriptor() {
        return descriptor;
    }

    @Override
    public Type getType() {
        return type;
    }

    public static class Builder extends Result.Builder<Builder, Test> {

        private Type type = Type.UNIT;
        private final TestDescriptor descriptor;

        public Builder(TestDescriptor descriptor) {
            requireNonNull(descriptor);
            this.descriptor = descriptor;
        }

        public Builder type(Type type) {
            requireNonNull(type);
            this.type = type;
            return this;
        }

        @Override
        protected Test createResult() {
            return new Test();
        }

        @Override
        protected void updateResult(Test result) {
            result.descriptor = descriptor;
            result.type = type;
        }
    }
}
