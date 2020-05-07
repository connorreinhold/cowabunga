package cyr7.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class Sets {

    public static <T> Set<T> union(Set<? extends T> lhs, Set<? extends T> rhs) {
        return Stream.concat(lhs.stream(), rhs.stream()).collect(Collectors.toSet());
    }
    
    public static <T> Set<T> intersection(Set<? extends T> lhs, Set<? extends T> rhs) {
        Set<T> elements = new HashSet<>(lhs);
        elements.retainAll(rhs);
        return elements;
    }

    public static <T> Set<T> difference(Set<T> lhs, Set<T> rhs) {
        Set<T> elements = new HashSet<>(lhs);
        elements.removeAll(rhs);
        return elements;
    }

    private Sets() { }
}


