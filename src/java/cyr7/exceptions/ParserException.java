package cyr7.exceptions;

import java_cup.runtime.ComplexSymbolFactory;

public class ParserException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 2782625958984643794L;
    
    public final int line;
    public final int col;
    
    public ParserException(String msg, int line, int col) {
        super(msg);
        this.col = col;
        this.line = line;        
    }

    public ParserException(String msg, ComplexSymbolFactory.Location location) {
        super(msg);
        this.col = location.getColumn();
        this.line = location.getLine();
    }

}
