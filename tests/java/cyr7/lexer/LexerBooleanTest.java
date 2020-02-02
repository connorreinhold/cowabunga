package cyr7.lexer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerBooleanTest {

    @Test
    void booleanLiteralTest() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("( true false ) "));
        MyLexer.Token token;

        assertEquals(MyLexer.TokenType.L_PAREN, lexer.nextToken().type);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.BOOL_LITERAL, token.type);
        assertEquals(true, token.attribute);

        token = lexer.nextToken();
        assertEquals(MyLexer.TokenType.BOOL_LITERAL, token.type);
        assertEquals(false, token.attribute);

        assertEquals(MyLexer.TokenType.R_PAREN, lexer.nextToken().type);
    }

    @Test
    void booleanTypeTest() throws IOException {
        MyLexer lexer = new MyLexer(new StringReader("( bool ) "));
        assertEquals(MyLexer.TokenType.L_PAREN, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.TYPE_BOOL, lexer.nextToken().type);
        assertEquals(MyLexer.TokenType.R_PAREN, lexer.nextToken().type);
    }

}
