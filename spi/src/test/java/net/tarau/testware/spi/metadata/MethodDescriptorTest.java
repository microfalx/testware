package net.tarau.testware.spi.metadata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MethodDescriptorTest {

    @Test
    void create() throws Exception {
        MethodDescriptor descriptor = MethodDescriptor.create(TestDescriptorTest.class.getDeclaredMethod("create")).build();
        assertNotNull(descriptor);
    }

}