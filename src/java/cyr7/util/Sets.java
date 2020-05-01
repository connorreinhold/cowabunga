package cyr7.util;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Sets {

    public static Set<String> union(Set<String> lhs, Set<String> rhs) {
        return Stream.concat(lhs.stream(), rhs.stream()).collect(Collectors.toSet());
    }

    private Sets() { }

}
