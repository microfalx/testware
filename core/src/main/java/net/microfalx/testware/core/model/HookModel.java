package net.microfalx.testware.core.model;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer;
import net.microfalx.binserde.annotation.Tag;
import net.microfalx.binserde.utils.ArgumentUtils;
import net.microfalx.testware.api.Hook;

import static net.microfalx.testware.core.model.AbstractModel.BASE_TAG;

@Tag(BASE_TAG + 13)
public class HookModel extends ResultModel<HookModel> {

    @Tag(120)
    @TaggedFieldSerializer.Tag(120)
    private Hook.Type type;

    public Hook.Type getType() {
        return type;
    }

    public HookModel setType(Hook.Type type) {
        this.type = type;
        return this;
    }

    public static HookModel from(Hook hook) {
        ArgumentUtils.requireNonNull(hook);
        HookModel model = new HookModel();
        ResultModel.updateResult(hook, model);
        model.setType(hook.getType());
        return model;
    }
}
