package cyr7.ir.fold.visitors;

import java.math.BigInteger;

import cyr7.ir.interpret.IRSimulator.Trap;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import edu.cornell.cs.cs4120.util.InternalCompilerError;

public final class BinopCalc {

    public static IRExpr calc(IRBinOp n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());

        if (!n.left().isConstant() || !n.right().isConstant()) {
            return n;
        }
        final long l = n.left().constant();
        final long r = n.right().constant();
        long value;

        // Copied from staff-given interpreter code.
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
            value = BigInteger.valueOf(l).multiply(BigInteger.valueOf(r))
                    .shiftRight(64).longValue();
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
}
