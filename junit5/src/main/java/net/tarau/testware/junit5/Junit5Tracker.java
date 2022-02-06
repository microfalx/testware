package net.tarau.testware.junit5;

import net.tarau.resource.FileResource;
import net.tarau.testware.core.model.ForkModel;
import net.tarau.testware.core.model.TestModel;
import net.tarau.testware.core.repository.ResourceRepository;
import net.tarau.testware.spi.Hook;
import net.tarau.testware.spi.Test;
import net.tarau.testware.spi.metadata.ClassDescriptor;
import net.tarau.testware.spi.metadata.MethodDescriptor;
import net.tarau.testware.spi.metadata.TestDescriptor;
import net.tarau.testware.spi.util.FileUtils;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public class Junit5Tracker {

    private static Logger LOGGER = Logger.getLogger(Junit5Tracker.class.getName());

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

    static MethodDescriptor getMethodDescriptor(ExtensionContext extensionContext, Executable executable) {
        requireNonNull(executable);
        MethodDescriptor.Builder builder = MethodDescriptor.create(getClassDescriptor(extensionContext), executable);
        builder.displayName(extensionContext.getDisplayName())
                .tags(extensionContext.getTags());
        return builder.build();
    }

    static TestDescriptor getTestDescriptor(ExtensionContext extensionContext, Executable executable) {
        requireNonNull(extensionContext);
        TestDescriptor.Builder builder = TestDescriptor.create(getClassDescriptor(extensionContext),
                getMethodDescriptor(extensionContext, executable));
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
                if (hook.getType().isBefore()) continue;
                current = current.withHook(hook);
            }
            tests.remove(current);
            tests.add(current);
        }
        for (Hook hook : currentHooks.get()) {
            if (!hook.getType().isBefore()) continue;
            test = test.withHook(hook);
        }
        currentHooks.remove();
        tests.add(test);
        currentTest.set(test);
    }

    static void persist() {
        ForkModel forkModel = new ForkModel();
        for (Test test : tests) {
            forkModel.add(TestModel.from(test));
        }
        ResourceRepository repository = new ResourceRepository(FileResource.directory(FileUtils.getLocalRepository()));
        try {
            repository.store(forkModel);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to persist test data", e);
        }
    }

    static class ShutdownThread extends Thread {

        public ShutdownThread() {
            setName("TestWare JUnit5 Tracker");
        }

        @Override
        public void run() {
            persist();
        }
    }
}
