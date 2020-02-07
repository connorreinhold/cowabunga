package cyr7.exceptions;

import java.io.IOException;

public class SyntaxException extends CompilerException {

    /**
     * 
     */
    private static final long serialVersionUID = -3429789812268322044L;
    
    public SyntaxException(int line, int col) {
        super("Syntax Error.", line, col);
    }

}
