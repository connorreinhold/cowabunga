package cyr7.ir.nodes;

import java.util.Objects;

import cyr7.ir.visit.AggregateVisitor;
import cyr7.ir.visit.CheckConstFoldedIRVisitor;
import cyr7.ir.visit.IRVisitor;
import cyr7.visitor.MyIRVisitor;
import edu.cornell.cs.cs4120.util.InternalCompilerError;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * An intermediate representation for a binary operation
 * OP(left, right)
 */
public class IRBinOp extends IRExpr_c {

    /**
     * Binary operators
     */
    public enum OpType {
        ADD, SUB, MUL, HMUL, DIV, MOD, AND, OR, XOR, LSHIFT, RSHIFT, ARSHIFT,
        EQ, NEQ, LT, GT, LEQ, GEQ;

        @Override
        public String toString() {
            switch (this) {
            case ADD:
                return "ADD";
            case SUB:
                return "SUB";
            case MUL:
                return "MUL";
            case HMUL:
                return "HMUL";
            case DIV:
                return "DIV";
            case MOD:
                return "MOD";
            case AND:
                return "AND";
            case OR:
                return "OR";
            case XOR:
                return "XOR";
            case LSHIFT:
                return "LSHIFT";
            case RSHIFT:
                return "RSHIFT";
            case ARSHIFT:
                return "ARSHIFT";
            case EQ:
                return "EQ";
            case NEQ:
                return "NEQ";
            case LT:
                return "LT";
            case GT:
                return "GT";
            case LEQ:
                return "LEQ";
            case GEQ:
                return "GEQ";
            }
            throw new InternalCompilerError("Unknown op type");
        }
        
        public boolean isCmpOp() {
            return this == EQ || this == NEQ || this == LT || 
                    this == GT || this == LEQ || this == GEQ;
        }
    };

    private OpType type;
    private IRExpr left, right;

    public IRBinOp(Location location, OpType type, IRExpr left, IRExpr right) {
        super(location);
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public OpType opType() {
        return type;
    }

    public IRExpr left() {
        return left;
    }

    public IRExpr right() {
        return right;
    }

    @Override
    public String label() {
        return type.toString();
    }

    @Override
    public IRNode visitChildren(IRVisitor v) {
        IRExpr left = (IRExpr) v.visit(this, this.left);
        IRExpr right = (IRExpr) v.visit(this, this.right);

        if (left != this.left || right != this.right)
            return v.nodeFactory().IRBinOp(type, left, right);

        return this;
    }

    @Override
    public <T> T aggregateChildren(AggregateVisitor<T> v) {
        T result = v.unit();
        result = v.bind(result, v.visit(left));
        result = v.bind(result, v.visit(right));
        return result;
    }

    @Override
    public boolean isConstFolded(CheckConstFoldedIRVisitor v) {
        if (isConstant()) {
            switch (type) {
            case DIV:
            case MOD:
                return right.constant() == 0;
            default:
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isConstant() {
        return left.isConstant() && right.isConstant();
    }

    @Override
    public void printSExp(SExpPrinter p) {
        p.startList();
        p.printAtom(type.toString());
        left.printSExp(p);
        right.printSExp(p);
        p.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IRBinOp irBinOp = (IRBinOp) o;
        return type == irBinOp.type &&
            Objects.equals(left, irBinOp.left) &&
            Objects.equals(right, irBinOp.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, left, right);
    }

    @Override
    public <T> T accept(MyIRVisitor<T> v) {
        return v.visit(this);
    }

}
