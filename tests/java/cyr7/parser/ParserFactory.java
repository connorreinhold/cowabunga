package cyr7.parser;

import cyr7.lexer.MultiFileLexer;
import cyr7.lexer.MyLexer;
import java_cup.runtime.ComplexSymbolFactory;

import java.io.StringReader;

public class ParserFactory {

    static public XiParser make(String contents, boolean isIxi)
            throws Exception {
        MyLexer lexer = new MultiFileLexer(new StringReader(contents), isIxi);
        return new XiParser(lexer, new ComplexSymbolFactory());
    }

    private ParserFactory() { }

}
