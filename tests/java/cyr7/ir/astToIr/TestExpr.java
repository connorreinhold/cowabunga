package cyr7.ir.astToIr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cyr7.C;
import cyr7.ast.Node;
import cyr7.ast.expr.binexpr.RemExprNode;
import cyr7.ast.expr.binexpr.SubExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ir.AstToIrVisitor;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import java_cup.runtime.ComplexSymbolFactory.Location;

class TestExpr {

    private static void assertEq(IRNode expected, Node toTransform) {
        assertEquals(expected,
                toTransform.accept(new AstToIrVisitor()).assertFirst());
    }

    private static void assertEq(IRNode expected, Node toTransform,
            IdGenerator generator) {
        assertEquals(expected, toTransform.accept(new AstToIrVisitor(generator))
                .assertFirst());
    }

    private final IRNodeFactory make = new IRNodeFactory_c(
            new Location(-1, -1));


    @Test
    void testRem() {
        Node node = new RemExprNode(C.LOC, new LiteralIntExprNode(C.LOC, "5"),
                new LiteralIntExprNode(C.LOC, "10"));
        IRExpr expected = make.IRBinOp(OpType.MOD, make.IRConst(5),
                make.IRConst(10));
        assertEq(expected, node);
    }

    @Test
    void testSub() {
        Node node = new SubExprNode(C.LOC, new LiteralIntExprNode(C.LOC, "5"),
                new LiteralIntExprNode(C.LOC, "10"));
        IRExpr expected = make.IRBinOp(OpType.SUB, make.IRConst(5),
                make.IRConst(10));
        assertEq(expected, node);
    }

    @Test
    void testBoolNegExpr() {
        Node node = new BoolNegExprNode(C.LOC,
                new LiteralBoolExprNode(C.LOC, false));
        IRExpr expected = make.IRBinOp(OpType.XOR, make.IRConst(1),
                make.IRConst(0));
        assertEq(expected, node);
    }

    @Test
    void testIntNegExpr() {
        Node node = new IntNegExprNode(C.LOC,
                new LiteralIntExprNode(C.LOC, "5"));
        IRExpr expected = make.IRBinOp(OpType.SUB, make.IRConst(0),
                make.IRConst(5));
        assertEq(expected, node);
    }

    @Test
    void testLiteralInt() {
        Node node = new LiteralIntExprNode(C.LOC, "5");
        IRExpr expected = make.IRConst(5);
        assertEq(expected, node);
    }

    @Test
    void testLiteralBool() {
        Node node = new LiteralBoolExprNode(C.LOC, true);
        IRExpr expected = make.IRConst(1);
        assertEq(expected, node);
    }

    @Test
    void testLiteralChar() {
        Node node = new LiteralCharExprNode(C.LOC, "x");
        IRExpr expected = make.IRConst('x');
        assertEq(expected, node);
    }

}
