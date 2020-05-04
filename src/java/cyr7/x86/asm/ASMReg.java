package cyr7.x86.asm;

import cyr7.x86.visitor.ASMArgVisitor;
import cyr7.x86.visitor.AbstractASMVisitor;

public enum ASMReg implements ASMTempRegArg {

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

    // https://en.wikipedia.org/wiki/FLAGS_register
    FLAGS,

    AL, CL, DL, BL, // 8 bit registers

    // Newer 8 bit registers
    R8B, R9B, R10B, R11B, R12B, R13B, R14B, R15B;

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

    @Override
    public <R> R accept(ASMArgVisitor<R> visitor) {
        return visitor.accept(this);
    }

    public ASMRegSize size() {
        switch (this) {
            case RAX:
            case RCX:
            case RDX:
            case RBX:
            case RSP:
            case RBP:
            case RSI:
            case RDI:
            case R8:
            case R9:
            case R10:
            case R11:
            case R12:
            case R13:
            case R14:
            case R15:
            case FLAGS:
                return ASMRegSize.QWORD;
            case AL:
            case CL:
            case DL:
            case BL:
            case R8B:
            case R9B:
            case R10B:
            case R11B:
            case R12B:
            case R13B:
            case R14B:
            case R15B:
                return ASMRegSize.BYTE;
            default:
                return ASMRegSize.QWORD;
        }
    }

}
