package cyr7.lexer;

import org.junit.jupiter.api.Test;

import cyr7.exceptions.InvalidCharacterLiteralException;
import cyr7.exceptions.InvalidStringEscapeCharacterException;
import cyr7.exceptions.InvalidTokenException;
import cyr7.exceptions.LeadingZeroIntegerException;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void doesNotBreakLexer() {
        MyLexer lexer = new MyLexer(new StringReader("##"));
        assertThrows(InvalidTokenException.class, lexer::nextToken);
    }

    @Test
    void highMultAndGreaterThan() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("*>>>"));
        MyLexer.Token token;

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.HIGH_MULT, token.type);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.GT, token.type);
    }

    @Test
    void multAndHighMult() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("***>>>*>*>>*>>"));
        assertEquals(MyLexer.TokenType.MULT, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.MULT, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.HIGH_MULT, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.GT, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.MULT, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.GT, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.HIGH_MULT, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.HIGH_MULT, lexer.nextToken().type);
    }

    @Test
    void lessThanEqualsGreaterThan() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("<<=>>="));
        assertEquals(MyLexer.TokenType.LT, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.LTE, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.GT, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.GTE, lexer.nextToken().type);
    }

    @Test
    void equalsNotEquals() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("=!!===="));
        assertEquals(MyLexer.TokenType.ASSIGN, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.NEG_BOOL, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.NOT_EQUALS, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.EQUALS, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.ASSIGN, lexer.nextToken().type);
    }

    @Test
    void minusAndNegativeIntLiteral() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("1234---5-10"));
        MyLexer.Token token;

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals("1234", token.attribute);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.MINUS, token.type);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.MINUS, token.type);

        // see insertionsort.xi for why this should be minus token and int literal instead of negative int literal.

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.MINUS, token.type);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals("5", token.attribute);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.MINUS, token.type);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals("10", token.attribute);
    }

    @Test
    void commentSingleLine() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("// this is a comment 'a' \" testing \" have a good day"));
        assertNull(lexer.nextToken());
    }

    @Test
    void idThenComment() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("1234 // this is a comment have a good day"));

        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals("1234", token.attribute);

        assertNull(lexer.nextToken());
    }

    @Test
    void commentMultipleLines() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader(
                "// this is a comment have a good day\n"
                        + "1234 asdf // whatever\n"
                        + "while () // blkj \t asdf\n"
                        + "if use\n"
                        + "++ \"// comment // inside a string\" // comment\n"
        ));

        assertEquals(MyLexer.TokenType.INT_LITERAL, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.ID, lexer.nextToken().type);

        assertEquals(MyLexer.TokenType.WHILE, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.L_PAREN, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.R_PAREN, lexer.nextToken().type);

        assertEquals(MyLexer.TokenType.IF, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.USE, lexer.nextToken().type);

        assertEquals(MyLexer.TokenType.PLUS, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.PLUS, lexer.nextToken().type);
    }

    @Test
    void commentDifferentLineEndings() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader(
                "// this is a comment have a good day\r"
                        + "1234 asdf // whatever\n"
                        + "while () // blkj \t asdf\r\n"
                        + "if use\n"
                        + "++ \"// comment // inside a string\" // comment\n"
        ));

        assertEquals(MyLexer.TokenType.INT_LITERAL, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.ID, lexer.nextToken().type);

        assertEquals(MyLexer.TokenType.WHILE, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.L_PAREN, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.R_PAREN, lexer.nextToken().type);

        assertEquals(MyLexer.TokenType.IF, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.USE, lexer.nextToken().type);

        assertEquals(MyLexer.TokenType.PLUS, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.PLUS, lexer.nextToken().type);
    }

    @Test
    void integerOverflow() throws IOException {
        String veryBigNumber = "99999999999999999999999";
        MyLexer lexer = new MyLexer(new StringReader(veryBigNumber));
        MyLexer.Token token;

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals(veryBigNumber, token.attribute);

        lexer = new MyLexer(new StringReader("" + Integer.MAX_VALUE));
        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals(String.valueOf(Integer.MAX_VALUE), token.attribute);

        lexer = new MyLexer(new StringReader("" + Integer.MIN_VALUE));
        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.MINUS, token.type);
        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals(String.valueOf(Integer.MIN_VALUE).substring(1), token.attribute);
    }

    @Test
    void integerEdgeCases() throws InvalidCharacterLiteralException, InvalidStringEscapeCharacterException, IOException {
        String illegalInt = "0010";
        MyLexer lexer = new MyLexer(new StringReader(illegalInt));
        assertThrows(LeadingZeroIntegerException.class, lexer::nextToken);

        lexer = new MyLexer(new StringReader("00"));
        assertThrows(LeadingZeroIntegerException.class, lexer::nextToken);
    }

    @Test
    void stringEscapingUnicode() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("\"\\xAAAA\"")); // \xAAAA
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
    void stringEscapingDoubleQuote() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("\"\\\"\"")); // \"
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("\"", token.attribute);
    }

    @Test
    void stringEscapingSingleQuote() throws IOException {
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
    void stringEscapingBackslash() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("\"\\\\\"")); // \\
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("\\", token.attribute);
    }

    @Test
    void stringEscapingNewLine() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("\"\\n\"")); // \n
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("\n", token.attribute);
    }

    @Test
    void stringInvalidEscaping() {
        MyLexer lexer = new MyLexer(new StringReader("\"\\x\"")); // \x
        assertThrows(InvalidStringEscapeCharacterException.class, lexer::nextToken);
        lexer = new MyLexer(new StringReader("\"asdf\\g\"")); // \g
        assertThrows(InvalidStringEscapeCharacterException.class, lexer::nextToken);
        lexer = new MyLexer(new StringReader("\"\\b1324\"")); // \b
        assertThrows(InvalidStringEscapeCharacterException.class, lexer::nextToken);
    }

    @Test
    void stringEmpty() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("\"\"")); // ""
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.STRING_LITERAL, token.type);
        assertEquals("", token.attribute);
    }

    @Test
    void characterEscapingUnicode() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("'\\xAAAA'")); // \xAAAA
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.CHAR_LITERAL, token.type);
        assertEquals("\uAAAA", token.attribute);

        lexer = new MyLexer(new StringReader("'\\x123'")); // \x123
        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.CHAR_LITERAL, token.type);
        assertEquals("\u0123", token.attribute);

        lexer = new MyLexer(new StringReader("'\\xFD'")); // \xFD
        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.CHAR_LITERAL, token.type);
        assertEquals("\u00FD", token.attribute);

        lexer = new MyLexer(new StringReader("'\\x0'")); // \x0
        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.CHAR_LITERAL, token.type);
        assertEquals("\u0000", token.attribute);
    }

    @Test
    void characterEscapingDoubleQuote() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("'\\\"'")); // \"
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.CHAR_LITERAL, token.type);
        assertEquals("\"", token.attribute);

        lexer = new MyLexer(new StringReader("'\"'")); // \"
        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.CHAR_LITERAL, token.type);
        assertEquals("\"", token.attribute);
    }

    @Test
    void characterEscapingSingleQuote() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("'\\''")); // \'
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.CHAR_LITERAL, token.type);
        assertEquals("'", token.attribute);
    }

    @Test
    void characterEscapingNewLine() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("'\\n'")); // \n
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.CHAR_LITERAL, token.type);
        assertEquals("\n", token.attribute);
    }

    @Test
    void characterInvalidEscaping() {
        MyLexer lexer = new MyLexer(new StringReader("'\\x'")); // \x
        assertThrows(Exception.class, lexer::nextToken);
        lexer = new MyLexer(new StringReader("'\\g'")); // \g
        assertThrows(Exception.class, lexer::nextToken);
        lexer = new MyLexer(new StringReader("'\\b'")); // \b
        assertThrows(Exception.class, lexer::nextToken);
    }

    @Test
    void characterEscapingBackslash() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("'\\\\'")); // \\
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.CHAR_LITERAL, token.type);
        assertEquals("\\", token.attribute);
    }

    @Test
    void characterInvalidFormat() {
        MyLexer lexer = new MyLexer(new StringReader("''")); // empty character literal
        assertThrows(InvalidCharacterLiteralException.class, lexer::nextToken);
        lexer = new MyLexer(new StringReader("'as'")); // two line character literal
        assertThrows(InvalidCharacterLiteralException.class, lexer::nextToken);
        lexer = new MyLexer(new StringReader("\u583c\u5f46")); // outside of (unicode) BMP
        assertThrows(Exception.class, lexer::nextToken);
    }

    @Test
    void identifierAndCharacter() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("blip'a'asdf"));
        MyLexer.Token token;

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.ID, token.type);
        assertEquals("blip'a'asdf", token.attribute);

        assertNull(lexer.nextToken());
    }

    @Test
    void functionCallTest() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("william(-5) + '我'"));
        MyLexer.Token token;

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.ID, token.type);
        assertEquals("william", token.attribute);

        assertEquals(MyLexer.TokenType.L_PAREN, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.MINUS, lexer.nextToken().type);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals("5", token.attribute);

        assertEquals(MyLexer.TokenType.R_PAREN, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.PLUS, lexer.nextToken().type);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.CHAR_LITERAL, token.type);
        assertEquals("我", token.attribute);
    }

    @Test
    void tabsAsWhitespace() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("\twilliam(\t-\t5)\t+\t\t\t\t\t\t'我'\t\t\t"));
        MyLexer.Token token;

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.ID, token.type);
        assertEquals("william", token.attribute);

        assertEquals(MyLexer.TokenType.L_PAREN, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.MINUS, lexer.nextToken().type);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals("5", token.attribute);

        assertEquals(MyLexer.TokenType.R_PAREN, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.PLUS, lexer.nextToken().type);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.CHAR_LITERAL, token.type);
        assertEquals("我", token.attribute);

        assertNull(lexer.nextToken());
    }

    @Test
    void stringWithNewlineThrowsError() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("\"\n\"")); // single string with newline
        assertThrows(Exception.class, lexer::nextToken);

        lexer = new MyLexer(new StringReader("\"blah blah \n\""));
        assertThrows(Exception.class, lexer::nextToken);
    }

}