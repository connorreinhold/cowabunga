package cyr7.parser.util;

import cyr7.ast.FunctionDeclNode;
import cyr7.ast.FunctionHeaderDeclNode;
import cyr7.ast.UseNode;
import cyr7.ast.XiProgramNode;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.ast.stmt.VarDeclNode;
import cyr7.ast.type.ITypeExprNode;

import java.util.LinkedList;

public class ASTBuilder {
    LinkedList<VarDeclNode> args;
    LinkedList<UseNode> uses;
    LinkedList<ITypeExprNode> returnTypes;
    FunctionHeaderDeclNode functionHeader;
    LinkedList<StmtNode> statements;
    BlockStmtNode block;
    FunctionDeclNode function;
    LinkedList<FunctionDeclNode> functions;
    XiProgramNode tree;

    public ASTBuilder() {
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
