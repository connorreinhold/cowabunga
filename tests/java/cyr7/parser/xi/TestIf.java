package cyr7.parser.xi;

import cyr7.parser.XiParser;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TestIf {

    XiParser parser;
    String program;
    
    @Test
    void test() throws Exception {
        
        // Normal if-else statement
        program = "main() { if true { return 1 } else { return 4 } }";
        parser = ParserFactory.make(program, false);
        assertDoesNotThrow(parser::parse);
        
        // Without blocks, the next statements after if/else 
        // is one statement long
        program = "main() { if true { return 1 } else { return 4 } return 0 }";
        parser = ParserFactory.make(program, false);
        assertDoesNotThrow(parser::parse);

        // Dangling else
        program = "main() { if true { return 1 } if false { return 3 } else { return 4 } }";
        parser = ParserFactory.make(program, false);
        assertDoesNotThrow(parser::parse);
        
    }

}
