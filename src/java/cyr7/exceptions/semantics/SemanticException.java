package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class SemanticException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -2906712701112586812L;
    public final int line;
    public final int col;
    public final String errorMsg;
    public final String filename;
    
    protected SemanticException(String msg, Location loc) {
        super(String.format("%d:%d error:%s", 
                loc.getLine(), loc.getColumn(), msg));
        this.line = loc.getLine();
        this.col = loc.getColumn();
        this.errorMsg = msg;
        this.filename = loc.getUnit();
    }
}