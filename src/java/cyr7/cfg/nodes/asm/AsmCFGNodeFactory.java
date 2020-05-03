package cyr7.cfg.nodes.asm;

import cyr7.cfg.nodes.asm.AsmCFGIfNode.ASMCompareType;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMRegSize;
import java_cup.runtime.ComplexSymbolFactory.Location;

public final class AsmCFGNodeFactory {

    private final Location location;

    public AsmCFGNodeFactory(Location location) {
        this.location = location;
    }

    public AsmCFGCallNode Call(String target, AsmCFGNode out) {
        return new AsmCFGCallNode(location, target, out);
    }

    public AsmCFGIfNode If(AsmCFGNode trueBranch,
            AsmCFGNode falseBranch, ASMCompareType type,
            ASMArg leftOperand, ASMArg rightOperand) {
        return new AsmCFGIfNode(location, trueBranch, falseBranch, type, leftOperand, rightOperand);
    }

    public AsmCFGMemAssignNode MemAssign(ASMAddrExpr address, ASMArg value, AsmCFGNode out) {
        return new AsmCFGMemAssignNode(location, address, value, out);
    }

    public AsmCFGReturnNode Return() {
        return new AsmCFGReturnNode(location);
    }

    public AsmCFGStartNode Start(AsmCFGNode out) {
        return new AsmCFGStartNode(location, out);
    }

    public AsmCFGVarAssignNode VarAssign(
        String variable,
        ASMArg value,
        ASMRegSize size,
        AsmCFGNode outNode) {

        return new AsmCFGVarAssignNode(location, variable, value, size, outNode);
    }

}
