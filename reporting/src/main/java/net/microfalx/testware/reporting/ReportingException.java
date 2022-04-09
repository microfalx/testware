package net.microfalx.testware.reporting;

public class ReportingException extends RuntimeException{

    public ReportingException(String message) {
        super(message);
    }

    public ReportingException(String message, Throwable cause) {
        super(message, cause);
    }
}
