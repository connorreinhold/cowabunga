package cyr7.lexer;

import java_cup.runtime.ComplexSymbolFactory;

import java.io.IOException;
import java.io.StringReader;

import cyr7.exceptions.LexerException;
public class LexerFactory {

    static public MyLexer make(String contents) throws Exception {
        MyLexer lexer = new MyLexer(new StringReader(contents), false);
        // When returning a Lexer for test cases, ignore the first token which is an XI file token
        lexer.next_token();
        return lexer;
    }

    private LexerFactory() { }

}
