package cyr7.x86.asm;

import java.util.List;

public class ASMLineFactory {

    public static ASMLineFactory instance = new ASMLineFactory();

    private ASMLineFactory() {
    }

    public ASMInstr Add(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.ADDQ, List.of(dest, source));
    }

    public ASMInstr Sub(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SUBQ, List.of(dest, source));
    }

    public ASMInstr Mul(ASMArg target, ASMArg left, ASMArg right) {
        return new ASMInstr(ASMInstrType.IMULQ, List.of(target, left, right));
    }

    public ASMInstr Div(ASMArg target) {
        return new ASMInstr(ASMInstrType.IDIVQ, List.of(target));
    }

    public ASMInstr And(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.ANDQ, List.of(dest, source));
    }

    public ASMInstr Or(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.ORQ, List.of(dest, source));
    }

    public ASMInstr Xor(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.XORQ, List.of(dest, source));
    }

    public ASMInstr ARShift(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SARQ, List.of(dest, source));
    }

    public ASMInstr RShift(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SHRQ, List.of(dest, source));
    }

    public ASMInstr LShift(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SHLQ, List.of(dest, source));
    }

    public ASMInstr Lea(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.LEAQ, List.of(dest, source));
    }

    public ASMInstr Push(ASMArg source) {
        return new ASMInstr(ASMInstrType.PUSHQ, List.of(source));
    }

    public ASMInstr Pop(ASMArg dest) {
        return new ASMInstr(ASMInstrType.POPQ, List.of(dest));
    }

    public ASMInstr Cmp(ASMArg left, ASMArg right) {
        return new ASMInstr(ASMInstrType.CMPQ, List.of(left, right));
    }

    public ASMInstr Mov(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.MOVQ, List.of(dest, source));
    }

    public ASMInstr MovAbs(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.MOVABSQ, List.of(dest, source));
    }

    public ASMInstr Call(ASMArg label) {
        return new ASMInstr(ASMInstrType.CALLQ, List.of(label));
    }

    public ASMInstr Ret() {
        return new ASMInstr(ASMInstrType.RETQ, List.of());
    }

    public ASMInstr SetZ(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETZ, List.of(dest));
    }

    public ASMInstr SetNE(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETNE, List.of(dest));
    }

    public ASMInstr SetLE(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETLE, List.of(dest));
    }

    public ASMInstr Jump(ASMArg target) {
        return new ASMInstr(ASMInstrType.JMP, List.of(target));
    }

    public ASMInstr JumpNE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JNE, List.of(target));
    }

    public ASMInstr JumpE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JE, List.of(target));
    }

    public ASMInstr JumpGE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JGE, List.of(target));
    }

    public ASMInstr JumpG(ASMArg target) {
        return new ASMInstr(ASMInstrType.JG, List.of(target));
    }

    public ASMInstr JumpLE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JLE, List.of(target));
    }

    public ASMInstr JumpL(ASMArg target) {
        return new ASMInstr(ASMInstrType.JL, List.of(target));
    }

    public ASMInstr CQO() {
        return new ASMInstr(ASMInstrType.CQO, List.of());
    }

}
