package cyr7.exceptions.lexer;

public class InvalidStringEscapeCharacterException extends LexerException {

    /**
     *
     */
    private static final long serialVersionUID = 1299252579294475465L;
    private static final String[] validEscapes = new String[]
        {"\\n", "\\t", "\\r", "\\f", "\\\"", "\\\\", "\\x[0000-FFFF]"};

    private static final String validEscapeMsg = String.format(
        "(valid escape characters are: %s)",
        String.join(", ", validEscapes));

    public InvalidStringEscapeCharacterException(String c, int line,
                                                 int column, String filename) {
        super(String.format("Illegal character constant: %s %s.",
            c, validEscapeMsg), line, column, filename);
    }
}
