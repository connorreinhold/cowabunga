package cyr7.x86.reg_allocator;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.abst.ASMAbstract;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMAssemblerDirective;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLabel;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMRegSize;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.tiler.TilerData;
import cyr7.x86.tiler.TilerFactory;
import cyr7.x86.visitor.TempVisitor;
import polyglot.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASMTrivialRegAllocGenerator implements ASMGenerator {

    private final TilerFactory tilerFactory;
    private final IdGenerator generator;

    public ASMTrivialRegAllocGenerator(TilerFactory tilerFactory,
                                       IdGenerator generator) {
        this.tilerFactory = tilerFactory;
        this.generator = generator;
    }

    @Override
    public List<ASMLine> generate(IRFuncDecl funcDecl) {
        List<ASMLine> body
            = ASMAbstract.generateBody(funcDecl, generator, tilerFactory);
        List<ASMTempArg> uniqueTemps = uniqueTemps(body);
        List<ASMLine> prologue
            = ASMAbstract.createPrologue(funcDecl.name(), uniqueTemps.size());
        List<ASMLine> epilogue
            = ASMAbstract.createEpilogue(uniqueTemps.size());

        List<ASMLine> lines = new ArrayList<>();
        lines.addAll(prologue);
        lines.addAll(body);
        lines.addAll(epilogue);

        return replaceTemporariesWithMemoryAccess(lines, uniqueTemps);
    }

    /**
     * Counts the number of unique temporaries used in a list of instructions.
     */
    private List<ASMTempArg> uniqueTemps(List<ASMLine> lines) {
        Set<ASMTempArg> temps = new HashSet<>();
        var tempVisitor = new TempVisitor();
        lines.forEach(l -> temps.addAll(l.accept(tempVisitor)));
        return new ArrayList<>(temps);
    }

    private List<ASMLine> replaceTemporariesWithMemoryAccess(
        List<ASMLine> lines, List<ASMTempArg> tempArgs) {

        Map<String, Integer> temporaryToIndexMap = new HashMap<>();
        for (int i = 0; i < tempArgs.size(); i++) {
            ASMTempArg tempArg = tempArgs.get(i);
            temporaryToIndexMap.put(tempArg.name, i);
        }

        return lines.stream().flatMap(asmLine -> {
            if (asmLine instanceof ASMInstr) {
                return replaceInstrTempArgsWithMemoryAccess(
                    (ASMInstr) asmLine,
                    temporaryToIndexMap).stream();
            } else {
                return Stream.of(asmLine);
            }
        }).collect(Collectors.toUnmodifiableList());
    }

    protected List<ASMLine> replaceInstrTempArgsWithMemoryAccess(
        ASMInstr instr,
        Map<String, Integer> temporaryToIndexMap) {

        ASMTrivialArgTranslator translator
            = new ASMTrivialArgTranslator(temporaryToIndexMap, instr);
        return translator.translate();
    }

}

