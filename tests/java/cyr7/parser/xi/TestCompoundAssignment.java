package cyr7.parser.xi;

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
import cyr7.ast.stmt.CompoundAssignStmtNode;
import cyr7.ast.stmt.CompoundAssignStmtNode.CompoundOpType;
import cyr7.ast.stmt.StmtNode;
import cyr7.parser.util.ParserFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class TestCompoundAssignment {

    private Location LOC = new Location(-1, -1);

    private CompoundAssignStmtNode compAssign(CompoundOpType type, ExprNode lhs,
            ExprNode rhs) {
        return new CompoundAssignStmtNode(LOC, type, lhs, rhs);
    }

    private ExprNode constant(long num) {
        return new LiteralIntExprNode(LOC, Long.toString(num));
    }

    private ExprNode var(String id) {
        return new VariableAccessExprNode(LOC, id);
    }

    @Test
    void testBasicCompoundAssignment() throws Exception {
        List<StmtNode> expr = ParserFactory.parseStatement("a += a");
        assertEquals(
                List.of(compAssign(CompoundOpType.PLUS, var("a"), var("a"))),
                expr);

        expr = ParserFactory.parseStatement("a /= 12;");
        assertEquals(
                List.of(compAssign(CompoundOpType.DIV, var("a"), constant(12))),
                expr);
    }

    @Test
    void testComplexRHS() throws Exception {
        var expr = ParserFactory.parseStatement("a += f(1,2,4) + 34");
        assertEquals(
                List.of(compAssign(CompoundOpType.PLUS, var("a"),
                        new BinExprNode(LOC, ASTOpType.ADD,
                                new FunctionCallExprNode(LOC, "f",
                                        List.of(constant(1), constant(2),
                                                constant(4))),
                                constant(34)))),
                expr);
    }

    @Test
    void testLHS() throws Exception {
        var expr = ParserFactory.parseStatement("foo()[0] += {3, 2}");
        assertEquals(List.of(
                compAssign(CompoundOpType.PLUS, 
                        new ArrayAccessExprNode(LOC, 
                                new FunctionCallExprNode(LOC, "foo", 
                                        List.of()), constant(0)),
                        new LiteralArrayExprNode(LOC, 
                                List.of(constant(3), constant(2))))
        ), expr);
    }


}
