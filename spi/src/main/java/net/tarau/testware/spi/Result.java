package net.tarau.testware.spi;

import net.tarau.binserde.utils.ArgumentUtils;
import net.tarau.testware.api.Status;

public abstract class Result implements net.tarau.testware.api.Result {

    private Status status;

    private String failureMessage;
    private Throwable throwable;

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getFailureMessage() {
        return failureMessage;
    }

    @Override
    public Throwable getThrowable() {
        return throwable;
    }

    public static abstract class Builder<B extends Builder, R extends Result> {

        private Status status;
        private String failureMessage;
        private Throwable throwable;

        public B status(Status status) {
            ArgumentUtils.requireNonNull(status);
            this.status = status;
            return (B) this;
        }

        public B failureMessage(String failureMessage) {
            this.failureMessage = failureMessage;
            return (B) this;
        }

        public B throwable(Throwable throwable) {
            this.throwable = throwable;
            return (B) this;
        }
    }
}
