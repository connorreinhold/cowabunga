package cyr7.exceptions.lexer;

public class InvalidCharacterLiteralException extends LexerException {

    /**
     *
     */
    private static final long serialVersionUID = 8214275233613349494L;

    public InvalidCharacterLiteralException(String c, int line, int column,
                                            String filename) {
        super(String.format("Illegal character constant: %s", c),
            line, column, filename);
    }

}
