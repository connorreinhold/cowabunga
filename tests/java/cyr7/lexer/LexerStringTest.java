package cyr7.lexer;

import cyr7.exceptions.InvalidStringEscapeCharacterException;
import cyr7.parser.xi.sym;
import java_cup.runtime.Symbol;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LexerStringTest {

    @Test
    void stringWithNewlineThrowsError() throws Exception {

        // single string with newline
        MyLexer lexer = LexerFactory.make("\"\n\"");
        assertThrows(Exception.class, lexer::next_token);

        lexer = LexerFactory.make("\"blah blah \n\"");
        assertThrows(Exception.class, lexer::next_token);
    }

    @Test
    void stringWithTabs() throws Exception {
        MyLexer lexer = LexerFactory.make("\"\t\t\t\"");
        Symbol token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\t\t\t", token.value);
    }

    @Test
    void stringEscapingUnicode() throws Exception {
        // \xAAAA
        MyLexer lexer = LexerFactory.make("\"\\xAAAA\"");
        Symbol token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\uAAAA", token.value);

        lexer = LexerFactory.make("\"\\x123\""); // \x123
        token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\u0123", token.value);

        lexer = LexerFactory.make("\"\\xFD\""); // \xFD
        token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\u00FD", token.value);

        lexer = LexerFactory.make("\"\\x0\""); // \x0
        token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\u0000", token.value);
    }

    @Test
    void stringEscapingDoubleQuote() throws Exception {
        MyLexer lexer = LexerFactory.make("\"\\\"\""); // \"
        Symbol token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\"", token.value);
    }

    @Test
    void stringEscapingSingleQuote() throws Exception {
        MyLexer lexer = LexerFactory.make("\"\\'\""); // \'
        Symbol token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("'", token.value);

        lexer = LexerFactory.make("\"'\""); // \'
        token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("'", token.value);
    }

    @Test
    void stringEscapingBackslash() throws Exception {
        MyLexer lexer = LexerFactory.make("\"\\\\\""); // \\
        Symbol token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\\", token.value);
    }

    @Test
    void stringEscapingNewLine() throws Exception {
        MyLexer lexer = LexerFactory.make("\"\\n\""); // \n
        Symbol token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\n", token.value);
    }

    @Test
    void stringInvalidEscaping() throws Exception {
        MyLexer lexer = LexerFactory.make("\"\\x\""); // \x
        assertThrows(InvalidStringEscapeCharacterException.class,
                lexer::next_token);
        lexer = LexerFactory.make("\"asdf\\g\""); // \g
        assertThrows(InvalidStringEscapeCharacterException.class,
                lexer::next_token);
        lexer = LexerFactory.make("\"\\b1324\""); // \b
        assertThrows(InvalidStringEscapeCharacterException.class,
                lexer::next_token);
    }

    @Test
    void stringEmpty() throws Exception {
        MyLexer lexer = LexerFactory.make("\"\""); // ""
        Symbol token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("", token.value);
    }

}
