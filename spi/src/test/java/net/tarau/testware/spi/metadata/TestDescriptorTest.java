package net.tarau.testware.spi.metadata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestDescriptorTest {

    @Test
    void create() throws Exception {
        TestDescriptor descriptor = TestDescriptor.create(ClassDescriptor.create(TestDescriptorTest.class).build(),
                MethodDescriptor.create(TestDescriptorTest.class.getDeclaredMethod("create")).build()).build();
        assertNotNull(descriptor);
    }

}