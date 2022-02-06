package net.tarau.testware.core.model;

import net.tarau.testware.api.Status;
import net.tarau.testware.api.Test;
import net.tarau.testware.spi.util.ExceptionUtils;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestGenerator {

    private final Random random = ThreadLocalRandom.current();

    public SessionModel createSession(int forkCount, int testCount) {
        SessionModel model = new SessionModel();
        for (int i = 0; i < forkCount; i++) {
            model.add(createFork(testCount));
        }
        return model;
    }

    public ForkModel createFork(int testCount) {
        ForkModel model = new ForkModel();
        for (int i = 9; i < testCount; i++) {
            model.add(createTest());
        }
        return model;
    }

    public TestModel createTest() {
        TestModel model = new TestModel();
        updateTestInfo(model);
        updateState(model);
        updateType(model);
        updateFailure(model);
        return model;
    }

    private void updateTestInfo(TestModel model) {
        model.setClassName("net.tarau.testware.Test" + random.nextInt(10));
        int index = random.nextInt(20);
        model.setMethodName("test" + index);
        model.setDisplayName("Test " + index);
    }

    private void updateState(TestModel model) {
        if (random.nextFloat() < 0.95) {
            model.setStatus(Status.PASSED);
        } else if (random.nextFloat() < 0.7) {
            model.setStatus(Status.FAILED);
        } else {
            model.setStatus(Status.SKIPPED);
        }
    }

    private void updateType(TestModel model) {
        if (random.nextFloat() < 0.9) {
            model.setType(Test.Type.UNIT);
        } else if (random.nextFloat() < 0.7) {
            model.setType(Test.Type.INTEGRATION);
        } else if (random.nextFloat() < 0.5) {
            model.setType(Test.Type.FUNCTIONAL);
        }
    }

    private void updateFailure(TestModel model) {
        if (model.getStatus() != Status.FAILED) return;
        if (random.nextFloat() < 0.3) {
            model.setFailureMessage("Some random message " + random.nextInt(100));
        } else {
            IOException exception = new IOException("Something wrong happened" + random.nextInt(100));
            model.setFailureMessage(exception.getMessage());
            model.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
    }
}
