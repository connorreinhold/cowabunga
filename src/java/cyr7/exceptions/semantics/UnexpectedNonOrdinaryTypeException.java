package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class UnexpectedNonOrdinaryTypeException extends SemanticException {

    /**
     * 
     */
    private static final long serialVersionUID = -6130982998996069299L;

    public UnexpectedNonOrdinaryTypeException(Location loc) {
        super("Expression does not evaluate to an int, bool, or an array", loc);
    }

}
