package net.tarau.testware.junit5;

import net.tarau.testware.api.Hook;
import net.tarau.testware.api.metadata.ClassDescriptor;
import net.tarau.testware.junit5.annotation.*;
import net.tarau.testware.spi.Test;
import net.tarau.testware.spi.metadata.TestDescriptor;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.time.Duration;

/**
 * An interceptor for JUnit 5 tests.
 * <p>
 * Junit5 needs to be executed with {@code -Djunit.jupiter.extensions.autodetection.enabled=true} to pick up the
 * interceptors.
 */
public class Junit5Interceptor implements InvocationInterceptor {

    @Override
    public <T> T interceptTestClassConstructor(Invocation<T> invocation, ReflectiveInvocationContext<Constructor<T>> invocationContext, ExtensionContext extensionContext) throws Throwable {
        return processHook(Hook.Type.CONSTRUCTOR, invocation, invocationContext, extensionContext);
    }

    @Override
    public void interceptBeforeAllMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        processHook(Hook.Type.BEFORE_ALL, invocation, invocationContext, extensionContext);
    }

    @Override
    public void interceptBeforeEachMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        processHook(Hook.Type.BEFORE_EACH, invocation, invocationContext, extensionContext);
    }

    @Override
    public void interceptAfterEachMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        processHook(Hook.Type.AFTER_EACH, invocation, invocationContext, extensionContext);
    }

    @Override
    public void interceptAfterAllMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        processHook(Hook.Type.AFTER_ALL, invocation, invocationContext, extensionContext);
    }

    @Override
    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        processTest(invocation, invocationContext, extensionContext);
    }

    private <T> T processHook(Hook.Type type, Invocation<T> invocation,
                              ReflectiveInvocationContext<? extends Executable> invocationContext,
                              ExtensionContext extensionContext) throws Throwable {
        long startTime = System.nanoTime();
        try {
            return invocation.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            long duration = System.nanoTime() - startTime;
            net.tarau.testware.spi.Hook.Builder builder = net.tarau.testware.spi.Hook.create(Junit5Tracker.getMethodDescriptor(extensionContext, invocationContext.getExecutable())).type(type);
            builder.duration(Duration.ofNanos(duration));
            Junit5Tracker.registerHook(builder.build());
        }
    }

    private net.tarau.testware.api.Test.Type getType(ClassDescriptor classDescriptor) {
        if (classDescriptor.isAnnotated(UnitTest.class)) {
            return net.tarau.testware.api.Test.Type.UNIT;
        } else if (classDescriptor.isAnnotated(IntegrationTest.class)) {
            return net.tarau.testware.api.Test.Type.INTEGRATION;
        } else if (classDescriptor.isAnnotated(PerformanceTest.class)) {
            return net.tarau.testware.api.Test.Type.PERFORMANCE;
        } else if (classDescriptor.isAnnotated(FunctionalTest.class)) {
            return net.tarau.testware.api.Test.Type.FUNCTIONAL;
        } else if (classDescriptor.isAnnotated(End2EndTest.class)) {
            return net.tarau.testware.api.Test.Type.E2E;
        } else {
            return net.tarau.testware.api.Test.Type.UNIT;
        }
    }

    private Test.Builder createTest(ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) {
        TestDescriptor testDescriptor = Junit5Tracker.getTestDescriptor(extensionContext, invocationContext.getExecutable());
        Test.Builder builder = Test.create(testDescriptor);
        if (testDescriptor.getClassDescriptor().isPresent()) {
            builder.type(getType(testDescriptor.getClassDescriptor().get()));
        }
        return builder;
    }

    private <T> T processTest(Invocation<T> invocation,
                              ReflectiveInvocationContext<Method> invocationContext,
                              ExtensionContext extensionContext) throws Throwable {
        long startTime = System.nanoTime();
        try {
            return invocation.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            long duration = System.nanoTime() - startTime;
            Test.Builder builder = createTest(invocationContext, extensionContext);
            builder.duration(Duration.ofNanos(duration));
            Junit5Tracker.registerTest(builder.build());
        }
    }
}
