package cyr7.exceptions;

public class LexerIntegerOverflowException extends LexerException {

    /**
     *
     */
    private static final long serialVersionUID = 8308364919649807668L;

    public LexerIntegerOverflowException(String c, int line, int col,
                                         String filename) {
        super(
            String.format("Exceptionally large integer literal detected: %s",
                c),
            line, col, filename);
    }

}
