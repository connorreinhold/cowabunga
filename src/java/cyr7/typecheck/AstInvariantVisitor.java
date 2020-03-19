package cyr7.typecheck;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.BinExprNode;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ast.expr.unaryexpr.LengthExprNode;
import cyr7.ast.stmt.ArrayDeclStmtNode;
import cyr7.ast.stmt.AssignmentStmtNode;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.CompoundAssignStmtNode;
import cyr7.ast.stmt.ExprStmtNode;
import cyr7.ast.stmt.IfElseStmtNode;
import cyr7.ast.stmt.MultiAssignStmtNode;
import cyr7.ast.stmt.ProcedureStmtNode;
import cyr7.ast.stmt.ReturnStmtNode;
import cyr7.ast.stmt.VarDeclStmtNode;
import cyr7.ast.stmt.VarInitStmtNode;
import cyr7.ast.stmt.WhileStmtNode;
import cyr7.ast.toplevel.FunctionDeclNode;
import cyr7.ast.toplevel.FunctionHeaderDeclNode;
import cyr7.ast.toplevel.IxiProgramNode;
import cyr7.ast.toplevel.UseNode;
import cyr7.ast.toplevel.XiProgramNode;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.visitor.AbstractVisitor;
import cyr7.visitor.PostOrderReduceTraversal;

class AstInvariantVisitor extends AbstractVisitor<Boolean>
        implements PostOrderReduceTraversal.ReduceVisitor<Boolean> {

    @Override
    public Boolean unit() {
        return true;
    }

    @Override
    public Boolean combine(Boolean left, Boolean right) {
        return left && right;
    }

    @Override
    public Boolean visit(FunctionDeclNode n) {
        return true;
    }

    @Override
    public Boolean visit(FunctionHeaderDeclNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(IxiProgramNode n) {
        return true;
    }

    @Override
    public Boolean visit(UseNode n) {
        return true;
    }

    @Override
    public Boolean visit(VarDeclNode n) {
        return true;
    }

    @Override
    public Boolean visit(XiProgramNode n) {
        return true;
    }

    @Override
    public Boolean visit(PrimitiveTypeNode n) {
        return true;
    }

    @Override
    public Boolean visit(TypeExprArrayNode n) {
        return true;
    }

    @Override
    public Boolean visit(ArrayDeclStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(AssignmentStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(BlockStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(ExprStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(IfElseStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(MultiAssignStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(ProcedureStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(ReturnStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(VarDeclStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(VarInitStmtNode n) {
        return true;
    }

    @Override
    public Boolean visit(WhileStmtNode n) {
        return true;
    }

    // Expressions

    @Override
    public Boolean visit(FunctionCallExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(ArrayAccessExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(VariableAccessExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(BinExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(LiteralArrayExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(LiteralBoolExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(LiteralCharExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(LiteralIntExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(LiteralStringExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(BoolNegExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(IntNegExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(LengthExprNode n) {
        return n.getType() != null;
    }

    @Override
    public Boolean visit(CompoundAssignStmtNode n) {
        return n.lhs != null && n.rhs != null && n.opType != null;
    }
}
