package cyr7.exceptions.parser;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class ParserException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 2782625958984643794L;

    public final int line, column;
    public final String errorMsg;
    public final String filename;

    ParserException(String msg, Location loc) {
        super(String.format("%d:%d error:%s", loc.getLine(),
            loc.getColumn(), msg));
        this.line = loc.getLine();
        this.column = loc.getColumn();
        this.filename = loc.getUnit();
        this.errorMsg = msg;
    }

}
