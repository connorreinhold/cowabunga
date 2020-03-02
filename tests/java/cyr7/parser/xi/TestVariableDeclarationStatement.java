package cyr7.parser.xi;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.stmt.*;
import cyr7.ast.type.PrimitiveEnum;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.exceptions.parser.ParserException;
import cyr7.exceptions.parser.UnexpectedTokenException;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestVariableDeclarationStatement {

    @Test
    void testVarInitStmtNode() throws Exception {
        StmtNode statement =
            ParserFactory.parseStatement("a: int = x").get(0);
        assertEquals(new VarInitStmtNode(
            null,
            new VarDeclNode(
                null,
                "a",
                new PrimitiveTypeNode(
                    null,
                    PrimitiveEnum.INT
                )
            ),
            new VariableAccessExprNode(
                null,
                "x"
            )
        ), statement);

        statement =
            ParserFactory.parseStatement("b: int[][] = x").get(0);
        assertEquals(new VarInitStmtNode(
            null,
            new VarDeclNode(
                null,
                "b",
                new TypeExprArrayNode(
                    null,
                    new TypeExprArrayNode(
                        null,
                        new PrimitiveTypeNode(
                            null,
                            PrimitiveEnum.INT
                        ),
                        Optional.empty()
                    ),
                    Optional.empty()
                )
            ),
            new VariableAccessExprNode(
                null,
                "x"
            )
        ), statement);

        statement = ParserFactory.parseStatement("i: int = c[3];").get(0);
        assertEquals(statement, new VarInitStmtNode(
            null,
            new VarDeclNode(
                null,
                "i",
                new PrimitiveTypeNode(
                    null,
                    PrimitiveEnum.INT
                )
            ),
            new ArrayAccessExprNode(
                null,
                new VariableAccessExprNode(
                    null,
                    "c"
                ),
                new LiteralIntExprNode(
                    null,
                    "3"
                )
            )
        ));
    }

    @Test
    void testVarDeclStmtNode() throws Exception {
        StmtNode statement =
            ParserFactory.parseStatement("z: int").get(0);
        assertEquals(new VarDeclStmtNode(
            null,
            new VarDeclNode(
                null,
                "z",
                new PrimitiveTypeNode(
                    null,
                    PrimitiveEnum.INT
                )
            )
        ), statement);

        statement =
            ParserFactory.parseStatement("c: bool[][]").get(0);
        assertEquals(new VarDeclStmtNode(
            null,
            new VarDeclNode(
                null,
                "c",
                new TypeExprArrayNode(
                    null,
                    new TypeExprArrayNode(
                        null,
                        new PrimitiveTypeNode(
                            null,
                            PrimitiveEnum.BOOL
                        ),
                        Optional.empty()
                    ),
                    Optional.empty()
                )
            )
        ), statement);
    }

    @Test
    void testSingleVariableDeclaration() throws Exception {
    }

    @Test
    void testSingleVarDeclarationsInvalidExamples() {
        assertThrows(UnexpectedTokenException.class,
            () -> ParserFactory.parseStatement("_"),
            "Single var decl statements cannot be empty"
        );

        assertThrows(UnexpectedTokenException.class,
            () -> ParserFactory.parseStatement("a: (int[][], bool)"),
            "Invalid type declaration"
        );
    }

    @Test
    void testMultiAssignDeclarations() throws Exception {
        StmtNode statement =
            ParserFactory.parseStatement("a: int, b: bool = function()").get(0);
        assertEquals(statement, new MultiAssignStmtNode(
            null,
            List.of(
                Optional.of(
                    new VarDeclNode(
                        null,
                        "a",
                        new PrimitiveTypeNode(
                            null,
                            PrimitiveEnum.INT
                        )
                    )
                ),
                Optional.of(
                    new VarDeclNode(
                        null,
                        "b",
                        new PrimitiveTypeNode(
                            null,
                            PrimitiveEnum.BOOL
                        )
                    )
                )
            ),
            new FunctionCallExprNode(
                null,
                "function",
                List.of()
            )
        ));

        statement =
            ParserFactory.parseStatement("a: int, _ = f()").get(0);
        assertEquals(statement, new MultiAssignStmtNode(
            null,
            List.of(
                Optional.of(
                    new VarDeclNode(
                        null,
                        "a",
                        new PrimitiveTypeNode(
                            null,
                            PrimitiveEnum.INT
                        )
                    )
                ),
                Optional.empty()
            ),
            new FunctionCallExprNode(
                null,
                "f",
                List.of()
            )
        ));

        statement =
            ParserFactory.parseStatement("_, _ = f()").get(0);
        assertEquals(statement, new MultiAssignStmtNode(
            null,
            List.of(
                Optional.empty(),
                Optional.empty()
            ),
            new FunctionCallExprNode(
                null,
                "f",
                List.of()
            )
        ));
    }

    @Test
    void testMultiVarDeclarationsInvalidExamples() {
        assertThrows(UnexpectedTokenException.class,
            () -> ParserFactory.parseStatement("_, _"),
            "Multi var decl statements must have a initialization expr"
        );

        assertThrows(UnexpectedTokenException.class,
            () -> ParserFactory.parseStatement("_, _ = x"),
            "Multi var decl statements must have a function call expr"
        );

        assertThrows(UnexpectedTokenException.class,
            () -> ParserFactory.parseStatement("a: int[5][6], b: bool[5][7]"),
            "Multi var decl statements cannot declare arrays"
        );
    }

    @Test
    void testArrayVarDeclarations() throws Exception {
        StmtNode statement =
            ParserFactory.parseStatement("a: int[5]").get(0);
        assertEquals(statement, new ArrayDeclStmtNode(
            null,
            "a",
            new TypeExprArrayNode(
                null,
                new PrimitiveTypeNode(
                    null,
                    PrimitiveEnum.INT
                ),
                Optional.of(
                    new LiteralIntExprNode(
                        null,
                        "5"
                    )
                )
            )
        ));

        statement =
            ParserFactory.parseStatement("a: int[5][]").get(0);
        assertEquals(statement, new ArrayDeclStmtNode(
            null,
            "a",
            new TypeExprArrayNode(
                null,
                new TypeExprArrayNode(
                    null,
                    new PrimitiveTypeNode(
                        null,
                        PrimitiveEnum.INT
                    ),
                    Optional.empty()
                ),
                Optional.of(
                    new LiteralIntExprNode(
                        null,
                        "5"
                    )
                )
            )
        ));

        statement =
            ParserFactory.parseStatement("a: int[5 + i][4 + j]").get(0);
        assertEquals(statement, new ArrayDeclStmtNode(
            null,
            "a",
            new TypeExprArrayNode(
                null,
                new TypeExprArrayNode(
                    null,
                    new PrimitiveTypeNode(
                        null,
                        PrimitiveEnum.INT
                    ),
                    Optional.of(
                        new AddExprNode(
                            null,
                            new LiteralIntExprNode(
                                null,
                                "4"
                            ),
                            new VariableAccessExprNode(
                                null,
                                "j"
                            )
                        )
                    )
                ),
                Optional.of(
                    new AddExprNode(
                        null,
                        new LiteralIntExprNode(
                            null,
                            "5"
                        ),
                        new VariableAccessExprNode(
                            null,
                            "i"
                        )
                    )
                )
            )
        ));
    }

    @Test
    void testArrayVarDeclarationsInvalidExamples() {
        assertThrows(ParserException.class,
            () -> ParserFactory.parseStatement("a: bool[][5]"));

        assertThrows(ParserException.class,
            () -> ParserFactory.parseStatement("a: int[][5][]"));

        assertThrows(ParserException.class,
            () -> ParserFactory.parseStatement("a: int[x][][5]"));

        assertThrows(ParserException.class,
            () -> ParserFactory.parseStatement("a: [x][][5]"));

        assertThrows(ParserException.class,
            () -> ParserFactory.parseStatement("a: [x][][] = j"));

        assertThrows(ParserException.class,
            () -> ParserFactory.parseStatement("a: [x][][5] = j"));
    }

}
