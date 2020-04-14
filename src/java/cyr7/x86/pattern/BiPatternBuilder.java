package cyr7.x86.pattern;

import java.util.Optional;
import java.util.function.Function;

public class BiPatternBuilder<L, R, L1, R1> implements Pattern {

    private static final Function<Object, Boolean> trueFunction = x -> true;

    public static Left<Object> left() {
        return new Left<>(trueFunction);
    }

    private final Function<Object, Boolean> left;
    private final Function<Object, Boolean> right;

    private final Function<Object, Optional<Object>> leftMapping;
    private final Function<Object, Optional<Object>> rightMapping;

    private L leftObj;
    private R rightObj;
    private boolean commutes = false;

    public BiPatternBuilder(Function<Object, Boolean> left,
                            Function<Object, Boolean> right,
                            Function<Object, Optional<Object>> leftMapping,
                            Function<Object, Optional<Object>> rightMapping) {
        this.left = left;
        this.right = right;
        this.leftMapping = leftMapping;
        this.rightMapping = rightMapping;
    }

    public BiPatternBuilder<L, R, L1, R1> enableCommutes() {
        commutes = true;
        return this;
    }

    public boolean matches(Object preL, Object preR) {
        Optional<Object> postL = leftMapping.apply(preL), postR = rightMapping.apply(preR);
        if (postL.isEmpty() || postR.isEmpty()) {
            return false;
        }

        Object l = postL.get(), r = postR.get();

        boolean result;
        result = left.apply(l) && right.apply(r);
        if (result) {
            leftObj = (L) l;
            rightObj = (R) r;
            return true;
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

    public <L2> BiPatternBuilder<L, R, L2, R1> mappingLeft(Class<L2> l1Class, Function<L2, ? super L> map) {
        return new BiPatternBuilder<>(
            left,
            right,
            x -> l1Class.isInstance(x) ? Optional.of(map.apply((L2) x)) : Optional.empty(),
            rightMapping);
    }

    public <R2> BiPatternBuilder<L, R, L1, R2> mappingRight(Class<R2> r1Class, Function<R2, ? super R> map) {
        return new BiPatternBuilder<>(
            left,
            right,
            leftMapping,
            x -> r1Class.isInstance(x) ? Optional.of(map.apply((R2) x)) : Optional.empty());
    }

    @Override
    public boolean matches(Object[] objs) {
        return objs.length == 2 && matches(objs[0], objs[1]);
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

        public BiPatternBuilder<L1, R1, L1, R1> finish() {
            return new BiPatternBuilder<>(left.predicate, this.predicate, Optional::of, Optional::of);
        }

        public Right<L1, R1> and(Function<R1, Boolean> clause) {
            return new Right<>(left,
                x -> predicate.apply(x) && clause.apply((R1) x));
        }

        public <U extends R1> Right<L1, U> instOf(Class<U> uClass) {
            return new Right<>(left,
                x -> predicate.apply(x) && uClass.isInstance(x));
        }

    }

}
