package cyr7.parser.xi;

import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.LTExprNode;
import cyr7.ast.expr.binexpr.SubExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.stmt.*;
import cyr7.exceptions.parser.UnexpectedTokenException;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static cyr7.parser.util.ParserFactory.LOC;

public class TestIfElseStatement {

    @Test
    void testIfElseAndReturnInteraction() throws Exception {
        StmtNode statement = ParserFactory.parseStatement("return").get(0);
        assertEquals(statement, new ReturnStmtNode(LOC, new LinkedList<>()));

        statement =
            ParserFactory.parseStatement("if false { return false } else { " +
                "return true }").get(0);
        assertEquals(statement, new IfElseStmtNode(
            LOC,
            new LiteralBoolExprNode(
                LOC,
                false),
            new BlockStmtNode(
                LOC,
                List.of(
                    new ReturnStmtNode(
                        LOC,
                        List.of(
                            new LiteralBoolExprNode(
                                LOC,
                                false
                            )
                        )
                    )
                )
            ),
            Optional.of(
                new BlockStmtNode(
                    LOC,
                    List.of(
                        new ReturnStmtNode(
                            LOC,
                            List.of(
                                new LiteralBoolExprNode(
                                    LOC,
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
            LOC,
            new LiteralBoolExprNode(
                LOC,
                false
            ),
            new BlockStmtNode(
                LOC,
                List.of(
                    new ReturnStmtNode(
                        LOC,
                        List.of(
                            new LiteralBoolExprNode(
                                LOC,
                                false
                            )
                        )
                    ))
            ),
            Optional.empty()
        ));
        assertEquals(statements.get(1), new ReturnStmtNode(
            LOC,
            List.of(
                new LiteralBoolExprNode(
                    LOC,
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
            LOC,
            new LiteralBoolExprNode(LOC, true),
            new BlockStmtNode(
                LOC,
                List.of(
                    new ReturnStmtNode(
                        LOC,
                        List.of()
                    )
                )
            ),
            Optional.empty()
        ), statement);

        statement = ParserFactory.parseStatement("if (a < b) b = b - a; else " +
            "a = a - b;").get(0);
        assertEquals(new IfElseStmtNode(
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
                LOC, new VariableAccessExprNode(LOC, "b"),
                new SubExprNode(
                    LOC,
                    new VariableAccessExprNode(
                        LOC,
                        "b"
                    ),
                    new VariableAccessExprNode(
                        LOC,
                        "a"
                    )
                )
            ),
            Optional.of(
                new AssignmentStmtNode(
                    LOC,
                    
                    new VariableAccessExprNode(LOC, "a"),
                    
                    new SubExprNode(
                        LOC,
                        new VariableAccessExprNode(
                            LOC,
                            "a"
                        ),
                        new VariableAccessExprNode(
                            LOC,
                            "b"
                        )
                    )
                )
            )
        ), statement);
    }

}
