package cyr7.x86.asm;

import cyr7.x86.visitor.AbstractASMVisitor;

public enum ASMReg implements ASMArg {
    // Classic Registers
    RAX, // Accumulator Register
    RCX, // Counter Register
    RDX, // Data Register
    RBX, // Base Register
    RSP, // Stack Pointer Register
    RBP, // Stack Base Pointer Register
    RSI, // Source Register
    RDI, // Destination Register

    // Newer Registers
    R8, R9, R10, R11, R12, R13, R14, R15,

    AL, CL, DL, BL; // 8 bit registers

    @Override
    public String getIntelArg() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return "ASMReg [register=" + this.name() + "]";
    }

    public ASMReg correspondingQWordReg() {
        switch (this) {
            case AL:
                return RAX;
            case CL:
                return RCX;
            case DL:
                return RDX;
            case BL:
                return RBX;
            default:
                return this;
        }
    }

    @Override
    public <R> R accept(AbstractASMVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
