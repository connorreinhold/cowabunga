package cyr7.ir.visit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGVarAssignNode;
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
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRTemp;
import cyr7.util.OneOfTwo;
import cyr7.visitor.MyIRVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;
import cyr7.ir.visit.CFGConstructorVisitor.Result;

public class CFGConstructorVisitor implements MyIRVisitor<Result> {

    public static class Result extends OneOfTwo<CFGNode, IRExpr> {
        private Result(CFGNode first, IRExpr second) {
            super(first, second);
        }

        protected static Result cfg(CFGNode c) {
            return new Result(c, null);
        }

        protected static Result expr(IRExpr e) {
            return new Result(null, e);
        }
    }

    private final Map<String, CFGNode> labelToCFG;
    private CFGNode successor;
    private final CFGNode absoluteLastReturn = new CFGReturnNode(new Location(
            Integer.MAX_VALUE, Integer.MAX_VALUE));

    /**
     * This boolean is for testing purposes, enforcing that IRSeq is only found
     * at the top-level of the IRTree.
     */
    private boolean hasEnteredIRSeq;

    public CFGConstructorVisitor() {
        this.labelToCFG = new HashMap<>();
        successor = absoluteLastReturn;
        this.hasEnteredIRSeq = false;
    }

    @Override
    public Result visit(IRSeq n) {
        if (hasEnteredIRSeq) {
            throw new UnsupportedOperationException(
                    "Cannot enter the IRSeq visitor twice");
        } else {
            this.hasEnteredIRSeq = true;
        }

        ArrayList<IRStmt> stmts = new ArrayList<>(n.stmts());
        for (int i = stmts.size() - 1; i >= 0; i++) {
            var stmt = stmts.get(i);
            successor = stmt.accept(this)
                            .assertFirst();
        }
        return Result.cfg(successor);
    }

    @Override
    public Result visit(IRBinOp n) {
        return Result.expr(n);
    }

    @Override
    public Result visit(IRCall n) {
        throw new UnsupportedOperationException("Cannot use IRCall in LIR.");
    }

    @Override
    public Result visit(IRConst n) {
        return Result.expr(n);
    }

    @Override
    public Result visit(IRESeq n) {
        throw new UnsupportedOperationException("Cannot use IRESeq in LIR.");
    }

    @Override
    public Result visit(IRMem n) {
        return Result.expr(n);
    }

    @Override
    public Result visit(IRName n) {
        throw new UnsupportedOperationException(
                "There are no reasons to use IRName other than it being inside of IRJump or IRCallStmt.");
    }

    @Override
    public Result visit(IRTemp n) {
        return Result.expr(n);
    }

    @Override
    public Result visit(IRCallStmt n) {
        return Result.cfg(new CFGCallNode(n.location(), n, successor));
    }

    @Override
    public Result visit(IRCJump n) {
        assert !n.hasFalseLabel();  // IR should be lowered, meaning false
                                    // branches are fall-throughs.
        String trueBranch = n.trueLabel();
        CFGIfNode ifNode = new CFGIfNode(n.location(), this.labelToCFG.get(
                trueBranch), successor, n.cond());
        return Result.cfg(ifNode);
    }

    @Override
    public Result visit(IRCompUnit n) {
        throw new UnsupportedOperationException(
                "Cannot use IRCompUnit in this visitor.");
    }

    @Override
    public Result visit(IRExp n) {
        throw new UnsupportedOperationException("Cannot use IRExp in LIR.");
    }

    @Override
    public Result visit(IRFuncDecl n) {
        throw new UnsupportedOperationException(
                "Cannot use IRFuncDecl in this visitor.");
    }

    @Override
    public Result visit(IRJump n) {
        if (n.target() instanceof IRName) {
            String target = ((IRName) n.target()).name();
            return Result.cfg(this.labelToCFG.get(target));
        }
        throw new UnsupportedOperationException(
                "IRJump contains a non-name target.");
    }

    @Override
    public Result visit(IRLabel n) {
        this.labelToCFG.put(n.name(), successor);
        return Result.cfg(absoluteLastReturn);
    }

    @Override
    public Result visit(IRMove n) {
        if (n.target() instanceof IRTemp) {
            IRTemp temp = (IRTemp) n.target();
            return Result.cfg(new CFGVarAssignNode(n.location(), temp.name(), n.source(),
                    successor));
        } else {
            return Result.cfg(new CFGMemAssignNode(n.location(), n.target(), n.source(),
                    successor));
        }
    }

    @Override
    public Result visit(IRReturn n) {
        return Result.cfg(new CFGReturnNode(n.location()));
    }

}