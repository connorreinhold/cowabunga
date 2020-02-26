package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class UnboundIdentifierException extends SemanticException {

    /**
     * 
     */
    private static final long serialVersionUID = 557039212300917232L;

    public UnboundIdentifierException(String id, Location loc) {
        super(String.format("Unbound identifier `%s`", id), loc);
    }

}
