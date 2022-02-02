package net.tarau.testware.junit5;

import net.tarau.testware.spi.Hook;
import net.tarau.testware.spi.Test;
import net.tarau.testware.spi.metadata.ClassDescriptor;
import net.tarau.testware.spi.metadata.MethodDescriptor;
import net.tarau.testware.spi.metadata.TestDescriptor;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public class Junit5Tracker {

    private static final Map<Class<?>, ClassDescriptor> classDescriptors = new ConcurrentHashMap<>();
    private static final Collection<Test> tests = new ConcurrentLinkedDeque<>();
    private static volatile Thread shutdownThread;
    private static volatile ThreadLocal<Collection<Hook>> currentHooks = ThreadLocal.withInitial(ArrayList::new);
    private static volatile ThreadLocal<Test> currentTest = new ThreadLocal<>();

    static ClassDescriptor getClassDescriptor(ExtensionContext extensionContext) {
        requireNonNull(extensionContext);
        synchronized (Junit5Tracker.class) {
            if (shutdownThread == null) {
                shutdownThread = new ShutdownThread();
                Runtime.getRuntime().addShutdownHook(shutdownThread);
            }
        }
        return classDescriptors.computeIfAbsent(extensionContext.getRequiredTestClass(), c -> {
            ClassDescriptor.Builder builder = ClassDescriptor.create(c);
            builder.displayName(extensionContext.getDisplayName())
                    .tags(extensionContext.getTags());
            return builder.build();
        });
    }

    static MethodDescriptor getMethodDescriptor(ExtensionContext extensionContext) {
        requireNonNull(extensionContext);
        MethodDescriptor.Builder builder = MethodDescriptor.create(extensionContext.getRequiredTestMethod());
        builder.displayName(extensionContext.getDisplayName())
                .tags(extensionContext.getTags());
        return builder.build();
    }

    static TestDescriptor getTestDescriptor(ExtensionContext extensionContext) {
        requireNonNull(extensionContext);
        TestDescriptor.Builder builder = TestDescriptor.create(getClassDescriptor(extensionContext), getMethodDescriptor(extensionContext));
        return builder.build();
    }

    static void registerHook(Hook hook) {
        requireNonNull(hook);
        currentHooks.get().add(hook);
    }

    static void registerTest(Test test) {
        requireNonNull(test);
        Test current = currentTest.get();
        if (current != null) {
            for (Hook hook : currentHooks.get()) {
                current = current.withHook(hook);
            }
            tests.remove(current);
            tests.add(current);
        }
        currentHooks.remove();
        tests.add(test);
        currentTest.set(test);
    }

    static class ShutdownThread extends Thread {

        public ShutdownThread() {
            setName("TestWare JUnit5 Tracker");
        }

        @Override
        public void run() {

        }
    }
}
