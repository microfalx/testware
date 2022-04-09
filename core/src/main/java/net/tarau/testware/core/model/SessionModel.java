package net.microfalx.testware.core.model;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer;
import net.microfalx.binserde.annotation.Tag;
import net.microfalx.testware.spi.util.CollectionUtils;

import java.util.Collection;

import static net.microfalx.binserde.utils.ArgumentUtils.requireNonNull;
import static net.microfalx.testware.core.model.AbstractModel.BASE_TAG;
import static net.microfalx.testware.spi.util.CollectionUtils.immutable;

@Tag(BASE_TAG + 10)
public class SessionModel extends AbstractModel<SessionModel> {

    @Tag(100)
    @TaggedFieldSerializer.Tag(100)
    private Collection<ForkModel> forks;

    public Collection<ForkModel> getForks() {
        return immutable(forks);
    }

    public SessionModel setForks(Collection<ForkModel> forks) {
        this.forks = forks;
        return this;
    }

    public SessionModel add(ForkModel fork) {
        requireNonNull(fork);
        forks = CollectionUtils.required(forks);
        forks.add(fork);
        return this;
    }


}
