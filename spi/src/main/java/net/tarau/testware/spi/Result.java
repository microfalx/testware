package net.tarau.testware.spi;

import net.tarau.testware.api.Status;

import java.time.Duration;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public abstract class Result implements net.tarau.testware.api.Result {

    private Duration duration;
    private Status status;

    private String failureMessage;
    private Throwable throwable;

    @Override
    public final Duration getDuration() {
        return duration;
    }

    @Override
    public final Status getStatus() {
        return status;
    }

    @Override
    public final String getFailureMessage() {
        return failureMessage;
    }

    @Override
    public final Throwable getThrowable() {
        return throwable;
    }

    public static abstract class Builder<B extends Builder, R extends Result> {

        private Duration duration = Duration.ZERO;
        private Status status = Status.NA;
        private String failureMessage;
        private Throwable throwable;

        public B duration(Duration duration) {
            requireNonNull(duration);
            this.duration = duration;
            return (B) this;
        }

        public B status(Status status) {
            requireNonNull(status);
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

        protected abstract R createResult();

        protected abstract void updateResult(R result);

        public R build() {
            R result = createResult();
            updateResult(result);
            Result localResult = result;
            localResult.status = status;
            localResult.duration = duration;
            localResult.failureMessage = failureMessage;
            localResult.throwable = throwable;
            return result;
        }
    }
}
