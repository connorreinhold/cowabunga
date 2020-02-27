package cyr7.exceptions;

import cyr7.lexer.LexerUtil;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

public class UnexpectedTokenException extends ParserException {

    /**
     * 
     */
    private static final long serialVersionUID = -5207509401617912010L;

    public UnexpectedTokenException(ComplexSymbol symbol) {
        super(
            String.format("%d:%d error:%s",
                symbol.xleft.getLine(),
                symbol.xleft.getColumn(),
                "Unexpected token "
                    + LexerUtil.symbolDescription(symbol.sym, symbol.value)),
            symbol.xleft
        );
    }
}
