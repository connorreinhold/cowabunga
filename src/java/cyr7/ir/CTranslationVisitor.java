package cyr7.ir;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.binexpr.AndExprNode;
import cyr7.ast.expr.binexpr.DivExprNode;
import cyr7.ast.expr.binexpr.EqualsExprNode;
import cyr7.ast.expr.binexpr.GTEExprNode;
import cyr7.ast.expr.binexpr.GTExprNode;
import cyr7.ast.expr.binexpr.HighMultExprNode;
import cyr7.ast.expr.binexpr.LTEExprNode;
import cyr7.ast.expr.binexpr.LTExprNode;
import cyr7.ast.expr.binexpr.MultExprNode;
import cyr7.ast.expr.binexpr.NotEqualsExprNode;
import cyr7.ast.expr.binexpr.OrExprNode;
import cyr7.ast.expr.binexpr.RemExprNode;
import cyr7.ast.expr.binexpr.SubExprNode;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ast.stmt.ArrayDeclStmtNode;
import cyr7.ast.stmt.AssignmentStmtNode;
import cyr7.ast.stmt.BlockStmtNode;
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
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRNode;
import cyr7.visitor.AbstractVisitor;

import java.util.List;

public class CTranslationVisitor extends AbstractVisitor<List<IRNode>> {

    private final IRLabel tLabel, fLabel;

    public CTranslationVisitor(IRLabel tLabel, IRLabel fLabel) {
        this.tLabel = tLabel;
        this.fLabel = fLabel;
    }

    @Override
    public List<IRNode> visit(FunctionDeclNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(FunctionHeaderDeclNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(IxiProgramNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(UseNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(VarDeclNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(XiProgramNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(PrimitiveTypeNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(TypeExprArrayNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(ArrayDeclStmtNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(AssignmentStmtNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(BlockStmtNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(ExprStmtNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(IfElseStmtNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(MultiAssignStmtNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(ProcedureStmtNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(ReturnStmtNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(VarDeclStmtNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(VarInitStmtNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(WhileStmtNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(FunctionCallExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(ArrayAccessExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(VariableAccessExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(AddExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(AndExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(DivExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(EqualsExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(GTEExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(GTExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(HighMultExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(LTEExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(LTExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(MultExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(NotEqualsExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(OrExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(RemExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(SubExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(LiteralArrayExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(LiteralBoolExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(LiteralCharExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(LiteralIntExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(LiteralStringExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(BoolNegExprNode n) {
        return null;
    }

    @Override
    public List<IRNode> visit(IntNegExprNode n) {
        return null;
    }
}
