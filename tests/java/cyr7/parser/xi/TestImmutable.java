package cyr7.parser.xi;

import cyr7.ast.toplevel.FunctionDeclNode;
import cyr7.ast.toplevel.FunctionHeaderDeclNode;
import cyr7.ast.toplevel.XiProgramNode;
import cyr7.ast.stmt.BlockStmtNode;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestImmutable {

    @Test
    void testImmutable() {
        XiProgramNode programNode = new XiProgramNode(null, new LinkedList<>(), new LinkedList<>());
        assertThrows(UnsupportedOperationException.class,
            () -> programNode.functions.add(
            new FunctionDeclNode(
                null,
                new FunctionHeaderDeclNode(
                    null,
                    "whatever",
                    new LinkedList<>(),
                    new LinkedList<>()
                ),
                new BlockStmtNode(
                    null,
                    new LinkedList<>()
                )
            )
        ));
    }

}
