package cyr7.exceptions;

import java_cup.runtime.ComplexSymbolFactory;

import java.util.Optional;

public class ParserException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 2782625958984643794L;

    public final Optional<Integer> line, column;

    public ParserException(String msg) {
        super(msg);

        this.line = Optional.empty();
        this.column = Optional.empty();
    }

    public ParserException(String msg, int line, int column) {
        super(msg);

        this.line = Optional.of(line);
        this.column = Optional.of(column);
    }

}
