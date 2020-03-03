package cyr7.parser.xi;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.binexpr.AndExprNode;
import cyr7.ast.expr.binexpr.DivExprNode;
import cyr7.ast.expr.binexpr.EqualsExprNode;
import cyr7.ast.expr.binexpr.GTEExprNode;
import cyr7.ast.expr.binexpr.GTExprNode;
import cyr7.ast.expr.binexpr.HighMultExprNode;
import cyr7.ast.expr.binexpr.LTEExprNode;
import cyr7.ast.expr.binexpr.LTExprNode;
import cyr7.ast.expr.binexpr.MultExprNode;
import cyr7.ast.expr.binexpr.NotEqualsExprNode;
import cyr7.ast.expr.binexpr.OrExprNode;
import cyr7.ast.expr.binexpr.RemExprNode;
import cyr7.ast.expr.binexpr.SubExprNode;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.exceptions.lexer.LexerIntegerOverflowException;
import cyr7.exceptions.parser.ParserIntegerOverflowException;
import cyr7.parser.ParserUtil;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static cyr7.parser.util.ParserFactory.LOC;

class TestExpr {

    @Test
    void testIntOperations() throws Exception {
        String expr = "-1 + 1";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new AddExprNode(LOC,
            new IntNegExprNode(LOC,
                new LiteralIntExprNode(LOC, "1")),
            new LiteralIntExprNode(LOC, "1")
        );
        assertEquals(parsed, expected);

        expr = "1 + 2 + 3";
        parsed = ParserFactory.parseExpr(expr);
        expected = new AddExprNode(LOC,
            new AddExprNode(LOC,
                new LiteralIntExprNode(LOC, "1"),
                new LiteralIntExprNode(LOC, "2")),
            new LiteralIntExprNode(LOC, "3")
        );
        assertEquals(parsed, expected);

        expr = "1 + (2 + 3)";
        parsed = ParserFactory.parseExpr(expr);
        expected = new AddExprNode(LOC,
            new LiteralIntExprNode(LOC, "1"),
            new AddExprNode(LOC,
                new LiteralIntExprNode(LOC, "2"),
                new LiteralIntExprNode(LOC, "3"))
        );
        assertEquals(parsed, expected);

        expr = "1 / (2 * 3 / 4)";
        parsed = ParserFactory.parseExpr(expr);
        expected = new DivExprNode(LOC,
            new LiteralIntExprNode(LOC, "1"),
            new DivExprNode(LOC,
                new MultExprNode(LOC,
                    new LiteralIntExprNode(LOC, "2"),
                    new LiteralIntExprNode(LOC, "3")),
                new LiteralIntExprNode(LOC, "4"))
        );
        assertEquals(parsed, expected);

        expr = "1 *>> 2 + 3 / 4";
        parsed = ParserFactory.parseExpr(expr);
        expected = new AddExprNode(LOC,
            new HighMultExprNode(LOC,
                new LiteralIntExprNode(LOC, "1"),
                new LiteralIntExprNode(LOC, "2")),
            new DivExprNode(LOC,
                new LiteralIntExprNode(LOC, "3"),
                new LiteralIntExprNode(LOC, "4"))
        );

        expr = "0 - -3 / 4 % 5";
        parsed = ParserFactory.parseExpr(expr);
        expected = new SubExprNode(LOC,
            new LiteralIntExprNode(LOC, "0"),
            new RemExprNode(LOC,
                new DivExprNode(LOC,
                    new IntNegExprNode(LOC,
                        new LiteralIntExprNode(LOC, "3")),
                    new LiteralIntExprNode(LOC, "4")
                ),
                new LiteralIntExprNode(LOC, "5"))
        );

        assertEquals(parsed, expected);
    }


    @Test
    void testIntComparators() throws Exception {
        String expr = "-1 > 1";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new GTExprNode(LOC,
            new IntNegExprNode(LOC,
                new LiteralIntExprNode(LOC, "1")),
            new LiteralIntExprNode(LOC, "1")
        );
        assertEquals(parsed, expected);


        expr = "-2 < 1 + 1";
        parsed = ParserFactory.parseExpr(expr);
        expected = new LTExprNode(LOC,
            new IntNegExprNode(LOC,
                new LiteralIntExprNode(LOC, "2")),
            new AddExprNode(LOC,
                new LiteralIntExprNode(LOC, "1"),
                new LiteralIntExprNode(LOC, "1"))
        );
        assertEquals(parsed, expected);

        expr = "2 + function() >= 1 % 1";
        parsed = ParserFactory.parseExpr(expr);
        expected = new GTEExprNode(LOC,
            new AddExprNode(LOC,
                new LiteralIntExprNode(LOC, "2"),
                new FunctionCallExprNode(LOC,
                    "hello",
                    new LinkedList<>())),
            new RemExprNode(LOC,
                new LiteralIntExprNode(LOC, "1"),
                new LiteralIntExprNode(LOC, "1"))
        );

        expr = "(4 *>> 6) <= 1 + 1";
        parsed = ParserFactory.parseExpr(expr);
        expected = new LTEExprNode(LOC,
            new HighMultExprNode(LOC,
                new LiteralIntExprNode(LOC, "4"),
                new LiteralIntExprNode(LOC, "6")),
            new AddExprNode(LOC,
                new LiteralIntExprNode(LOC, "1"),
                new LiteralIntExprNode(LOC, "1"))
        );
        assertEquals(parsed, expected);

        expr = "3 != hello()";
        parsed = ParserFactory.parseExpr(expr);
        expected = new NotEqualsExprNode(LOC,
            new LiteralIntExprNode(LOC, "3"),
            new FunctionCallExprNode(LOC,
                "hello",
                new LinkedList<>())
        );
        assertEquals(parsed, expected);

        expr = "-4 == hello()";
        parsed = ParserFactory.parseExpr(expr);
        expected = new EqualsExprNode(LOC,
            new IntNegExprNode(LOC,
                new LiteralIntExprNode(LOC, "4")),
            new FunctionCallExprNode(LOC,
                "hello",
                new LinkedList<>())
        );
        assertEquals(parsed, expected);
    }

    @Test
    void testBoolOperations() throws Exception {
        String expr = "true | false & true";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new OrExprNode(LOC,
            new LiteralBoolExprNode(LOC, true),
            new AndExprNode(LOC,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, true)
            )
        );
        assertEquals(parsed, expected);

        expr = "true | false | true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new OrExprNode(LOC,
            new OrExprNode(LOC,
                new LiteralBoolExprNode(LOC, true),
                new LiteralBoolExprNode(LOC, false)
            ),
            new LiteralBoolExprNode(LOC, true)
        );
        assertEquals(parsed, expected);

        expr = "(true | false) & true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new AndExprNode(LOC,
            new OrExprNode(LOC,
                new LiteralBoolExprNode(LOC, true),
                new LiteralBoolExprNode(LOC, false)
            ),
            new LiteralBoolExprNode(LOC, true)
        );
        assertEquals(parsed, expected);
    }

    @Test
    void testBoolComparators() throws Exception {
        String expr = "true == false & true";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected =
            new AndExprNode(LOC,
                new EqualsExprNode(LOC,
                    new LiteralBoolExprNode(LOC, true),
                    new LiteralBoolExprNode(LOC, false)),
                new LiteralBoolExprNode(LOC, true)
            );
        assertEquals(parsed, expected);

        expr = "true | false != true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new OrExprNode(LOC,
            new LiteralBoolExprNode(LOC, true),
            new NotEqualsExprNode(LOC,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, true)
            )
        );
        assertEquals(parsed, expected);

        expr = "(true | false) != !true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new NotEqualsExprNode(LOC,
            new OrExprNode(LOC,
                new LiteralBoolExprNode(LOC, true),
                new LiteralBoolExprNode(LOC, false)
            ),
            new BoolNegExprNode(LOC,
                new LiteralBoolExprNode(LOC, true))
        );
        assertEquals(parsed, expected);

        expr = "!!!true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BoolNegExprNode(LOC,
            new BoolNegExprNode(LOC,
                new BoolNegExprNode(LOC,
                    new LiteralBoolExprNode(LOC, true)))
        );
        assertEquals(parsed, expected);
    }

    @Test
    void testVariables() throws Exception {
        String expr = "a + b";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected =
            new AddExprNode(LOC,
                new VariableAccessExprNode(LOC, "a"),
                new VariableAccessExprNode(LOC, "b")
            );
        assertEquals(parsed, expected);

        expr = "a[2][3][4]";
        parsed = ParserFactory.parseExpr(expr);
        expected = new ArrayAccessExprNode(LOC,
            new ArrayAccessExprNode(LOC,
                new ArrayAccessExprNode(LOC,
                    new VariableAccessExprNode(LOC, "a"),
                    new LiteralIntExprNode(LOC, "2")),
                new LiteralIntExprNode(LOC, "3")),
            new LiteralIntExprNode(LOC, "4"));
        assertEquals(parsed, expected);

        expr = "abcdefghij[2+2]";
        parsed = ParserFactory.parseExpr(expr);
        expected = new ArrayAccessExprNode(LOC,
            new VariableAccessExprNode(LOC, "abcdefghij"),
            new AddExprNode(LOC,
                new LiteralIntExprNode(LOC, "2"),
                new LiteralIntExprNode(LOC, "2")));
        assertEquals(parsed, expected);

        expr = "length(a[2+b])";
        parsed = ParserFactory.parseExpr(expr);
        LinkedList<ExprNode> params = new LinkedList<>();
        params.add(new ArrayAccessExprNode(LOC,
            new VariableAccessExprNode(LOC, "a"),
            new AddExprNode(LOC,
                new LiteralIntExprNode(LOC, "2"),
                new VariableAccessExprNode(LOC, "b"))));
        expected = new FunctionCallExprNode(LOC, "length", params);

        assertEquals(parsed, expected);
    }

    @Test
    void testFunctionCalls() throws Exception {
        String expr = "hello('a', b, 3, a[4], \"hello\")";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new FunctionCallExprNode(LOC,
            "hello",
            List.of(new LiteralCharExprNode(LOC, "a"),
                new VariableAccessExprNode(LOC, "b"),
                new LiteralIntExprNode(LOC, "3"),
                new ArrayAccessExprNode(LOC,
                    new VariableAccessExprNode(LOC, "a"),
                    new LiteralIntExprNode(LOC, "4")),
                new LiteralStringExprNode(LOC, "hello")));
        assertEquals(parsed, expected);

        expr = "a(b + 4) + b[a] *>> a[b+a]";
        parsed = ParserFactory.parseExpr(expr);
        expected = new AddExprNode(LOC,
            new FunctionCallExprNode(LOC,
                "a", List.of(new AddExprNode(LOC,
                new VariableAccessExprNode(LOC, "b"),
                new LiteralIntExprNode(LOC, "4")))),
            new HighMultExprNode(LOC, new ArrayAccessExprNode(LOC,
                new VariableAccessExprNode(LOC, "b"),
                new VariableAccessExprNode(LOC, "a")),
                new ArrayAccessExprNode(LOC,
                    new VariableAccessExprNode(LOC, "a"),
                    new AddExprNode(LOC,
                        new VariableAccessExprNode(LOC, "b"),
                        new VariableAccessExprNode(LOC, "a")))));
        ParserUtil.printSExpr(parsed);
        ParserUtil.printSExpr(expected);
        assertEquals(parsed, expected);
    }


    @Test
    void testMisc() throws Exception {
        String expr = "{1,2,3,4,5}";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new LiteralArrayExprNode(LOC, List.of(
            new LiteralIntExprNode(LOC, "1"),
            new LiteralIntExprNode(LOC, "2"),
            new LiteralIntExprNode(LOC, "3"),
            new LiteralIntExprNode(LOC, "4"),
            new LiteralIntExprNode(LOC, "5")
        ));
        assertEquals(parsed, expected);
    }
    
    @Test
    void testMaxInt() throws Exception {
        String expr = "9223372036854775807";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new LiteralIntExprNode(LOC, expr);
        assertEquals(parsed, expected);
        
        expr = "-9223372036854775808";
        parsed = ParserFactory.parseExpr(expr);
        expected = new IntNegExprNode(LOC, 
                new LiteralIntExprNode(LOC, "9223372036854775808"));
        assertEquals(parsed, expected);
        
        final String largeExpr = "9223372036854775808";
        assertThrows(ParserIntegerOverflowException.class, () -> 
            ParserFactory.parseExpr(largeExpr)
        );
        
        final String largeNegInt = "-9223372036854775809";
        assertThrows(LexerIntegerOverflowException.class, () -> 
            ParserFactory.parseExpr(largeNegInt)
        );
        
        final String veryLargeInt = "99129428931919223372036854775809";
        assertThrows(LexerIntegerOverflowException.class, () -> 
            ParserFactory.parseExpr(veryLargeInt)
        );
        
        final String veryLargeNegInt = "-99129428931919223372036854775809";
        assertThrows(LexerIntegerOverflowException.class, () -> 
            ParserFactory.parseExpr(veryLargeNegInt)
        );
    }
}
