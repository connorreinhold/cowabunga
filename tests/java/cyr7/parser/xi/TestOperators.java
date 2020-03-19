package cyr7.parser.xi;

import static cyr7.parser.util.ParserFactory.LOC;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.binexpr.BinExprNode;
import cyr7.ast.expr.binexpr.BinExprNode.ASTOpType;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.parser.util.ParserFactory;

public class TestOperators {

    @Test
    void testAddMul() throws Exception {
        ExprNode expr = ParserFactory.parseExpr("1 + 2 * 3");
        assertEquals(expr,
                new BinExprNode(LOC, ASTOpType.ADD,
                        new LiteralIntExprNode(LOC, "1"),
                        new BinExprNode(LOC, ASTOpType.MUL,
                                new LiteralIntExprNode(LOC, "2"),
                                new LiteralIntExprNode(LOC, "3"))));
    }

    @Test
    void testAddLeftAssociative() throws Exception {
        ExprNode expr = ParserFactory.parseExpr("1 + 2 + 3");
        assertEquals(expr,
                new BinExprNode(LOC, ASTOpType.ADD,
                        new BinExprNode(LOC, ASTOpType.ADD,
                                new LiteralIntExprNode(LOC, "1"),
                                new LiteralIntExprNode(LOC, "2")),
                        new LiteralIntExprNode(LOC, "3")));
    }

    @Test
    void testAddMulParenthesis() throws Exception {
        ExprNode expr = ParserFactory.parseExpr("(1 + 2) * 3");
        assertEquals(expr,
                new BinExprNode(LOC, ASTOpType.MUL,
                        new BinExprNode(LOC, ASTOpType.ADD,
                                new LiteralIntExprNode(LOC, "1"),
                                new LiteralIntExprNode(LOC, "2")),
                        new LiteralIntExprNode(LOC, "3")));
    }

}
