package cyr7.lexer;

import cyr7.exceptions.lexer.InvalidCharacterLiteralException;
import cyr7.parser.sym;
import java_cup.runtime.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCharacter {

    @Test
    void characterEscapingUnicode() throws Exception {
        MyLexer lexer = LexerFactory.make("'\\xAAAA'");
        Symbol token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\uAAAA", token.value);

        // Lowercase is just as good.
        lexer = LexerFactory.make("'\\xaaaa'");
        token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\uAAAA", token.value);
        

        // Lowercase is just as good.
        lexer = LexerFactory.make("'\\xAAaa'"); // \xAAAA
        token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\uAAAA", token.value);

        
        lexer = LexerFactory.make("'\\x123'"); // \x123
        token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\u0123", token.value);

        lexer = LexerFactory.make("'\\xFD'"); // \xFD
        token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\u00FD", token.value);

        lexer = LexerFactory.make("'\\x0'"); // \x0
        token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\u0000", token.value);
    }

    @Test
    void characterEscapingDoubleQuote() throws Exception {
        MyLexer lexer = LexerFactory.make("'\\\"'"); // \"
        Symbol token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\"", token.value);

        lexer = LexerFactory.make("'\"'"); // \"
        token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\"", token.value);
    }

    @Test
    void characterEscapingSingleQuote() throws Exception {
        MyLexer lexer = LexerFactory.make("'\\''"); // \'
        Symbol token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("'", token.value);
    }

    @Test
    void characterEscapingNewLine() throws Exception {
        MyLexer lexer = LexerFactory.make("'\\n'"); // \n
        Symbol token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\n", token.value);
    }

    @Test
    void characterInvalidEscaping() throws Exception {
        MyLexer lexer = LexerFactory.make("'\\x'"); // \x
        assertThrows(Exception.class, lexer::next_token);
        lexer = LexerFactory.make("'\\g'"); // \g
        assertThrows(Exception.class, lexer::next_token);
        lexer = LexerFactory.make("'\\b'"); // \b
        assertThrows(Exception.class, lexer::next_token);
    }

    @Test
    void characterEscapingBackslash() throws Exception {
        MyLexer lexer = LexerFactory.make("'\\\\'"); // \\
        Symbol token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\\", token.value);
    }

    @Test
    void characterInvalidFormat() throws Exception {
        // empty character literal
        MyLexer lexer = LexerFactory.make("''");
        assertThrows(InvalidCharacterLiteralException.class, lexer::next_token);

        // two line character literal
        lexer = LexerFactory.make("'as'");
        assertThrows(InvalidCharacterLiteralException.class, lexer::next_token);

        // outside of (unicode) BMP
        lexer = LexerFactory.make("\u583c\u5f46");
        assertThrows(Exception.class, lexer::next_token);
    }

    @Test
    void characterTabTest() throws Exception {
        MyLexer lexer = LexerFactory.make("'\t'");
        Symbol token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\t", token.value);
    }

}
