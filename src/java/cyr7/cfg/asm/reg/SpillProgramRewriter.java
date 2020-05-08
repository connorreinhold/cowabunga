package cyr7.cfg.asm.reg;

import cyr7.ir.IdGenerator;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

final class SpillProgramRewriter implements Runnable {

    private final IdGenerator generator;
    private final List<ASMLine> lines;
    private final HashSet<ASMTempRegArg> spilledNodes;
    private final SpillMemAllocator spillAllocator;

    private final ArrayList<ASMLine> rewritten;

    SpillProgramRewriter(
        IdGenerator generator,
        List<ASMLine> lines,
        HashSet<ASMTempRegArg> spilledNodes,
        SpillMemAllocator spillAllocator) {

        this.generator = generator;
        this.lines = lines;
        this.spilledNodes = spilledNodes;
        this.spillAllocator = spillAllocator;
        this.rewritten = new ArrayList<>();
    }

    @Override
    public void run() {
        spillAllocator.clearNewTemps();

        for (ASMLine line : lines) {
            if (line instanceof ASMInstr) {
                SpillArgTranslator translator = new SpillArgTranslator(
                    spilledNodes,
                    generator,
                    spillAllocator,
                    (ASMInstr) line);
                translator.run();
                rewritten.addAll(translator.translated());
            } else {
                rewritten.add(line);
            }
        }
    }

    public ArrayList<ASMLine> rewritten() {
        return rewritten;
    }

}
