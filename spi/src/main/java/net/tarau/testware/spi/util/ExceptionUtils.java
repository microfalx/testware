package net.tarau.testware.spi.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

    /**
     * Extracts the stack trace from an exception.
     *
     * @param throwable the exception
     * @return a non-null instance
     */
    public static String getStackTrace(Throwable throwable) {
        if (throwable == null) return null;
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        return writer.toString();
    }
}
