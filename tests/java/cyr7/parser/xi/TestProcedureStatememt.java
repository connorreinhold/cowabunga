package cyr7.parser.xi;

import cyr7.ast.expr.ArrayExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.stmt.ProcedureStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProcedureStatememt {

    @Test
    void testProcedureStatement() throws Exception {
        StmtNode statement = ParserFactory.parseStatement("f()").get(0);
        assertEquals(statement, new ProcedureStmtNode(
            null,
            new FunctionCallExprNode(
                null,
                "f",
                List.of()
            )
        ));

        statement = ParserFactory.parseStatement("length({5, 10, 15})").get(0);
        assertEquals(statement, new ProcedureStmtNode(
            null,
            new FunctionCallExprNode(
                null,
                "length",
                List.of(
                    new ArrayExprNode(
                        null,
                        List.of(
                            new LiteralIntExprNode(
                                null,
                                "5"
                            ),
                            new LiteralIntExprNode(
                                null,
                                "10"
                            ),
                            new LiteralIntExprNode(
                                null,
                                "15"
                            )
                        )
                    )
                )
            )
        ));
    }

}
