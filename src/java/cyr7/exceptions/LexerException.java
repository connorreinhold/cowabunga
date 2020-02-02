package cyr7.exceptions;

import java.io.IOException;

public class LexerException extends IOException {

    /**
     *
     */
    private static final long serialVersionUID = 4008610117681931606L;

    public LexerException(String msg, int line, int col) {
        super(String.format("%d:%d error:%s", line + 1, col + 1, msg));
    }
}
