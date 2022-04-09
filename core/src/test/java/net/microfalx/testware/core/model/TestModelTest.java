package net.microfalx.testware.core.model;

import net.microfalx.binserde.SerializerFactory;
import net.microfalx.testware.core.model.TestModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

class TestModelTest {

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
        TestModel test = generator.createTest();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SerializerFactory.serialize(test, outputStream);

        TestModel deserializedTest = SerializerFactory.deserialize(TestModel.class, new ByteArrayInputStream(outputStream.toByteArray()));
        Assertions.assertThat(test).usingRecursiveComparison().isEqualTo(deserializedTest);
    }

}