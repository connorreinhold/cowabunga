package cyr7.x86.asm;

public enum ASMInstrType {
    // Arithmetic
    ADDQ, SUBQ, IMULQ, IDIVQ, LEAQ,

    // Logical
    ANDQ, ORQ, XORQ,

    // Shift Operators
    SHLQ, SHRQ, SARQ,

    // Data Transfer
    MOV, PUSHQ, POPQ, MOVABSQ, MOVZX,

    // Control Flow
    CMPQ, CALLQ, RETQ,

    // Jumps
    JMP, JE, JNE, JLE, JGE, JL, JG,

    // Set Instructions
    SETZ, SETNE, SETLE, SETL, SETG, SETGE,

    // cqo
    CQO;

    public String getIntelOpCode() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return "ASMInstrType [type=" + this.name() + "]";
    }
}
