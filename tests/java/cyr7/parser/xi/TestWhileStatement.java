package cyr7.parser.xi;

import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.binexpr.LTExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.stmt.*;
import cyr7.ast.stmt.assign.VariableAssignNode;
import cyr7.exceptions.UnexpectedTokenException;
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
                new VariableAssignNode(null, "a"),
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
    }

    @Test
    void testWhileAndSemicolonInteraction() throws Exception {
        StmtNode statement = ParserFactory
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
                        new VariableAssignNode(null, "a"),
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

    @Test
    void testWhileAndReturnInteraction() throws Exception {
        try {
            ParserFactory.parseStatement("while i < j return");
        } catch (UnexpectedTokenException e) {
            assertEquals(13, e.column.get());
        }

        try {
            ParserFactory.parseStatement("while (i < j) return 0;");
        } catch (UnexpectedTokenException e) {
            assertEquals(15, e.column.get());
        }

        StmtNode statement =
            ParserFactory.parseStatement("while b { return 0; }").get(0);
        assertEquals(new WhileStmtNode(
            null,
            new VariableAccessExprNode(null, "b"),
            new BlockStmtNode(
                null,
                List.of(
                    new ReturnStmtNode(
                        null,
                        List.of(
                            new LiteralIntExprNode(null, "0")
                        )
                    )
                )
            )
        ), statement);

        statement =
            ParserFactory.parseStatement(
                "while c { b = true return 0 }"
            ).get(0);
        assertEquals(new WhileStmtNode(
            null,
            new VariableAccessExprNode(null, "c"),
            new BlockStmtNode(
                null,
                List.of(
                    new AssignmentStmtNode(
                        null,
                        new VariableAssignNode(null, "b"),
                        new LiteralBoolExprNode(null, true)
                    ),
                    new ReturnStmtNode(
                        null,
                        List.of(
                            new LiteralIntExprNode(null, "0")
                        )
                    )
                )
            )
        ), statement);
    }

}
