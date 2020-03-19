package cyr7.parser.xi;

import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.binexpr.LTExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.stmt.*;
import cyr7.exceptions.parser.UnexpectedTokenException;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static cyr7.parser.util.ParserFactory.LOC;

public class TestWhileStatement {

    @Test
    void testSingleLineWhileStatement() throws Exception {
        StmtNode expected = new WhileStmtNode(
            LOC,
            new LTExprNode(
                LOC,
                new VariableAccessExprNode(
                    LOC,
                    "a"
                ),
                new VariableAccessExprNode(
                    LOC,
                    "b"
                )
            ),
            new AssignmentStmtNode(
                LOC,
                new VariableAccessExprNode(LOC, "a"),
                new AddExprNode(
                    LOC,
                    new VariableAccessExprNode(
                        LOC,
                        "a"
                    ),
                    new LiteralIntExprNode(
                        LOC,
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
                LOC,
            new LTExprNode(
                LOC,
                new VariableAccessExprNode(
                    LOC,
                    "a"
                ),
                new VariableAccessExprNode(
                    LOC,
                    "b"
                )
            ),
            new BlockStmtNode(
                LOC,
                List.of(
                    new AssignmentStmtNode(
                        LOC,
                        new VariableAccessExprNode(LOC, "a"),
                        new AddExprNode(
                            LOC,
                            new VariableAccessExprNode(
                                LOC,
                                "a"
                            ),
                            new LiteralIntExprNode(
                                LOC,
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
            assertEquals(13, e.column);
        }

        try {
            ParserFactory.parseStatement("while (i < j) return 0;");
        } catch (UnexpectedTokenException e) {
            assertEquals(15, e.column);
        }

        StmtNode statement =
            ParserFactory.parseStatement("while b { return 0; }").get(0);
        assertEquals(new WhileStmtNode(
            LOC,
            new VariableAccessExprNode(LOC, "b"),
            new BlockStmtNode(
                LOC,
                List.of(
                    new ReturnStmtNode(
                        LOC,
                        List.of(
                            new LiteralIntExprNode(LOC, "0")
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
            LOC,
            new VariableAccessExprNode(LOC, "c"),
            new BlockStmtNode(
                LOC,
                List.of(
                    new AssignmentStmtNode(
                        LOC,
                        new VariableAccessExprNode(LOC, "b"),
                    new LiteralBoolExprNode(LOC, true)
                    ),
                    new ReturnStmtNode(
                        LOC,
                        List.of(
                            new LiteralIntExprNode(LOC, "0")
                        )
                    )
                )
            )
        ), statement);
    }

}
