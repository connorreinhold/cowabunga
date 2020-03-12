package cyr7.ir.astToIr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cyr7.ir.DefaultIdGenerator;
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
import cyr7.ir.ASTToIRVisitor;
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
                toTransform.accept(new ASTToIRVisitor(new DefaultIdGenerator())).assertFirst());
    }

    private static void assertEq(IRNode expected, Node toTransform,
            IdGenerator generator) {
        assertEquals(expected, toTransform.accept(new ASTToIRVisitor(generator))
                .assertFirst());
    }

    private final IRNodeFactory make = new IRNodeFactory_c(
            new Location(-1, -1));

    /*
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
    void testOr() {
        Node node = new OrExprNode(C.LOC, new LiteralBoolExprNode(C.LOC, true),
                new LiteralBoolExprNode(C.LOC, false));

        LookaheadIdGenerator generator = new LookaheadIdGenerator();
        String x = generator.peekTemp(0);
        String lt = generator.peekLabel(0);
        String lf = generator.peekLabel(1);
        String lf_ = generator.peekLabel(2);

        IRStmt left = make.IRJump(make.IRName(lt));
        IRStmt right = make.IRJump(make.IRName(lf_));

        IRExpr expected = make
                .IRESeq(make.IRSeq(make.IRMove(make.IRTemp(x), make.IRConst(1)),
                        left, make.IRLabel(lf), right, make.IRLabel(lf_),
                        make.IRMove(make.IRTemp(x), make.IRConst(0)),
                        make.IRLabel(lt)), make.IRTemp(x));
        assertEq(expected, node, generator);
    }
    */

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
