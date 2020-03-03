package cyr7.parser.xi;

import cyr7.ast.toplevel.FunctionDeclNode;
import cyr7.ast.toplevel.FunctionHeaderDeclNode;
import cyr7.ast.toplevel.XiProgramNode;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.ReturnStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.ast.type.TypeExprNode;
import cyr7.ast.type.PrimitiveEnum;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.parser.XiParser;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static cyr7.parser.util.ParserFactory.LOC;

class TestFunctionDeclarations {

    XiProgramNode expected;
    XiParser parser;
    Object tree;
    String program;
    
    @Test
    void test() throws Exception {
        program = "main(): int { return 0; }";
        LinkedList<FunctionDeclNode> funcs = new LinkedList<>();
        LinkedList<TypeExprNode> retTypes = new LinkedList<>();
        LinkedList<ExprNode> retExpr = new LinkedList<>();
        retExpr.add(new LiteralIntExprNode(LOC, "0"));
        StmtNode stmt = new ReturnStmtNode(LOC, retExpr);
        LinkedList<StmtNode> stmts = new LinkedList<>();
        stmts.add(stmt);
        BlockStmtNode block = new BlockStmtNode(LOC, stmts);
        Collections.addAll(retTypes,
            new PrimitiveTypeNode(LOC, PrimitiveEnum.INT));
        
        Collections.addAll(
                funcs, new FunctionDeclNode(LOC,
                        new FunctionHeaderDeclNode(LOC, "main",
                                new LinkedList<>(),
                                retTypes),
                        block));
        expected = new XiProgramNode(LOC, new LinkedList<>(), funcs);
        parser = ParserFactory.make(program, false);
        tree = parser.parse().value;
        assertEquals(expected, tree);    
    }
    
}
