package cyr7.exceptions;

public class ParserException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 2782625958984643794L;
    
    final private int line;
    final private int col;
    
    public ParserException(String msg, int line, int col) {
        super(msg);
        this.col = col;
        this.line = line;        
    }
    
}