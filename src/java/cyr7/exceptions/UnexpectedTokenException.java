package cyr7.exceptions;

import cyr7.lexer.LexerUtil;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

public class UnexpectedTokenException extends ParserException {

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
