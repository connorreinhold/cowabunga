package cyr7.parser.xi;

import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.ReturnStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.ast.stmt.WhileStmtNode;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static cyr7.parser.util.ParserFactory.LOC;

public class TestBlockStatement {

    @Test
    void testNestedBlockStatement() throws Exception {
        StmtNode statement =
            ParserFactory
                .parseStatement("{ { }; { return }; return };")
                .get(0);
        assertEquals(statement, new BlockStmtNode(
            LOC,
            List.of(
                new BlockStmtNode(
                    LOC,
                    List.of()
                ),
                new BlockStmtNode(
                    LOC,
                    List.of(
                        new ReturnStmtNode(
                            LOC,
                            List.of()
                        )
                    )
                ),
                new ReturnStmtNode(
                    LOC,
                    List.of()
                )
            )
        ));

        statement = ParserFactory.parseStatement("{ while x { }; };").get(0);
        assertEquals(statement, new BlockStmtNode(
            LOC,
            List.of(
                new WhileStmtNode(
                    LOC,
                    new VariableAccessExprNode(
                        LOC,
                        "x"
                    ),
                    new BlockStmtNode(
                        LOC,
                        List.of()
                    )
                )
            )
        ));
    }

}
