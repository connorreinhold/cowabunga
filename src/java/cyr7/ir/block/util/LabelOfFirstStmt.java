package cyr7.ir.block.util;

import java.util.Optional;

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
import cyr7.visitor.MyIRVisitor;

public class LabelOfFirstStmt implements MyIRVisitor<Optional<String>> {

    @Override
    public Optional<String> visit(IRBinOp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> visit(IRCall n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> visit(IRConst n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> visit(IRESeq n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> visit(IRMem n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> visit(IRName n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> visit(IRTemp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> visit(IRCallStmt n) {
        return Optional.empty();
    }

    @Override
    public Optional<String> visit(IRCJump n) {
        return Optional.empty();
    }

    @Override
    public Optional<String> visit(IRCompUnit n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> visit(IRExp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> visit(IRFuncDecl n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> visit(IRJump n) {
        return Optional.empty();
    }

    @Override
    public Optional<String> visit(IRLabel n) {
        return Optional.of(n.name());
    }

    @Override
    public Optional<String> visit(IRMove n) {
        return Optional.empty();
    }

    @Override
    public Optional<String> visit(IRReturn n) {
        return Optional.empty();
    }

    @Override
    public Optional<String> visit(IRSeq n) {
        throw new UnsupportedOperationException();
    }

}
