package cyr7.parser.xi;

import static cyr7.parser.util.ParserFactory.LOC;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.BinExprNode;
import cyr7.ast.expr.binexpr.BinExprNode.ASTOpType;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.parser.util.ParserFactory;

public class TestArrayAccessExpr {

    @Test
    void testFunctionCallArrayAccess() throws Exception {
        ExprNode parsed = ParserFactory.parseExpr("f()[0]");
        ExprNode expected = new ArrayAccessExprNode(LOC,
                new FunctionCallExprNode(LOC, "f", List.of()),
                new LiteralIntExprNode(LOC, "0"));
        assertEquals(expected, parsed);
    }

    @Test
    void testArrayAccessPrecedence() throws Exception {
        ExprNode parsed = ParserFactory.parseExpr("5 + a[0]");
        ExprNode expected = new BinExprNode(LOC, ASTOpType.ADD,
                new LiteralIntExprNode(LOC, "5"),
                new ArrayAccessExprNode(LOC,
                        new VariableAccessExprNode(LOC, "a"),
                        new LiteralIntExprNode(LOC, "0")));
        assertEquals(expected, parsed);

        parsed = ParserFactory.parseExpr("5 * a[0]");
        expected = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralIntExprNode(LOC, "5"),
                new ArrayAccessExprNode(LOC,
                        new VariableAccessExprNode(LOC, "a"),
                        new LiteralIntExprNode(LOC, "0")));
        assertEquals(expected, parsed);

        parsed = ParserFactory.parseExpr("5 + -a[0]");
        expected = new BinExprNode(LOC, ASTOpType.ADD,
                new LiteralIntExprNode(LOC, "5"),
                new IntNegExprNode(LOC,
                        new ArrayAccessExprNode(LOC,
                                new VariableAccessExprNode(LOC, "a"),
                                new LiteralIntExprNode(LOC, "0"))));
        assertEquals(expected, parsed);
    }

    @Test
    void testArrayLiteralAccess() throws Exception {
        ExprNode parsed = ParserFactory.parseExpr("{1, 3, 5,}[7]");
        ExprNode expected = new ArrayAccessExprNode(LOC,
                new LiteralArrayExprNode(LOC,
                        List.of(new LiteralIntExprNode(LOC, "1"),
                                new LiteralIntExprNode(LOC, "3"),
                                new LiteralIntExprNode(LOC, "5"))),
                new LiteralIntExprNode(LOC, "7"));
        assertEquals(expected, parsed);

        parsed = ParserFactory
                .parseExpr("{ {f()}[h()], {1}[0], {1,2}[2]} [g()]");
        expected = new ArrayAccessExprNode(LOC,
                new LiteralArrayExprNode(LOC, List.of(
                        new ArrayAccessExprNode(LOC,
                                new LiteralArrayExprNode(LOC,
                                        List.of(new FunctionCallExprNode(LOC,
                                                "f", List.of()))),
                                new FunctionCallExprNode(LOC, "h", List.of())),
                        new ArrayAccessExprNode(LOC, new LiteralArrayExprNode(
                                LOC, List.of(new LiteralIntExprNode(LOC, "1"))),
                                new LiteralIntExprNode(LOC, "0")),
                        new ArrayAccessExprNode(LOC, new LiteralArrayExprNode(
                                LOC,
                                List.of(new LiteralIntExprNode(LOC, "1"),
                                        new LiteralIntExprNode(LOC, "2"))),
                                new LiteralIntExprNode(LOC, "2")))),
                new FunctionCallExprNode(LOC, "g", List.of()));
        assertEquals(expected, parsed);
    }

}
