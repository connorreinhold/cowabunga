package cyr7.x86.pattern;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SeqPatternBuilder<T> implements Pattern {

    private static final Function<Object, Boolean> trueFunction = x -> true;

    public static SeqPatternBuilder<Object> first() {
        return new SeqPatternBuilder<>(Optional.empty(), trueFunction);
    }

    final Optional<SeqPatternBuilder<?>> prev;
    private final Function<Object, Boolean> predicate;

    private SeqPatternBuilder(
        Optional<SeqPatternBuilder<?>> prev,
        Function<Object, Boolean> predicate) {

        this.prev = prev;
        this.predicate = predicate;
    }

    public SeqPatternBuilder<T> and(Function<T, Boolean> clause) {
        return new SeqPatternBuilder<>(prev,
            x -> predicate.apply(x) && clause.apply((T) x));
    }

    public <U extends T> SeqPatternBuilder<U> instOf(Class<U> uClass) {
        return new SeqPatternBuilder<>(prev,
            x -> predicate.apply(x) && uClass.isInstance(x));
    }

    public SeqPatternBuilder<Object> next() {
        return new SeqPatternBuilder<>(Optional.of(this), trueFunction);
    }

    @Override
    public boolean matches(Object[] objs) {
        return accepts(new LinkedList<>(List.of(objs)));
    }

    private boolean accepts(LinkedList<Object> objects) {
        if (objects.isEmpty() && prev.isEmpty()) {
            return true;
        } else if (objects.isEmpty() || prev.isEmpty()) {
            return false;
        }

        Object last = objects.removeLast();
        return prev.get().accepts(objects) && predicate.apply(last);
    }

}
