package net.tarau.testware.core.model;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer;
import net.tarau.binserde.annotation.Tag;

import java.time.ZonedDateTime;
import java.util.UUID;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

@SuppressWarnings("unchecked")
public abstract class AbstractModel<T extends AbstractModel<T>> {

    public static final int BASE_ENUM_TAG = 100;
    public static final int BASE_TAG = 200;

    @Tag(1)
    @TaggedFieldSerializer.Tag(1)
    private String id = UUID.randomUUID().toString();

    @Tag(2)
    @TaggedFieldSerializer.Tag(2)
    private ZonedDateTime timestamp = ZonedDateTime.now();

    public String getId() {
        return id;
    }

    public T setId(String id) {
        this.id = id;
        return (T) this;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public T setTimestamp(ZonedDateTime timestamp) {
        requireNonNull(timestamp);
        this.timestamp = timestamp;
        return (T) this;
    }

    public T mostRecent(T value) {
        if (value == null) return (T) this;
        if (getTimestamp().isAfter(value.getTimestamp())) {
            return (T) this;
        } else {
            return value;
        }
    }

}
