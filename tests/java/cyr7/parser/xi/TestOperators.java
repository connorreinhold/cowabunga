package cyr7.parser.xi;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.binexpr.MultExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOperators {

    @Test
    void testAddMul() throws Exception {
        ExprNode expr = ParserFactory.parseExpr("1 + 2 * 3");
        assertEquals(expr, new AddExprNode(
            null,
            new LiteralIntExprNode(null, "1"),
            new MultExprNode(
                null,
                new LiteralIntExprNode(null, "2"),
                new LiteralIntExprNode(null, "3")
            )
        ));
    }

    @Test
    void testAddLeftAssociative() throws Exception {
        ExprNode expr = ParserFactory.parseExpr("1 + 2 + 3");
        assertEquals(expr, new AddExprNode(
            null,
            new AddExprNode(
                null,
                new LiteralIntExprNode(null, "1"),
                new LiteralIntExprNode(null, "2")
            ),
            new LiteralIntExprNode(null, "3")
        ));
    }

    @Test
    void testAddMulParenthesis() throws Exception {
        ExprNode expr = ParserFactory.parseExpr("(1 + 2) * 3");
        assertEquals(expr, new MultExprNode(
            null,
            new AddExprNode(
                null,
                new LiteralIntExprNode(null, "1"),
                new LiteralIntExprNode(null, "2")
            ),
            new LiteralIntExprNode(null, "3")
        ));
    }

}
