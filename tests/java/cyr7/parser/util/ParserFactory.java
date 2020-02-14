package cyr7.parser.util;

import java_cup.runtime.ComplexSymbolFactory;

import java.io.IOException;
import java.io.StringReader;

import cyr7.exceptions.LexerException;
import cyr7.lexer.MultiFileLexer;
import cyr7.lexer.MyLexer;
import cyr7.parser.XiParser;
public class ParserFactory {

    static public XiParser make(String contents, boolean isIxi) 
            throws Exception {
        MyLexer lexer = new MultiFileLexer(new StringReader(contents), isIxi);
        XiParser parser = new XiParser(lexer, new ComplexSymbolFactory());
        return parser;
    }

    private ParserFactory() { }

}
