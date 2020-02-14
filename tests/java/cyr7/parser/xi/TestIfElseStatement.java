package cyr7.parser.xi;

import cyr7.ast.XiProgramNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.IfElseStmtNode;
import cyr7.ast.stmt.ReturnStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.exceptions.UnexpectedTokenException;
import cyr7.parser.XiParser;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.parser.Parser;
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
                ParserFactory.parseStatement("if false return false else return true").get(0);
        assertEquals(statement, new IfElseStmtNode(
                null,
                new LiteralBoolExprNode(
                        null,
                        false),
                new ReturnStmtNode(
                        null,
                        List.of(
                                new LiteralBoolExprNode(
                                        null,
                                        false
                                )
                        )
                ),
                Optional.of(
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
        ));

        List<StmtNode> statements = ParserFactory.parseStatement("if false return false " +
                "return true");
        assertEquals(statements.get(0), new IfElseStmtNode(
                null,
                new LiteralBoolExprNode(
                        null,
                        false
                ),
                new ReturnStmtNode(
                        null,
                        List.of(
                                new LiteralBoolExprNode(
                                        null,
                                        false
                                )
                        )
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
                ParserFactory.parseStatement("if a < b { return false return true }"));
    }

    @Test
    void testIfElseAndSemicolonInteraction() throws Exception {
        StmtNode statement = ParserFactory.parseStatement("if (true) { return; };").get(0);
        assertEquals(statement, new IfElseStmtNode(
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
        ));
    }

}
