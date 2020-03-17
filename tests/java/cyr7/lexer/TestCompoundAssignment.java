package cyr7.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cyr7.parser.sym;

class TestCompoundAssignment {

    @Test
    void testOperators() throws Exception {
        MyLexer lexer = LexerFactory.make("12 += 43;");
        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);
        assertEquals(sym.COMPOUND_PLUS, lexer.next_token().sym);
        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);
        assertEquals(sym.SEMICOLON, lexer.next_token().sym);

        lexer = LexerFactory.make("12 *= += -= 43");
        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);
        assertEquals(sym.COMPOUND_MULT, lexer.next_token().sym);
        assertEquals(sym.COMPOUND_PLUS, lexer.next_token().sym);
        assertEquals(sym.COMPOUND_MINUS, lexer.next_token().sym);
        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);

        lexer = LexerFactory.make("12 %= ; /= 43");
        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);
        assertEquals(sym.COMPOUND_REM, lexer.next_token().sym);
        assertEquals(sym.SEMICOLON, lexer.next_token().sym);
        assertEquals(sym.COMPOUND_DIV, lexer.next_token().sym);
        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);

        lexer = LexerFactory.make("a &= true");
        assertEquals(sym.ID, lexer.next_token().sym);
        assertEquals(sym.COMPOUND_AND, lexer.next_token().sym);
        assertEquals(sym.BOOL_LITERAL, lexer.next_token().sym);

        lexer = LexerFactory.make("a *>>= 23");
        assertEquals(sym.ID, lexer.next_token().sym);
        assertEquals(sym.COMPOUND_HIGH_MULT, lexer.next_token().sym);
        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);

        lexer = LexerFactory.make("12 +=|= 43;");
        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);
        assertEquals(sym.COMPOUND_PLUS, lexer.next_token().sym);
        assertEquals(sym.COMPOUND_OR, lexer.next_token().sym);
        assertEquals(sym.INT_LITERAL, lexer.next_token().sym);
        assertEquals(sym.SEMICOLON, lexer.next_token().sym);

        lexer = LexerFactory.make("|||||||||=");
        assertEquals(sym.LOGICAL_OR, lexer.next_token().sym);
        assertEquals(sym.LOGICAL_OR, lexer.next_token().sym);
        assertEquals(sym.LOGICAL_OR, lexer.next_token().sym);
        assertEquals(sym.LOGICAL_OR, lexer.next_token().sym);
        assertEquals(sym.LOGICAL_OR, lexer.next_token().sym);
        assertEquals(sym.LOGICAL_OR, lexer.next_token().sym);
        assertEquals(sym.LOGICAL_OR, lexer.next_token().sym);
        assertEquals(sym.LOGICAL_OR, lexer.next_token().sym);
        assertEquals(sym.COMPOUND_OR, lexer.next_token().sym);
    }
}