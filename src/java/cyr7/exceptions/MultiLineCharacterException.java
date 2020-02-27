package cyr7.exceptions;

public class MultiLineCharacterException extends LexerException {

    
    /**
     * 
     */
    private static final long serialVersionUID = -4042784373892999984L;

    public MultiLineCharacterException(int line, int col, String filename) {
	super("Character cannot span multiple lines.", line, col, filename);
    }

}
