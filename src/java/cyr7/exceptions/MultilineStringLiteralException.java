package cyr7.exceptions;

public class MultilineStringLiteralException extends LexerException {

    public MultilineStringLiteralException(int line, int col) {
        super("Illegal line end in string literal", line, col);
    }

}
