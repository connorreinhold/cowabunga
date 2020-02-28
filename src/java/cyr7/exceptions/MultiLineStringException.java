package cyr7.exceptions;

public class MultiLineStringException extends LexerException {

    /**
     *
     */
    private static final long serialVersionUID = -6135026520877262066L;

    public MultiLineStringException(int line, int col, String filename) {
        super("Strings cannot span multiple lines.", line, col, filename);
    }

}
