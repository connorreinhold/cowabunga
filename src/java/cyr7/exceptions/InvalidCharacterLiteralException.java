package cyr7.exceptions;

import java.io.IOException;

public class InvalidCharacterLiteralException extends LexerException {

    /**
     * 
     */
    private static final long serialVersionUID = 8214275233613349494L;

    public InvalidCharacterLiteralException(String c, int line, int column) {
	super(String.format("Illegal character constant: %s", c), 
		line, column);
    }

}
