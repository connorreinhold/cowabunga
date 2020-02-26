package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class DuplicateIdentifierException extends SemanticException {

    /**
     * 
     */
    private static final long serialVersionUID = 4160668270275922381L;

    public DuplicateIdentifierException(String id, Location loc) {
        super(String.format("Duplicate identifier `%s`", id), loc);
    }
    
}
