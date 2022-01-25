package net.tarau.testware.spi.metadata;

import net.tarau.binserde.utils.ArgumentUtils;
import net.tarau.testware.api.Status;
import net.tarau.testware.api.Type;
import net.tarau.testware.api.annotation.Bug;
import net.tarau.testware.api.annotation.Category;
import net.tarau.testware.api.annotation.Issue;
import net.tarau.testware.api.metadata.ClassDescriptor;
import net.tarau.testware.api.metadata.MethodDescriptor;

import java.util.*;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public final class TestDescriptor extends BaseDescriptor implements net.tarau.testware.api.metadata.TestDescriptor {

    private ClassDescriptor classDescriptor;
    private MethodDescriptor methodDescriptor;

    private Set<String> issues;
    private boolean bug;
    private boolean flaky;
    private Type type;
    private Status status;

    private String failureMessage;
    private Throwable throwable;

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
    public Type getType() {
        return type;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Set<String> getIssues() {
        return issues != null ? Collections.unmodifiableSet(issues) : Collections.emptySet();
    }

    @Override
    public boolean isBug() {
        return bug;
    }

    @Override
    public boolean isFlaky() {
        return flaky;
    }

    @Override
    public String getFailureMessage() {
        return failureMessage;
    }

    @Override
    public Throwable getThrowable() {
        return throwable;
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

        private Status status;
        private Type type;
        private Set<String> issues;
        private boolean bug;
        private boolean flaky;

        private String failureMessage;
        private Throwable throwable;

        public Builder(ClassDescriptor classDescriptor, MethodDescriptor methodDescriptor) {
            requireNonNull(classDescriptor);
            requireNonNull(methodDescriptor);

            this.classDescriptor = classDescriptor;
            this.methodDescriptor = methodDescriptor;
        }

        public Builder bug(boolean bug) {
            this.bug = bug;
            return this;
        }

        public Builder flaky(boolean flaky) {
            this.flaky = flaky;
            return this;
        }

        public Builder status(Status status) {
            ArgumentUtils.requireNonNull(status);
            this.status = status;
            return this;
        }

        public Builder type(Type type) {
            ArgumentUtils.requireNonNull(type);
            this.type = type;
            return this;
        }

        public Builder issues(String... issues) {
            return issues(Arrays.asList(issues));
        }

        public Builder issues(Collection<String> issues) {
            requireNonNull(issues);
            if (this.issues == null) {
                this.issues = new HashSet<>();
            }
            this.issues.addAll(issues);
            return this;
        }

        public Builder failureMessage(String failureMessage) {
            this.failureMessage = failureMessage;
            return this;
        }

        public Builder throwable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        private void updateFromAnnotations() {
            displayName(methodDescriptor.getDisplayName());
            description(methodDescriptor.getDescription());
            tags(methodDescriptor.getTags());
            tags(classDescriptor.getTags());
            methodDescriptor.getAnnotations(Issue.class).forEach(issue -> issues(issue.value()));
            classDescriptor.getAnnotations(Issue.class).forEach(issue -> issues(issue.value()));
            methodDescriptor.findAnnotation(Bug.class).ifPresent(bug -> bug(true));
            classDescriptor.findAnnotation(Category.class).ifPresent(category -> category(category.value()));
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
            descriptor.issues = issues;
            descriptor.bug = bug;
            descriptor.flaky = flaky;
            descriptor.status = status;
            descriptor.type = type;
            descriptor.failureMessage = failureMessage;
            descriptor.throwable = throwable;
        }

    }
}
