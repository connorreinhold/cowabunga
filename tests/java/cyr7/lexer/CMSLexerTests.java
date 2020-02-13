package cyr7.lexer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import cyr7.exceptions.LeadingZeroIntegerException;
import cyr7.exceptions.LexerException;
import cyr7.parser.sym;
import cyr7.util.Util;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Symbol;

class CMSLexerTests {

    StringReader sr;
    MyLexer lexer;
    ComplexSymbol token;
    
    void testErrorOutput(String expectedError, MyLexer lexer) throws IOException {
        try {
            lexer.next_token();
            fail("Method call did not produce error.");
        } catch (LexerException e) {
            String msg = e.getMessage();
            int len = expectedError.length();
            assertEquals(expectedError, msg.substring(0, len));
        }
    }
    
    @Test
    void invalidEscapeInStringTests() throws Exception {
        String string03 = "\"\\q\"";
        lexer = LexerFactory.make(string03);        
        testErrorOutput("1:2 error", lexer);
        
        String string04 = "\"\\x\"";
        lexer = LexerFactory.make(string04);        
        testErrorOutput("1:2 error", lexer);
        
        
        String string06 = "\"\\ \\ \\ \"";
        lexer = LexerFactory.make(string06);        
        testErrorOutput("1:2 error", lexer);
        
        
        String string07 = "\"\"\"\"\"\"\"\"\"\"\"\"\"\""
                + "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"";
        lexer = LexerFactory.make(string07);
        for (int i = 0; i < 20; i++) {
            assertEquals(sym.STRING_LITERAL, lexer.next_token().sym);
        }
        
        String string09 = "\"abcdedf";
        lexer = LexerFactory.make(string09);
        testErrorOutput("1:1 error", lexer);
        
    }
    
    
    @Test
    void invalidEscapeCharacterTests() throws Exception {
        String string08 = "\"\\\\\"";
        lexer = LexerFactory.make(string08);
        token = lexer.next_token();
        assertEquals(1, token.xleft.getLine());
        assertEquals(1, token.xleft.getColumn());
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\\\\", Util.unescapeString(token.value.toString()));
        
        
        String string09 = "\"\\\\\\\"\"";
        lexer = LexerFactory.make(string09);
        token = lexer.next_token();
        assertEquals(1, token.xleft.getLine());
        assertEquals(1, token.xleft.getColumn());
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\\\\\\\"", Util.unescapeString(token.value.toString()));
        
        
        String string05 = "\"Hello \"\"\\\"World\\\"\"\"; x = \"\"\\\\\"";
        lexer = LexerFactory.make(string05);
        
        token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("Hello ", token.value.toString());
        
        token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\"World\"", token.value.toString());

        token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("; x = ", token.value.toString());
        
        token = lexer.next_token();
        assertEquals(sym.STRING_LITERAL, token.sym);
        assertEquals("\\", token.value.toString());
    }
    
    
    @Test
    void nonTerminatingCharacterFormat() throws Exception {
        String char06 = "'a";
        lexer = LexerFactory.make(char06);
        testErrorOutput("1:1 error", lexer);
    }
    
    
    @Test
    void testNonterminatingString() throws Exception {
        String string09 = "\"abcdedf";
        lexer = LexerFactory.make(string09);
        testErrorOutput("1:1 error", lexer);
    }
    
    @Test
    void testNumbers() throws Exception {
        String int01 = "00\n01";
        lexer = LexerFactory.make(int01);
        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("0", token.value.toString());
        
        token = lexer.next_token();
        assertEquals(sym.INT_LITERAL, token.sym);
        assertEquals("0", token.value.toString());

        assertThrows(LeadingZeroIntegerException.class, lexer::next_token);        
    }
    
    @Test
    void largeInt() throws Exception {
        String largeIntLiteral = "1000000000000000000000000000000";
        lexer = LexerFactory.make(largeIntLiteral);
        testErrorOutput("1:1 error", lexer);

    }

}
