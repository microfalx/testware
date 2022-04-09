package net.microfalx.testware.core.model;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer;
import net.microfalx.binserde.annotation.Tag;
import net.microfalx.testware.api.Result;
import net.microfalx.testware.api.Status;
import net.microfalx.testware.api.metadata.ClassDescriptor;
import net.microfalx.testware.api.metadata.MethodDescriptor;
import net.microfalx.testware.spi.util.ExceptionUtils;

import java.time.Duration;

public abstract class ResultModel<T extends ResultModel<T>> extends AbstractModel<T> {

    @Tag(100)
    @TaggedFieldSerializer.Tag(100)
    private String className;
    @Tag(101)
    @TaggedFieldSerializer.Tag(101)
    private String methodName;
    @Tag(102)
    @TaggedFieldSerializer.Tag(102)
    private String displayName;
    @Tag(103)
    @TaggedFieldSerializer.Tag(103)
    private String description;

    @Tag(110)
    @TaggedFieldSerializer.Tag(110)
    private Status status;
    @Tag(111)
    @TaggedFieldSerializer.Tag(111)
    private Duration duration;
    @Tag(112)
    @TaggedFieldSerializer.Tag(112)
    private String failureMessage;
    @Tag(113)
    @TaggedFieldSerializer.Tag(113)
    private String stackTrace;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    static <R extends Result, M extends ResultModel<M>> void updateResult(R result, M model) {
        if (result.getClassDescriptor().isPresent()) {
            ClassDescriptor classDescriptor = result.getClassDescriptor().get();
            model.setClassName(classDescriptor.getTestClass().getName());
        }
        if (result.getMethodDescriptor().isPresent()) {
            MethodDescriptor methodDescriptor = result.getMethodDescriptor().get();
            model.setMethodName(methodDescriptor.getTestMethod().getName());
            model.setDisplayName(methodDescriptor.getDisplayName());
        }
        model.setDuration(result.getDuration());
        model.setFailureMessage(result.getFailureMessage());
        model.setStackTrace(ExceptionUtils.getStackTrace(result.getThrowable()));
        model.setStatus(result.getStatus());
    }
}
