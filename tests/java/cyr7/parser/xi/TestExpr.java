package cyr7.parser.xi;

import static cyr7.parser.util.ParserFactory.LOC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.BinExprNode;
import cyr7.ast.expr.binexpr.BinExprNode.ASTOpType;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ast.expr.unaryexpr.LengthExprNode;
import cyr7.exceptions.lexer.LexerIntegerOverflowException;
import cyr7.exceptions.parser.ParserIntegerOverflowException;
import cyr7.parser.ParserUtil;
import cyr7.parser.util.ParserFactory;

class TestExpr {

    @Test
    void testIntOperations() throws Exception {
        String expr = "-1 + 1";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new BinExprNode(LOC, ASTOpType.ADD,
                new IntNegExprNode(LOC, new LiteralIntExprNode(LOC, "1")),
                new LiteralIntExprNode(LOC, "1"));
        assertEquals(parsed, expected);

        expr = "1 + 2 + 3";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.ADD,
                new BinExprNode(LOC, ASTOpType.ADD,
                        new LiteralIntExprNode(LOC, "1"),
                        new LiteralIntExprNode(LOC, "2")),
                new LiteralIntExprNode(LOC, "3"));
        assertEquals(parsed, expected);

        expr = "1 + (2 + 3)";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.ADD,
                new LiteralIntExprNode(LOC, "1"),
                new BinExprNode(LOC, ASTOpType.ADD,
                        new LiteralIntExprNode(LOC, "2"),
                        new LiteralIntExprNode(LOC, "3")));
        assertEquals(parsed, expected);

        expr = "1 / (2 * 3 / 4)";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.DIV,
                new LiteralIntExprNode(LOC, "1"),
                new BinExprNode(LOC, ASTOpType.DIV,
                        new BinExprNode(LOC, ASTOpType.MUL,
                                new LiteralIntExprNode(LOC, "2"),
                                new LiteralIntExprNode(LOC, "3")),
                        new LiteralIntExprNode(LOC, "4")));
        assertEquals(parsed, expected);

        expr = "1 *>> 2 + 3 / 4";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.ADD,
                new BinExprNode(LOC, ASTOpType.HIGH_MUL,
                        new LiteralIntExprNode(LOC, "1"),
                        new LiteralIntExprNode(LOC, "2")),
                new BinExprNode(LOC, ASTOpType.DIV,
                        new LiteralIntExprNode(LOC, "3"),
                        new LiteralIntExprNode(LOC, "4")));

        expr = "0 - -3 / 4 % 5";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.SUB,
                new LiteralIntExprNode(LOC, "0"),
                new BinExprNode(LOC, ASTOpType.MOD,
                        new BinExprNode(LOC, ASTOpType.DIV,
                                new IntNegExprNode(LOC,
                                        new LiteralIntExprNode(LOC, "3")),
                                new LiteralIntExprNode(LOC, "4")),
                        new LiteralIntExprNode(LOC, "5")));

        assertEquals(parsed, expected);
    }

    @Test
    void testIntComparators() throws Exception {
        String expr = "-1 > 1";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new BinExprNode(LOC, ASTOpType.GT,
                new IntNegExprNode(LOC, new LiteralIntExprNode(LOC, "1")),
                new LiteralIntExprNode(LOC, "1"));
        assertEquals(parsed, expected);

        expr = "-2 < 1 + 1";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.LT,
                new IntNegExprNode(LOC, new LiteralIntExprNode(LOC, "2")),
                new BinExprNode(LOC, ASTOpType.ADD,
                        new LiteralIntExprNode(LOC, "1"),
                        new LiteralIntExprNode(LOC, "1")));
        assertEquals(parsed, expected);

        expr = "2 + function() >= 1 % 1";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.GEQ,
                new BinExprNode(LOC, ASTOpType.ADD,
                        new LiteralIntExprNode(LOC, "2"),
                        new FunctionCallExprNode(LOC, "hello",
                                new LinkedList<>())),
                new BinExprNode(LOC, ASTOpType.MOD,
                        new LiteralIntExprNode(LOC, "1"),
                        new LiteralIntExprNode(LOC, "1")));

        expr = "(4 *>> 6) <= 1 + 1";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.LEQ,
                new BinExprNode(LOC, ASTOpType.HIGH_MUL,
                        new LiteralIntExprNode(LOC, "4"),
                        new LiteralIntExprNode(LOC, "6")),
                new BinExprNode(LOC, ASTOpType.ADD,
                        new LiteralIntExprNode(LOC, "1"),
                        new LiteralIntExprNode(LOC, "1")));
        assertEquals(parsed, expected);

        expr = "3 != hello()";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.NEQ,
                new LiteralIntExprNode(LOC, "3"),
                new FunctionCallExprNode(LOC, "hello", new LinkedList<>()));
        assertEquals(parsed, expected);

        expr = "-4 == hello()";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.EQ,
                new IntNegExprNode(LOC, new LiteralIntExprNode(LOC, "4")),
                new FunctionCallExprNode(LOC, "hello", new LinkedList<>()));
        assertEquals(parsed, expected);
    }

    @Test
    void testBoolOperations() throws Exception {
        String expr = "true | false & true";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new BinExprNode(LOC, ASTOpType.OR,
                new LiteralBoolExprNode(LOC, true),
                new BinExprNode(LOC, ASTOpType.AND,
                        new LiteralBoolExprNode(LOC, false),
                        new LiteralBoolExprNode(LOC, true)));
        assertEquals(parsed, expected);

        expr = "true | false | true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.OR,
                new BinExprNode(LOC, ASTOpType.OR,
                        new LiteralBoolExprNode(LOC, true),
                        new LiteralBoolExprNode(LOC, false)),
                new LiteralBoolExprNode(LOC, true));
        assertEquals(parsed, expected);

        expr = "(true | false) & true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.AND,
                new BinExprNode(LOC, ASTOpType.OR,
                        new LiteralBoolExprNode(LOC, true),
                        new LiteralBoolExprNode(LOC, false)),
                new LiteralBoolExprNode(LOC, true));
        assertEquals(parsed, expected);
    }

    @Test
    void testBoolComparators() throws Exception {
        String expr = "true == false & true";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new BinExprNode(LOC, ASTOpType.AND,
                new BinExprNode(LOC, ASTOpType.EQ,
                        new LiteralBoolExprNode(LOC, true),
                        new LiteralBoolExprNode(LOC, false)),
                new LiteralBoolExprNode(LOC, true));
        assertEquals(parsed, expected);

        expr = "true | false != true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.OR,
                new LiteralBoolExprNode(LOC, true),
                new BinExprNode(LOC, ASTOpType.NEQ,
                        new LiteralBoolExprNode(LOC, false),
                        new LiteralBoolExprNode(LOC, true)));
        assertEquals(parsed, expected);

        expr = "(true | false) != !true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BinExprNode(LOC, ASTOpType.NEQ,
                new BinExprNode(LOC, ASTOpType.OR,
                        new LiteralBoolExprNode(LOC, true),
                        new LiteralBoolExprNode(LOC, false)),
                new BoolNegExprNode(LOC, new LiteralBoolExprNode(LOC, true)));
        assertEquals(parsed, expected);

        expr = "!!!true";
        parsed = ParserFactory.parseExpr(expr);
        expected = new BoolNegExprNode(LOC, new BoolNegExprNode(LOC,
                new BoolNegExprNode(LOC, new LiteralBoolExprNode(LOC, true))));
        assertEquals(parsed, expected);
    }

    @Test
    void testVariables() throws Exception {
        String expr = "a + b";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new BinExprNode(LOC, ASTOpType.ADD,
                new VariableAccessExprNode(LOC, "a"),
                new VariableAccessExprNode(LOC, "b"));
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
                new BinExprNode(LOC, ASTOpType.ADD,
                        new LiteralIntExprNode(LOC, "2"),
                        new LiteralIntExprNode(LOC, "2")));
        assertEquals(parsed, expected);

        expr = "length(a[2+b])";
        parsed = ParserFactory.parseExpr(expr);
        ExprNode param = new ArrayAccessExprNode(LOC,
                new VariableAccessExprNode(LOC, "a"),
                new BinExprNode(LOC, ASTOpType.ADD,
                        new LiteralIntExprNode(LOC, "2"),
                        new VariableAccessExprNode(LOC, "b")));
        expected = new LengthExprNode(LOC, param);

        assertEquals(parsed, expected);
    }

    @Test
    void testFunctionCalls() throws Exception {
        String expr = "hello('a', b, 3, a[4], \"hello\")";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new FunctionCallExprNode(LOC, "hello",
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
        expected = new BinExprNode(LOC, ASTOpType.ADD,
                new FunctionCallExprNode(LOC, "a",
                        List.of(new BinExprNode(LOC, ASTOpType.ADD,
                                new VariableAccessExprNode(LOC, "b"),
                                new LiteralIntExprNode(LOC, "4")))),
                new BinExprNode(LOC, ASTOpType.HIGH_MUL,
                        new ArrayAccessExprNode(LOC,
                                new VariableAccessExprNode(LOC, "b"),
                                new VariableAccessExprNode(LOC, "a")),
                        new ArrayAccessExprNode(LOC,
                                new VariableAccessExprNode(LOC, "a"),
                                new BinExprNode(LOC, ASTOpType.ADD,
                                        new VariableAccessExprNode(LOC, "b"),
                                        new VariableAccessExprNode(LOC,
                                                "a")))));
        ParserUtil.printSExpr(parsed);
        ParserUtil.printSExpr(expected);
        assertEquals(parsed, expected);
    }

    @Test
    void testMisc() throws Exception {
        String expr = "{1,2,3,4,5}";
        ExprNode parsed = ParserFactory.parseExpr(expr);
        ExprNode expected = new LiteralArrayExprNode(LOC,
                List.of(new LiteralIntExprNode(LOC, "1"),
                        new LiteralIntExprNode(LOC, "2"),
                        new LiteralIntExprNode(LOC, "3"),
                        new LiteralIntExprNode(LOC, "4"),
                        new LiteralIntExprNode(LOC, "5")));
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
        assertThrows(ParserIntegerOverflowException.class,
                () -> ParserFactory.parseExpr(largeExpr));

        final String largeNegInt = "-9223372036854775809";
        assertThrows(LexerIntegerOverflowException.class,
                () -> ParserFactory.parseExpr(largeNegInt));

        final String veryLargeInt = "99129428931919223372036854775809";
        assertThrows(LexerIntegerOverflowException.class,
                () -> ParserFactory.parseExpr(veryLargeInt));

        final String veryLargeNegInt = "-99129428931919223372036854775809";
        assertThrows(LexerIntegerOverflowException.class,
                () -> ParserFactory.parseExpr(veryLargeNegInt));
    }
}
