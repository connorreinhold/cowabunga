package cyr7.parser.xi;

import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.ast.stmt.VarDeclNode;
import cyr7.ast.stmt.VarDeclStmtNode;
import cyr7.ast.type.PrimitiveEnum;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.exceptions.ParserException;
import cyr7.exceptions.UnexpectedTokenException;
import cyr7.parser.ParserUtil;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import javax.lang.model.type.PrimitiveType;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestVariableDeclarationStatement {

    @Test
    void testSingleVariableDeclaration() throws Exception {
        StmtNode statement =
            ParserFactory.parseStatement("a: int = x").get(0);
        assertEquals(new VarDeclStmtNode(
            null,
            List.of(
                Optional.of(new VarDeclNode(
                    null,
                    "a",
                    new PrimitiveTypeNode(
                        null,
                        PrimitiveEnum.INT)
                ))
            ),
            Optional.of(
                new VariableAccessExprNode(
                    null,
                    "x"
                )
            )
        ), statement);

        statement =
            ParserFactory.parseStatement("b: int[][] = x").get(0);
        assertEquals(new VarDeclStmtNode(
            null,
            List.of(
                Optional.of(new VarDeclNode(
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
                ))
            ),
            Optional.of(
                new VariableAccessExprNode(
                    null,
                    "x"
                )
            )
        ), statement);

        statement =
            ParserFactory.parseStatement("c: bool[][]").get(0);
        assertEquals(new VarDeclStmtNode(
            null,
            List.of(
                Optional.of(new VarDeclNode(
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
                ))
            ),
            Optional.empty()
        ), statement);
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
    void testMultiVarDeclarations() throws Exception {
        StmtNode statement =
            ParserFactory.parseStatement("a: int, b: bool").get(0);
        assertEquals(statement, new VarDeclStmtNode(
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
            Optional.empty()
        ));

        statement =
            ParserFactory.parseStatement("a: int, _ = f()").get(0);
        assertEquals(statement, new VarDeclStmtNode(
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
            Optional.of(
                new FunctionCallExprNode(
                    null,
                    "f",
                    List.of()
                )
            )
        ));

        statement =
            ParserFactory.parseStatement("_, _ = f()").get(0);
        assertEquals(statement, new VarDeclStmtNode(
            null,
            List.of(
                Optional.empty(),
                Optional.empty()
            ),
            Optional.of(
                new FunctionCallExprNode(
                    null,
                    "f",
                    List.of()
                )
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
        assertEquals(statement, new VarDeclStmtNode(
                null,
                List.of(
                    Optional.of(new VarDeclNode(
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
                    ))
                ),
                Optional.empty()
            )
        );

        statement =
            ParserFactory.parseStatement("a: int[5][]").get(0);
        assertEquals(statement, new VarDeclStmtNode(
                null,
                List.of(
                    Optional.of(new VarDeclNode(
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
                    ))
                ),
                Optional.empty()
            )
        );

        statement =
            ParserFactory.parseStatement("a: int[5 + i][4 + j]").get(0);
        assertEquals(statement, new VarDeclStmtNode(
                null,
                List.of(
                    Optional.of(new VarDeclNode(
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
                    ))
                ),
                Optional.empty()
            )
        );
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
    }

}
