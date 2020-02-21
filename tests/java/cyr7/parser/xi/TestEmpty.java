package cyr7.parser.xi;

import cyr7.ast.toplevel.XiProgramNode;
import cyr7.parser.XiParser;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEmpty {

    XiProgramNode expected;
    XiParser parser;
    Object tree;
    
    @Test
    void test() throws Exception {
        String program = "";
        expected = new XiProgramNode(null, new LinkedList<>(), new LinkedList<>());
        parser = ParserFactory.make(program, false);
        tree = parser.parse().value;
        assertEquals(expected, tree);
        
        program = "\n\n\n\n";
        expected = new XiProgramNode(null, new LinkedList<>(), new LinkedList<>());
        parser = ParserFactory.make(program, false);
        tree = parser.parse().value;
        assertEquals(expected, tree);

    }
    
}
