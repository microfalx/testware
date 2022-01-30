package net.tarau.testware.spi.util;

import net.tarau.resource.ResourceUtils;

/**
 * Exposes system properties to control the behaviour of features.
 * <p>
 * All configuration keys start with "testware".
 */
public class Configuration {

    /**
     * Returns a String configuration entry (under testware.*).
     *
     * @param key          the configuration key
     * @param defaultValue the default value if the value is NULL or EMPTY.
     * @return the configuration as String
     */
    public static String getString(String key, String defaultValue) {
        String value = System.getProperty("testware." + key);
        return ResourceUtils.defaultIfEmpty(value, defaultValue);
    }

    /**
     * Returns a Boolean configuration entry (under testware.*).
     *
     * @param key          the configuration key
     * @param defaultValue the default value if the value is NULL or EMPTY.
     * @return the configuration as String
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key, null);
        if (ResourceUtils.isEmpty(value)) {
            return defaultValue;
        } else {
            return "on".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value);
        }
    }

    /**
     * Returns a Boolean configuration entry (under testware.*).
     *
     * @param key          the configuration key
     * @param defaultValue the default value if the value is NULL or EMPTY.
     * @return the configuration as String
     */
    public static int getInteger(String key, int defaultValue) {
        String value = getString(key, null);
        if (ResourceUtils.isEmpty(value)) {
            return defaultValue;
        } else {
            return Integer.parseInt(value);
        }
    }
}
