package cyr7.lexer;

import cyr7.parser.xi.sym;
import java_cup.runtime.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerBooleanTest {

    @Test
    void booleanLiteralTest() throws Exception {
        MyLexer lexer = LexerFactory.make("( true false ) ");
        Symbol token;

        assertEquals(sym.L_PAREN, lexer.next_token().sym);

        token = lexer.next_token();
        assertEquals(sym.BOOL_LITERAL, token.sym);
        assertEquals(true, token.value);

        token = lexer.next_token();
        assertEquals(sym.BOOL_LITERAL, token.sym);
        assertEquals(false, token.value);

        assertEquals(sym.R_PAREN, lexer.next_token().sym);
    }

    @Test
    void booleanTypeTest() throws Exception {
        MyLexer lexer = LexerFactory.make("( bool )");
        assertEquals(sym.L_PAREN, lexer.next_token().sym);
        assertEquals(sym.TYPE_BOOL, lexer.next_token().sym);
        assertEquals(sym.R_PAREN, lexer.next_token().sym);
    }

}
