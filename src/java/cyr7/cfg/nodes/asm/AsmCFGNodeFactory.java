package cyr7.cfg.nodes.asm;

import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRExpr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public final class AsmCFGNodeFactory {

    private final Location location;

    public AsmCFGNodeFactory(Location location) {
        this.location = location;
    }

    public AsmCFGCallNode Call(IRCallStmt callStmt, AsmCFGNode out) {
        return new AsmCFGCallNode(location, callStmt, out);
    }

    public AsmCFGIfNode If(AsmCFGNode trueBranch, AsmCFGNode falseBranch, IRExpr cond) {
        return new AsmCFGIfNode(location, trueBranch, falseBranch, cond);
    }

    public AsmCFGMemAssignNode MemAssign(IRExpr target, IRExpr value, AsmCFGNode out) {
        return new AsmCFGMemAssignNode(location, target, value, out);
    }

    public AsmCFGReturnNode Return(int numReturnValue) {
        return new AsmCFGReturnNode(location, numReturnValue);
    }

    public AsmCFGStartNode Start(AsmCFGNode out) {
        return new AsmCFGStartNode(location, out);
    }

    public AsmCFGVarAssignNode VarAssign(String variable, IRExpr value, AsmCFGNode out) {
        return new AsmCFGVarAssignNode(location, variable, value, out);
    }

}
