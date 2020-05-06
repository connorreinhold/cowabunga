package cyr7.cfg.asm.reg;

import cyr7.ir.IdGenerator;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMRegSize;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

final class SpillArgTranslator implements Runnable {

    private static final ASMLineFactory make = ASMLineFactory.instance;
    private static final ASMArgFactory makeArg = ASMArgFactory.instance;

    // input

    private final HashSet<ASMTempRegArg> spilledTemps;
    private final IdGenerator idGenerator;
    private final SpillMemAllocator spillAllocator;
    private final ASMInstr instr;

    // output
    private final List<ASMLine> translated = new ArrayList<>();

    public SpillArgTranslator(
        HashSet<ASMTempRegArg> spilledTemps,
        IdGenerator idGenerator,
        SpillMemAllocator spillAllocator,
        ASMInstr instr) {

        this.spilledTemps = spilledTemps;
        this.idGenerator = idGenerator;
        this.spillAllocator = spillAllocator;
        this.instr = instr;
    }

    public List<ASMLine> translated() {
        return translated;
    }

    @Override
    public void run() {
        List<ASMArg> newArgs = new ArrayList<>(instr.args.size());
        List<ASMLine> prelude = new ArrayList<>(1);
        List<ASMLine> postlude = new ArrayList<>(1);

        for (ASMArg arg : instr.args) {
            if (arg instanceof ASMTempArg) {
                newArgs.add(translateTempArg((ASMTempArg) arg, prelude, postlude));
            } else if (arg instanceof ASMMemArg) {
                newArgs.add(translateMemArg((ASMMemArg) arg, prelude, postlude));
            } else {
                newArgs.add(arg);
            }
        }

        translated.addAll(prelude);
        translated.add(instr.withArgsReplaced(newArgs));
        translated.addAll(postlude);
    }

    /**
     *  If only _t3 is a spill,
     *  Translate something like:
     *      mov rax, [ _t2 + 4 * _t3 + 6 ]
     *
     *  Into:
     *      mov _t99, [ RBP + ... ]
     *      mov rax, [ _t2 + 4 * _t99 + 6 ]
     *      mov [ RBP + ... ], _t99
     */
    private ASMMemArg translateMemArg(ASMMemArg arg, List<ASMLine> prelude, List<ASMLine> postlude) {
        Optional<ASMTempRegArg> newBase = arg.address.base
            .map(baseArg -> translateTempRegArg(baseArg, prelude, postlude));
        Optional<ASMTempRegArg> newIndex = arg.address.index
            .map(baseArg -> translateTempRegArg(baseArg, prelude, postlude));

        return makeArg.mem(makeArg.addr(
            newBase,
            arg.address.scale,
            newIndex,
            arg.address.displacement));
    }

    private ASMTempRegArg translateTempRegArg(ASMTempRegArg arg, List<ASMLine> prelude, List<ASMLine> postlude) {
        if (arg instanceof ASMTempArg) {
            return translateTempArg((ASMTempArg) arg, prelude, postlude);
        } else {
            return arg;
        }
    }

    /**
     *  Lecture notes:
     *
     *  If t2 is spilled and we're translating
     *      add _t2, 5
     *
     *  Then it becomes:
     *      mov _t99, [ RBP + ... ]
     *      add _t99, 5
     *      mov [ RBP + ... ], _t99
     */
    private ASMTempArg translateTempArg(ASMTempArg arg, List<ASMLine> prelude, List<ASMLine> postlude) {
        if (!spilledTemps.contains(arg)) {
            return arg;
        }

        // make a new temp of the same size
        ASMTempArg newTemp = makeNewSpillTemp(arg.size);
        ASMMemArg argMem = makeArg.mem(addrOfSpilledTemp(arg));

        prelude.add(make.Mov(newTemp, argMem));
        postlude.add(0, make.Mov(argMem, newTemp));

        return newTemp;
    }

    private ASMTempArg makeNewSpillTemp(ASMRegSize size) {
        return makeArg.temp(idGenerator.newTemp("spill"), size);
    }

    private ASMAddrExpr addrOfSpilledTemp(ASMTempArg tempArg) {
        assert spilledTemps.contains(tempArg);
        Integer index = spillAllocator.indexOf(tempArg);

        // [RBP + 8 * (index + 1)]
        return makeArg.addr(
            Optional.of(ASMReg.RBP),
            ScaleValues.ONE,
            Optional.empty(),
            -8 * (index + 1));
    }

}
