package cyr7.exceptions.parser;

import cyr7.lexer.LexerUtil;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

public class UnexpectedTokenException extends ParserException {

    /**
     *
     */
    private static final long serialVersionUID = -5207509401617912010L;

    public UnexpectedTokenException(ComplexSymbol symbol) {
        super("Unexpected token "
                + LexerUtil.symbolDescription(symbol.sym, symbol.value),
                symbol.xleft);
    }
}
