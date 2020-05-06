package cyr7.cfg.ir;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGNodeFactory;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.opt.CopyPropagationOptimization;
import cyr7.cfg.util.IrCfgTestUtil;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRTemp;
import java_cup.runtime.ComplexSymbolFactory.Location;
import polyglot.util.Pair;

class TestCopyPropagation {

    /**
     * start <p>
     * y = 15 <p>
     * x = y <p>
     * z = x + x
     * return
     */
    @Test
    void testLinearCFG() {
        final Location loc = new Location(-1, -1);
        final var cfg = new CFGNodeFactory(loc);
        final var ir = new IRNodeFactory_c(loc);

        CFGNode returnNode = new CFGReturnNode(loc);

        CFGNode zIsDoubleX = cfg.VarAssign("z",
                            ir.IRBinOp(OpType.ADD, ir.IRTemp("x"), ir.IRTemp("x")),
                            returnNode);
        CFGNode deadAssign = new CFGVarAssignNode(loc, "x",
                                    new IRTemp(loc, "y"), zIsDoubleX);
        CFGNode firstAssign = new CFGVarAssignNode(loc, "y",
                                    new IRConst(loc, 15), deadAssign);
        CFGNode root = new CFGStartNode(loc, firstAssign);

        CFGStartNode start = new CopyPropagationOptimization().optimize(root);

        CFGNode zIsDoubleY = cfg.VarAssign("z",
                ir.IRBinOp(OpType.ADD, ir.IRTemp("y"), ir.IRTemp("y")),
                returnNode);

        Set<CFGNode> expectedNodes = Set.of(root, firstAssign,
                deadAssign, zIsDoubleY, returnNode);

        List<Pair<CFGNode, CFGNode>> expectedEdges = List.of(
                new Pair<>(root, firstAssign),
                new Pair<>(firstAssign, deadAssign),
                new Pair<>(deadAssign, zIsDoubleY),
                new Pair<>(zIsDoubleY, returnNode));

        assertTrue(IrCfgTestUtil.assertEqualGraphs(
                            start, expectedNodes, expectedEdges));

    }


    /**
     * x = 30
     * if (x > 30) {
     *    y = x
     * } else {
     *    y = x
     * }
     * println(y)
     * return
     */
    @Test
    void testBranchingCFG() {
        final Location loc = new Location(-1, -1);
        final var cfg = new CFGNodeFactory(loc);
        final var ir = new IRNodeFactory_c(loc);
        CFGNode returnNode = cfg.Return();
        CFGNode printNode = cfg.Call(
                ir.IRCallStmt(List.of(), ir.IRName("println"),
                        List.of(ir.IRTemp("y"))), returnNode);

        CFGNode yIsX1 = cfg.VarAssign("y", ir.IRTemp("x"), printNode);
        CFGNode yIsX2 = cfg.VarAssign("y", ir.IRTemp("x"), printNode);

        CFGNode ifNode = cfg.If(yIsX2, yIsX1,
                    ir.IRBinOp(OpType.GT, ir.IRTemp("x"), ir.IRConst(30)));

        CFGNode xAssign = cfg.VarAssign("x", ir.IRConst(30), ifNode);
        CFGNode root = cfg.Start(xAssign);

        CFGStartNode start = new CopyPropagationOptimization().optimize(root);

        CFGNode printNodeOpt = cfg.Call(
                ir.IRCallStmt(List.of(), ir.IRName("println"),
                        List.of(ir.IRTemp("x"))), returnNode);

        Set<CFGNode> expectedNodes = Set.of(root, xAssign, ifNode,
                                            yIsX1, yIsX2,
                                            printNodeOpt, returnNode);
        List<Pair<CFGNode, CFGNode>> expectedEdges = List.of(
                new Pair<>(root, xAssign),
                new Pair<>(xAssign, ifNode),
                new Pair<>(ifNode, yIsX1),
                new Pair<>(ifNode, yIsX2),
                new Pair<>(yIsX1, printNode),
                new Pair<>(yIsX2, printNode),
                new Pair<>(printNode, returnNode));

        assertTrue(IrCfgTestUtil.assertEqualGraphs(
                            start, expectedNodes, expectedEdges));
    }


    /**
     * return 5
     */
    @Test
    void testReturnValueCFG() {
        final Location loc = new Location(-1, -1);
        final var cfg = new CFGNodeFactory(loc);
        final var ir = new IRNodeFactory_c(loc);
        final var gen = new DefaultIdGenerator();
        CFGNode returnNode = cfg.Return();

        CFGNode setRV = cfg.VarAssign(gen.retTemp(0), ir.IRConst(5), returnNode);

        CFGNode root = cfg.Start(setRV);

        CFGStartNode start = new CopyPropagationOptimization().optimize(root);

        Set<CFGNode> expectedNodes = Set.of(root, setRV, returnNode);
        List<Pair<CFGNode, CFGNode>> expectedEdges = List.of(
                new Pair<>(root, setRV),
                new Pair<>(setRV, returnNode));

        assertTrue(IrCfgTestUtil.assertEqualGraphs(
                            start, expectedNodes, expectedEdges));
    }




    /**
     * x = 0;
     * y = 0;
     * while (x < 12) {
     *    y = x
     *    x = y + 1
     * }
     * return y
     */
    @Test
    void testWhileLoopCFG() {
        final Location loc = new Location(-1, -1);
        final var cfg = new CFGNodeFactory(loc);
        final var ir = new IRNodeFactory_c(loc);
        final var gen = new DefaultIdGenerator();

        CFGNode returnNode = cfg.Return();

        CFGNode setRV = cfg.VarAssign(gen.retTemp(0), ir.IRTemp("y"), returnNode);

        CFGNode stub = new CFGStubNode();

        CFGNode xIncrement = cfg.VarAssign("x",
                ir.IRBinOp(OpType.ADD, ir.IRTemp("y"), ir.IRConst(1)), stub);

        CFGNode yIsX = cfg.VarAssign("y", ir.IRTemp("x"), xIncrement);

        CFGNode whileIfNode = cfg.If(yIsX, setRV,
                ir.IRBinOp(OpType.LT, ir.IRTemp("x"), ir.IRConst(12)));

        xIncrement.replaceOutEdge(stub, whileIfNode);

        CFGNode setY = cfg.VarAssign("y", ir.IRConst(0), whileIfNode);

        CFGNode setX = cfg.VarAssign("x", ir.IRConst(0), setY);

        CFGNode root = cfg.Start(setX);

        CFGStartNode start = new CopyPropagationOptimization().optimize(root);

        CFGNode altXIncrement = cfg.VarAssign("x",
                ir.IRBinOp(OpType.ADD, ir.IRTemp("x"), ir.IRConst(1)), whileIfNode);

        Set<CFGNode> expectedNodes = Set.of(root, setX, setY,
                whileIfNode, yIsX, altXIncrement, setRV, returnNode);

        List<Pair<CFGNode, CFGNode>> expectedEdges = List.of(
                new Pair<>(root, setX),
                new Pair<>(setX, setY),
                new Pair<>(setY, whileIfNode),
                new Pair<>(whileIfNode, yIsX),
                new Pair<>(yIsX, altXIncrement),
                new Pair<>(altXIncrement, whileIfNode),
                new Pair<>(whileIfNode, setRV),
                new Pair<>(setRV, returnNode)
                );

        assertTrue(IrCfgTestUtil.assertEqualGraphs(
                            start, expectedNodes, expectedEdges));
    }


}
