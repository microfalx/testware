package net.tarau.testware.core.model;

import net.tarau.binserde.SerializerFactory;
import net.tarau.binserde.annotation.Tag;
import net.tarau.testware.api.Status;
import net.tarau.testware.api.Type;

import java.time.ZonedDateTime;
import java.util.UUID;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

@SuppressWarnings("unchecked")
public abstract class AbstractModel<T extends AbstractModel<T>> {

    public static final int BASE_ENUM_TAG = 100;
    public static final int BASE_TAG = 200;

    @Tag(1)
    private String id = UUID.randomUUID().toString();

    @Tag(2)
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

    static {
        SerializerFactory serializerFactory = SerializerFactory.getInstance();
        serializerFactory.register(Type.class, AbstractModel.BASE_ENUM_TAG);
        serializerFactory.register(Status.class, AbstractModel.BASE_ENUM_TAG + 1);
        serializerFactory.register(SessionModel.class);
        serializerFactory.register(ForkModel.class);
        serializerFactory.register(TestModel.class);
    }
}
