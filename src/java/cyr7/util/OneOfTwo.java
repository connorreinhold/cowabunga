package cyr7.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public final class OneOfTwo<T, U> {

    public static <T, U> OneOfTwo<T, U> ofFirst(T first) {
        assert first != null;
        return new OneOfTwo<>(first, null);
    }

    public static <T, U> OneOfTwo<T, U> ofSecond(U second) {
        assert second != null;
        return new OneOfTwo<>(null, second);
    }

    private final Optional<T> first;
    private final Optional<U> second;

    private OneOfTwo(T first, U second) {
        this.first = Optional.ofNullable(first);
        this.second = Optional.ofNullable(second);
    }

    public Optional<T> getFirst() {
        return first;
    }

    public Optional<U> getSecond() {
        return second;
    }

    public <W> W match(Function<T, W> mapFirst, Function<U, W> mapSecond) {
        return getFirst().map(mapFirst)
            .orElse(getSecond().map(mapSecond).orElseThrow());
    }

    public void consume(Consumer<T> consumeFirst, Consumer<U> consumeSecond) {
        getFirst().ifPresentOrElse(
            consumeFirst,
            () -> getSecond().ifPresent(consumeSecond));
    }

    public T assertFirst() {
        return first.get();
    }

    public U assertSecond() {
        return second.get();
    }

}
