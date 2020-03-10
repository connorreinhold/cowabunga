package cyr7.ir.lowering;

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

public class ContainsJumpsStmtVisitor implements MyIRVisitor<Boolean> {

    public static ContainsJumpsStmtVisitor instance =
            new ContainsJumpsStmtVisitor();

    /**
     * Call is not an statement in LIR. Use CallStmt instead.
     */
    @Override
    public Boolean visit(IRCall n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRBinOp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRConst n) {
        throw new UnsupportedOperationException();
    }

    /**
     * This is not a statement in LIR
     */
    @Override
    public Boolean visit(IRESeq n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRMem n) {
        return false;
    }

    @Override
    public Boolean visit(IRName n) {
        return false;
    }

    @Override
    public Boolean visit(IRTemp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRCallStmt n) {
        return false;
    }

    @Override
    public Boolean visit(IRCJump n) {
        return true;
    }

    @Override
    public Boolean visit(IRCompUnit n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRExp n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRFuncDecl n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean visit(IRJump n) {
        return true;
    }

    @Override
    public Boolean visit(IRLabel n) {
        return false;
    }

    @Override
    public Boolean visit(IRMove n) {
        return false;
    }

    @Override
    public Boolean visit(IRReturn n) {
        return true;
    }

    /**
     * No Seq in LIR
     */
    @Override
    public Boolean visit(IRSeq n) {
        throw new UnsupportedOperationException();
    }

}
