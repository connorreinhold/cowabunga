package cyr7.exceptions;

public class InvalidTokenException extends LexerException {

    /**
     * 
     */
    private static final long serialVersionUID = 8452045191222781323L;

    public InvalidTokenException(String c, int line, int col) {
	super(String.format("Invalid Token: %s", c), line, col);
    }

}
