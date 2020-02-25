package cyr7.exceptions.semantics;

import cyr7.semantics.ExpandedType;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class TypeMismatchException extends SemanticException {
    
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public TypeMismatchException(ExpandedType actual, 
            ExpandedType expected, Location loc) {
        super(String.format("Cannot convert from %s to %s", 
                actual, expected), loc);
    }
}
