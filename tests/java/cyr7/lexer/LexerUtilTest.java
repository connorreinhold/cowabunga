package cyr7.lexer;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LexerUtilTest {

    @Test
    void testTypeAttributeDescription() {
        for (MyLexer.TokenType tokenType : MyLexer.TokenType.values()) {
            MyLexer lexer = new MyLexer(new StringReader(""));
            String description = LexerUtil.typeAttributeDescription(lexer.new Token(tokenType, "blah"));
            assertNotNull(description);
            assertFalse(description.isEmpty());
        }
    }

}
