package cyr7.exceptions;

public class ParserIntegerOverflowException extends LexerException {

    /**
     * 
     */
    private static final long serialVersionUID = 8308364919649807668L;

    public ParserIntegerOverflowException(int line, int col) { 
        super("Integer overflow.", line, col);
    }
    
}
