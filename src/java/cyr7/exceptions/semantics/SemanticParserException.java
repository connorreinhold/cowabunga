package cyr7.exceptions.semantics;

import cyr7.exceptions.ParserException;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class SemanticParserException extends SemanticException {

    public SemanticParserException(ParserException e) {
        super(e.errorMsg, new Location(e.filename, e.line, e.column));
    }

    public SemanticParserException(Exception e, Location location) {
        super(e.getMessage(), location);
    }

}
