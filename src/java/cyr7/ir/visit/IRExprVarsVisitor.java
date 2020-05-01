package cyr7.ir.visit;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRTemp;
import cyr7.util.Sets;
import cyr7.visitor.MyIRVisitor;

import java.util.Set;

public enum IRExprVarsVisitor implements MyIRVisitor<Set<String>> {

    INSTANCE;

    @Override
    public Set<String> visit(IRBinOp n) {
        return Sets.union(n.left().accept(this), n.right().accept(this));
    }

    @Override
    public Set<String> visit(IRCall n) {
        throw new AssertionError();
    }

    @Override
    public Set<String> visit(IRConst n) {
        return Set.of();
    }

    @Override
    public Set<String> visit(IRESeq n) {
        throw new AssertionError();
    }

    @Override
    public Set<String> visit(IRMem n) {
        return n.expr().accept(this);
    }

    @Override
    public Set<String> visit(IRName n) {
        return Set.of();
    }

    @Override
    public Set<String> visit(IRTemp n) {
        return Set.of(n.name());
    }

    @Override
    public Set<String> visit(IRCallStmt n) {
        throw new AssertionError();
    }

    @Override
    public Set<String> visit(IRCJump n) {
        return n.cond().accept(this);
    }

    @Override
    public Set<String> visit(IRCompUnit n) {
        throw new AssertionError();
    }

    @Override
    public Set<String> visit(IRExp n) {
        throw new AssertionError();
    }

    @Override
    public Set<String> visit(IRFuncDecl n) {
        throw new AssertionError();
    }

    @Override
    public Set<String> visit(IRJump n) {
        throw new AssertionError();
    }

    @Override
    public Set<String> visit(IRLabel n) {
        return Set.of();
    }

    @Override
    public Set<String> visit(IRMove n) {
        throw new AssertionError();
    }

    @Override
    public Set<String> visit(IRReturn n) {
        throw new AssertionError();
    }

    @Override
    public Set<String> visit(IRSeq n) {
        throw new AssertionError();
    }
}
