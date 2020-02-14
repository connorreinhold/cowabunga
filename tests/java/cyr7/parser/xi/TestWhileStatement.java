package cyr7.parser.xi;

import cyr7.ast.expr.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.binexpr.LTExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.stmt.*;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestWhileStatement {

    @Test
    void testSingleLineWhileStatement() throws Exception {
        StmtNode expected = new WhileStmtNode(
            null,
            new LTExprNode(
                null,
                new VariableAccessExprNode(
                    null,
                    "a"
                ),
                new VariableAccessExprNode(
                    null,
                    "b"
                )
            ),
            new AssignmentStmtNode(
                null,
                new VariableAssignAccessNode(null, "a"),
                new AddExprNode(
                    null,
                    new VariableAccessExprNode(
                        null,
                        "a"
                    ),
                    new LiteralIntExprNode(
                        null,
                        "1"
                    )
                )
            )
        );
        StmtNode statement = ParserFactory
                .parseStatement("while (a < b) a = " + "a + 1; ").get(0);
        assertEquals(expected, statement);
        statement = ParserFactory
            .parseStatement("while (a < b) a = a + 1 \n return 0 ").get(0);
        assertEquals(expected, statement);

        statement = ParserFactory
                .parseStatement("while (a < b) { a = a + 1; }; ").get(0);
        assertEquals(statement, new WhileStmtNode(
                null,
            new LTExprNode(
                null,
                new VariableAccessExprNode(
                    null,
                    "a"
                ),
                new VariableAccessExprNode(
                    null,
                    "b"
                )
            ),
            new BlockStmtNode(
                null,
                List.of(
                    new AssignmentStmtNode(
                        null,
                        new VariableAssignAccessNode(null, "a"),
                        new AddExprNode(
                            null,
                            new VariableAccessExprNode(
                                null,
                                "a"
                            ),
                            new LiteralIntExprNode(
                                null,
                                "1"
                            )
                        )
                    )
                )
            )
        ));
    }

}
