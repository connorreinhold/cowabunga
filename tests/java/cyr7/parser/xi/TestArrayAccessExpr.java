package cyr7.parser.xi;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.binexpr.MultExprNode;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestArrayAccessExpr {

    @Test
    void testFunctionCallArrayAccess() throws Exception {
        ExprNode parsed = ParserFactory.parseExpr("f()[0]");
        ExprNode expected = new ArrayAccessExprNode(
            null,
            new FunctionCallExprNode(null, "f", List.of()),
            new LiteralIntExprNode(null, "0")
        );
        assertEquals(expected, parsed);
    }

    @Test
    void testArrayAccessPrecedence() throws Exception {
        ExprNode parsed = ParserFactory.parseExpr("5 + a[0]");
        ExprNode expected = new AddExprNode(null,
            new LiteralIntExprNode(null, "5"),
            new ArrayAccessExprNode(null,
                new VariableAccessExprNode(null, "a"),
                new LiteralIntExprNode(null, "0")
            )
        );
        assertEquals(expected, parsed);

        parsed = ParserFactory.parseExpr("5 * a[0]");
        expected = new MultExprNode(null,
            new LiteralIntExprNode(null, "5"),
            new ArrayAccessExprNode(null,
                new VariableAccessExprNode(null, "a"),
                new LiteralIntExprNode(null, "0")
            )
        );
        assertEquals(expected, parsed);

        parsed = ParserFactory.parseExpr("5 + -a[0]");
        expected = new AddExprNode(null,
            new LiteralIntExprNode(null, "5"),
            new IntNegExprNode(null,
                new ArrayAccessExprNode(null,
                    new VariableAccessExprNode(null, "a"),
                    new LiteralIntExprNode(null, "0")
                )
            )
        );
        assertEquals(expected, parsed);
    }

    @Test
    void testArrayLiteralAccess() throws Exception {
        ExprNode parsed = ParserFactory.parseExpr("{1, 3, 5,}[7]");
        ExprNode expected = new ArrayAccessExprNode(null,
            new LiteralArrayExprNode(null, List.of(
                new LiteralIntExprNode(null, "1"),
                new LiteralIntExprNode(null, "3"),
                new LiteralIntExprNode(null, "5")
            )),
            new LiteralIntExprNode(null, "7")
        );
        assertEquals(expected, parsed);

        parsed = ParserFactory.parseExpr(
            "{ {f()}[h()], {1}[0], {1,2}[2]} [g()]");
        expected = new ArrayAccessExprNode(null,
            new LiteralArrayExprNode(null, List.of(
                new ArrayAccessExprNode(null,
                    new LiteralArrayExprNode(null, List.of(
                        new FunctionCallExprNode(null, "f", List.of())
                    )),
                    new FunctionCallExprNode(null, "h", List.of())),
                new ArrayAccessExprNode(null,
                    new LiteralArrayExprNode(null, List.of(
                        new LiteralIntExprNode(null, "1")
                    )),
                    new LiteralIntExprNode(null, "0")),
                new ArrayAccessExprNode(null,
                    new LiteralArrayExprNode(null, List.of(
                        new LiteralIntExprNode(null, "1"),
                        new LiteralIntExprNode(null, "2")
                    )),
                    new LiteralIntExprNode(null, "2"))
            )),
            new FunctionCallExprNode(null, "g", List.of())
        );
        assertEquals(expected, parsed);
    }

}
