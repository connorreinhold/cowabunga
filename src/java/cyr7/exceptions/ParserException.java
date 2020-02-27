package cyr7.exceptions;

import java.util.Optional;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class ParserException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 2782625958984643794L;

    public final int line, column;
    public final String errorMsg;
    public final String filename;
    
    public ParserException(String msg) {
        super(String.format("%d:%d error:%s", -1, -1, msg));
        this.line = -1;
        this.column = -1;
        this.errorMsg = msg;
        this.filename = null;
    }

    public ParserException(String msg, Location loc) {
        super(String.format("%d:%d error:%s", loc.getLine(), 
                loc.getColumn(), msg));
        this.line = loc.getLine();
        this.column = loc.getColumn();
        this.filename = loc.getUnit();
        this.errorMsg = msg;
    }

}
