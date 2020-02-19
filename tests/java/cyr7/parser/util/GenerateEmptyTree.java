package cyr7.parser.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import cyr7.ast.*;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.ast.stmt.VarDeclStmtNode;
import cyr7.ast.type.TypeExprNode;

public class GenerateEmptyTree {
    LinkedList<VarDeclNode> args;
    LinkedList<UseNode> uses;
    LinkedList<TypeExprNode> returnTypes;
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
    
    public XiProgramNode addReturnType(TypeExprNode t) {
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
