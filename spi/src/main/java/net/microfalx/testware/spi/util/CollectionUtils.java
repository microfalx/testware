package net.microfalx.testware.spi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.Collections.*;

public class CollectionUtils {

    public static <T> Collection<T> immutable(Collection<T> collection) {
        return collection != null ? unmodifiableCollection(collection) : emptyList();
    }

    public static <T> Collection<T> required(Collection<T> collection) {
        return collection != null ? collection : new ArrayList<>();
    }

    public static <T> List<T> immutable(List<T> list) {
        return list != null ? unmodifiableList(list) : emptyList();
    }

    public static <T> Set<T> immutable(Set<T> list) {
        return list != null ? unmodifiableSet(list) : emptySet();
    }
}
