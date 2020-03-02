package cyr7.exceptions.semantics;

import cyr7.semantics.types.ExpandedType;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class UncomparableValuesException extends SemanticException {

    /**
     *
     */
    private static final long serialVersionUID = -583689414053689624L;

    public UncomparableValuesException(ExpandedType lhs,
                                       ExpandedType rhs, Location loc) {
        super(String.format("Cannot compare %s with %s", lhs, rhs), loc);
    }

}
