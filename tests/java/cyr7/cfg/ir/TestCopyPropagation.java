package cyr7.cfg.ir;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cyr7.cfg.ir.flatten.CFGFlattener;
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
import cyr7.ir.nodes.IRStmt;
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

        CFGNode zIsDoubleY = cfg.VarAssign("z",
                ir.IRBinOp(OpType.ADD, ir.IRTemp("y"), ir.IRTemp("y")),
                new CFGStubNode());

        Set<CFGNode> expectedNodes = IrCfgTestUtil.nodeSet(root, firstAssign,
                deadAssign, zIsDoubleY, returnNode);

        List<Pair<CFGNode, CFGNode>> expectedEdges = IrCfgTestUtil.edgeList(
                new Pair<>(root, firstAssign),
                new Pair<>(firstAssign, deadAssign),
                new Pair<>(deadAssign, zIsDoubleY),
                new Pair<>(zIsDoubleY, returnNode));

        CFGStartNode start = new CopyPropagationOptimization().optimize(root);

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
     * println(x) // Should change here.
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

        CFGNode printNodeOpt = cfg.Call(
                ir.IRCallStmt(List.of(), ir.IRName("println"),
                        List.of(ir.IRTemp("x"))), new CFGStubNode());

        Set<CFGNode> expectedNodes = IrCfgTestUtil.nodeSet(root, xAssign, ifNode,
                                            yIsX1, yIsX2,
                                            printNodeOpt, returnNode);

        List<Pair<CFGNode, CFGNode>> expectedEdges = IrCfgTestUtil.edgeList(
                new Pair<>(root, xAssign),
                new Pair<>(xAssign, ifNode),
                new Pair<>(ifNode, yIsX1),
                new Pair<>(ifNode, yIsX2),
                new Pair<>(yIsX1, printNodeOpt),
                new Pair<>(yIsX2, printNodeOpt),
                new Pair<>(printNodeOpt, returnNode));

        CFGStartNode start = new CopyPropagationOptimization().optimize(root);

        IrCfgTestUtil.printIR(start);

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

        Set<CFGNode> expectedNodes = IrCfgTestUtil.nodeSet(root, setRV, returnNode);
        List<Pair<CFGNode, CFGNode>> expectedEdges = IrCfgTestUtil.edgeList(
                new Pair<>(root, setRV),
                new Pair<>(setRV, returnNode));

        CFGStartNode start = new CopyPropagationOptimization().optimize(root);

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

        CFGNode altXIncrement = cfg.VarAssign("x",
                ir.IRBinOp(OpType.ADD, ir.IRTemp("x"), ir.IRConst(1)),
                new CFGStubNode());

        Set<CFGNode> expectedNodes = IrCfgTestUtil.nodeSet(root, setX, setY,
                whileIfNode, yIsX, altXIncrement, setRV, returnNode);

        List<Pair<CFGNode, CFGNode>> expectedEdges = IrCfgTestUtil.edgeList(
                new Pair<>(root, setX),
                new Pair<>(setX, setY),
                new Pair<>(setY, whileIfNode),
                new Pair<>(whileIfNode, yIsX),
                new Pair<>(yIsX, altXIncrement),
                new Pair<>(altXIncrement, whileIfNode),
                new Pair<>(whileIfNode, setRV),
                new Pair<>(setRV, returnNode)
                );



        CFGStartNode start = new CopyPropagationOptimization().optimize(root);

        assertTrue(IrCfgTestUtil.assertEqualGraphs(
                            start, expectedNodes, expectedEdges));
    }


    /**
     * x = 0;
     * y = 0;
     * a = x;
     * b = y;
     * f(x, y)
     * println(a)  // becomes println(x)
     * println(b)  // becomes println(y)
     * x = foo()
     * y = foo()
     * c = a + b   // no change
     * println(c)  // no change
     * return
     */
    @Test
    void testFunctionCallCFG() {
        final Location loc = new Location(-1, -1);
        final var cfg = new CFGNodeFactory(loc);
        final var ir = new IRNodeFactory_c(loc);

        CFGNode returnNode = cfg.Return();

        CFGNode printlnC = cfg.Call(ir.IRCallStmt(List.of(),
                                              ir.IRName("println"),
                                              List.of(ir.IRTemp("c"))),
                                    returnNode);

        CFGNode cIsAPlusB = cfg.VarAssign("c",
                ir.IRBinOp(OpType.ADD, ir.IRTemp("a"), ir.IRTemp("b")), printlnC);

        CFGNode yIsFoo = cfg.Call(ir.IRCallStmt(List.of("y"),
                ir.IRName("foo"), List.of()), cIsAPlusB);

        CFGNode xIsFoo = cfg.Call(ir.IRCallStmt(List.of("x"),
                ir.IRName("foo"), List.of()), yIsFoo);

        CFGNode printlnB = cfg.Call(ir.IRCallStmt(List.of(),
                ir.IRName("println"),
                List.of(ir.IRTemp("b"))), xIsFoo);

        CFGNode printlnA = cfg.Call(ir.IRCallStmt(List.of(),
                ir.IRName("println"),
                List.of(ir.IRTemp("a"))), printlnB);

        CFGNode fOfXAndY = cfg.Call(ir.IRCallStmt(List.of(), ir.IRName("f"),
                            List.of(ir.IRTemp("x"), ir.IRTemp("y"))), printlnA);

        CFGNode bIsY = cfg.VarAssign("b", ir.IRTemp("y"), fOfXAndY);

        CFGNode aIsX = cfg.VarAssign("a", ir.IRTemp("x"), bIsY);

        CFGNode setY = cfg.VarAssign("y", ir.IRConst(0), aIsX);

        CFGNode setX = cfg.VarAssign("x", ir.IRConst(0), setY);

        CFGNode root = cfg.Start(setX);

        CFGNode printlnX = cfg.Call(ir.IRCallStmt(List.of(),
                ir.IRName("println"),
                List.of(ir.IRTemp("x"))), new CFGStubNode());

        CFGNode printlnY = cfg.Call(ir.IRCallStmt(List.of(),
                ir.IRName("println"),
                List.of(ir.IRTemp("y"))), new CFGStubNode());

        Set<CFGNode> expectedNodes = IrCfgTestUtil.nodeSet(root, setX, setY,
                aIsX, bIsY, fOfXAndY, printlnX, printlnY,
                xIsFoo, yIsFoo, cIsAPlusB, printlnC, returnNode);

        List<Pair<CFGNode, CFGNode>> expectedEdges = IrCfgTestUtil.edgeList(
                new Pair<>(root, setX),
                new Pair<>(setX, setY),
                new Pair<>(setY, aIsX),
                new Pair<>(aIsX, bIsY),
                new Pair<>(bIsY, fOfXAndY),
                new Pair<>(fOfXAndY, printlnX),
                new Pair<>(printlnX, printlnY),
                new Pair<>(printlnY, xIsFoo),
                new Pair<>(xIsFoo, yIsFoo),
                new Pair<>(yIsFoo, cIsAPlusB),
                new Pair<>(cIsAPlusB, printlnC),
                new Pair<>(printlnC, returnNode));

        CFGStartNode start = new CopyPropagationOptimization().optimize(root);

        assertTrue(IrCfgTestUtil.assertEqualGraphs(
                            start, expectedNodes, expectedEdges));

        IRStmt result = CFGFlattener.flatten(start);
        System.out.println(result);
    }
}
