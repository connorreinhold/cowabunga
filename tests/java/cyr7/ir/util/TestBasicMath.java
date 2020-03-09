package cyr7.ir.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.ast.AbstractNode;
import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.stmt.AssignmentStmtNode;
import cyr7.ast.stmt.BlockStmtNode;
import java_cup.runtime.ComplexSymbolFactory.Location;

class TestBasicMath {

    void test(long expected, AbstractNode toplevel) {

        assertEquals(expected,
                IRFactory.testExpr(IRFactory.parseAstStmt(toplevel)));
    }

    @Test
    void testFai() {
        Location loc = new Location(-1, -1);
        /*test(0, new BlockStmtNode(loc, List.of(
                new AssignmentStmtNode(loc, new VariableAccessExprNode(loc, "x"), new LiteralIntExprNode(loc, "12")),
                new AssignmentStmtNode(loc, new VariableAccessExprNode(loc, "x"), new AddExprNode(loc, LiteralIntExprNode(loc, "12"), VariableAccessExprNode(loc, "x"))
                )));*/
    }

}
