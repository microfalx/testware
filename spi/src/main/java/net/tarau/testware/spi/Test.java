package net.tarau.testware.spi;

import net.tarau.testware.api.Hook;
import net.tarau.testware.api.metadata.TestDescriptor;
import net.tarau.testware.spi.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public class Test extends Result implements net.tarau.testware.api.Test, Cloneable {

    private TestDescriptor descriptor;
    private Type type;
    private Collection<net.tarau.testware.api.Hook> hooks = new ArrayList<>();

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

    @Override
    public Collection<net.tarau.testware.api.Hook> getHooks() {
        return CollectionUtils.immutable(hooks);
    }

    public Test withHook(Hook hook) {
        requireNonNull(hook);
        Test copy = copy();
        copy.hooks.add(hook);
        return copy;
    }

    protected Test copy() {
        try {
            return (Test) clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Test test = (Test) o;

        if (!descriptor.equals(test.descriptor)) return false;
        return type == test.type;
    }

    @Override
    public int hashCode() {
        int result = descriptor.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Test{" +
                "descriptor=" + descriptor +
                ", type=" + type +
                ", hooks=" + hooks +
                "} " + super.toString();
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
