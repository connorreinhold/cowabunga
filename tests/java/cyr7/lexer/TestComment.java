package cyr7.lexer;

import java_cup.runtime.Symbol;
import org.junit.jupiter.api.Test;

import cyr7.parser.sym;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestComment {

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

}
