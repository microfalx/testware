package net.tarau.testware.reporting.template;

import net.tarau.binserde.utils.ArgumentUtils;

/**
 * Holds a page inside a site.
 */
public class Page {

    private final Site site;
    private final String id;

    public Page(Site site, String id) {
        ArgumentUtils.requireNonNull(site);
        ArgumentUtils.requireNonNull(id);

        this.site = site;
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public String getId() {
        return id;
    }
}
