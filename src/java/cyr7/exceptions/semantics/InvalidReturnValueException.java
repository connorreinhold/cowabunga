package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class InvalidReturnValueException extends SemanticException {

    /**
     * 
     */
    private static final long serialVersionUID = -1050219666188619273L;

    public InvalidReturnValueException(Location loc) {
        super("Attempted to return a value that does not evaluate to an"
                + " int, bool, or an array.", loc);
    }

}
