package net.tarau.testware.reporting.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Holds the document navigation.
 */
public class Navigation {

    private static Navigation instance = new Navigation();

    private Collection<Item> items = new ArrayList<>();

    public static Navigation getInstance() {
        return instance;
    }

    public Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items);
    }

    public Item addItem(String label, String link) {
        Item item = new Item(label, link);
        items.add(item);
        return item;
    }

    public static class Item {
        private String label;
        private String href;

        public Item(String label, String href) {
            this.label = label;
            this.href = href;
        }

        public String getLabel() {
            return label;
        }

        public String getHref() {
            return href;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "label='" + label + '\'' +
                    ", href='" + href + '\'' +
                    '}';
        }
    }
}
