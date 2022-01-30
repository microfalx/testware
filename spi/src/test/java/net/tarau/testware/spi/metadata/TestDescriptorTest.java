package net.tarau.testware.spi.metadata;

import net.tarau.testware.api.annotation.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Name("Test Descriptor")
@Issue({"i1"})
@Category("c1")
class TestDescriptorTest extends BaseDescriptorTest {

    @Test
    @Name("Simple Create")
    @Description("Create a descriptor only with required values")
    void create() throws Exception {
        TestDescriptor descriptor = create("create").build();
        assertNotNull(descriptor);
        assertSame(TestDescriptorTest.class, descriptor.getClassDescriptor().get().getTestClass());
        assertSame("create", descriptor.getMethodDescriptor().get().getTestMethod().getName());
    }

    @Test
    @Tag({"t1", "t2"})
    void tags() throws Exception {
        TestDescriptor descriptor = create("tags").tags("t3").build();
        Assertions.assertThat(descriptor.getTags()).containsOnly("b1", "b2", "t1", "t2", "t3");
    }

    @Test
    @Name("Display Name")
    void getDisplayName() throws Exception {
        TestDescriptor descriptor = create("getDisplayName").build();
        assertEquals("Display Name", descriptor.getDisplayName());
    }

    @Test
    @Description("The Get Description")
    void getDescription() throws Exception {
        TestDescriptor descriptor = create("getDescription").build();
        assertEquals("The Get Description", descriptor.getDescription());
    }

    @Test
    @Description("The Get Description")
    @Issue({"i2", "i3"})
    @Bug
    void getIssues() throws Exception {
        TestDescriptor descriptor = create("getIssues").build();
        Assertions.assertThat(descriptor.getIssues()).containsOnly("i0", "i1", "i2", "i3");
        assertTrue(descriptor.isBug());
    }

    @Test
    void getModule() throws Exception {
        TestDescriptor descriptor = create("getIssues").module("m1").build();
        assertEquals("m1", descriptor.getModule());
    }

    @Test
    void getCategory() throws Exception {
        TestDescriptor descriptor = create("getIssues").build();
        assertEquals("c1", descriptor.getCategory());
    }

    @Test
    void toStringTest() throws Exception {
        TestDescriptor descriptor = create("toStringTest").build();
        assertNotNull(descriptor.toString());
    }

    private TestDescriptor.Builder create(String methodName) throws Exception {
        return TestDescriptor.create(ClassDescriptor.create(TestDescriptorTest.class).build(),
                MethodDescriptor.create(TestDescriptorTest.class.getDeclaredMethod(methodName)).build());
    }

}