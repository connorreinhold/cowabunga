package cyr7.parser.xi;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.ast.expr.ArrayAccessExprNode;
import cyr7.ast.expr.ArrayExprNode;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.LengthCallExprNode;
import cyr7.ast.expr.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.*;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.parser.util.ParserFactory;

class TextExpr {

    @Test
    void testIntOperations() throws Exception {
        String expr = "-1 + 1";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new AddExprNode(null,
            new IntNegExprNode(null,
                new LiteralIntExprNode(null, "1")),
            new LiteralIntExprNode(null, "1")
        );
        assertEquals(parsed, expected);

        expr = "1 + 2 + 3";
        parsed = ParserFactory.parseExpr(expr);
        expected = new AddExprNode(null,
            new AddExprNode(null,
                new LiteralIntExprNode(null, "1"),
                new LiteralIntExprNode(null, "2")),
            new LiteralIntExprNode(null, "3")
        );
        assertEquals(parsed, expected);

        expr = "1 + (2 + 3)";
        parsed = ParserFactory.parseExpr(expr);
        expected = new AddExprNode(null,
            new LiteralIntExprNode(null, "1"),
            new AddExprNode(null,
                new LiteralIntExprNode(null, "2"),
                new LiteralIntExprNode(null, "3"))
        );
        assertEquals(parsed, expected);

        expr = "1 / (2 * 3 / 4)";
        parsed = ParserFactory.parseExpr(expr);
        expected = new DivExprNode(null,
            new LiteralIntExprNode(null, "1"),
            new DivExprNode(null,
                new MultExprNode(null,
                    new LiteralIntExprNode(null, "2"),
                    new LiteralIntExprNode(null, "3")),
                new LiteralIntExprNode(null, "4"))
        );
        assertEquals(parsed, expected);

        expr = "1 *>> 2 + 3 / 4";
        parsed = ParserFactory.parseExpr(expr);
        expected = new AddExprNode(null,
            new HighMultExprNode(null,
                new LiteralIntExprNode(null, "1"),
                new LiteralIntExprNode(null, "2")),
            new DivExprNode(null,
                new LiteralIntExprNode(null, "3"),
                new LiteralIntExprNode(null, "4"))
        );

        expr = "0 - -3 / 4 % 5";
        parsed = ParserFactory.parseExpr(expr);
        expected = new SubExprNode(null,
            new LiteralIntExprNode(null, "0"),
            new RemExprNode(null,
                new DivExprNode(null,
                    new IntNegExprNode(null,
                        new LiteralIntExprNode(null, "3")),
                    new LiteralIntExprNode(null, "4")
                ),
                new LiteralIntExprNode(null, "5"))
        );

        assertEquals(parsed, expected);
    }


    @Test
    void testIntComparators() throws Exception {
        String expr = "-1 > 1";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new GTExprNode(null,
            new IntNegExprNode(null,
                new LiteralIntExprNode(null, "1")),
            new LiteralIntExprNode(null, "1")
        );
        assertEquals(parsed, expected);


        expr = "-2 < 1 + 1";
        parsed = ParserFactory.parseExpr(expr);
        expected = new LTExprNode(null,
            new IntNegExprNode(null,
                new LiteralIntExprNode(null, "2")),
            new AddExprNode(null,
                new LiteralIntExprNode(null, "1"),
                new LiteralIntExprNode(null, "1"))
        );
        assertEquals(parsed, expected);

        expr = "2 + function() >= 1 % 1";
        parsed = ParserFactory.parseExpr(expr);
        expected = new GTEExprNode(null,
            new AddExprNode(null,
                new LiteralIntExprNode(null, "2"),
                new FunctionCallExprNode(null,
                    "hello",
                    new LinkedList<>())),
            new RemExprNode(null,
                new LiteralIntExprNode(null, "1"),
                new LiteralIntExprNode(null, "1"))
        );

        expr = "(4 *>> 6) <= 1 + 1";
        parsed = ParserFactory.parseExpr(expr);
        expected = new LTEExprNode(null,
            new HighMultExprNode(null,
                new LiteralIntExprNode(null, "4"),
                new LiteralIntExprNode(null, "6")),
            new AddExprNode(null,
                new LiteralIntExprNode(null, "1"),
                new LiteralIntExprNode(null, "1"))
        );
        assertEquals(parsed, expected);

        expr = "3 != hello()";
        parsed = ParserFactory.parseExpr(expr);
        expected = new NotEqualsExprNode(null,
            new LiteralIntExprNode(null, "3"),
            new FunctionCallExprNode(null,
                "hello",
                new LinkedList<>())
        );
        assertEquals(parsed, expected);

        expr = "-4 == hello()";
        parsed = ParserFactory.parseExpr(expr);
        expected = new EqualsExprNode(null,
            new IntNegExprNode(null,
                new LiteralIntExprNode(null, "4")),
            new FunctionCallExprNode(null,
                "hello",
                new LinkedList<>())
        );
        assertEquals(parsed, expected);
    }

    @Test
    void testBoolOperations() throws Exception {
        String expr = "true | false & true";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new OrExprNode(null,
            new LiteralBoolExprNode(null, true),
            new AndExprNode(null,
                new LiteralBoolExprNode(null, false),
                new LiteralBoolExprNode(null, true)
            )
        );
        assertEquals(parsed, expected);

        expr = "true | false | true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new OrExprNode(null,
            new OrExprNode(null,
                new LiteralBoolExprNode(null, true),
                new LiteralBoolExprNode(null, false)
            ),
            new LiteralBoolExprNode(null, true)
        );
        assertEquals(parsed, expected);

        expr = "(true | false) & true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new AndExprNode(null,
            new OrExprNode(null,
                new LiteralBoolExprNode(null, true),
                new LiteralBoolExprNode(null, false)
            ),
            new LiteralBoolExprNode(null, true)
        );
        assertEquals(parsed, expected);
    }

    @Test
    void testBoolComparators() throws Exception {
        String expr = "true == false & true";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected =
            new AndExprNode(null,
                new EqualsExprNode(null,
                    new LiteralBoolExprNode(null, true),
                    new LiteralBoolExprNode(null, false)),
                new LiteralBoolExprNode(null, true)
            );
        assertEquals(parsed, expected);

        expr = "true | false != true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new OrExprNode(null,
            new LiteralBoolExprNode(null, true),
            new NotEqualsExprNode(null,
                new LiteralBoolExprNode(null, false),
                new LiteralBoolExprNode(null, true)
            )
        );
        assertEquals(parsed, expected);

        expr = "(true | false) != !true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new NotEqualsExprNode(null,
            new OrExprNode(null,
                new LiteralBoolExprNode(null, true),
                new LiteralBoolExprNode(null, false)
            ),
            new BoolNegExprNode(null,
                new LiteralBoolExprNode(null, true))
        );
        assertEquals(parsed, expected);

        expr = "!!!true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BoolNegExprNode(null,
            new BoolNegExprNode(null,
                new BoolNegExprNode(null,
                    new LiteralBoolExprNode(null, true)))
        );
        assertEquals(parsed, expected);
    }

    @Test
    void testVariables() throws Exception {
        String expr = "a + b";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected =
            new AddExprNode(null,
                new VariableAccessExprNode(null, "a"),
                new VariableAccessExprNode(null, "b")
            );
        assertEquals(parsed, expected);

        expr = "a[2][3][4]";
        parsed = ParserFactory.parseExpr(expr);
        expected = new ArrayAccessExprNode(null,
            new ArrayAccessExprNode(null,
                new ArrayAccessExprNode(null,
                    new VariableAccessExprNode(null, "a"),
                    new LiteralIntExprNode(null, "2")),
                new LiteralIntExprNode(null, "3")),
            new LiteralIntExprNode(null, "4"));
        assertEquals(parsed, expected);

        expr = "abcdefghij[2+2]";
        parsed = ParserFactory.parseExpr(expr);
        expected = new ArrayAccessExprNode(null,
            new VariableAccessExprNode(null, "abcdefghij"),
            new AddExprNode(null,
                new LiteralIntExprNode(null, "2"),
                new LiteralIntExprNode(null, "2")));
        assertEquals(parsed, expected);

        expr = "length(a[2+b])";
        parsed = ParserFactory.parseExpr(expr);
        LinkedList<ExprNode> params = new LinkedList<>();
        params.add(new ArrayAccessExprNode(null,
            new VariableAccessExprNode(null, "a"),
            new AddExprNode(null,
                new LiteralIntExprNode(null, "2"),
                new VariableAccessExprNode(null, "b"))));
        expected = new LengthCallExprNode(null, params);

        assertEquals(parsed, expected);
    }

    @Test
    void testFunctionCalls() throws Exception {
        String expr = "hello('a', b, 3, a[4], \"hello\")";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new FunctionCallExprNode(null,
            "hello",
            List.of(new LiteralCharExprNode(null, "a"),
                new VariableAccessExprNode(null, "b"),
                new LiteralIntExprNode(null, "3"),
                new ArrayAccessExprNode(null,
                    new VariableAccessExprNode(null, "a"),
                    new LiteralIntExprNode(null, "4")),
                new LiteralStringExprNode(null, "hello")));
        assertEquals(parsed, expected);

        expr = "a(b + 4) + b[a] *>> a[b+a]";
        parsed = ParserFactory.parseExpr(expr);
        expected = new AddExprNode(null,
            new FunctionCallExprNode(null,
                "a", List.of(new AddExprNode(null,
                new VariableAccessExprNode(null, "b"),
                new LiteralIntExprNode(null, "4")))),
            new HighMultExprNode(null, new ArrayAccessExprNode(null,
                new VariableAccessExprNode(null, "b"),
                new VariableAccessExprNode(null, "a")),
                new ArrayAccessExprNode(null,
                    new VariableAccessExprNode(null, "a"),
                    new AddExprNode(null,
                        new VariableAccessExprNode(null, "b"),
                        new VariableAccessExprNode(null, "a")))));
        assertEquals(parsed, expected);
    }


    @Test
    void testMisc() throws Exception {
        String expr = "{1,2,3,4,5}";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new ArrayExprNode(null, List.of(
            new LiteralIntExprNode(null, "1"),
            new LiteralIntExprNode(null, "2"),
            new LiteralIntExprNode(null, "3"),
            new LiteralIntExprNode(null, "4"),
            new LiteralIntExprNode(null, "5")
        ));
        assertEquals(parsed, expected);
    }
}
