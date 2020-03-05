package cyr7.ir.astToIr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cyr7.ast.Node;
import cyr7.ir.AstToIrVisitor;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRExpr;
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
    void testIntNegExpr() {
        Node node = new IntNegExprNode(C.LOC, new LiteralIntExprNode(C.LOC, "5"));
        IRExpr result = node.accept(new AstToIrVisitor()).assertFirst();

    }

}
