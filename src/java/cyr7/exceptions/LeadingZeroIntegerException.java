package cyr7.exceptions;

public class LeadingZeroIntegerException extends LexerException {

    /**
     *
     */
    private static final long serialVersionUID = 1968415907358215945L;

    public LeadingZeroIntegerException(String c, int line, int col) {
        super(String.format("Leading 0s detected: %s", c), line, col);
    }
}
