package cyr7.parser.xi;

import cyr7.ast.FunctionDeclNode;
import cyr7.ast.FunctionHeaderDeclNode;
import cyr7.ast.XiProgramNode;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.ReturnStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.ast.stmt.VarDeclNode;
import cyr7.ast.type.ITypeExprNode;
import cyr7.ast.type.PrimitiveEnum;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.parser.XiParser;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestFunctionDeclarations {

    LinkedList<VarDeclNode> args;
    LinkedList<ITypeExprNode> returnTypes;
    FunctionHeaderDeclNode function;
    LinkedList<FunctionHeaderDeclNode> functions;
    XiProgramNode expected;
    StringReader prgm;
    XiParser parser;
    Object tree;
    String program;
    
    @Test
    void test() throws Exception {
        program = "main(): int { return 0; }";
        LinkedList<FunctionDeclNode> funcs = new LinkedList<>();
        LinkedList<ITypeExprNode> retTypes = new LinkedList<>();
        LinkedList<ExprNode> retExpr = new LinkedList<>();
        retExpr.add(new LiteralIntExprNode(null, "0"));
        StmtNode stmt = new ReturnStmtNode(null, retExpr);
        LinkedList<StmtNode> stmts = new LinkedList<>();
        stmts.add(stmt);
        BlockStmtNode block = new BlockStmtNode(null, stmts);
        Collections.addAll(retTypes, 
                new ITypeExprNode[] {
                    new PrimitiveTypeNode(null, PrimitiveEnum.INT)
                });
        
        Collections.addAll(
                funcs, new FunctionDeclNode[]{
                        new FunctionDeclNode(null,
                                new FunctionHeaderDeclNode(null, "main",
                                        new LinkedList<>(),
                                        retTypes),
                                block)
        });
        expected = new XiProgramNode(null, new LinkedList<>(), funcs);
        parser = ParserFactory.make(program, false);
        tree = parser.parse().value;
        assertEquals(expected, tree);    
    }
    
}