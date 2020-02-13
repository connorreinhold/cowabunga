package cyr7.exceptions;

public class NonTerminatingCharacterException extends LexerException {

    /**
     * 
     */
    private static final long serialVersionUID = 2203922588906288312L;

    public NonTerminatingCharacterException(String msg, int line, int col) {
        super("Character does not terminate.", line, col);
    }

}
