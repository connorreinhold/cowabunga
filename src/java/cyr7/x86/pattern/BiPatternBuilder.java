package cyr7.x86.pattern;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import cyr7.x86.tiler.BasicTiler;

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
    private L1 preMapLeft;
    private R1 preMapRight;
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

    public boolean matches(Object preL, Object preR, boolean recurse) {
        Optional<Object> postL = leftMapping.apply(preL), postR = rightMapping.apply(preR);
        if (postL.isEmpty() || postR.isEmpty()) {
            return false;
        }
        Object l = postL.get(), r = postR.get();

        boolean result;
        result = left.apply(l) && right.apply(r);
        if (result) {
            preMapLeft = (L1) preL;
            preMapRight = (R1) preR;

            leftObj = (L) l;
            rightObj = (R) r;
            return true;
        }

        if (commutes && recurse) {
            return matches(preR, preL, false);
        } else {
            return false;
        }
    }

    public <L2> BiPatternBuilder<L, R, L2, R1> mappingLeft(Class<L2> l1Class, Function<L2, ? super L> map) {
        return new BiPatternBuilder<>(
            left,
            right,
            x -> l1Class.isInstance(x) ? Optional.of(map.apply((L2) x)) : Optional.empty(),
            rightMapping);
    }

//    public <L2, Const> BiPatternBuilder<L, R, L2, R1> mappingLeft(Class<L2> r1Class, Const userData, BiFunction<L2, Const, ? super R> map) {
//        return new BiPatternBuilder<>(
//            left,
//            right,
//            leftMapping,
//            x -> r1Class.isInstance(x) ? Optional.of(map.apply((L2) x, userData)) : Optional.empty());
//    }
    
    public <R2> BiPatternBuilder<L, R, L1, R2> mappingRight(Class<R2> r1Class, Function<R2, ? super R> map) {
        return new BiPatternBuilder<>(
            left,
            right,
            leftMapping,
            x -> r1Class.isInstance(x) ? Optional.of(map.apply((R2) x)) : Optional.empty());
    }
    
//    public <R2, Const> BiPatternBuilder<L, R, L1, R2> mappingRight(Class<R2> r1Class, Const userData, BiFunction<R2, Const, ? super R> map) {
//        return new BiPatternBuilder<>(
//            left,
//            right,
//            leftMapping,
//            x -> r1Class.isInstance(x) ? Optional.of(map.apply((R2) x, userData)) : Optional.empty());
//    }
    
    @Override
    public boolean matches(Object[] objs) {
        return objs.length == 2 && matches(objs[0], objs[1], true);
    }

    public L leftObj() {
        assert leftObj != null;
        return leftObj;
    }

    public R rightObj() {
        assert rightObj != null;
        return rightObj;
    }

    public L1 preMapLeft() {
        assert preMapLeft != null;
        return preMapLeft;
    }

    public R1 preMapRight() {
        assert preMapRight != null;
        return preMapRight;
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
