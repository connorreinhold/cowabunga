package cyr7.x86.pattern;

import java.util.Optional;
import java.util.function.Function;

public class BiPatternBuilder<L, R> {

    private static final Function<Object, Boolean> trueFunction = x -> true;

    public static Left<Object> left() {
        return new Left<>(trueFunction);
    }

    private final Function<Object, Boolean> left;
    private final Function<Object, Boolean> right;

    private L leftObj;
    private R rightObj;
    private boolean commutes = false;

    BiPatternBuilder(Right<L, R> right) {
        this.left = right.left.predicate;
        this.right = right.predicate;
    }

    public BiPatternBuilder(Function<Object, Boolean> left,
                            Function<Object, Boolean> right) {
        this.left = left;
        this.right = right;
    }

    public BiPatternBuilder<L, R> enableCommutes() {
        commutes = true;
        return this;
    }

    public boolean matchesOpts(Optional<? super L> lOpt, Optional<? super R> rOpt) {
        if (lOpt.isEmpty() || rOpt.isEmpty()) {
            return false;
        }

        Object l = lOpt.get(), r = rOpt.get();

        boolean result = left.apply(l) && right.apply(r);
        if (result) {
            leftObj = (L) l;
            rightObj = (R) r;
        }

        if (!commutes) {
            return result;
        }

        result = right.apply(l) && left.apply(r);
        if (result) {
            leftObj = (L) r;
            rightObj = (R) l;
        }

        return result;
    }

    public L leftObj() {
        assert leftObj != null;
        return leftObj;
    }

    public R rightObj() {
        assert rightObj != null;
        return rightObj;
    }

    public static class Left<L1> {

        final Function<Object, Boolean> predicate;

        private Left(Function<Object, Boolean> predicate) {
            this.predicate = predicate;
        }

        public <R1> Right<L1, R1> right() {
            return new Right<>(this, trueFunction);
        }

        public Left<L1> and(Function<L1, Boolean> clause) {
            return new Left<L1>(
                x -> predicate.apply(x) && clause.apply((L1) x));
        }

        public <U extends L1> Left<U> instOf(Class<U> uClass) {
            return new Left<U>(
                x -> predicate.apply(x) && uClass.isInstance(x));
        }

    }

    public static class Right<L1, R1> {

        final Left<L1> left;

        final Function<Object, Boolean> predicate;

        private Right(Left<L1> left, Function<Object, Boolean> predicate) {
            this.left = left;
            this.predicate = predicate;
        }

        public BiPatternBuilder<L1, R1> finish() {
            return new BiPatternBuilder<>(this);
        }

        public Right<L1, R1> and(Function<R1, Boolean> clause) {
            return new Right<L1, R1>(left,
                x -> predicate.apply(x) && clause.apply((R1) x));
        }

        public <U extends R1> Right<L1, U> instOf(Class<U> uClass) {
            return new Right<L1, U>(left,
                x -> predicate.apply(x) && uClass.isInstance(x));
        }

    }

}
