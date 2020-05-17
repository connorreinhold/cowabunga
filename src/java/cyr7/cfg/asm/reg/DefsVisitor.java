package cyr7.cfg.asm.reg;

import cyr7.cfg.asm.nodes.AsmCFGIfNode;
import cyr7.cfg.asm.nodes.AsmCFGOpNode;
import cyr7.cfg.asm.nodes.AsmCFGReturnNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cfg.asm.visitor.AsmCFGVisitor;
import cyr7.util.Sets;
import cyr7.x86.ASMConstants;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

final class DefsVisitor implements AsmCFGVisitor<Set<ASMTempRegArg>> {

    @Override
    public Set<ASMTempRegArg> visit(AsmCFGOpNode n) {
        ASMInstr instr = n.sourceInstr();
        switch (instr.type) {
            // arithmetic operations, exactly two arguments:
            // the first is the dest, the second is the source
            // modifies the flags register
            case ADDQ:
            case SUBQ:
            case ANDQ:
            case ORQ:
            case XORQ:
            case LEAQ:
            case SHLQ:
            case SHRQ:
            case SARQ:
            case MOVQ:
            case MOV:
            case MOVABSQ:
            case MOVZX:
            case SETZ:
            case SETNE:
            case SETLE:
            case SETL:
            case SETG:
            case SETGE:
                if (instr.args.get(0) instanceof ASMTempRegArg) {
                    return Set.of((ASMTempRegArg) instr.args.get(0));
                } else {
                    return Set.of();
                }

            case IMULQ:
                if (instr.args.size() == 2
                    && instr.args.get(0) instanceof ASMTempRegArg) {
                    return Set.of((ASMTempRegArg) instr.args.get(0));
                }
                // fallthrough intentionally
            case IDIVQ:
            case CQO: // https://www.felixcloutier.com/x86/cwd:cdq:cqo
                return Set.of(ASMReg.RDX, ASMReg.RAX);

            // push and pop
            case PUSHQ:
            case POPQ:
                return Set.of();

            // compare
            case CMPQ:
                return Set.of();

            case CALLQ:
                String mangledName = ((ASMLabelArg) instr.args.get(0)).label;
                // A call defs its return registers and all
                // non-callee-saved registers (i.e. caller-saved registers + arg registers)
                return Sets.union(
                    MangledNameParser.returnRegisters(mangledName),
                    Sets.difference(
                        Sets.union(
                            Set.of(ASMConstants.CALLER_SAVED_REGISTERS),
                            Set.of(ASMConstants.ARGUMENT_REGISTERS)
                        ),
                        Set.of(ASMReg.RSP, ASMReg.RBP)));

            case JMP:
                return Set.of();

            // control flow
            // these are represented as an inherent part of the CFG
            case RETQ:
            case JE:
            case JNE:
            case JLE:
            case JGE:
            case JL:
            case JG:
                throw new IllegalArgumentException();
        }

        throw new AssertionError();
    }

    @Override
    public Set<ASMTempRegArg> visit(AsmCFGIfNode n) {
        return Collections.emptySet();
    }

    @Override
    public Set<ASMTempRegArg> visit(AsmCFGReturnNode n) {
        return Collections.emptySet();
    }

    @Override
    public Set<ASMTempRegArg> visit(AsmCFGStartNode n) {
        return Sets.difference(Set.of(
            ASMConstants.CALLER_SAVED_REGISTERS
        ), Set.of(ASMReg.RBP, ASMReg.RSP));
    }

}
