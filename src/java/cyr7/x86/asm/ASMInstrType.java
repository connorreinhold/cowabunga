package cyr7.x86.asm;

enum ASMInstrType {
    // Arithmetic
    ADDQ, SUBQ, IMULQ, IDIVQ, LEAQ,

    // Logical
    ANDQ, ORQ, XORQ,

    // Shift Operators
    SHLQ, SHRQ, SARQ,

    // Data Transfer
    MOVQ, PUSHQ, POPQ,

    // Control Flow
    CMPQ, CALLQ, RETQ,

    // Jumps
    JMP, JE, JNE, JLE, JGE, JL, JG,

    // Set Instructions
    SETZ, SETNE, SETLE,

    // cqo
    CQO;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
