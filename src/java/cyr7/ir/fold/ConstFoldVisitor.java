package cyr7.ir.fold;

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
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRTemp;

/**
 * Called by the IRNode's IRNode.accept(IRVisitor) method. The returned value is
 * relative to the callee node. In this implementation for constant folding, the
 * return type is an IRNode, in which the returned IRNode represents a
 * constant-folded version of that node, if possible.
 *
 * @author ayang
 *
 */
public class ConstFoldVisitor implements IRVisitor<IRNode> {

    @Override
    public IRNode visit(IRBinOp n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRCall n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRConst n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRESeq n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRMem n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRName n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRTemp n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRCallStmt n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRCJump n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRCompUnit n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRExp n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRFuncDecl n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRJump n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRLabel n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRMove n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRReturn n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRNode visit(IRSeq n) {
        // TODO Auto-generated method stub
        return null;
    }

}
