package cyr7.cfg.asm.reg;

import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMInstrType;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

final class FinalProgramRewriter implements Runnable {

    private final ArrayList<ASMLine> program;
    private final HashMap<ASMTempRegArg, Integer> coloring;
    private final ASMReg[] registers;
    private final HashSet<Integer> coalescedMoves;
    private final Alias alias;

    private final List<ASMLine> rewritten = new ArrayList<>();

    public FinalProgramRewriter(
        ArrayList<ASMLine> program,
        HashMap<ASMTempRegArg, Integer> coloring,
        ASMReg[] registers,
        HashSet<Integer> coalescedMoves,
        Alias alias) {

        this.program = new ArrayList<>(program);
        this.coloring = coloring;
        this.registers = registers;
        this.coalescedMoves = coalescedMoves;
        this.alias = alias;
    }

    public List<ASMLine> rewritten() {
        return rewritten;
    }

    @Override
    public void run() {
        // the last instruction of the program should be
        assert ((ASMInstr) program.get(program.size() - 1)).type
            == ASMInstrType.RETQ;

        for (int i = program.size() - 2; i >= 0; i--) {
            if (coalescedMoves.contains(i)) {
                continue;
            }

            ASMLine line = program.get(i);

            if (line instanceof ASMInstr) {
                rewritten.add(0, replaceTempsWithRegs((ASMInstr) line));
            } else {
                rewritten.add(0, line);
            }
        }
    }

    private ASMInstr replaceTempsWithRegs(ASMInstr instr) {
        List<ASMArg> newArgs = new ArrayList<>(instr.args.size());

        for (ASMArg arg : instr.args) {
            if (arg instanceof ASMTempArg) {
                newArgs.add(reg((ASMTempArg) arg));
            } else if (arg instanceof ASMMemArg) {
                newArgs.add(replaceTempsWithRegs((ASMMemArg) arg));
            } else {
                newArgs.add(arg);
            }
        }

        return instr.withArgsReplaced(newArgs);
    }

    private ASMMemArg replaceTempsWithRegs(ASMMemArg memArg) {
        Optional<ASMReg> newBase = memArg.address.base.map(this::reg);
        Optional<ASMReg> newIndex = memArg.address.index.map(this::reg);
        return new ASMMemArg(new ASMAddrExpr(
            newBase, memArg.address.scale, newIndex, memArg.address.displacement
        ));
    }

    private ASMReg reg(ASMTempRegArg temp) {
        ASMReg reg = registers[coloring.get(alias.get(temp))];
        if (temp instanceof ASMTempArg) {
            ASMTempArg tempArg = (ASMTempArg) temp;
            switch (tempArg.size) {
                case QWORD:
                    return reg;
                case BYTE:
                    return reg.correspondingByteReg();
                default:
                    return reg;
            }
        } else {
            return reg;
        }
    }

}
