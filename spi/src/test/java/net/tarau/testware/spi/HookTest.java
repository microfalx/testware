package net.tarau.testware.spi;

import net.tarau.testware.spi.metadata.ClassDescriptor;
import net.tarau.testware.spi.metadata.MethodDescriptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HookTest {

    @org.junit.jupiter.api.Test
    void createDefault() throws Exception {
        Hook hook = Hook.create(create("createDefault").build()).build();
        assertNotNull(hook);
        assertNotNull(hook.getClassDescriptor().get());
        assertNotNull(hook.getMethodDescriptor().get());
        assertEquals(Hook.Type.BEFORE_EACH, hook.getType());
    }

    @org.junit.jupiter.api.Test
    void createIntegration() throws Exception {
        Hook hook = Hook.create(create("createDefault").build()).type(Hook.Type.BEFORE_ALL).build();
        assertEquals(Hook.Type.BEFORE_ALL, hook.getType());
    }

    private MethodDescriptor.Builder create(String methodName) throws Exception {
        return MethodDescriptor.create(ClassDescriptor.create(HookTest.class).build(),
                TestTest.class.getDeclaredMethod(methodName));
    }

}