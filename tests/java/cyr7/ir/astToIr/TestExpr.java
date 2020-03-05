package cyr7.ir.astToIr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import cyr7.ast.Node;
import cyr7.ast.expr.binexpr.OrExprNode;
import cyr7.ast.expr.binexpr.RemExprNode;
import cyr7.ast.expr.binexpr.SubExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ir.AstToIrVisitor;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRExpr;
import cyr7.typecheck.TypeCheckUtil;
import org.junit.jupiter.api.Test;

import cyr7.C;
import cyr7.ast.AbstractNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.util.IRFactory;

class TestExpr {

    private static void assertEq(IRNode expected, Node toTransform) {
        assertEquals(expected, toTransform.accept(new AstToIrVisitor()).assertFirst());
    }

    @Test
    void testLiteral() {
        AbstractNode astNode = new LiteralBoolExprNode(C.LOC, false);
        IRNode result = IRFactory.parseAstExpr(astNode);
        long expectedResult = 0;
        assertEquals(expectedResult, IRFactory.testExpr(result));

        astNode = new LiteralBoolExprNode(C.LOC, true);
        result = IRFactory.parseAstExpr(astNode);
        expectedResult = 1;
        assertEquals(expectedResult, IRFactory.testExpr(result));

        astNode = new LiteralIntExprNode(C.LOC, "9223372036854775807");
        result = IRFactory.parseAstExpr(astNode);
        expectedResult = Long.parseLong("9223372036854775807");
        assertEquals(expectedResult, IRFactory.testExpr(result));
    }

    @Test
    void testUnaryOps() {
        AbstractNode astNode = new BoolNegExprNode(C.LOC,
                new LiteralBoolExprNode(C.LOC, true));
        IRNode result = IRFactory.parseAstExpr(astNode);
        long expectedResult = 0;
        assertEquals(expectedResult, IRFactory.testExpr(result));

        astNode = new IntNegExprNode(C.LOC,
                new LiteralIntExprNode(C.LOC, "9223372036854775807"));
        result = IRFactory.parseAstExpr(astNode);
        expectedResult = Long.parseLong("-9223372036854775807");
        assertEquals(expectedResult, IRFactory.testExpr(result));
    }

    @Test
    void testBinOps() {
        AbstractNode astNode = new AddExprNode(C.LOC,
                new LiteralIntExprNode(C.LOC, "5"),
                new LiteralIntExprNode(C.LOC, "5"));

        IRNode result = IRFactory.parseAstExpr(astNode);
        long expectedResult = 10;
        assertEquals(expectedResult, IRFactory.testExpr(result));

        astNode = new AddExprNode(C.LOC, new LiteralIntExprNode(C.LOC, "5"),
                new LiteralIntExprNode(C.LOC, "5"));

        result = IRFactory.parseAstExpr(astNode);
        expectedResult = 10;
        assertEquals(expectedResult, IRFactory.testExpr(result));
    }

    @Test
    void testRem() {
        Node node = new RemExprNode(C.LOC,
            new LiteralIntExprNode(C.LOC, "5"),
            new LiteralIntExprNode(C.LOC, "10"));
        IRExpr expected = new IRBinOp(OpType.MOD, new IRConst(5), new IRConst(10));
        assertEq(expected, node);
    }

    @Test
    void testSub() {
        Node node = new SubExprNode(C.LOC,
            new LiteralIntExprNode(C.LOC, "5"),
            new LiteralIntExprNode(C.LOC, "10"));
        IRExpr expected = new IRBinOp(OpType.SUB, new IRConst(5), new IRConst(10));
        assertEq(expected, node);
    }

    @Test
    void testBoolNegExpr() {
        Node node = new BoolNegExprNode(C.LOC,
            new LiteralBoolExprNode(C.LOC, false));
        IRExpr expected = new IRBinOp(OpType.XOR,
            new IRConst(1),
            new IRConst(0));
        assertEq(expected, node);
    }

    @Test
    void testIntNegExpr() {
        Node node = new IntNegExprNode(C.LOC, new LiteralIntExprNode(C.LOC, "5"));
        IRExpr expected = new IRBinOp(OpType.SUB, new IRConst(0), new IRConst(5));
        assertEq(expected, node);
    }

    @Test
    void testLiteralInt() {
        Node node = new LiteralIntExprNode(C.LOC, "5");
        IRExpr expected = new IRConst(5);
        assertEq(expected, node);
    }

    @Test
    void testLiteralBool() {
        Node node = new LiteralBoolExprNode(C.LOC, true);
        IRExpr expected = new IRConst(1);
        assertEq(expected, node);
    }

    @Test
    void testLiteralChar() {
        Node node = new LiteralCharExprNode(C.LOC, "x");
        IRExpr expected = new IRConst(120);
        assertEq(expected, node);
    }

    @Test
    void testLiteralString() {
        fail();
    }

    @Test
    void testLiteralArray() {
        fail();
    }

}
