package cyr7.exceptions;

public class SyntaxException extends ParserException {

    /**
     * 
     */
    private static final long serialVersionUID = -3429789812268322044L;
    
    public SyntaxException(int line, int col) {
        super("Syntax Error.", line, col);
    }

}
