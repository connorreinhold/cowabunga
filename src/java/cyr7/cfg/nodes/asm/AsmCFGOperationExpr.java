package cyr7.cfg.nodes.asm;

import java.util.List;

import cyr7.x86.asm.ASMArg;

public class AsmCFGOperationExpr {

    public enum ASMOperation {
        ADD,
        AND,
        CMP,
        IDIV,
        IMUL,
        LEA,
        ORQ,
        SARQ,
        SETG,
        SETGE,
        SETL,
        SETLE,
        SETNE,
        SETZ,
        SHL,
        SHR,
        SUB,
        XOR,
        MOVE
    }

    public final List<ASMArg> args;
    public final ASMOperation operation;

    public AsmCFGOperationExpr(ASMOperation operation, List<ASMArg> args) {
        this.args = args;
        this.operation = operation;
    }

}
