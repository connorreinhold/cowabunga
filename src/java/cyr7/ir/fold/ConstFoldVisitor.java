package cyr7.ir.fold;

import java.math.BigInteger;

import cyr7.ir.interpret.IRSimulator.Trap;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRTemp;
import cyr7.util.OneOfTwo;
import edu.cornell.cs.cs4120.util.InternalCompilerError;

/**
 * Called by the OneOfTwo<IRExpr, IRStmt>'s OneOfTwo<IRExpr,
 * IRStmt>.accept(IRVisitor) method. The returned value is relative to the
 * callee node. In this implementation for constant folding, the return type is
 * an OneOfTwo<IRExpr, IRStmt>, in which the returned OneOfTwo<IRExpr, IRStmt>
 * represents a constant-folded version of that node, if possible.
 *
 * @author ayang
 *
 */
public class ConstFoldVisitor implements MyIRVisitor<OneOfTwo<IRExpr, IRStmt>> {

    private IRExpr performConstantBinop(IRBinOp n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());

        if (!n.left().isConstant() || !n.right().isConstant()) {
            return n;
        }
        final long l = n.left()
                        .constant();
        final long r = n.right()
                        .constant();
        long value;
        switch (n.opType()) {
        case ADD:
            value = l + r;
            break;
        case SUB:
            value = l - r;
            break;
        case MUL:
            value = l * r;
            break;
        case HMUL:
            value = BigInteger.valueOf(l)
                               .multiply(BigInteger.valueOf(r))
                               .shiftRight(64)
                               .longValue();
            break;
        case DIV:
            if (r == 0)
                throw new Trap("Division by zero!");
            value = l / r;
            break;
        case MOD:
            if (r == 0)
                throw new Trap("Division by zero!");
            value = l % r;
            break;
        case AND:
            value = l & r;
            break;
        case OR:
            value = l | r;
            break;
        case XOR:
            value = l ^ r;
            break;
        case LSHIFT:
            value = l << r;
            break;
        case RSHIFT:
            value = l >>> r;
            break;
        case ARSHIFT:
            value = l >> r;
            break;
        case EQ:
            value = l == r ? 1 : 0;
            break;
        case NEQ:
            value = l != r ? 1 : 0;
            break;
        case LT:
            value = l < r ? 1 : 0;
            break;
        case GT:
            value = l > r ? 1 : 0;
            break;
        case LEQ:
            value = l <= r ? 1 : 0;
            break;
        case GEQ:
            value = l >= r ? 1 : 0;
            break;
        default:
            throw new InternalCompilerError("Invalid binary operation");
        }
        return make.IRConst(value);
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRBinOp n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        IRExpr leftFold = n.left()
                           .accept(this)
                           .assertFirst();
        IRExpr rightFold = n.right()
                            .accept(this)
                            .assertFirst();
        n = make.IRBinOp(n.opType(), leftFold, rightFold);
        if (n.opType() == OpType.DIV || n.opType() == OpType.MOD
                && rightFold.constant() == 0) {
            return OneOfTwo.ofFirst(n);
        }
        return OneOfTwo.ofFirst(performConstantBinop(n));
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRCall n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRConst n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRESeq n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRMem n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRName n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRTemp n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRCallStmt n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRCJump n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRCompUnit n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRExp n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRFuncDecl n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRJump n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRLabel n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRMove n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRReturn n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<IRExpr, IRStmt> visit(IRSeq n) {
        // TODO Auto-generated method stub
        return null;
    }

}
