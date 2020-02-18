package cyr7.semantics;

public enum ResultType {
	VOID,
	UNIT;
	
	public static ResultType leastUpperBound(ResultType r1, ResultType r2) {
        if (r1 == UNIT || r2 == UNIT) {
            return UNIT;
        } else {
            return VOID;
        }
	}
}
