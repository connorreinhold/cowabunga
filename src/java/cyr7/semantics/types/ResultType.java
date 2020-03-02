package cyr7.semantics.types;

public enum ResultType {
    VOID,
    UNIT;

    /**
     * Returns the least-upper-bound result type, which is defined to be
     * {@code UNIT} if either {@code r1} or {@code r2} is {@code UNIT}.
     * <p>
     * Otherwise, it is defined to be {@code VOID}.
     */
    public static ResultType leastUpperBound(ResultType r1, ResultType r2) {
        if (r1 == UNIT || r2 == UNIT) {
            return UNIT;
        } else {
            return VOID;
        }
    }

}