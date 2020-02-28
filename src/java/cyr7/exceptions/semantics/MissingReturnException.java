package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class MissingReturnException extends SemanticException {

    /**
     *
     */
    private static final long serialVersionUID = 3051927779516284008L;

    public MissingReturnException(Location loc) {
        super("Missing return statement", loc);
    }

}
