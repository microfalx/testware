package net.microfalx.testware.spi;

import net.microfalx.testware.api.Hook;
import net.microfalx.testware.api.metadata.ClassDescriptor;
import net.microfalx.testware.api.metadata.MethodDescriptor;
import net.microfalx.testware.api.metadata.TestDescriptor;
import net.microfalx.testware.spi.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static net.microfalx.binserde.utils.ArgumentUtils.requireNonNull;

public class Test extends Result implements net.microfalx.testware.api.Test, Cloneable {

    private TestDescriptor testDescriptor;
    private Type type;
    private Collection<Hook> hooks = new ArrayList<>();

    public static Builder create(TestDescriptor descriptor) {
        return new Builder(descriptor);
    }

    @Override
    public Optional<ClassDescriptor> getClassDescriptor() {
        return testDescriptor.getClassDescriptor();
    }

    @Override
    public Optional<MethodDescriptor> getMethodDescriptor() {
        return testDescriptor.getMethodDescriptor();
    }

    @Override
    public Optional<TestDescriptor> getTestDescriptor() {
        return Optional.of(testDescriptor);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Collection<Hook> getHooks() {
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

        if (!testDescriptor.equals(test.testDescriptor)) return false;
        return type == test.type;
    }

    @Override
    public int hashCode() {
        int result = testDescriptor.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Test{" + "descriptor=" + testDescriptor + ", type=" + type + ", hooks=" + hooks + "} " + super.toString();
    }

    public static class Builder extends Result.Builder<Builder, Test> {

        private Type type = Type.UNIT;
        private final TestDescriptor testDescriptor;

        public Builder(TestDescriptor testDescriptor) {
            requireNonNull(testDescriptor);
            this.testDescriptor = testDescriptor;
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
            result.testDescriptor = testDescriptor;
            result.type = type;
        }
    }
}
