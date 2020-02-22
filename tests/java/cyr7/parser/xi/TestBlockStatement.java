package cyr7.parser.xi;

import cyr7.ast.VariableAccessNode;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.ReturnStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.ast.stmt.WhileStmtNode;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBlockStatement {

    @Test
    void testNestedBlockStatement() throws Exception {
        StmtNode statement =
            ParserFactory
                .parseStatement("{ { }; { return }; return };")
                .get(0);
        assertEquals(statement, new BlockStmtNode(
            null,
            List.of(
                new BlockStmtNode(
                    null,
                    List.of()
                ),
                new BlockStmtNode(
                    null,
                    List.of(
                        new ReturnStmtNode(
                            null,
                            List.of()
                        )
                    )
                ),
                new ReturnStmtNode(
                    null,
                    List.of()
                )
            )
        ));

        statement = ParserFactory.parseStatement("{ while x { }; };").get(0);
        assertEquals(statement, new BlockStmtNode(
            null,
            List.of(
                new WhileStmtNode(
                    null,
                    new VariableAccessNode(
                        null,
                        "x"
                    ),
                    new BlockStmtNode(
                        null,
                        List.of()
                    )
                )
            )
        ));
    }

}
