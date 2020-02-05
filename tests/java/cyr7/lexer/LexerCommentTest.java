package cyr7.lexer;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LexerCommentTest {

    @Test
    void commentSingleLine() throws Exception {
        MyLexer lexer = new MyLexer(new StringReader(
                "// this is a comment 'a' \" testing \" have a good day"));
        assertNull(lexer.nextToken());
    }

    @Test
    void idThenComment() throws Exception {
        MyLexer lexer = new MyLexer(new StringReader(
                "1234 // this is a comment have a good day"));

        MyLexer.Token token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.INT_LITERAL, token.type);
        assertEquals("1234", token.attribute);

        assertNull(lexer.nextToken());
    }

    @Test
    void commentMultipleLines() throws Exception {
        MyLexer lexer = new MyLexer(new StringReader(
                "// this is a comment have a good day\n"
                        + "1234 asdf // whatever\n"
                        + "while () // blkj \t asdf\n" + "if use\n"
                        + "++ \"// comment // inside a string\" // comment\n"));

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
    void commentDifferentLineEndings() throws Exception {
        MyLexer lexer = new MyLexer(new StringReader(
                "// this is a comment have a good day\r"
                        + "1234 asdf // whatever\n"
                        + "while () // blkj \t asdf\r\n" + "if use\n"
                        + "++ \"// comment // inside a string\" // comment\n"));

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

}
