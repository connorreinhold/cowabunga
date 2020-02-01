package cyr7.lexer;

import cyr7.exceptions.InvalidCharacterLiteralException;
import cyr7.exceptions.InvalidStringEscapeCharacterException;
import cyr7.exceptions.LeadingZeroIntegerException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LexerIntegerTest {

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
    void integerEdgeCases() {
        String illegalInt = "0010";
        MyLexer lexer = new MyLexer(new StringReader(illegalInt));
        assertThrows(LeadingZeroIntegerException.class, lexer::nextToken);

        lexer = new MyLexer(new StringReader("00"));
        assertThrows(LeadingZeroIntegerException.class, lexer::nextToken);
    }

    @Test
    void integerGeneral() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("100 1234567890"));
        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals("100", token.attribute);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals("1234567890", token.attribute);
    }

}
