package net.tarau.testware.spi;

import net.tarau.testware.api.metadata.MethodDescriptor;

public class Hook extends Result implements net.tarau.testware.api.Hook {

    private MethodDescriptor descriptor;
    private Type type;

    @Override
    public MethodDescriptor getDescriptor() {
        return descriptor;
    }

    @Override
    public Type getType() {
        return type;
    }
}
