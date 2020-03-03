package cyr7.ir;

import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.ExprNode;
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
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRNode;
import cyr7.util.Util;
import cyr7.visitor.AbstractVisitor;

import java.util.ArrayList;
import java.util.List;

public final class CTranslationVisitor extends AbstractVisitor<List<IRNode>> {

    private final IdGenerator generator;
    private final String tLabel, fLabel;

    public CTranslationVisitor(IdGenerator generator, String tLabel, String fLabel) {
        this.generator = generator;
        this.tLabel = tLabel;
        this.fLabel = fLabel;
    }

    @Override
    public List<IRNode> visit(AndExprNode n) {
        String tPrime = generator.newLabel();
        List<IRNode> list = new ArrayList<>();
        list.addAll(n.left.accept(new CTranslationVisitor(generator, tPrime, tLabel)));
        list.add(new IRLabel(tPrime));
        list.addAll(n.right.accept(new CTranslationVisitor(generator, tLabel, fLabel)));
        return Util.immutableCopy(list);
    }

    @Override
    public List<IRNode> visit(BoolNegExprNode n) {
        return n.accept(new CTranslationVisitor(generator, fLabel, tLabel));
    }

    @Override
    public List<IRNode> visit(OrExprNode n) {
        String fPrime = generator.newLabel();
        List<IRNode> list = new ArrayList<>();
        list.addAll(n.left.accept(new CTranslationVisitor(generator, tLabel, fPrime)));
        list.add(new IRLabel(fPrime));
        list.addAll(n.right.accept(new CTranslationVisitor(generator, tLabel, fLabel)));
        return Util.immutableCopy(list);
    }

    @Override
    public List<IRNode> visit(LiteralBoolExprNode n) {
        IRName name = new IRName(n.contents ? tLabel : fLabel);
        return List.of(new IRJump(name));
    }

    // General

    private List<IRNode> cjump(ExprNode n) {
        return List.of(new IRCJump(n.accept(new AstToIrVisitor()).assertFirst(), tLabel, fLabel));
    }

    @Override
    public List<IRNode> visit(FunctionCallExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(ArrayAccessExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(VariableAccessExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(AddExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(DivExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(EqualsExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(GTEExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(GTExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(HighMultExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(LTEExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(LTExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(MultExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(NotEqualsExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(RemExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(SubExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(LiteralArrayExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(LiteralCharExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(LiteralIntExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(LiteralStringExprNode n) {
        return cjump(n);
    }

    @Override
    public List<IRNode> visit(IntNegExprNode n) {
        return cjump(n);
    }

    // Unsupported

    @Override
    public List<IRNode> visit(FunctionDeclNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(FunctionHeaderDeclNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(IxiProgramNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(UseNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(VarDeclNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(XiProgramNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(PrimitiveTypeNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(TypeExprArrayNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(ArrayDeclStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(AssignmentStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(BlockStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(ExprStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(IfElseStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(MultiAssignStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(ProcedureStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(ReturnStmtNode n) {
        throw new UnsupportedOperationException();

    }

    @Override
    public List<IRNode> visit(VarDeclStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(VarInitStmtNode n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IRNode> visit(WhileStmtNode n) {
        throw new UnsupportedOperationException();
    }

}
