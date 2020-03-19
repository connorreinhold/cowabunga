package cyr7.parser.xi;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.binexpr.MultExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static cyr7.parser.util.ParserFactory.LOC;

public class TestOperators {

    @Test
    void testAddMul() throws Exception {
        ExprNode expr = ParserFactory.parseExpr("1 + 2 * 3");
        assertEquals(expr, new AddExprNode(
            LOC,
            new LiteralIntExprNode(LOC, "1"),
            new MultExprNode(
                LOC,
                new LiteralIntExprNode(LOC, "2"),
                new LiteralIntExprNode(LOC, "3")
            )
        ));
    }

    @Test
    void testAddLeftAssociative() throws Exception {
        ExprNode expr = ParserFactory.parseExpr("1 + 2 + 3");
        assertEquals(expr, new AddExprNode(
            LOC,
            new AddExprNode(
                LOC,
                new LiteralIntExprNode(LOC, "1"),
                new LiteralIntExprNode(LOC, "2")
            ),
            new LiteralIntExprNode(LOC, "3")
        ));
    }

    @Test
    void testAddMulParenthesis() throws Exception {
        ExprNode expr = ParserFactory.parseExpr("(1 + 2) * 3");
        assertEquals(expr, new MultExprNode(
            LOC,
            new AddExprNode(
                LOC,
                new LiteralIntExprNode(LOC, "1"),
                new LiteralIntExprNode(LOC, "2")
            ),
            new LiteralIntExprNode(LOC, "3")
        ));
    }

}
