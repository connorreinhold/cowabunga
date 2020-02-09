package cyr7.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import cyr7.ast.IxiProgramNode;
import cyr7.lexer.MyLexer;
import cyr7.parser.ixi.IxiParser;
import java_cup.runtime.ComplexSymbolFactory;

class TestInterfaceFiles {

    @Test
    void testEmptyProgram() throws Exception {
        IxiProgramNode expected = new IxiProgramNode(new LinkedList<>());
        StringReader prgm = new StringReader("");
        IxiParser parser = new IxiParser(new MyLexer(prgm),
                new ComplexSymbolFactory());
        Object tree = parser.parse().value;
        assertEquals(tree, expected);
    }

}
