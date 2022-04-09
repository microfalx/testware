package net.microfalx.testware.reporting.template;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the site for a project.
 */
public class Site {

    private Navigation navigation = new Navigation();
    private Map<String, Page> pages = new HashMap<>();

    /**
     * Returns the navigation.
     *
     * @return a non-null instance
     */
    public Navigation getNavigation() {
        return navigation;
    }

    /**
     * Returns the pages.
     *
     * @return a non-null instance
     */
    public Map<String, Page> getPages() {
        return Collections.unmodifiableMap(pages);
    }
}
