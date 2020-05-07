package cyr7.cfg.ir.nodes;

import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRExpr;
import java_cup.runtime.ComplexSymbolFactory.Location;

public final class CFGNodeFactory {

    private final Location location;

    public CFGNodeFactory(Location location) {
        this.location = location;
    }

    public CFGCallNode Call(IRCallStmt callStmt, CFGNode out) {
        return new CFGCallNode(location, callStmt, out);
    }

    public CFGIfNode If(CFGNode trueBranch, CFGNode falseBranch, IRExpr cond) {
        return new CFGIfNode(location, trueBranch, falseBranch, cond);
    }

    public CFGMemAssignNode MemAssign(IRExpr target, IRExpr value, CFGNode out) {
        return new CFGMemAssignNode(location, target, value, out);
    }

    public CFGReturnNode Return() {
        return new CFGReturnNode(location);
    }

    public CFGStartNode Start(CFGNode out) {
        return new CFGStartNode(location, out);
    }

    public CFGVarAssignNode VarAssign(String variable, IRExpr value, CFGNode out) {
        return new CFGVarAssignNode(location, variable, value, out);
    }

    public CFGSelfLoopNode SelfLoop() {
        return new CFGSelfLoopNode();
    }

}
