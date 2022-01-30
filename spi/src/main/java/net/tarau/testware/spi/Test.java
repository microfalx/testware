package net.tarau.testware.spi;

import net.tarau.binserde.utils.ArgumentUtils;
import net.tarau.testware.api.metadata.TestDescriptor;

public class Test extends Result implements net.tarau.testware.api.Test {

    private TestDescriptor descriptor;
    private Type type;

    @Override
    public TestDescriptor getDescriptor() {
        return descriptor;
    }

    @Override
    public Type getType() {
        return type;
    }

    public static class Builder extends Result.Builder<Builder, Test> {
        private Type type;

        public Builder type(Type type) {
            ArgumentUtils.requireNonNull(type);
            this.type = type;
            return this;
        }
    }
}
