package cyr7.lexer;

import cyr7.exceptions.InvalidStringEscapeCharacterException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LexerStringTest {

    @Test
    void stringWithNewlineThrowsError() throws IOException {

        // single string with newline
        MyLexer lexer = new MyLexer(new StringReader("\"\n\""));
        assertThrows(Exception.class, lexer::nextToken);

        lexer = new MyLexer(new StringReader("\"blah blah \n\""));
        assertThrows(Exception.class, lexer::nextToken);
    }

    @Test
    void stringWithTabs() throws Exception {
        MyLexer lexer = new MyLexer(new StringReader("\"\t\t\t\""));
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("\t\t\t", token.attribute);
    }

    @Test
    void stringEscapingUnicode() throws Exception {
        // \xAAAA
        MyLexer lexer = new MyLexer(new StringReader("\"\\xAAAA\""));
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("\uAAAA", token.attribute);

        lexer = new MyLexer(new StringReader("\"\\x123\"")); // \x123
        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("\u0123", token.attribute);

        lexer = new MyLexer(new StringReader("\"\\xFD\"")); // \xFD
        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("\u00FD", token.attribute);

        lexer = new MyLexer(new StringReader("\"\\x0\"")); // \x0
        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("\u0000", token.attribute);
    }

    @Test
    void stringEscapingDoubleQuote() throws Exception {
        MyLexer lexer = new MyLexer(new StringReader("\"\\\"\"")); // \"
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("\"", token.attribute);
    }

    @Test
    void stringEscapingSingleQuote() throws Exception {
        MyLexer lexer = new MyLexer(new StringReader("\"\\'\"")); // \'
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("'", token.attribute);

        lexer = new MyLexer(new StringReader("\"'\"")); // \'
        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("'", token.attribute);
    }

    @Test
    void stringEscapingBackslash() throws Exception {
        MyLexer lexer = new MyLexer(new StringReader("\"\\\\\"")); // \\
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("\\", token.attribute);
    }

    @Test
    void stringEscapingNewLine() throws Exception {
        MyLexer lexer = new MyLexer(new StringReader("\"\\n\"")); // \n
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("\n", token.attribute);
    }

    @Test
    void stringInvalidEscaping() {
        MyLexer lexer = new MyLexer(new StringReader("\"\\x\"")); // \x
        assertThrows(InvalidStringEscapeCharacterException.class,
                lexer::nextToken);
        lexer = new MyLexer(new StringReader("\"asdf\\g\"")); // \g
        assertThrows(InvalidStringEscapeCharacterException.class,
                lexer::nextToken);
        lexer = new MyLexer(new StringReader("\"\\b1324\"")); // \b
        assertThrows(InvalidStringEscapeCharacterException.class,
                lexer::nextToken);
    }

    @Test
    void stringEmpty() throws Exception {
        MyLexer lexer = new MyLexer(new StringReader("\"\"")); // ""
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("", token.attribute);
    }

}
