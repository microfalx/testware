package net.tarau.testware.core.model;

import net.tarau.binserde.annotation.Tag;
import net.tarau.testware.api.Status;
import net.tarau.testware.api.Type;

import java.util.Set;

import static net.tarau.testware.core.model.AbstractModel.BASE_TAG;
import static net.tarau.testware.spi.util.CollectionUtils.immutable;

@Tag(BASE_TAG + 10)
public class TestModel extends AbstractModel<ForkModel> {

    @Tag(100)
    private String className;
    @Tag(101)
    private String method;
    @Tag(102)
    private String displayName;
    @Tag(103)
    private String description;

    @Tag(120)
    private Set<String> tags;
    @Tag(121)
    private Set<String> issues;
    @Tag(122)
    private boolean bug;
    @Tag(123)
    private boolean flaky;

    @Tag(140)
    private Status status;
    @Tag(141)
    private Type type;

    @Tag(150)
    private String failureMessage;
    @Tag(151)
    private String stackTrace;

    public String getId() {
        return className + "." + method;
    }

    public String getClassName() {
        return className;
    }

    public TestModel setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public TestModel setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TestModel setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TestModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<String> getTags() {
        return immutable(tags);
    }

    public TestModel setTags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    public Set<String> getIssues() {
        return immutable(issues);
    }

    public TestModel setIssues(Set<String> issues) {
        this.issues = issues;
        return this;
    }

    public boolean isBug() {
        return bug;
    }

    public TestModel setBug(boolean bug) {
        this.bug = bug;
        return this;
    }

    public boolean isFlaky() {
        return flaky;
    }

    public TestModel setFlaky(boolean flaky) {
        this.flaky = flaky;
        return this;
    }

    public Status getStatus() {
        return status != null ? status : Status.NA;
    }

    public TestModel setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Type getType() {
        return type != null ? type : Type.OTHER;
    }

    public TestModel setType(Type type) {
        this.type = type;
        return this;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public TestModel setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
        return this;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public TestModel setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }
}
