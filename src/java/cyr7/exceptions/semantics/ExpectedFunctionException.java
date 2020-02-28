package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class ExpectedFunctionException extends SemanticException {

    /**
     *
     */
    private static final long serialVersionUID = 4593503080403812072L;

    public ExpectedFunctionException(Location loc) {
        super("Expected function call", loc);
    }

}
