package net.microfalx.testware.spi.metadata;

import net.microfalx.testware.api.annotation.Category;
import net.microfalx.testware.api.annotation.Description;
import net.microfalx.testware.api.annotation.Name;
import net.microfalx.testware.api.annotation.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Name("Class Descriptor")
@Description("The Class Descriptor")
@Tag({"t1", "t2"})
@Category("Class")
class ClassDescriptorTest extends BaseDescriptorTest {

    @Test
    @Name("Simple Create")
    @Description("Create a descriptor only with required values")
    void create() throws Exception {
        ClassDescriptor descriptor = ClassDescriptor.create(ClassDescriptorTest.class).build();
        assertNotNull(descriptor);
        assertSame(ClassDescriptorTest.class, descriptor.getTestClass());
    }

    @Test
    @Tag({"t3"})
    void tags() throws Exception {
        ClassDescriptor descriptor = ClassDescriptor.create(ClassDescriptorTest.class).tags("t4").build();
        Assertions.assertThat(descriptor.getTags()).containsOnly("b1", "b2", "t1", "t2", "t4");
    }

    @Test
    void isAnnotated() {
        ClassDescriptor descriptor = ClassDescriptor.create(ClassDescriptorTest.class).build();
        assertFalse(descriptor.isAnnotated(Test.class));
        assertTrue(descriptor.isAnnotated(Name.class));
    }

    @Test
    @Name("First Name")
    void findAnnotation() {
        ClassDescriptor descriptor = ClassDescriptor.create(ClassDescriptorTest.class).build();
        assertEquals("Class Descriptor", descriptor.findAnnotation(Name.class).get().value());
    }

    @Test
    @Name("First Name")
    void getAnnotation() {
        ClassDescriptor descriptor = ClassDescriptor.create(ClassDescriptorTest.class).build();
        Assertions.assertThat(descriptor.getAnnotations(Name.class).stream().map(Name::value))
                .containsExactly("Class Descriptor", "Base Descriptor");
    }

    @Test
    @Name("Display Name")
    void getDisplayName() {
        ClassDescriptor descriptor = ClassDescriptor.create(ClassDescriptorTest.class).build();
        assertEquals("Class Descriptor", descriptor.getDisplayName());
    }

    @Test
    void getCategory() {
        ClassDescriptor descriptor = ClassDescriptor.create(ClassDescriptorTest.class).build();
        assertEquals("Class", descriptor.getCategory());
    }

    @Test
    @Description("Get Description")
    void getDescription() {
        ClassDescriptor descriptor = ClassDescriptor.create(ClassDescriptorTest.class).build();
        assertEquals("The Class Descriptor", descriptor.getDescription());
    }

    @Test
    void toStringTest() {
        ClassDescriptor descriptor = ClassDescriptor.create(ClassDescriptorTest.class).build();
        assertNotNull(descriptor.toString());
    }

}