package net.microfalx.testware.core.model;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer;
import net.microfalx.binserde.SerializerFactory;
import net.microfalx.testware.api.Hook;
import net.microfalx.testware.api.Status;
import net.microfalx.testware.api.Test;
import net.microfalx.testware.spi.util.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Serde {

    /**
     * Deserialize a model.
     *
     * @param type        the model type
     * @param inputStream the stream containing serialized data
     * @param <T>         the model type
     * @return the model
     * @throws IOException if an I/O error occurs
     */
    public static <T> T deserialize(Class<T> type, InputStream inputStream) throws IOException {
        if (isKryo()) {
            Input input = new Input(inputStream);
            try {
                return createKryo().readObject(input, type);
            } finally {
                input.close();
            }
        } else {
            return SerializerFactory.deserialize(type, inputStream);
        }
    }

    /**
     * Serialize a model.
     *
     * @param object       the model
     * @param outputStream the stream which will contain serialized data
     * @param <T>          the model type
     * @throws IOException if an I/O error occurs
     */
    public static <T> void serialize(T object, OutputStream outputStream) throws IOException {
        if (isKryo()) {
            Output output = new Output(outputStream);
            createKryo().writeObject(output, object);
            output.close();
        } else {
            SerializerFactory.serialize(object, outputStream);
        }
    }

    private static Kryo createKryo() {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.setDefaultSerializer(TaggedFieldSerializer.class);
        kryo.register(Status.class, AbstractModel.BASE_ENUM_TAG + 1);
        kryo.register(Test.Type.class, AbstractModel.BASE_ENUM_TAG + 2);
        kryo.register(Hook.Type.class, AbstractModel.BASE_ENUM_TAG + 3);
        kryo.register(SessionModel.class, AbstractModel.BASE_TAG + 12);
        kryo.register(ForkModel.class, AbstractModel.BASE_TAG + 11);
        kryo.register(TestModel.class, AbstractModel.BASE_TAG + 10);
        return kryo;
    }

    private static boolean isKryo() {
        return "kryo".equalsIgnoreCase(Configuration.getString("storage.format", "kryo"));
    }

    static {
        SerializerFactory serializerFactory = SerializerFactory.getInstance();
        serializerFactory.register(Status.class, AbstractModel.BASE_ENUM_TAG + 1);
        serializerFactory.register(Hook.Type.class, AbstractModel.BASE_ENUM_TAG + 2);
        serializerFactory.register(Test.Type.class, AbstractModel.BASE_ENUM_TAG + 3);
        serializerFactory.register(SessionModel.class);
        serializerFactory.register(ForkModel.class);
        serializerFactory.register(TestModel.class);

    }
}
