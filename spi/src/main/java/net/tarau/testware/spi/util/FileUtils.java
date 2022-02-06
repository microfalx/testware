package net.tarau.testware.spi.util;

import net.tarau.binserde.utils.ArgumentUtils;
import net.tarau.resource.ResourceUtils;

import java.io.File;

/**
 * Various utilities around file system.
 */
public class FileUtils {

    public static final String LOCAL_REPO_NAME = "teamware";

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

    /**
     * Checks if the parent directory of the file exists and if not it creates it.
     *
     * @param file the file
     * @return same file after check
     */
    public static File checkFile(File file) {
        ArgumentUtils.requireNonNull(file);

        checkDirectory(file.getParentFile());
        return file;
    }

    /**
     * Checks if the  directory and if not it creates it.
     *
     * @param directory the file
     * @return same file after check
     */
    public static File checkDirectory(File directory) {
        ArgumentUtils.requireNonNull(directory);

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IllegalArgumentException("The directory is not valid, cannot be created");
            }
        }
        return directory;
    }

    /**
     * Returns the directory which will store test reports.
     *
     * @return a non-null instance
     */
    public static File getLocalRepository() {
        return checkDirectory(new File(getBuildDirectory(), LOCAL_REPO_NAME));
    }

    /**
     * Returns the build directory.
     *
     * @return a non-null instance
     */
    public static File getBuildDirectory() {
        String mavenBuild = System.getenv("MAVEN_BUILD");
        if (ResourceUtils.isEmpty(mavenBuild)) {
            File target = new File(FileUtils.getWorkingDirectory(), "target");
            if (target.exists()) return target;
            File builder = new File(FileUtils.getWorkingDirectory(), "build");
            if (target.exists()) return target;
            throw new IllegalStateException("Cannot detect build directory");
        } else {
            return new File(mavenBuild);
        }
    }


}
