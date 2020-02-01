package cyr7.exceptions;

import java.io.IOException;

public class InvalidCharacterException extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = 8214275233613349494L;

    public InvalidCharacterException(String c, int line, int column) {
	super(String.format("%d:%d error: Illegal character constant: %s",
		line + 1, column, c));
    }

}
