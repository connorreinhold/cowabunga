package cyr7.x86.asm;

import cyr7.util.OneOfTwo;

import java.util.List;

public class ASMFactory {

    public static ASMInstr Add(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.ADDQ, List.of(dest, source));
    }

    public static ASMInstr Sub(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SUBQ, List.of(dest, source));
    }

    public static ASMInstr Mul(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.IMULQ, List.of(dest, source));
    }

    public static ASMInstr Div(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.IDIVQ, List.of(dest, source));
    }

    public static ASMInstr And(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.ANDQ, List.of(dest, source));
    }

    public static ASMInstr Or(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.ORQ, List.of(dest, source));
    }

    public static ASMInstr ARShift(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SARQ, List.of(dest, source));
    }

    public static ASMInstr RShift(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SHRQ, List.of(dest, source));
    }

    public static ASMInstr LShift(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SHLQ, List.of(dest, source));
    }

    public static ASMInstr Lea(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.LEAQ, List.of(dest, source));
    }

    public static ASMInstr Push(ASMArg source) {
        return new ASMInstr(ASMInstrType.PUSHQ, List.of(source));
    }

    public static ASMInstr Pop(ASMArg dest) {
        return new ASMInstr(ASMInstrType.POPQ, List.of(dest));
    }

    public static ASMInstr Cmp(ASMArg left, ASMArg right) {
        return new ASMInstr(ASMInstrType.CMPQ, List.of(left, right));
    }

    public static ASMInstr Call(ASMLabelArg label) {
        return new ASMInstr(ASMInstrType.CALLQ, List.of(label));
    }

    public static ASMInstr Ret() {
        return new ASMInstr(ASMInstrType.RETQ, List.of());
    }

    public static ASMInstr SetZ(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETZ, List.of(dest));
    }

    public static ASMInstr SetNE(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETNE, List.of(dest));
    }

    public static ASMInstr SetLE(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETLE, List.of(dest));
    }

    public static ASMInstr Jump(ASMArg target) {
        return new ASMInstr(ASMInstrType.J, List.of(target));
    }

    public static ASMInstr JumpNE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JNE, List.of(target));
    }

    public static ASMInstr JumpE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JE, List.of(target));
    }

    public static ASMInstr JumpGE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JGE, List.of(target));
    }

    public static ASMInstr JumpG(ASMArg target) {
        return new ASMInstr(ASMInstrType.JG, List.of(target));
    }

    public static ASMInstr JumpLE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JLE, List.of(target));
    }

    public static ASMInstr JumpL(ASMArg target) {
        return new ASMInstr(ASMInstrType.JL, List.of(target));
    }

    public static ASMInstr CQO() {
        return new ASMInstr(ASMInstrType.CQO, List.of());
    }

}
