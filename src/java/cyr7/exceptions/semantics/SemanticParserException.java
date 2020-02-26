package cyr7.exceptions.semantics;

import cyr7.exceptions.ParserException;
import java_cup.runtime.ComplexSymbolFactory;

public class SemanticParserException extends SemanticException {

    public SemanticParserException(ParserException e) {
        super(e.getMessage(), new ComplexSymbolFactory.Location(e.line.get(), e.column.get()));
    }

    public SemanticParserException(Exception e, ComplexSymbolFactory.Location location) {
        super(e.getMessage(), location);
    }

}
