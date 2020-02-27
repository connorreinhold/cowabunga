package cyr7.exceptions;

public class NonTerminatingStringException extends LexerException {

    /**
     * 
     */
    private static final long serialVersionUID = 2203922588906288312L;

    public NonTerminatingStringException(String msg, int line, int col, 
            String filename) {
        super("String does not terminate.", line, col, filename);
    }

}
