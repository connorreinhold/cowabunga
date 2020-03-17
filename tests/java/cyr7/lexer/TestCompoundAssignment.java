package cyr7.lexer;

import java_cup.runtime.Symbol;
import org.junit.jupiter.api.Test;

import cyr7.exceptions.lexer.InvalidCharacterLiteralException;
import cyr7.exceptions.lexer.InvalidStringEscapeCharacterException;
import cyr7.exceptions.lexer.InvalidTokenException;
import cyr7.exceptions.lexer.LexerIntegerOverflowException;
import cyr7.exceptions.lexer.MultiLineCharacterException;
import cyr7.exceptions.lexer.MultiLineStringException;
import cyr7.parser.sym;

import static org.junit.jupiter.api.Assertions.*;

class TestCompoundAssignment {

    @Test
    void doesNotBreakLexer() throws Exception {
        MyLexer lexer = LexerFactory.make("##");
        assertThrows(InvalidTokenException.class, lexer::next_token);

        lexer = LexerFactory.make("\"		\"");
        assertEquals("\t\t", lexer.next_token().value);

        lexer = LexerFactory.make("\"\n\n\nHello World\"");
        assertThrows(MultiLineStringException.class, lexer::next_token);

        lexer = LexerFactory.make("\'\n\'"); // Characters cannot
                                                         // span multiple lines.
        assertThrows(MultiLineCharacterException.class, lexer::next_token);

        lexer = LexerFactory.make("'\r\n'"); // Characters cannot
                                                         // span multiple lines.
        assertThrows(MultiLineCharacterException.class, lexer::next_token);

        lexer = LexerFactory.make("'\r'"); // Characters cannot span
                                                       // multiple lines.
        assertThrows(MultiLineCharacterException.class, lexer::next_token);

        lexer = LexerFactory.make("\'\\xAFFFF\'"); // Characters
                                                               // cannot span
                                                               // multiple
                                                               // lines.
        assertThrows(InvalidCharacterLiteralException.class, lexer::next_token);

        lexer = LexerFactory.make("'\f'"); // Characters cannot span
                                                       // multiple lines.
        assertDoesNotThrow(lexer::next_token);
    }

    @Test
    void highMultAndGreaterThan() throws Exception {
        MyLexer lexer = LexerFactory.make("*>>>");
        Symbol token;

        token = lexer.next_token();
        assertEquals(sym.HIGH_MULT, token.sym);

        token = lexer.next_token();
        assertEquals(sym.GT, token.sym);
    }

    @Test
    void multAndHighMult() throws Exception {
        MyLexer lexer = LexerFactory.make("***>>>*>*>>*>>");
        assertEquals(sym.MULT, lexer.next_token().sym);
        assertEquals(sym.MULT, lexer.next_token().sym);
        assertEquals(sym.HIGH_MULT, lexer.next_token().sym);
        assertEquals(sym.GT, lexer.next_token().sym);
        assertEquals(sym.MULT, lexer.next_token().sym);
        assertEquals(sym.GT, lexer.next_token().sym);
        assertEquals(sym.HIGH_MULT, lexer.next_token().sym);
        assertEquals(sym.HIGH_MULT, lexer.next_token().sym);
    }

    @Test
    void lessThanEqualsGreaterThan() throws Exception {
        MyLexer lexer = LexerFactory.make("<<=>>=");
        assertEquals(sym.LT, lexer.next_token().sym);
        assertEquals(sym.LTE, lexer.next_token().sym);
        assertEquals(sym.GT, lexer.next_token().sym);
        assertEquals(sym.GTE, lexer.next_token().sym);
    }

    @Test
    void equalsNotEquals() throws Exception {
        MyLexer lexer = LexerFactory.make("=!!====");
        assertEquals(sym.ASSIGN, lexer.next_token().sym);
        assertEquals(sym.NEG_BOOL, lexer.next_token().sym);
        assertEquals(sym.NOT_EQUALS, lexer.next_token().sym);
        assertEquals(sym.EQUALS, lexer.next_token().sym);
        assertEquals(sym.ASSIGN, lexer.next_token().sym);
    }

    @Test
    void minusAndNegativeIntLiteral() throws Exception {
        MyLexer lexer = LexerFactory.make("1234---5-10");
        Symbol token;

        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("1234", token.value);

        token = lexer.next_token();
        assertEquals(sym.MINUS, token.sym);

        token = lexer.next_token();
        assertEquals(sym.MINUS, token.sym);

        // see insertionsort.xi for why this should be minus token and int
        // literal instead of negative int literal.

        token = lexer.next_token();
        assertEquals(sym.MINUS, token.sym);

        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("5", token.value);

        token = lexer.next_token();
        assertEquals(sym.MINUS, token.sym);

        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("10", token.value);
    }

    @Test
    void commentSingleLine() throws Exception {
        MyLexer lexer = LexerFactory.make(
                "// this is a comment 'a' \" testing \" have a good day");
        assertEquals(sym.EOF, lexer.next_token().sym);
    }

    @Test
    void idThenComment() throws Exception {
        MyLexer lexer = LexerFactory.make(
                "1234 // this is a comment have a good day");

        Symbol token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("1234", token.value);

        assertEquals(sym.EOF, lexer.next_token().sym);
    }

    @Test
    void commentMultipleLines() throws Exception {
        MyLexer lexer = LexerFactory.make(
                "// this is a comment have a good day\n"
                        + "1234 asdf // whatever\n"
                        + "while () // blkj \t asdf\n" + "if use\n"
                        + "++ \"// comment // inside a string\" // comment\n");

        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);
        assertEquals(sym.ID, lexer.next_token().sym);

        assertEquals(sym.WHILE, lexer.next_token().sym);
        assertEquals(sym.L_PAREN, lexer.next_token().sym);
        assertEquals(sym.R_PAREN, lexer.next_token().sym);

        assertEquals(sym.IF, lexer.next_token().sym);
        assertEquals(sym.USE, lexer.next_token().sym);

        assertEquals(sym.PLUS, lexer.next_token().sym);
        assertEquals(sym.PLUS, lexer.next_token().sym);
    }

    @Test
    void commentDifferentLineEndings() throws Exception {
        MyLexer lexer = LexerFactory.make(
                "// this is a comment have a good day\r"
                        + "1234 asdf // whatever\n"
                        + "while () // blkj \t asdf\r\n" + "if use\n"
                        + "++ \"// comment // inside a string\" // comment\n");

        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);
        assertEquals(sym.ID, lexer.next_token().sym);

        assertEquals(sym.WHILE, lexer.next_token().sym);
        assertEquals(sym.L_PAREN, lexer.next_token().sym);
        assertEquals(sym.R_PAREN, lexer.next_token().sym);

        assertEquals(sym.IF, lexer.next_token().sym);
        assertEquals(sym.USE, lexer.next_token().sym);

        assertEquals(sym.PLUS, lexer.next_token().sym);
        assertEquals(sym.PLUS, lexer.next_token().sym);
    }

    @Test
    void integerOverflow() throws Exception {
        String veryBigNumber = "99999999999999999999999";
        MyLexer lexer = LexerFactory.make(veryBigNumber);
        Symbol token;
        assertThrows(LexerIntegerOverflowException.class, lexer::next_token);

        lexer = LexerFactory.make("" + Integer.MAX_VALUE);
        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals(String.valueOf(Integer.MAX_VALUE), token.value);

        lexer = LexerFactory.make("" + Integer.MIN_VALUE);
        token = lexer.next_token();
        assertEquals(sym.MINUS, token.sym);
        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals(String.valueOf(Integer.MIN_VALUE)
                           .substring(1), token.value);
    }

    @Test
    void integerEdgeCases() throws Exception{
        String illegalInt = "0010";
        MyLexer lexer = LexerFactory.make(illegalInt);
        Symbol token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("0", token.value.toString());
        
        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("0", token.value.toString());
        
        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("10", token.value.toString());
        
        lexer = LexerFactory.make("00");
        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);
        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);
    }

    @Test
    void stringEscapingUnicode() throws Exception {
        MyLexer lexer = LexerFactory.make("\"\\xAAAA\""); // \xAAAA
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
        MyLexer lexer = LexerFactory.make("\"\\\'\""); // \'
        Symbol token = lexer.next_token();
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

    @Test
    void characterEscapingUnicode() throws Exception {
        MyLexer lexer = LexerFactory.make("'\\xAAAA'"); // \xAAAA
        Symbol token = lexer.next_token();
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
    }

    @Test
    void characterEscapingSingleQuote() throws Exception {
        MyLexer lexer = LexerFactory.make("'\\\''"); // \'
        Symbol token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("\'", token.value);
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
        lexer = LexerFactory.make("\'\u583c\u5f46\'");
        assertThrows(InvalidCharacterLiteralException.class, lexer::next_token);
    }

    @Test
    void identifierAndCharacter() throws Exception {
        MyLexer lexer = LexerFactory.make("blip'a'asdf");
        Symbol token;

        token = lexer.next_token();
        assertEquals(sym.ID, token.sym);
        assertEquals("blip'a'asdf", token.value);

        assertEquals(sym.EOF, lexer.next_token().sym);
    }

    @Test
    void functionCallTest() throws Exception {
        MyLexer lexer = LexerFactory.make("william(-5) + '我'");
        Symbol token;

        token = lexer.next_token();
        assertEquals(sym.ID, token.sym);
        assertEquals("william", token.value);

        assertEquals(sym.L_PAREN, lexer.next_token().sym);
        assertEquals(sym.MINUS, lexer.next_token().sym);

        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("5", token.value);

        assertEquals(sym.R_PAREN, lexer.next_token().sym);
        assertEquals(sym.PLUS, lexer.next_token().sym);

        token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("我", token.value);
    }

    @Test
    void tabsAsWhitespace() throws Exception {
        MyLexer lexer = LexerFactory.make(
                "\twilliam(\t-\t5)\t+\t\t\t\t\t\t'我'\t\t\t");
        Symbol token;

        token = lexer.next_token();
        assertEquals(sym.ID, token.sym);
        assertEquals("william", token.value);

        assertEquals(sym.L_PAREN, lexer.next_token().sym);
        assertEquals(sym.MINUS, lexer.next_token().sym);

        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("5", token.value);

        assertEquals(sym.R_PAREN, lexer.next_token().sym);
        assertEquals(sym.PLUS, lexer.next_token().sym);

        token = lexer.next_token();
        assertEquals(sym.CHAR_LITERAL, token.sym);
        assertEquals("我", token.value);

        assertEquals(sym.EOF, lexer.next_token().sym);
    }

}