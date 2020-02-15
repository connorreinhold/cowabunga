package cyr7.exceptions;

public class ParserIntegerOverflowException extends ParserException  {

    /**
     *
     */
    private static final long serialVersionUID = 8308364919649807668L;

    public ParserIntegerOverflowException(int line, int col) {
        super(String.format("%d:%d error:%s", line + 1, col + 1, "Integer overflow."));
    }

}