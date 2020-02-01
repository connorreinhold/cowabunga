package cyr7.exceptions;

public class InvalidIdentifierException extends LexerException {

    /**
     *
     */
    private static final long serialVersionUID = -8216669670677839037L;

    public InvalidIdentifierException(String c, int line, int col) {
        super(String.format("Invalid identifier token: %s", c), line, col);
    }

}
