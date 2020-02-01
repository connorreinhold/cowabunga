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

}