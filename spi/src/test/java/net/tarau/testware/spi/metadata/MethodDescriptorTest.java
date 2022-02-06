package net.tarau.testware.spi.metadata;

import net.tarau.testware.api.annotation.Description;
import net.tarau.testware.api.annotation.Name;
import net.tarau.testware.api.annotation.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Name("Class Descriptor")
@Description("Some class description")
@Tag({"t1", "t2"})
class MethodDescriptorTest extends BaseDescriptorTest {

    @Test
    void create() throws Exception {
        MethodDescriptor descriptor = create("create").build();
        assertNotNull(descriptor);
        assertEquals("create", descriptor.getTestMethod().getName());
    }

    @Test
    @Tag({"t3"})
    void tags() throws Exception {
        MethodDescriptor descriptor = create("tags").tags("t4").build();
        Assertions.assertThat(descriptor.getTags()).containsOnly("t3", "t4");
    }

    @Test
    void isAnnotated() throws Exception {
        MethodDescriptor descriptor = create("tags").tags("t4").build();
        assertTrue(descriptor.isAnnotated(Test.class));
        assertFalse(descriptor.isAnnotated(Name.class));
    }

    @Test
    @Name("Display Name")
    void getDisplayName() throws Exception {
        MethodDescriptor descriptor = create("getDisplayName").build();
        assertEquals("Display Name", descriptor.getDisplayName());
    }

    @Test
    @Description("The Get Description")
    void getDescription() throws Exception {
        MethodDescriptor descriptor = create("getDescription").build();
        assertEquals("The Get Description", descriptor.getDescription());
    }

    @Test
    void toStringTest() throws Exception {
        MethodDescriptor descriptor = create("toStringTest").build();
        assertNotNull(descriptor.toString());
    }

    private MethodDescriptor.Builder create(String methodName) throws Exception {
        return MethodDescriptor.create(ClassDescriptor.create(MethodDescriptorTest.class).build(),
                MethodDescriptorTest.class.getDeclaredMethod(methodName));
    }

}