package cyr7.parser.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import cyr7.ast.FunctionDeclNode;
import cyr7.ast.FunctionHeaderDeclNode;
import cyr7.ast.UseNode;
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

public class GenerateEmptyTree {
    LinkedList<VarDeclNode> args;
    LinkedList<UseNode> uses;
    LinkedList<ITypeExprNode> returnTypes;
    FunctionHeaderDeclNode functionHeader;
    LinkedList<StmtNode> statements;
    BlockStmtNode block;
    FunctionDeclNode function;
    LinkedList<FunctionDeclNode> functions;
    XiProgramNode tree;
    
    public GenerateEmptyTree() {
        args = new LinkedList<>();
        uses = new LinkedList<>();
        returnTypes = new LinkedList<>();
        functionHeader = new FunctionHeaderDeclNode(null, 
                                "main", args, returnTypes);
        statements = new LinkedList<>();
        block = new BlockStmtNode(null, statements);
        function = new FunctionDeclNode(null, functionHeader, null);
        functions = new LinkedList<>();
        functions.add(function);
        tree = new XiProgramNode(null, uses, functions);
    }
    
    public XiProgramNode create() {
        return tree;
    }
    
    public XiProgramNode addReturnType(ITypeExprNode t) {
        returnTypes.add(t);
        return tree;
    }
    
    public XiProgramNode addStatement(StmtNode s) {
        statements.add(s);
        return tree;
    }
    
    public XiProgramNode addArgs(VarDeclNode a) {
        this.args.add(a);
        return tree;
    }

    
}
