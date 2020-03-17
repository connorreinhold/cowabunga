package cyr7.parser.xi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
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
    }

}
