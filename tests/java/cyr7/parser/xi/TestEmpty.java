package cyr7.parser.xi;

import cyr7.ast.toplevel.XiProgramNode;
import cyr7.exceptions.parser.ParserException;
import cyr7.parser.XiParser;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestEmpty {

    XiProgramNode expected;
    XiParser parser;
    Object tree;
    
    @Test
    void test() throws Exception {
        String program = "";
        parser = ParserFactory.make(program, false);
        assertThrows(ParserException.class, parser::parse);
        
        program = "\n\n\n\n";
        parser = ParserFactory.make(program, false);
        assertThrows(ParserException.class, parser::parse);
    }
    
}
