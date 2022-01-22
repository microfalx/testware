package net.tarau.testware.api.metadata;

import java.util.Set;

/**
 * Base interface for all descriptors supporting metadata.
 */
public interface MetadataAwareDescriptor {

    /**
     * Returns the display name in the context of the current descriptor.
     *
     * @return a non-empty String
     */
    String getDisplayName();

    /**
     * Returns the descriptions  in the context of the current descriptor.
     *
     * @return a non-empty String if a description is defined, null otherise
     */
    String getDescription();

    /**
     * Returns the category.
     *
     * @return the category, null if no category is available
     */
    String getCategory();

    /**
     * Returns the set of all tags in the context of the current descriptor.
     *
     * <p>Tags may be declared directly on the test element or <em>inherited</em>
     * from an outer context.
     */
    Set<String> getTags();
}
