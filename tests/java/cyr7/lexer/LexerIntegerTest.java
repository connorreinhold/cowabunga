package cyr7.lexer;

import cyr7.exceptions.LeadingZeroIntegerException;
import cyr7.parser.sym;
import java_cup.runtime.Symbol;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LexerIntegerTest {

    @Test
    void integerOverflow() throws Exception {
        String veryBigNumber = "99999999999999999999999";
        MyLexer lexer = LexerFactory.make(veryBigNumber);
        Symbol token;

        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals(veryBigNumber, token.value);

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
    void integerEdgeCases() {
        String illegalInt = "0010";
        MyLexer lexer = LexerFactory.make(illegalInt);
        assertThrows(LeadingZeroIntegerException.class, lexer::next_token);

        lexer = LexerFactory.make("00");
        assertThrows(LeadingZeroIntegerException.class, lexer::next_token);
    }

    @Test
    void integerGeneral() throws Exception {
        MyLexer lexer = LexerFactory.make("100 1234567890");
        Symbol token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("100", token.value);

        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("1234567890", token.value);
    }

}
