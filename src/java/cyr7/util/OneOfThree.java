package cyr7.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class OneOfThree<T, U, V> {

    public static <T, U, V> OneOfThree<T, U, V> ofFirst(T first) {
        assert first != null;
        return new OneOfThree<>(first, null, null);
    }

    public static <T, U, V> OneOfThree<T, U, V> ofSecond(U second) {
        assert second != null;
        return new OneOfThree<>(null, second, null);
    }

    public static <T, U, V> OneOfThree<T, U, V> ofThird(V third) {
        assert third != null;
        return new OneOfThree<>(null, null, third);
    }

    private final Optional<T> first;
    private final Optional<U> second;
    private final Optional<V> third;

    protected OneOfThree(T first, U second, V third) {
        this.first = Optional.ofNullable(first);
        this.second = Optional.ofNullable(second);
        this.third = Optional.ofNullable(third);
    }

    public Optional<T> getFirst() {
        return first;
    }

    public Optional<U> getSecond() {
        return second;
    }

    public Optional<V> getThird() {
        return third;
    }

    public <W> W match(Function<T, W> mapFirst,
                       Function<U, W> mapSecond,
                       Function<V, W> mapThird) {
        return getFirst().map(mapFirst)
            .orElse(getSecond().map(mapSecond)
                .orElse(getThird().map(mapThird)
                    .orElseThrow()));
    }

    public void consume(Consumer<T> consumeFirst,
                        Consumer<U> consumeSecond, Consumer<V> consumeThird) {
        getFirst().ifPresentOrElse(
            consumeFirst,
            () -> getSecond().ifPresentOrElse(
                consumeSecond,
                () -> getThird().ifPresent(
                    consumeThird)));
    }

    public T assertFirst() {
        return first.get();
    }

    public U assertSecond() {
        return second.get();
    }

    public V assertThird() {
        return third.get();
    }

}
