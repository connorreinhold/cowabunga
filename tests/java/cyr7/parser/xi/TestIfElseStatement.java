package cyr7.parser.xi;

import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.LTExprNode;
import cyr7.ast.expr.binexpr.SubExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.stmt.*;
import cyr7.ast.stmt.assign.ExprAssignNode;
import cyr7.exceptions.UnexpectedTokenException;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestIfElseStatement {

    @Test
    void testIfElseAndReturnInteraction() throws Exception {
        StmtNode statement = ParserFactory.parseStatement("return").get(0);
        assertEquals(statement, new ReturnStmtNode(null, new LinkedList<>()));

        statement =
            ParserFactory.parseStatement("if false { return false } else { " +
                "return true }").get(0);
        assertEquals(statement, new IfElseStmtNode(
            null,
            new LiteralBoolExprNode(
                null,
                false),
            new BlockStmtNode(
                null,
                List.of(
                    new ReturnStmtNode(
                        null,
                        List.of(
                            new LiteralBoolExprNode(
                                null,
                                false
                            )
                        )
                    )
                )
            ),
            Optional.of(
                new BlockStmtNode(
                    null,
                    List.of(
                        new ReturnStmtNode(
                            null,
                            List.of(
                                new LiteralBoolExprNode(
                                    null,
                                    true
                                )
                            )
                        )
                    )
                )
            )
        ));

        List<StmtNode> statements =
            ParserFactory.parseStatement(
                "if false { return false } return true"
            );
        assertEquals(statements.get(0), new IfElseStmtNode(
            null,
            new LiteralBoolExprNode(
                null,
                false
            ),
            new BlockStmtNode(
                null,
                List.of(
                    new ReturnStmtNode(
                        null,
                        List.of(
                            new LiteralBoolExprNode(
                                null,
                                false
                            )
                        )
                    ))
            ),
            Optional.empty()
        ));
        assertEquals(statements.get(1), new ReturnStmtNode(
            null,
            List.of(
                new LiteralBoolExprNode(
                    null,
                    true
                )
            )
        ));

        assertThrows(UnexpectedTokenException.class, () ->
            ParserFactory.parseStatement(
                "if a < b { return false return true }"
            ));

        assertThrows(UnexpectedTokenException.class, () ->
            ParserFactory.parseStatement(
                "if a < b { return a } else return b"
            ));
    }

    @Test
    void testIfElseAndSemicolonInteraction() throws Exception {
        StmtNode statement = ParserFactory.parseStatement("if (true) { " +
            "return; };").get(0);
        assertEquals(new IfElseStmtNode(
            null,
            new LiteralBoolExprNode(null, true),
            new BlockStmtNode(
                null,
                List.of(
                    new ReturnStmtNode(
                        null,
                        List.of()
                    )
                )
            ),
            Optional.empty()
        ), statement);

        statement = ParserFactory.parseStatement("if (a < b) b = b - a; else " +
            "a = a - b;").get(0);
        assertEquals(new IfElseStmtNode(
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
                new ExprAssignNode(
                    null,
                    new VariableAccessExprNode(null, "b")
                ),
                new SubExprNode(
                    null,
                    new VariableAccessExprNode(
                        null,
                        "b"
                    ),
                    new VariableAccessExprNode(
                        null,
                        "a"
                    )
                )
            ),
            Optional.of(
                new AssignmentStmtNode(
                    null,
                    new ExprAssignNode(
                        null,
                        new VariableAccessExprNode(null, "a")
                    ),
                    new SubExprNode(
                        null,
                        new VariableAccessExprNode(
                            null,
                            "a"
                        ),
                        new VariableAccessExprNode(
                            null,
                            "b"
                        )
                    )
                )
            )
        ), statement);
    }

}
