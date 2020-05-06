package cyr7.cfg.ir;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGNodeFactory;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.cfg.ir.opt.CCPOptimization;
import cyr7.cfg.util.IrCfgTestUtil;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRNodeFactory_c;
import java_cup.runtime.ComplexSymbolFactory.Location;
import polyglot.util.Pair;

class TestCondConstProp {

    /**
     * x = 30
     * if (x > 30) {
     *    y = x
     * } else {
     *    z = x
     * }
     * println(x)
     * return
     *
     * Should simplify to the following:
     * start
     * x = 30
     * z = x
     * println(x)
     * return
     */
    @Test
    void testBranchingCFG() {
        final Location loc = new Location(-1, -1);
        final var cfg = new CFGNodeFactory(loc);
        final var ir = new IRNodeFactory_c(loc);
        final var gen = new DefaultIdGenerator();

        CFGNode returnNode = cfg.Return();
        CFGNode printlnX = cfg.Call(
                ir.IRCallStmt(List.of(), ir.IRName("println"),
                        List.of(ir.IRTemp("x"))), returnNode);
        CFGNode yIsX = cfg.VarAssign("y", ir.IRTemp("x"), printlnX);
        CFGNode zIsX = cfg.VarAssign("z", ir.IRTemp("x"), printlnX);

        CFGNode ifNode = cfg.If(yIsX, zIsX,
                    ir.IRBinOp(OpType.GT, ir.IRTemp("x"), ir.IRConst(30)));

        CFGNode xAssign = cfg.VarAssign("x", ir.IRConst(30), ifNode);
        CFGNode root = cfg.Start(xAssign);

        CFGNode println30 = cfg.Call(
                ir.IRCallStmt(List.of(), ir.IRName("println"),
                        List.of(ir.IRConst(30))), new CFGStubNode());

        CFGNode zIs30 = cfg.VarAssign("z", ir.IRConst(30), new CFGStubNode());

        Set<CFGNode> expectedNodes = IrCfgTestUtil.nodeSet(
                                            root,
                                            xAssign,
                                            zIs30,
                                            println30,
                                            returnNode);

        List<Pair<CFGNode, CFGNode>> expectedEdges = IrCfgTestUtil.edgeList(
                new Pair<>(root, xAssign),
                new Pair<>(xAssign, zIs30),
                new Pair<>(zIs30, println30),
                new Pair<>(println30, returnNode));

        CFGStartNode start = CCPOptimization.optimize(root);

        IrCfgTestUtil.printIR(start);

        assertTrue(IrCfgTestUtil.assertEqualGraphs(
                            start, expectedNodes, expectedEdges));

    }
}
