package net.tarau.testware.core.model;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer;
import net.tarau.binserde.annotation.Tag;
import net.tarau.binserde.utils.ArgumentUtils;
import net.tarau.testware.api.Hook;
import net.tarau.testware.api.Test;
import net.tarau.testware.api.metadata.TestDescriptor;
import net.tarau.testware.spi.util.CollectionUtils;

import java.util.Collection;
import java.util.Set;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;
import static net.tarau.testware.core.model.AbstractModel.BASE_TAG;
import static net.tarau.testware.spi.util.CollectionUtils.immutable;

@Tag(BASE_TAG + 12)
public class TestModel extends ResultModel<TestModel> {

    @Tag(120)
    @TaggedFieldSerializer.Tag(120)
    private Test.Type type;

    @Tag(130)
    @TaggedFieldSerializer.Tag(130)
    private Set<String> tags;
    @Tag(131)
    @TaggedFieldSerializer.Tag(131)
    private Set<String> issues;
    @Tag(132)
    @TaggedFieldSerializer.Tag(132)
    private boolean bug;
    @Tag(133)
    @TaggedFieldSerializer.Tag(133)
    private boolean flaky;

    @Tag(140)
    @TaggedFieldSerializer.Tag(140)
    private Collection<HookModel> hooks;

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

    public Test.Type getType() {
        return type != null ? type : Test.Type.UNIT;
    }

    public TestModel setType(Test.Type type) {
        this.type = type;
        return this;
    }

    public Collection<HookModel> getHooks() {
        return immutable(hooks);
    }

    public void setHooks(Collection<HookModel> hooks) {
        this.hooks = hooks;
    }

    public TestModel add(HookModel hook) {
        requireNonNull(hook);
        hooks = CollectionUtils.required(hooks);
        hooks.add(hook);
        return this;
    }

    public static TestModel from(Test test) {
        ArgumentUtils.requireNonNull(test);
        TestModel model = new TestModel();
        ResultModel.updateResult(test, model);
        if (test.getTestDescriptor().isPresent()) {
            TestDescriptor testDescriptor = test.getTestDescriptor().get();
            model.setType(test.getType());
            model.setDisplayName(testDescriptor.getDisplayName());
            model.setBug(testDescriptor.isBug());
            model.setFlaky(testDescriptor.isFlaky());
            model.setIssues(testDescriptor.getIssues());
            model.setTags(testDescriptor.getTags());

        }
        for (Hook hook : test.getHooks()) {
            model.add(HookModel.from(hook));
        }
        return model;
    }
}
