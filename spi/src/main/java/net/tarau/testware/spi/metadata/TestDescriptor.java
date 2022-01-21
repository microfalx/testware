package net.tarau.testware.spi.metadata;

import net.tarau.testware.api.metadata.ClassDescriptor;
import net.tarau.testware.api.metadata.MethodDescriptor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import static java.util.Collections.unmodifiableSet;
import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public class TestDescriptor implements net.tarau.testware.api.metadata.TestDescriptor {

    private String displayName;
    private Set<String> tags;
    private ClassDescriptor classDescriptor;
    private MethodDescriptor methodDescriptor;

    public static Builder create(ClassDescriptor classDescriptor, MethodDescriptor methodDescriptor) {
        return new Builder(classDescriptor, methodDescriptor);
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Set<String> getTags() {
        return unmodifiableSet(tags);
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
        return new StringJoiner(", ", TestDescriptor.class.getSimpleName() + "[", "]").add("displayName='" + displayName + "'").add("tags=" + tags).add("classDescriptor=" + classDescriptor).add("methodDescriptor=" + methodDescriptor).toString();
    }

    public static class Builder {

        private String displayName;
        private Set<String> tags;
        private final ClassDescriptor classDescriptor;
        private final MethodDescriptor methodDescriptor;

        public Builder(ClassDescriptor classDescriptor, MethodDescriptor methodDescriptor) {
            requireNonNull(classDescriptor);
            requireNonNull(methodDescriptor);

            this.classDescriptor = classDescriptor;
            this.methodDescriptor = methodDescriptor;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public void tags(Set<String> tags) {
            requireNonNull(tags);
            this.tags = new HashSet<>(tags);
        }

        public TestDescriptor build() {
            TestDescriptor descriptor = new TestDescriptor();
            descriptor.classDescriptor = classDescriptor;
            descriptor.methodDescriptor = methodDescriptor;
            descriptor.displayName = displayName != null ? displayName : methodDescriptor.getDisplayName();
            descriptor.tags = tags;
            return descriptor;
        }
    }
}
