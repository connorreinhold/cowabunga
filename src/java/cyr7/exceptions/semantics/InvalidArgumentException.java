package cyr7.exceptions.semantics;

import cyr7.semantics.ExpandedType;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class InvalidArgumentException extends SemanticException {

    /**
     * 
     */
    private static final long serialVersionUID = -1732185540588971719L;

    public InvalidArgumentException(ExpandedType actual, 
            ExpandedType expected, Location loc) {
        super(String.format("Found type %s for argument, but type %s "
                + "was expected", actual, expected), loc);
    }

}
