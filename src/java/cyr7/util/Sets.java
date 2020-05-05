package cyr7.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Sets {

    public static <T> Set<T> union(Collection<? extends T> lhs, Collection<? extends T> rhs) {
        return Stream.concat(lhs.stream(), rhs.stream()).collect(Collectors.toSet());
    }

    public static <T> Set<T> difference(Collection<? extends T> lhs, Collection<? extends T> rhs) {
        Set<T> elements = new HashSet<>(lhs);
        elements.removeAll(rhs);
        return elements;
    }

    public static <T> Set<T> intersection(Collection<? extends T> lhs, Collection<? extends T> rhs) {
        HashSet<T> set = new HashSet<>(lhs);
        set.retainAll(rhs);
        return set;
    }

    private Sets() { }

}
