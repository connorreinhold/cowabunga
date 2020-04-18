package cyr7.x86.tiler;

import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLabel;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempArg.Size;
import cyr7.x86.visitor.AbstractASMVisitor;

public class ReduceTo8BitVisitor extends AbstractASMVisitor<ASMArg> {

    @Override
    public ASMArg visit(ASMTempArg t) {
        return new ASMTempArg(t.name, Size.BYTE);
    }

    @Override
    public ASMArg visit(ASMAddrExpr e) {
        throw new UnsupportedOperationException(
                "Cannot reduce an address to 8 bit");
    }

    @Override
    public ASMArg visit(ASMMemArg m) {
        return m;
    }

    @Override
    public ASMArg visit(ASMConstArg c) {
        return c;
    }

    @Override
    public ASMArg visit(ASMReg type) {
        switch (type) {
        case AL:
        case BL:
        case CL:
        case DL:
        case R8B:
        case R9B:
        case R10B:
        case R11B:
        case R12B:
        case R13B:
        case R14B:
        case R15B:
            return type;
        case R10:
            return ASMReg.R10B;
        case R11:
            return ASMReg.R11B;
        case R12:
            return ASMReg.R12B;
        case R13:
            return ASMReg.R13B;
        case R14:
            return ASMReg.R14B;
        case R15:
            return ASMReg.R15B;
        case R8:
            return ASMReg.R8B;
        case R9:
            return ASMReg.R9B;
        case RAX:
            return ASMReg.AL;
        case RBX:
            return ASMReg.BL;
        case RCX:
            return ASMReg.CL;
        case RDX:
            return ASMReg.DL;
        case RBP:
            break;
        case RDI:
            break;
        case RSI:
            break;
        case RSP:
            break;
        default:
            break;
        }
        throw new UnsupportedOperationException("Cannot reduce register " +
                type.name() + "to 8 bits.");
    }

    @Override
    public ASMArg visit(ASMInstr i) {
        throw new UnsupportedOperationException(
                "Cannot reduce an instruction to 8 bit");
    }

    @Override
    public ASMArg visit(ASMLabel lbl) {
        throw new UnsupportedOperationException(
                "Cannot reduce an instruction to 8 bit");
    }

    @Override
    public ASMArg visit(ASMLabelArg l) {
        throw new UnsupportedOperationException(
                "Cannot reduce a label to 8 bit");
    }

}