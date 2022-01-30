package net.tarau.testware.junit5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * An interceptor for JUnit 5 tests.
 * <p>
 * Junit5 needs to be executed with {@code -Djunit.jupiter.extensions.autodetection.enabled=true} to pick up the
 * interceptors.
 */
public class Junit5Interceptor implements InvocationInterceptor {

    @Override
    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        InvocationInterceptor.super.interceptTestMethod(invocation, invocationContext, extensionContext);
    }

    private void processFixture(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext,
                                ExtensionContext extensionContext) throws Throwable {
        final String uuid = UUID.randomUUID().toString();
        try {
            invocation.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
    }
}
