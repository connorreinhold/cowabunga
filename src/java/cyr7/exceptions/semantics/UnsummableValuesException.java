package cyr7.exceptions.semantics;

import cyr7.semantics.ExpandedType;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class UnsummableValuesException extends SemanticException {

    /**
     *
     */
    private static final long serialVersionUID = -2008717210227627823L;

    public UnsummableValuesException(ExpandedType lhs, ExpandedType rhs,
                                     Location loc) {
        super(String.format("Cannot add %s with %s", lhs, rhs), loc);
    }


}
