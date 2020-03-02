package cyr7.lexer;

import java.io.StringReader;

public class LexerFactory {

    static public MyLexer make(String contents) throws Exception {
        MyLexer lexer = new MultiFileLexer(new StringReader(contents), "", false);
        // When returning a Lexer for test cases, ignore the first token which is an XI file token
        lexer.next_token();
        return lexer;
    }

    private LexerFactory() { }

}
