package cyr7.ir.fold;

import cyr7.ir.interpret.IRSimulator.Trap;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import cyr7.util.OneOfThree;
import edu.cornell.cs.cs4120.util.InternalCompilerError;

/**
 * Called by the OneOfThree<IRExpr, IRStmt, IRFuncDecl>'s OneOfThree<IRExpr,
 * IRStmt>.accept(IRVisitor) method. The returned value is relative to the
 * callee node. In this implementation for constant folding, the return type is
 * an OneOfThree<IRExpr, IRStmt, IRFuncDecl>, in which the returned
 * OneOfThree<IRExpr, IRStmt, IRFuncDecl> represents a constant-folded version
 * of that node, if possible.
 * <p>
 * For any node, the fields of nodes are recursively folded. The returned node
 * has all of its child nodes folded if possible.
 *
 * @author ayang
 *
 */
public class LIRConstFoldVisitor extends MIRConstFoldVisitor {

    private boolean isZero(IRExpr n) {
        return n.isConstant() && n.constant() == 0;
    }

    private boolean isOne(IRExpr n) {
        return n.isConstant() && n.constant() == 1;
    }

    /**
     * Requires: {@code n} is a constant and is a power of two. This can be
     * checked via isConstant() and isPowerOfTwo().
     */
    protected long logBase2(IRExpr n) {
        long num = n.constant();
        long position = 1;
        for (int i = 0; i < 64; i++) {
            if ((position & num) == position) {
                return i;
            } else {
                position <<= 1;
            }
        }
        throw new UnsupportedOperationException("Tried to get exponent of 0.");
    }

    private boolean isPowerOfTwo(IRExpr n) {
        if (n.isConstant()) {
            long num = n.constant();
            return num != 0 && ((num & (num - 1)) == 0);
        } else {
            return false;
        }
    }

    private IRExpr simplifyAdd(IRBinOp n) {
        IRExpr left = n.left();
        IRExpr right = n.right();
        if (this.isZero(left)) {
            return right;
        } else if (this.isZero(right)) {
            return left;
        } else {
            return n;
        }
    }

    private IRExpr simplifyDivision(IRBinOp n, IRNodeFactory make) {
        IRExpr left = n.left();
        IRExpr right = n.right();

        if (this.isZero(right)) {
            throw new Trap("Division by zero!");
        } else if (this.isZero(left)) {
            return left;
        } else if (this.isOne(right)) {
            return left;
        }

        return n;
    }

    private IRExpr simplifyMod(IRBinOp n) {
        IRExpr left = n.left();
        IRExpr right = n.right();

        if (this.isZero(right)) {
            throw new Trap("Division by zero!");
        } else if (this.isZero(left)) {
            return left;
        }
        return n;
    }

    private IRExpr simplifySub(IRBinOp n) {
        if (this.isZero(n.right())) {
            return n.left();
        }
        return n;
    }

    private IRExpr simplifyMul(IRBinOp n, IRNodeFactory make) {
        IRExpr left = n.left();
        IRExpr right = n.right();

        if (this.isZero(left)) {
            return left;
        } else if (this.isZero(right)) {
            return right;
        }

        if (this.isOne(left)) {
            return right;
        } else if (this.isOne(right)) {
            return left;
        }

        if (this.isPowerOfTwo(n.left())) {
            long power = this.logBase2(left);
            return make.IRBinOp(OpType.LSHIFT, right, make.IRConst(power));
        }

        if (this.isPowerOfTwo(n.right())) {
            long power = this.logBase2(right);
            return make.IRBinOp(OpType.LSHIFT, left, make.IRConst(power));
        }

        return n;
    }

    private IRExpr simplifyHighMul(IRBinOp n) {
        IRExpr left = n.left();
        IRExpr right = n.right();
        if (this.isZero(left))
            return left;
        if (this.isZero(right))
            return right;
        return n;
    }

    private IRExpr simplifyAnd(IRBinOp n) {
        IRExpr left = n.left();
        IRExpr right = n.right();
        if (this.isZero(left)) {
            return left;
        } else if (this.isZero(right)) {
            return right;
        }
        return n;
    }

    private IRExpr simplifyXOR(IRBinOp binop, IRNodeFactory make) {
        if (binop.left()
                 .equals(binop.right())) {
            return make.IRConst(0);
        }

        if (this.isOne(binop.left()) && binop.right() instanceof IRBinOp) {
            // Negate the right hand side
            IRBinOp n = (IRBinOp) binop.right();
            switch (n.opType()) {
            case EQ:
                return make.IRBinOp(OpType.NEQ, n.left(), n.right());
            case GEQ:
                return make.IRBinOp(OpType.LT, n.left(), n.right());
            case GT:
                return make.IRBinOp(OpType.LEQ, n.left(), n.right());
            case LEQ:
                return make.IRBinOp(OpType.GT, n.left(), n.right());
            case LT:
                return make.IRBinOp(OpType.GEQ, n.left(), n.right());
            case NEQ:
                return make.IRBinOp(OpType.EQ, n.left(), n.right());
            case XOR:
                return this.isOne(n.left()) ? n.right() : binop;
            default:
                break;
            }
        }
        return binop;
    }

    private IRExpr simplifyComparison(IRBinOp n, IRNodeFactory make) {
        IRExpr trueExpr = make.IRConst(1);
        IRExpr falseExpr = make.IRConst(0);

        if (n.left()
             .equals(n.right())) {
            switch (n.opType()) {
            case EQ:
                return trueExpr;
            case NEQ:
                return falseExpr;
            case LT:
                return falseExpr;
            case GT:
                return falseExpr;
            case LEQ:
                return trueExpr;
            case GEQ:
                return trueExpr;
            default:
                return n;
            }
        }
        return n;
    }

    /**
     *
     * If one of the binary operator's two operands is not a constant, then the
     * binary operator node is not a constant.
     * <p>
     * If binary operator's type is MOD or DIV, then if the right operand is is
     * zero, then return.
     */
    @Override
    protected IRExpr performConstantBinop(IRBinOp n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());

        final IRExpr left = n.left()
                             .accept(this)
                             .assertFirst();
        final IRExpr right = n.right()
                              .accept(this)
                              .assertFirst();

        IRBinOp foldedValue = make.IRBinOp(n.opType(), left, right);
        if (left.isConstant() && right.isConstant()) {
            return super.performConstantBinop(foldedValue);
        }

        // // Copied from staff-given interpreter code.
        switch (n.opType()) {
        case ADD:
            return this.simplifyAdd(foldedValue);
        case SUB:
            return this.simplifySub(foldedValue);
        case MUL:
            return this.simplifyMul(foldedValue, make);
        case HMUL:
            return this.simplifyHighMul(foldedValue);
        case DIV:
            return this.simplifyDivision(foldedValue, make);
        case MOD:
            return this.simplifyMod(foldedValue);
        case AND:
            return this.simplifyAnd(foldedValue);
        case OR:
            return this.simplifyAdd(foldedValue);
        case XOR:
            return this.simplifyXOR(foldedValue, make);
        case LSHIFT:
            break;
        case RSHIFT:
            break;
        case ARSHIFT:
            break;
        case EQ:
            return this.simplifyComparison(foldedValue, make);
        case NEQ:
            return this.simplifyComparison(foldedValue, make);
        case LT:
            return this.simplifyComparison(foldedValue, make);
        case GT:
            return this.simplifyComparison(foldedValue, make);
        case LEQ:
            return this.simplifyComparison(foldedValue, make);
        case GEQ:
            return this.simplifyComparison(foldedValue, make);
        default:
            throw new InternalCompilerError("Invalid binary operation");
        }

        return foldedValue;
    }

    /**
     * CFold[ESeq(...s, e)] ::= ESeq(CFold[...s], CFold[e])
     */
    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRESeq n) {
        throw new UnsupportedOperationException(
                "Found ESeq when constant folding LIR Tree.");
    }

    /**
     * CFold[Exp(e)] ::= Exp(CFold[etarget])
     */
    @Override
    public OneOfThree<IRExpr, IRStmt, IRFuncDecl> visit(IRExp n) {
        throw new UnsupportedOperationException(
                "Found Exp while constant folding LIR Tree.");
    }

}
