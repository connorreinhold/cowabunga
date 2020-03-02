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
import cyr7.ir.nodes.*;
import cyr7.visitor.AbstractVisitor;

public class AstToIrVisitor extends AbstractVisitor<IRNode> {

    private int labelCounter;
    private int tempCounter;

    public AstToIrVisitor() {
        this.labelCounter = 0;
        this.tempCounter = 0;
    }

    private String newLabel() {
        return String.format("_l%d", (labelCounter++));
    }

    private String newTemp() {
        return String.format("_t%d", (tempCounter++));
    }

    public IRNode visit(ExprNode n) {
        if (n instanceof AddExprNode) {
            return visit((AddExprNode) n);
        } else if (n instanceof AndExprNode) {
            return visit((AndExprNode) n);
        }
    }

    @Override
    public IRNode visit(FunctionDeclNode n) {
        return null;
    }

    @Override
    public IRNode visit(FunctionHeaderDeclNode n) {
        return null;
    }

    @Override
    public IRNode visit(IxiProgramNode n) {
        return null;
    }

    @Override
    public IRNode visit(UseNode n) {
        return null;
    }

    @Override
    public IRNode visit(VarDeclNode n) {
        return null;
    }

    @Override
    public IRNode visit(XiProgramNode n) {
        return null;
    }

    @Override
    public IRNode visit(PrimitiveTypeNode n) {
        return null;
    }

    @Override
    public IRNode visit(TypeExprArrayNode n) {
        return null;
    }

    @Override
    public IRNode visit(ArrayDeclStmtNode n) {
        return null;
    }

    @Override
    public IRNode visit(AssignmentStmtNode n) {
        return null;
    }

    @Override
    public IRNode visit(BlockStmtNode n) {
        return null;
    }

    @Override
    public IRNode visit(ExprStmtNode n) {
        return null;
    }

    @Override
    public IRNode visit(IfElseStmtNode n) {
        return null;
    }

    @Override
    public IRNode visit(MultiAssignStmtNode n) {
        return null;
    }

    @Override
    public IRNode visit(ProcedureStmtNode n) {
        return null;
    }

    @Override
    public IRNode visit(ReturnStmtNode n) {
        return null;
    }

    @Override
    public IRNode visit(VarDeclStmtNode n) {
        return null;
    }

    @Override
    public IRNode visit(VarInitStmtNode n) {
        return null;
    }

    @Override
    public IRNode visit(WhileStmtNode n) {
        return null;
    }

    @Override
    public IRNode visit(FunctionCallExprNode n) {
        return null;
    }

    @Override
    public IRNode visit(ArrayAccessExprNode n) {
        return null;
    }

    @Override
    public IRNode visit(VariableAccessExprNode n) {
        return null;
    }

    @Override
    public IRNode visit(AddExprNode n) {
        return new IRBinOp(IRBinOp.OpType.ADD, this.visit(n.left), this.visit(n.right));
    }

    @Override
    public IRNode visit(AndExprNode n) {
        String x = newTemp();
        String l1 = newLabel();
        String l2 = newLabel();
        String l3 = newLabel();

        return new IRESeq(
                new IRSeq(
                    new IRMove(new IRTemp(x), new IRConst(0)),
                    new IRCJump(visit(n.left) , l1, l3),
                    new IRLabel(l1),
                    new IRCJump(visit(n.right), l2, l3),
                    new IRLabel(l2),
                    new IRMove(new IRTemp(x), new IRConst(1)),
                    new IRLabel(l3)),
                new IRTemp(x));
        ));
    }

    @Override
    public IRNode visit(DivExprNode n) {
        return new IRBinOp(IRBinOp.OpType.DIV, visit(n.left), visit(n.right));
    }

    @Override
    public IRNode visit(EqualsExprNode n) {
        return new IRBinOp(IRBinOp.OpType.EQ, visit(n.left), visit(n.right));
    }

    @Override
    public IRNode visit(GTEExprNode n) {
        return new IRBinOp(IRBinOp.OpType.GEQ, visit(n.left), visit(n.right));
    }

    @Override
    public IRNode visit(GTExprNode n) {
        return new IRBinOp(IRBinOp.OpType.GT, visit(n.left), visit(n.right));
    }

    @Override
    public IRNode visit(HighMultExprNode n) {
        return new IRBinOp(IRBinOp.OpType.HMUL, visit(n.left), visit(n.right));
    }

    @Override
    public IRNode visit(LTEExprNode n) {
        return new IRBinOp(IRBinOp.OpType.LEQ, visit(n.left), visit(n.right));
    }

    @Override
    public IRNode visit(LTExprNode n) {
        return new IRBinOp(IRBinOp.OpType.LT, visit(n.left), visit(n.right));
    }

    @Override
    public IRNode visit(MultExprNode n) {
        return new IRBinOp(IRBinOp.OpType.MUL, visit(n.left), visit(n.right));
    }

    @Override
    public IRNode visit(NotEqualsExprNode n) {
        return new IRBinOp(IRBinOp.OpType.NEQ, visit(n.left), visit(n.right));
    }

    @Override
    public IRNode visit(OrExprNode n) {
        String x = newTemp();
        String l1 = newLabel();
        String l2 = newLabel();
        String l3 = newLabel();

        return new IRESeq(
                new IRSeq(
                    new IRMove(new IRTemp(x), new IRConst(1)),
                    new IRCJump(visit(n.left) , l3, l1),
                    new IRLabel(l1),
                    new IRCJump(visit(n.right), l3, l2),
                    new IRLabel(l2),
                    new IRMove(new IRTemp(x), new IRConst(0)),
                    new IRLabel(l3)),
                new IRTemp(x));
        ));
    }

    @Override
    public IRNode visit(RemExprNode n) {
        return new IRBinOp(IRBinOp.OpType.MOD, visit(n.left), visit(n.right));
    }

    @Override
    public IRNode visit(SubExprNode n) {
        return new IRBinOp(IRBinOp.OpType.SUB, visit(n.left), visit(n.right));
    }

    @Override
    public IRNode visit(LiteralArrayExprNode n) {
        return null;
    }

    @Override
    public IRNode visit(LiteralBoolExprNode n) {
        return new IRConst(n.contents? 1:0);
    }

    @Override
    public IRNode visit(LiteralCharExprNode n) {
        return new IRConst(n.contents.charAt(0));
    }

    @Override
    public IRNode visit(LiteralIntExprNode n) {
        return new IRConst(Integer.parseInt(n.contents));
    }

    @Override
    public IRNode visit(LiteralStringExprNode n) { return null; }

    @Override
    public IRNode visit(BoolNegExprNode n) {
        return null;
    }

    @Override
    public IRNode visit(IntNegExprNode n) {
        return null;
    }
}
