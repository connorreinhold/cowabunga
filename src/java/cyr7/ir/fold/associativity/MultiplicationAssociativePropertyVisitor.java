package cyr7.ir.fold.associativity;

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
import polyglot.util.Pair;

/**
 * Applies the associative property of addition on an IR-expression tree. For
 * expressions a - b, they are treated as a + (-b).
 *
 * @author ayang
 *
 */
public class MultiplicationAssociativePropertyVisitor
        implements MyIRVisitor<Pair<IRExpr, Long>> {

    protected MultiplicationAssociativePropertyVisitor() {}

    private Pair<IRExpr, Long> pairOf(IRExpr e, Long l) {
        return new Pair<>(e, l);
    }

    @Override
    public Pair<IRExpr, Long> visit(IRBinOp n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        if (n.opType() == OpType.MUL) {
            if (n.left().isConstant()) {
                var res = n.right().accept(this);
                long accumulated = res.part2() * n.left().constant();
                return pairOf(res.part1(), accumulated);
            } else if (n.right().isConstant()) {
                var res = n.left().accept(this);
                long accumulated = res.part2() * n.right().constant();
                return pairOf(res.part1(), accumulated);
            } else {
                var resLeft = n.left().accept(this);
                var resRight = n.right().accept(this);
                long accumulated = resLeft.part2() * resRight.part2();
                return pairOf(make.IRBinOp(n.opType(), resLeft.part1(),
                        resRight.part1()), accumulated);
            }
        }
        return pairOf(n, 1L);
    }

    @Override
    public Pair<IRExpr, Long> visit(IRCall n) {
        return pairOf(n, 1L);
    }

    @Override
    public Pair<IRExpr, Long> visit(IRConst n) {
        return pairOf(n, 1L);
    }

    @Override
    public Pair<IRExpr, Long> visit(IRESeq n) {
        return pairOf(n, 1L);
    }

    @Override
    public Pair<IRExpr, Long> visit(IRMem n) {
        return pairOf(n, 1L);
    }

    @Override
    public Pair<IRExpr, Long> visit(IRName n) {
        return pairOf(n, 1L);
    }

    @Override
    public Pair<IRExpr, Long> visit(IRTemp n) {
        return pairOf(n, 1L);
    }

    @Override
    public Pair<IRExpr, Long> visit(IRCallStmt n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<IRExpr, Long> visit(IRCJump n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<IRExpr, Long> visit(IRCompUnit n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<IRExpr, Long> visit(IRExp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<IRExpr, Long> visit(IRFuncDecl n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<IRExpr, Long> visit(IRJump n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<IRExpr, Long> visit(IRLabel n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<IRExpr, Long> visit(IRMove n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<IRExpr, Long> visit(IRReturn n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<IRExpr, Long> visit(IRSeq n) {
        throw new UnsupportedOperationException();
    }

}
