package cyr7.parser.xi;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import cyr7.ast.toplevel.UseNode;
import cyr7.ast.toplevel.XiProgramNode;
import cyr7.parser.XiParser;
import cyr7.parser.util.ParserFactory;

class TestUse {

    XiProgramNode expected;
    XiParser parser;
    Object tree;

    @Test
    void test() throws Exception {
        String program = "use io\nuse math;\nuse nothing";
        LinkedList<UseNode> uses = new LinkedList<>();
        Collections.addAll(
            uses,
            new UseNode(null, "io"),
            new UseNode(null, "math"),
            new UseNode(null, "nothing")
        );
        expected = new XiProgramNode(null, uses, new LinkedList<>());
        parser = ParserFactory.make(program, false);
        tree = parser.parse().value;
        assertEquals(expected, tree);
    }

}
