package cyr7.lexer;

import cyr7.parser.sym;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLexerUtil {

    @Test
    void testTypeAttributeDescriptionExist() throws Exception {
        for (int i = 2; i < sym.terminalNames.length; i++) {
            if (i == sym.NEG_INT) {
                continue;
            }

            System.out.println("" + i + ", " + sym.terminalNames[i]);
            String description = LexerUtil.fullDescription(new ComplexSymbol(
                    "",
                    i,
                    new ComplexSymbolFactory.Location(1, 1, 1),
                    new ComplexSymbolFactory.Location(1, 1, 1),
                    ""
            ));
            assertNotNull(description);
            assertFalse(description.isEmpty());
        }
    }

    @Test
    void testBooleanLiteralDescription() throws Exception {
        ComplexSymbol token = new ComplexSymbol(
                "",
                sym.BOOL_LITERAL,
                new ComplexSymbolFactory.Location(1, 1),
                new ComplexSymbolFactory.Location(1, 1),
                true);
        assertEquals("1:1 true", LexerUtil.fullDescription(token));

        token = new ComplexSymbol(
                "",
                sym.BOOL_LITERAL,
                new ComplexSymbolFactory.Location(1, 1),
                new ComplexSymbolFactory.Location(1, 1),
                false);
        assertEquals("1:1 false", LexerUtil.fullDescription(token));
    }

    @Test
    void testEscapedNewlineIsUnescaped() throws Exception {
        MyLexer lexer = LexerFactory.make(
                "\"Hello, Worl\\x64!\\n\"");
        ComplexSymbol token = lexer.next_token();
        assertEquals("1:1 string Hello, World!\\n",
                LexerUtil.fullDescription(token));

        // " \n \r \f \t "
        lexer = LexerFactory.make("\" \\n \\r \\f \\t \"");
        token = lexer.next_token();
        assertEquals("1:1 string  \\n \\r \\f \\t ",
                LexerUtil.fullDescription(token));

        lexer = LexerFactory.make("'\\n'");
        token = lexer.next_token();
        assertEquals("1:1 character \\n", LexerUtil.fullDescription(token));

        lexer = LexerFactory.make("'\\r'");
        token = lexer.next_token();
        assertEquals("1:1 character \\r", LexerUtil.fullDescription(token));

        lexer = LexerFactory.make("'\\f'");
        token = lexer.next_token();
        assertEquals("1:1 character \\f", LexerUtil.fullDescription(token));

        lexer = LexerFactory.make("'\\t'");
        token = lexer.next_token();
        assertEquals("1:1 character \\t", LexerUtil.fullDescription(token));
    }

    @Test
    void testEscapedVisiblesAreEscaped() throws Exception {
        // "\t''\""
        MyLexer lexer = LexerFactory.make("\"\\t\\''\\\"\"");
        ComplexSymbol token = lexer.next_token();
        assertEquals("1:1 string \\t\\'\\'\\\"", LexerUtil.fullDescription(token));

        lexer = LexerFactory.make("'\\''");
        token = lexer.next_token();
        assertEquals("1:1 character '", LexerUtil.fullDescription(token));

        lexer = LexerFactory.make("'\\\"'");
        token = lexer.next_token();
        assertEquals("1:1 character \"", LexerUtil.fullDescription(token));
    }

}
