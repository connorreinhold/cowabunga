package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory;

public class ArrayTypeExpectedException extends SemanticException {

    /**
     *
     */
    private static final long serialVersionUID = -6130982998996069299L;

    public ArrayTypeExpectedException(ComplexSymbolFactory.Location loc) {
        super("Expected an array type.", loc);
    }

}