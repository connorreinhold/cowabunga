package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class EarlyReturnException extends SemanticException {

    /**
     * 
     */
    private static final long serialVersionUID = 7713637890221889005L;

    public EarlyReturnException(Location loc) {
        super("Early return statement", loc);
    }

}
