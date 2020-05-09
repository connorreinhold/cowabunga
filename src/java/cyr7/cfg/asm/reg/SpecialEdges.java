package cyr7.cfg.asm.reg;

import cyr7.cfg.asm.nodes.AsmCFGSourceNode;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempRegArg;

enum SpecialEdges {
    ;

    public static void addSpecialEdges(ASMInstr instr, InterferenceGraph graph) {
        switch (instr.type) {
            case ADDQ:
                break;
            case SUBQ:
                break;
            case IMULQ:
                break;
            case IDIVQ:
                if (instr.args.size() == 1
                    && instr.args.get(0) instanceof ASMTempArg) {
                    graph.addEdge(ASMReg.RDX, (ASMTempArg) instr.args.get(0));
                }
                break;
            case LEAQ:
                break;
            case ANDQ:
                break;
            case ORQ:
                break;
            case XORQ:
                break;
            case SHLQ:
                break;
            case SHRQ:
                break;
            case SARQ:
                break;
            case MOVQ:
                break;
            case MOV:
                break;
            case PUSHQ:
                break;
            case POPQ:
                break;
            case MOVABSQ:
                break;
            case MOVZX:
                break;
            case CMPQ:
                break;
            case CALLQ:
                break;
            case RETQ:
                break;
            case JMP:
                break;
            case JE:
                break;
            case JNE:
                break;
            case JLE:
                break;
            case JGE:
                break;
            case JL:
                break;
            case JG:
                break;
            case SETZ:
                break;
            case SETNE:
                break;
            case SETLE:
                break;
            case SETL:
                break;
            case SETG:
                break;
            case SETGE:
                break;
            case CQO:
                break;
        }
    }
}
