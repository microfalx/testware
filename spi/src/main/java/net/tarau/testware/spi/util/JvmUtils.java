package net.tarau.testware.spi.util;

import net.tarau.resource.ResourceUtils;

import java.io.File;

public class JvmUtils {

    /**
     * Returns the working directory.
     *
     * @return a non-null instance
     */
    public static File getWorkingDirectory() {
        String value = System.getProperty("user.dir");
        if (ResourceUtils.isEmpty(value)) {
            value = System.getProperty("user.home");
        }
        return new File(value);
    }
}
