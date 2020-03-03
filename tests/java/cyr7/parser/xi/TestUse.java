package cyr7.parser.xi;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.ast.toplevel.FunctionDeclNode;
import cyr7.ast.toplevel.FunctionHeaderDeclNode;
import cyr7.ast.toplevel.UseNode;
import cyr7.ast.toplevel.XiProgramNode;
import cyr7.parser.XiParser;
import cyr7.parser.util.ParserFactory;
import cyr7.ast.stmt.BlockStmtNode;

import static cyr7.parser.util.ParserFactory.LOC;

class TestUse {

    XiProgramNode expected;
    XiParser parser;
    Object tree;

    @Test
    void test() throws Exception {
        String program = "use io\nuse math;\nuse nothing main() {}";
        LinkedList<UseNode> uses = new LinkedList<>();
        Collections.addAll(uses, new UseNode(LOC, "io"),
                new UseNode(LOC, "math"), new UseNode(LOC, "nothing"));
        FunctionHeaderDeclNode header = new FunctionHeaderDeclNode(LOC, "main",
                new LinkedList<>(), new LinkedList<>());
        FunctionDeclNode decl = new FunctionDeclNode(LOC, header,
                new BlockStmtNode(LOC, new LinkedList<>()));
        expected = new XiProgramNode(LOC, uses,
                new LinkedList<>(List.of(decl)));
        parser = ParserFactory.make(program, false);
        tree = parser.parse().value;
        assertEquals(expected, tree);
    }

}
