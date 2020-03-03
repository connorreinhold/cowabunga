package cyr7.parser.xi;

import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.stmt.ProcedureStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static cyr7.parser.util.ParserFactory.LOC;

public class TestProcedureStatememt {

    @Test
    void testProcedureStatement() throws Exception {
        StmtNode statement = ParserFactory.parseStatement("f()").get(0);
        assertEquals(statement, new ProcedureStmtNode(
            LOC,
            new FunctionCallExprNode(
                LOC,
                "f",
                List.of()
            )
        ));

        statement = ParserFactory.parseStatement("lengthy({5, 10, 15})").get(0);
        assertEquals(statement, new ProcedureStmtNode(
            LOC,
            new FunctionCallExprNode(
                LOC,
                "lengthy",
                List.of(
                    new LiteralArrayExprNode(
                        LOC,
                        List.of(
                            new LiteralIntExprNode(
                                LOC,
                                "5"
                            ),
                            new LiteralIntExprNode(
                                LOC,
                                "10"
                            ),
                            new LiteralIntExprNode(
                                LOC,
                                "15"
                            )
                        )
                    )
                )
            )
        ));
    }

}
