package cyr7.util;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class OneOrTwo<L> implements Iterable<L> {

    private final L first;
    private final Optional<L> second;

    public OneOrTwo(L first, Optional<L> second) {
        this.first = first;
        this.second = second;
    }

    public OneOrTwo(L first) {
        this.first = first;
        this.second = Optional.empty();
    }

    public OneOrTwo(L first, L second) {
        this.first = first;
        this.second = Optional.of(second);
    }

    public L first() {
        return first;
    }

    public Optional<L> second() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneOrTwo<?> oneOrTwo = (OneOrTwo<?>) o;
        return Objects.equals(first, oneOrTwo.first) &&
            Objects.equals(second, oneOrTwo.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        if (second.isEmpty()) {
            return "(" + first + ")";
        } else {
            return "(" + first + ", " + second.get() + ")";
        }
    }

    @Override
    public Iterator<L> iterator() {
        return asList().iterator();
    }

    public List<L> asList() {
        if (second.isEmpty()) {
            return List.of(first);
        } else {
            return List.of(first, second.get());
        }
    }

}
