package cyr7.parser;

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
import cyr7.ast.stmt.*;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class SExpVisitor extends AbstractVisitor<Void> {

    @Override
    public Void visit(FunctionDeclNode n) {
        return null;
    }

    @Override
    public Void visit(FunctionHeaderDeclNode n) {
        return null;
    }

    @Override
    public Void visit(IxiProgramNode n) {
        return null;
    }

    @Override
    public Void visit(UseNode n) {
        return null;
    }

    @Override
    public Void visit(VarDeclNode n) {
        return null;
    }

    @Override
    public Void visit(XiProgramNode n) {
        return null;
    }

    @Override
    public Void visit(PrimitiveTypeNode n) {
        return null;
    }

    @Override
    public Void visit(TypeExprArrayNode n) {
        return null;
    }

    @Override
    public Void visit(ArrayAssignAccessNode n) {
        return null;
    }

    @Override
    public Void visit(ArrayDeclStmtNode n) {
        return null;
    }

    @Override
    public Void visit(AssignmentStmtNode n) {
        return null;
    }

    @Override
    public Void visit(BlockStmtNode n) {
        return null;
    }

    @Override
    public Void visit(ExprStmtNode n) {
        return null;
    }

    @Override
    public Void visit(IfElseStmtNode n) {
        return null;
    }

    @Override
    public Void visit(MultiAssignStmtNode n) {
        return null;
    }

    @Override
    public Void visit(ProcedureStmtNode n) {
        return null;
    }

    @Override
    public Void visit(ReturnStmtNode n) {
        return null;
    }

    @Override
    public Void visit(VarDeclStmtNode n) {
        return null;
    }

    @Override
    public Void visit(VariableAssignAccessNode n) {
        return null;
    }

    @Override
    public Void visit(VarInitStmtNode n) {
        return null;
    }

    @Override
    public Void visit(WhileStmtNode n) {
        return null;
    }

    @Override
    public Void visit(ArrayAccessExprNode n) {
        return null;
    }

    @Override
    public Void visit(ArrayExprNode n) {
        return null;
    }

    @Override
    public Void visit(FunctionCallExprNode n) {
        return null;
    }

    @Override
    public Void visit(VariableAccessExprNode n) {
        return null;
    }

    @Override
    public Void visit(LiteralBoolExprNode n) {
        return null;
    }

    @Override
    public Void visit(LiteralCharExprNode n) {
        return null;
    }

    @Override
    public Void visit(LiteralIntExprNode n) {
        return null;
    }

    @Override
    public Void visit(LiteralStringExprNode n) {
        return null;
    }

    @Override
    public Void visit(AddExprNode n) {
        return null;
    }

    @Override
    public Void visit(AndExprNode n) {
        return null;
    }

    @Override
    public Void visit(DivExprNode n) {
        return null;
    }

    @Override
    public Void visit(EqualsExprNode n) {
        return null;
    }

    @Override
    public Void visit(GTEExprNode n) {
        return null;
    }

    @Override
    public Void visit(GTExprNode n) {
        return null;
    }

    @Override
    public Void visit(HighMultExprNode n) {
        return null;
    }

    @Override
    public Void visit(LTEExprNode n) {
        return null;
    }

    @Override
    public Void visit(MultExprNode n) {
        return null;
    }

    @Override
    public Void visit(NotEqualsExprNode n) {
        return null;
    }

    @Override
    public Void visit(OrExprNode n) {
        return null;
    }

    @Override
    public Void visit(RemExprNode n) {
        return null;
    }

    @Override
    public Void visit(SubExprNode n) {
        return null;
    }
}
