package cyr7.exceptions.lexer;

public class LexerException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 4008610117681931606L;
    public final int line;
    public final int col;
    public final String errorMsg;
    public final String filename;

    LexerException(String msg, int line, int col, String filename) {
        super(String.format("%d:%d error:%s", line + 1, col + 1, msg));
        this.line = line;
        this.col = col;
        this.errorMsg = msg;
        this.filename = filename;
    }
}
