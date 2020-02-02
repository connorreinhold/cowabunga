package cyr7.lexer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class LexerUtilTest {

    @Test
    void testTypeAttributeDescriptionExist() {
        for (MyLexer.TokenType tokenType : MyLexer.TokenType.values()) {
            MyLexer lexer = new MyLexer(new StringReader(""));
            String description = LexerUtil.typeAttributeDescription(lexer.new Token(tokenType, "blah"));
            assertNotNull(description);
            assertFalse(description.isEmpty());
        }
    }

    @Test
    void testBooleanLiteralDescription() {
        MyLexer lexer = new MyLexer(new StringReader(""));
        MyLexer.Token token = lexer.new Token(MyLexer.TokenType.BOOL_LITERAL, true);
        assertEquals("1:1 true", LexerUtil.fullDescription(token));

        token = lexer.new Token(MyLexer.TokenType.BOOL_LITERAL, false);
        assertEquals("1:1 false", LexerUtil.fullDescription(token));
    }

    @Test
    void testEscapedNewlineIsUnescaped() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("\"Hello, Worl\\x64!\\n\""));
        MyLexer.Token token = lexer.nextToken();
        assertEquals("1:1 string Hello, World!\\n", LexerUtil.fullDescription(token));

        lexer = new MyLexer(new StringReader("\" \\n \\r \\f \\t \"")); // " \n \r \f \t "
        token = lexer.nextToken();
        assertEquals("1:1 string  \\n \\r \\f \\t ", LexerUtil.fullDescription(token));

        lexer = new MyLexer(new StringReader("'\\n'"));
        token = lexer.nextToken();
        assertEquals("1:1 character \\n", LexerUtil.fullDescription(token));

        lexer = new MyLexer(new StringReader("'\\r'"));
        token = lexer.nextToken();
        assertEquals("1:1 character \\r", LexerUtil.fullDescription(token));

        lexer = new MyLexer(new StringReader("'\\f'"));
        token = lexer.nextToken();
        assertEquals("1:1 character \\f", LexerUtil.fullDescription(token));

        lexer = new MyLexer(new StringReader("'\\t'"));
        token = lexer.nextToken();
        assertEquals("1:1 character \\t", LexerUtil.fullDescription(token));
    }

    @Test
    void testEscapedVisiblesAreEscaped() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("\"\\t\\''\\\"\"")); // "\t''\""
        MyLexer.Token token = lexer.nextToken();
        assertEquals("1:1 string \\t''\"", LexerUtil.fullDescription(token));

        lexer = new MyLexer(new StringReader("'\\''"));
        token = lexer.nextToken();
        assertEquals("1:1 character '", LexerUtil.fullDescription(token));

        lexer = new MyLexer(new StringReader("'\\\"'"));
        token = lexer.nextToken();
        assertEquals("1:1 character \"", LexerUtil.fullDescription(token));
    }

}
