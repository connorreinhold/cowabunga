package cyr7.x86.asm;

public enum ASMInstrType {
    // Arithmetic
    ADDQ, SUBQ, IMULQ, IDIVQ, LEAQ,

    // Logical
    ANDQ, ORQ, XORQ,

    // Shift Operators
    SHLQ, SHRQ, SARQ,

    // Data Transfer
    MOVQ, PUSHQ, POPQ, MOVABSQ,

    // Control Flow
    CMPQ, CALLQ, RETQ,

    // Jumps
    JMP, JE, JNE, JLE, JGE, JL, JG,

    // Set Instructions
    SETZ, SETNE, SETLE,

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
