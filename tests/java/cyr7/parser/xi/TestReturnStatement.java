package cyr7.parser.xi;

import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.stmt.ReturnStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.exceptions.parser.UnexpectedTokenException;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static cyr7.parser.util.ParserFactory.LOC;

public class TestReturnStatement {

    @Test
    void testForbidMultipleSemicolonsAfterReturn() throws Exception {
        try {
            ParserFactory.parseStatement("return ;;");
        } catch (UnexpectedTokenException e) {
            assertEquals(9, e.column);
        }

        try {
            ParserFactory.parseStatement("return 1 + 2;;");
        } catch (UnexpectedTokenException e) {
            assertEquals(14, e.column);
        }
    }

    @Test
    void testSemicolonReturnInteraction() throws Exception {
        StmtNode statement =
            ParserFactory.parseStatement("return x").get(0);
        assertEquals(new ReturnStmtNode(
            LOC,
            List.of(
                new VariableAccessExprNode(LOC, "x")
            )
        ), statement);

        statement =
            ParserFactory.parseStatement("return x;").get(0);
        assertEquals(new ReturnStmtNode(
            LOC,
            List.of(
                new VariableAccessExprNode(LOC, "x")
            )
        ), statement);

        statement =
            ParserFactory.parseStatement("return x, y;").get(0);
        assertEquals(new ReturnStmtNode(
            LOC,
            List.of(
                new VariableAccessExprNode(LOC, "x"),
                new VariableAccessExprNode(LOC, "y")
            )
        ),statement);
    }

}
