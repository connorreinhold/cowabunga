package cyr7.exceptions;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class ParserIntegerOverflowException extends ParserException  {

    /**
     *
     */
    private static final long serialVersionUID = 8308364919649807668L;

    public ParserIntegerOverflowException(Location loc) {
        super("Integer overflow", loc);
    }

}