package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory;

public class MissingReturnException extends SemanticException {

    public MissingReturnException(ComplexSymbolFactory.Location loc) {
        super("Missing return statement", loc);
    }

}
