package cyr7.x86.asm;

import cyr7.util.OneOfTwo;

import java.util.List;

public class ASMFactory {

    public static ASMInstr Add(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.ADD, List.of(dest, source));
    }

    public static ASMInstr Sub(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SUB, List.of(dest, source));
    }

    public static ASMInstr And(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.AND, List.of(dest, source));
    }

    public static ASMInstr Or(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.OR, List.of(dest, source));
    }

    public static ASMInstr Xor(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.XOR, List.of(dest, source));
    }

    public static ASMInstr Not(ASMArg dest) {
        return new ASMInstr(ASMInstrType.NOT, List.of(dest));
    }

    public static ASMInstr Mov(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.MOV, List.of(dest, source));
    }

    public static ASMInstr Push(ASMArg source) {
        return new ASMInstr(ASMInstrType.PUSH, List.of(source));
    }

    public static ASMInstr Pop(ASMArg dest) {
        return new ASMInstr(ASMInstrType.POP, List.of(dest));
    }

    public static ASMInstr Cmp(ASMArg left, ASMArg right) {
        return new ASMInstr(ASMInstrType.CMP, List.of(left, right));
    }

    public static ASMInstr Call(ASMLabelArg label) {
        return new ASMInstr(ASMInstrType.CALL, List.of(label));
    }

    public static ASMInstr Ret() {
        return new ASMInstr(ASMInstrType.RET, List.of());
    }
}
