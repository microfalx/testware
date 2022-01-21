package net.tarau.testware.spi.metadata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClassDescriptorTest {

    @Test
    void create() throws Exception {
        ClassDescriptor descriptor = ClassDescriptor.create(TestDescriptorTest.class).build();
        assertNotNull(descriptor);
    }

}