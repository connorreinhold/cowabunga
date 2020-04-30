package cyr7.ir.visit;

import cyr7.cfg.nodes.CFGNode;
import cyr7.ir.nodes.IRBinOp;
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
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRTemp;
import cyr7.util.OneOfTwo;
import cyr7.visitor.MyIRVisitor;

public class CFGConstructorVisitor implements MyIRVisitor<OneOfTwo<CFGNode, IRExpr>> {

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRBinOp n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRCall n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRConst n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRESeq n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRMem n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRName n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRTemp n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRCallStmt n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRCJump n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRCompUnit n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRExp n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRFuncDecl n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRJump n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRLabel n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRMove n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRReturn n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OneOfTwo<CFGNode, IRExpr> visit(IRSeq n) {
        // TODO Auto-generated method stub
        return null;
    }

}