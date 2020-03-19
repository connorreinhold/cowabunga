package cyr7.ir.astToIr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import cyr7.C;
import cyr7.ast.AbstractNode;
import cyr7.ast.expr.binexpr.AndExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.stmt.IfElseStmtNode;
import cyr7.ast.stmt.ReturnStmtNode;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.util.IRFactory;

class TestStmt {

    @Test
    void testIfElseStmt() {
        AbstractNode astNode = new IfElseStmtNode(C.LOC,
            new AndExprNode(C.LOC, new LiteralBoolExprNode(C.LOC, false),
                new LiteralBoolExprNode(C.LOC, true)),
            new ReturnStmtNode(C.LOC,
                List.of(new LiteralIntExprNode(C.LOC, "4"))),
            Optional.of(new ReturnStmtNode(C.LOC,
                List.of(new LiteralIntExprNode(C.LOC, "5")))));
        IRStmt result = (IRStmt) IRFactory.parseAstStmt(astNode);
        long expectedResult = 5;
        assertEquals(expectedResult, IRFactory.testStmts(result));
    }

}
