package net.tarau.testware.core.model;

import net.tarau.binserde.SerializerFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

class SessionModelTest {

    @Test
    void serializeOne() throws IOException {
        serializeAndDeserialize();
    }

    @Test
    void serializeMany() throws IOException {
        for (int i = 0; i < 1000; i++) {
            serializeAndDeserialize();
        }
    }

    private void serializeAndDeserialize() throws IOException {
        TestGenerator generator = new TestGenerator();
        SessionModel session = generator.createSession(4, 10);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SerializerFactory.serialize(session, outputStream);

        SessionModel deserializedSession = SerializerFactory.deserialize(SessionModel.class, new ByteArrayInputStream(outputStream.toByteArray()));
        Assertions.assertThat(session).usingRecursiveComparison().isEqualTo(deserializedSession);
    }

}