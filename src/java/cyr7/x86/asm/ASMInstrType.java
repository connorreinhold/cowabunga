package cyr7.x86.asm;

enum ASMInstrType {
    // Arithmetic
    ADD,
    SUB,

    // Logical
    AND,
    OR,
    XOR,
    NOT,

    // Data Transfer
    MOV,
    PUSH,
    POP,

    // Control Flow
    CMP,
    CALL,
    RET;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
