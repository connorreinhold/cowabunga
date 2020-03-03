package cyr7.ir.astToIr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
        AbstractNode astNode = new LiteralBoolExprNode(null, false);
        IRNode result = IRFactory.parseAst(astNode);
        long expectedResult = 0;
        assertEquals(expectedResult, IRFactory.testExpr(result));

        astNode = new LiteralBoolExprNode(null, true);
        result = IRFactory.parseAst(astNode);
        expectedResult = 1;
        assertEquals(expectedResult, IRFactory.testExpr(result));

        astNode = new LiteralIntExprNode(null, "9223372036854775807");
        result = IRFactory.parseAst(astNode);
        expectedResult = Long.parseLong("9223372036854775807");
        assertEquals(expectedResult, IRFactory.testExpr(result));
    }

    // @Test
    void testUnaryOps() {
        AbstractNode astNode = new BoolNegExprNode(null,
                new LiteralBoolExprNode(null, true));
        IRNode result = IRFactory.parseAst(astNode);
        long expectedResult = 0;
        assertEquals(expectedResult, IRFactory.testExpr(result));

        astNode = new IntNegExprNode(null,
                new LiteralIntExprNode(null, "9223372036854775807"));
        result = IRFactory.parseAst(astNode);
        expectedResult = Long.parseLong("-9223372036854775807");
        assertEquals(expectedResult, IRFactory.testExpr(result));
    }

    @Test
    void testBinOps() {
        AbstractNode astNode = new AddExprNode(null,
                new LiteralIntExprNode(null, "5"),
                new LiteralIntExprNode(null, "5"));

        IRNode result = IRFactory.parseAst(astNode);
        long expectedResult = 10;
        assertEquals(expectedResult, IRFactory.testExpr(result));

        astNode = new AddExprNode(null, new LiteralIntExprNode(null, "5"),
                new LiteralIntExprNode(null, "5"));

        result = IRFactory.parseAst(astNode);
        expectedResult = 10;
        assertEquals(expectedResult, IRFactory.testExpr(result));
    }

}
