package cyr7.cfg.asm.reg;

import cyr7.util.Sets;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMInstrType;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.Collections;
import java.util.Set;

final class Util {

    public static Set<ASMTempRegArg> getAllArgs(ASMInstr instr) {
        return instr.args.stream()
            .map(arg -> arg.accept(ArgUsesVisitor.INSTANCE))
            .reduce(Sets::union)
            .orElse(Collections.emptySet());
    }

    public static boolean isMoveInstruction(ASMInstr instr) {
        return instr.type == ASMInstrType.MOV || instr.type == ASMInstrType.MOVQ;
    }

}
