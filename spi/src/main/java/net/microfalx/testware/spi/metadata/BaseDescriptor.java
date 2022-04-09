package net.microfalx.testware.spi.metadata;

import net.microfalx.testware.api.metadata.MetadataAwareDescriptor;

import java.util.*;

import static net.microfalx.binserde.utils.ArgumentUtils.requireNonNull;

public abstract class BaseDescriptor extends AnnotationDescriptor implements MetadataAwareDescriptor {

    private String displayName;
    private String description;
    private String category;
    private String module;
    private Set<String> tags;

    public final String getDisplayName() {
        return displayName;
    }

    @Override
    public final String getDescription() {
        return description;
    }

    @Override
    public String getModule() {
        return module;
    }

    @Override
    public final String getCategory() {
        return category;
    }

    @Override
    public final Set<String> getTags() {
        return tags != null ? Collections.unmodifiableSet(tags) : Collections.emptySet();
    }

    @Override
    public String toString() {
        return "BaseDescriptor{" +
                "displayName='" + displayName + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", tags=" + tags +
                "} " + super.toString();
    }

    public static abstract class Builder<B extends Builder, D extends AnnotationDescriptor> extends AnnotationDescriptor.Builder<B, D> {

        private String displayName;
        private String description;
        private String module;
        private String category;
        private Set<String> tags;

        public B displayName(String displayName) {
            this.displayName = displayName;
            return (B) this;
        }

        public B description(String description) {
            this.description = description;
            return (B) this;
        }

        public B category(String category) {
            this.category = category;
            return (B) this;
        }

        public B module(String module) {
            this.module = module;
            return (B) this;
        }

        public B tags(String... tags) {
            return tags(Arrays.asList(tags));
        }

        public B tags(Collection<String> tags) {
            requireNonNull(tags);
            if (this.tags == null) {
                this.tags = new HashSet<>();
            }
            this.tags.addAll(tags);
            return (B) this;
        }

        @Override
        protected void updateDescriptor(D descriptor) {
            BaseDescriptor baseDescriptor = (BaseDescriptor) descriptor;
            baseDescriptor.displayName = displayName;
            baseDescriptor.description = description;
            baseDescriptor.category = category;
            baseDescriptor.module = module;
            baseDescriptor.tags = tags;
        }
    }
}
