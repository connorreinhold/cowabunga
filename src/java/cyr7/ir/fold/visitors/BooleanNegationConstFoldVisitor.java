package cyr7.ir.fold.visitors;

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
import cyr7.ir.nodes.IRTemp;
import cyr7.visitor.MyIRVisitor;

public class BooleanNegationConstFoldVisitor implements MyIRVisitor<IRExpr> {

    private final IRNodeFactory make;

    public BooleanNegationConstFoldVisitor(IRExpr xor) {
        this.make = new IRNodeFactory_c(xor.location());
    }

    private IRExpr original(IRExpr e) {
        return make.IRBinOp(OpType.XOR, make.IRConst(1), e);
    }

    @Override
    public IRExpr visit(IRBinOp n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
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
            if (n.left().isConstant() && n.left().constant() == 1) {
                return n.right();
            } else {
                return n;
            }
        case AND:
            break;
        case OR:
            break;
        case ADD:
            break;
        case ARSHIFT:
            break;
        case DIV:
            break;
        case HMUL:
            break;
        case LSHIFT:
            break;
        case MOD:
            break;
        case MUL:
            break;
        case RSHIFT:
            break;
        case SUB:
            break;
        default:
            break;
        }
        return original(n);
    }

    @Override
    public IRExpr visit(IRCall n) {
        return original(n);
    }

    @Override
    public IRExpr visit(IRConst n) {
        return original(n);
    }

    @Override
    public IRExpr visit(IRESeq n) {
        return original(n);
    }

    @Override
    public IRExpr visit(IRMem n) {
        return original(n);
    }

    @Override
    public IRExpr visit(IRName n) {
        return original(n);
    }

    @Override
    public IRExpr visit(IRTemp n) {
        return original(n);
    }

    @Override
    public IRExpr visit(IRCallStmt n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRCJump n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRCompUnit n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRExp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRFuncDecl n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRJump n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRLabel n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRMove n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRReturn n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRExpr visit(IRSeq n) {
        throw new UnsupportedOperationException();
    }

}
