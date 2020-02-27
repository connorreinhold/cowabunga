package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class ReturnValueInUnitFunctionException extends SemanticException {

    /**
     * 
     */
    private static final long serialVersionUID = 8044524915458298142L;

    public ReturnValueInUnitFunctionException(Location loc) {
        super("Attempted to return a value in a function that returns unit", 
                loc);
    }

}
