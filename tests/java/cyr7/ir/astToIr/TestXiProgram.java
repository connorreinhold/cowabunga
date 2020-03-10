package cyr7.ir.astToIr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.StringReader;

import cyr7.ir.DefaultIdGenerator;
import org.junit.jupiter.api.Test;

import cyr7.C;
import cyr7.ast.Node;
import cyr7.ast.toplevel.XiProgramNode;
import cyr7.ir.AstToIrVisitor;
import cyr7.ir.IdGenerator;
import cyr7.ir.interpret.IRSimulator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNode;
import cyr7.lexer.MultiFileLexer;
import cyr7.parser.ParserUtil;
import cyr7.parser.XiParser;
import cyr7.typecheck.TypeCheckUtil;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

class TestXiProgram {

    private static void assertEq(IRNode expected, Node toTransform) {
        assertEquals(expected,
                toTransform.accept(new AstToIrVisitor(new DefaultIdGenerator())).assertFirst());
    }

    private static void assertEq(IRNode expected, Node toTransform,
            IdGenerator generator) {
        assertEquals(expected, toTransform.accept(new AstToIrVisitor(generator))
                .assertFirst());
    }

    private static long test(IRNode expected, String prgm) throws Exception {
        ScannerBuffer lexer = new ScannerBuffer(
                new MultiFileLexer(new StringReader(prgm), "test.xi", false));
        XiParser parser = new XiParser(lexer, new ComplexSymbolFactory());
        XiProgramNode node = (XiProgramNode) parser.parse().value;
        TypeCheckUtil.typeCheckNoIxiFiles(node);
        // Should be a statement
        IRNode result = node.accept(new AstToIrVisitor(new DefaultIdGenerator())).assertSecond();
        IRFuncDecl downcasted = (IRFuncDecl) result;
        IRCompUnit compUnit = new IRCompUnit(C.LOC, "test");
        compUnit.appendFunc(downcasted);

        IRSimulator sim = new IRSimulator(compUnit);
        return sim.call("main");
    }

}
