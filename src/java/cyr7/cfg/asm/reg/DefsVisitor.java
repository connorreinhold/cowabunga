package cyr7.cfg.asm.reg;

import cyr7.cfg.asm.nodes.AsmCFGIfNode;
import cyr7.cfg.asm.nodes.AsmCFGOpNode;
import cyr7.cfg.asm.nodes.AsmCFGReturnNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cfg.asm.visitor.AsmCFGVisitor;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.Collections;
import java.util.Set;

public final class DefsVisitor implements AsmCFGVisitor<Set<ASMTempRegArg>> {

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
                if (instr.args.get(0) instanceof ASMTempRegArg) {
                    return Set.of(
                        (ASMTempRegArg) instr.args.get(0),
                        ASMReg.FLAGS);
                } else {
                    return Set.of();
                }

            case IMULQ:
                if (instr.args.size() == 2
                    && instr.args.get(0) instanceof ASMTempRegArg) {
                    return Set.of(
                        (ASMTempRegArg) instr.args.get(0),
                        ASMReg.FLAGS);
                }
                // fallthrough intentionally
            case IDIVQ:
            case CQO: // https://www.felixcloutier.com/x86/cwd:cdq:cqo
                return Set.of(ASMReg.RDX, ASMReg.RAX);

            // movs and sets
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

                // push and pop
            case PUSHQ:
            case POPQ:
                return Set.of(ASMReg.RSP);

            // compare
            case CMPQ:
                return Set.of(ASMReg.FLAGS);

            case CALLQ:
                String mangledName = ((ASMLabelArg) instr.args.get(0)).label;
                // A call defs its return registers.
                return Collections.unmodifiableSet(
                    MangledNameParser.returnRegisters(mangledName));

            // control flow
            case RETQ:
            case JMP:
            case JE:
            case JNE:
            case JLE:
            case JGE:
            case JL:
            case JG:
            default:
                return Set.of();
        }
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
        return Collections.emptySet();
    }

}
