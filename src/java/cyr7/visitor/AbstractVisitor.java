package cyr7.visitor;

import cyr7.ast.*;
import cyr7.ast.expr.ArrayAccessExprNode;
import cyr7.ast.expr.ArrayExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.*;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ast.stmt.*;
import cyr7.ast.toplevel.*;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;

public abstract class AbstractVisitor<T> {

    // top-level
    abstract public T visit(FunctionDeclNode n);
    abstract public T visit(FunctionHeaderDeclNode n);
    abstract public T visit(IxiProgramNode n);
    abstract public T visit(UseNode n);
    abstract public T visit(VarDeclNode n);
    abstract public T visit(XiProgramNode n);

    // type
    abstract public T visit(PrimitiveTypeNode n);
    abstract public T visit(TypeExprArrayNode n);

    // stmt
    abstract public T visit(ArrayAssignAccessNode n);
    abstract public T visit(ArrayDeclStmtNode n);
    abstract public T visit(AssignmentStmtNode n);
    abstract public T visit(BlockStmtNode n);
    abstract public T visit(ExprStmtNode n);
    abstract public T visit(IfElseStmtNode n);
    abstract public T visit(MultiAssignStmtNode n);
    abstract public T visit(ProcedureStmtNode n);
    abstract public T visit(ReturnStmtNode n);
    abstract public T visit(VarDeclStmtNode n);
    abstract public T visit(VariableAssignAccessNode n);
    abstract public T visit(VarInitStmtNode n);
    abstract public T visit(WhileStmtNode n);

    // expr
    abstract public T visit(ArrayAccessExprNode n);
    abstract public T visit(ArrayExprNode n);
    abstract public T visit(FunctionCallExprNode n);
    abstract public T visit(VariableAccessExprNode n);

    // bin expr
    abstract public T visit(AddExprNode n);
    abstract public T visit(AndExprNode n);
    abstract public T visit(DivExprNode n);
    abstract public T visit(EqualsExprNode n);
    abstract public T visit(GTEExprNode n);
    abstract public T visit(GTExprNode n);
    abstract public T visit(HighMultExprNode n);
    abstract public T visit(LTEExprNode n);
    abstract public T visit(LTExprNode n);
    abstract public T visit(MultExprNode n);
    abstract public T visit(NotEqualsExprNode n);
    abstract public T visit(OrExprNode n);
    abstract public T visit(RemExprNode n);
    abstract public T visit(SubExprNode n);

    // literal expr
    abstract public T visit(LiteralBoolExprNode n);
    abstract public T visit(LiteralCharExprNode n);
    abstract public T visit(LiteralIntExprNode n);
    abstract public T visit(LiteralStringExprNode n);

    // unary expr
    abstract public T visit(BoolNegExprNode n);
    abstract public T visit(IntNegExprNode n);

}
