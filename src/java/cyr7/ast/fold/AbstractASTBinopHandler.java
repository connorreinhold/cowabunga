package cyr7.ast.fold;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.expr.binexpr.BinExprNode;

public abstract class AbstractASTBinopHandler {

    protected ExprNode left;
    protected ExprNode right;
    protected boolean isCanonical;

    public ExprNode accept(BinExprNode n, boolean isCanonical) {
        this.left = n.left;
        this.right = n.right;
        this.isCanonical = isCanonical;

        switch (n.opType) {
        case ADD:
            return handleAdd(n);
        case AND:
            return handleAnd(n);
        case DIV:
            return handleDiv(n);
        case EQ:
            return handleEQ(n);
        case GEQ:
            return handleGEQ(n);
        case GT:
            return handleGT(n);
        case HIGH_MUL:
            return handleHighMult(n);
        case LEQ:
            return handleLEQ(n);
        case LT:
            return handleLT(n);
        case MOD:
            return handleMod(n);
        case MUL:
            return handleMult(n);
        case NEQ:
            return handleNEQ(n);
        case OR:
            return handleOr(n);
        case SUB:
            return handleSub(n);
        default:
            throw new UnsupportedOperationException();
        }
    }

    protected abstract ExprNode handleAnd(BinExprNode n);

    protected abstract ExprNode handleOr(BinExprNode n);

    protected abstract ExprNode handleAdd(BinExprNode n);

    protected abstract ExprNode handleSub(BinExprNode n);

    protected abstract ExprNode handleMult(BinExprNode n);

    protected abstract ExprNode handleDiv(BinExprNode n);

    protected abstract ExprNode handleMod(BinExprNode n);

    protected abstract ExprNode handleHighMult(BinExprNode n);

    protected abstract ExprNode handleGT(BinExprNode n);

    protected abstract ExprNode handleGEQ(BinExprNode n);

    protected abstract ExprNode handleLT(BinExprNode n);

    protected abstract ExprNode handleLEQ(BinExprNode n);

    protected abstract ExprNode handleEQ(BinExprNode n);

    protected abstract ExprNode handleNEQ(BinExprNode n);

}
