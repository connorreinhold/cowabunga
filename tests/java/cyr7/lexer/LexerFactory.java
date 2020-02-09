package cyr7.lexer;

import java_cup.runtime.ComplexSymbolFactory;

import java.io.StringReader;

public class LexerFactory {

    static public MyLexer make(String contents) {
        return new MyLexer(new StringReader(contents));
    }

    private LexerFactory() { }

}
