package cyr7.x86.asm;

import cyr7.ir.nodes.IRNode;

import java.util.List;
import java.util.Optional;

public class ASMLineFactory {

    public static ASMLineFactory instance = new ASMLineFactory();

    private final Optional<IRNode> node;

    private ASMLineFactory() {
        this.node = Optional.empty();
    }

    public ASMLineFactory(IRNode node) {
        this.node = Optional.of(node);
    }

    public ASMInstr Add(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.ADDQ, List.of(dest, source), node);
    }

    public ASMInstr Sub(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SUBQ, List.of(dest, source), node);
    }

    public ASMInstr Mul(ASMArg target, ASMArg source) {
        return new ASMInstr(ASMInstrType.IMULQ, List.of(target, source), node);
    }

    /**
     * Creates the IMUL ASMInstruction with one operand. The source operand (in
     * a general-purpose register or memory location) is multiplied by the value
     * in the RAX register and the product is stored in the RDX:RAX registers.
     */
    public ASMInstr Mul(ASMArg source) {
        return new ASMInstr(ASMInstrType.IMULQ, List.of(source), node);
    }

    public ASMInstr Div(ASMArg target) {
        return new ASMInstr(ASMInstrType.IDIVQ, List.of(target), node);
    }

    public ASMInstr And(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.ANDQ, List.of(dest, source), node);
    }

    public ASMInstr Or(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.ORQ, List.of(dest, source), node);
    }

    public ASMInstr Xor(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.XORQ, List.of(dest, source), node);
    }

    public ASMInstr ARShift(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SARQ, List.of(dest, source), node);
    }

    public ASMInstr RShift(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SHRQ, List.of(dest, source), node);
    }

    public ASMInstr LShift(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.SHLQ, List.of(dest, source), node);
    }

    public ASMInstr Lea(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.LEAQ, List.of(dest, source), node);
    }

    public ASMInstr Push(ASMArg source) {
        return new ASMInstr(ASMInstrType.PUSHQ, List.of(source), node);
    }

    public ASMInstr Pop(ASMArg dest) {
        return new ASMInstr(ASMInstrType.POPQ, List.of(dest), node);
    }

    public ASMInstr Cmp(ASMArg left, ASMArg right) {
        return new ASMInstr(ASMInstrType.CMPQ, List.of(left, right), node);
    }

    public ASMInstr Mov(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.MOV, List.of(dest, source), node);
    }

    public ASMInstr MovAbs(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.MOVABSQ, List.of(dest, source), node);
    }

    public ASMInstr MovZX(ASMArg dest, ASMArg source) {
        return new ASMInstr(ASMInstrType.MOVZX, List.of(dest, source), node);
    }

    public ASMInstr Call(ASMArg label) {
        return new ASMInstr(ASMInstrType.CALLQ, List.of(label), node);
    }

    public ASMInstr Ret() {
        return new ASMInstr(ASMInstrType.RETQ, List.of(), node);
    }

    public ASMInstr SetZ(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETZ, List.of(dest), node);
    }

    public ASMInstr SetNE(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETNE, List.of(dest), node);
    }

    public ASMInstr SetLE(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETLE, List.of(dest), node);
    }

    public ASMInstr SetGE(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETGE, List.of(dest), node);
    }

    public ASMInstr SetG(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETG, List.of(dest), node);
    }

    public ASMInstr SetL(ASMArg dest) {
        return new ASMInstr(ASMInstrType.SETL, List.of(dest), node);
    }

    public ASMInstr Jump(ASMArg target) {
        return new ASMInstr(ASMInstrType.JMP, List.of(target), node);
    }

    public ASMInstr JumpNE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JNE, List.of(target), node);
    }

    public ASMInstr JumpE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JE, List.of(target), node);
    }

    public ASMInstr JumpGE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JGE, List.of(target), node);
    }

    public ASMInstr JumpG(ASMArg target) {
        return new ASMInstr(ASMInstrType.JG, List.of(target), node);
    }

    public ASMInstr JumpLE(ASMArg target) {
        return new ASMInstr(ASMInstrType.JLE, List.of(target), node);
    }

    public ASMInstr JumpL(ASMArg target) {
        return new ASMInstr(ASMInstrType.JL, List.of(target), node);
    }

    public ASMInstr CQO() {
        return new ASMInstr(ASMInstrType.CQO, List.of(), node);
    }

}
