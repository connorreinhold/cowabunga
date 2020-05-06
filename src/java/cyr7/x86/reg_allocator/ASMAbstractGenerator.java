package cyr7.x86.reg_allocator;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.x86.abst.ASMAbstract;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.tiler.TilerFactory;
import cyr7.x86.visitor.TempVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class ASMAbstractGenerator implements ASMGenerator {

    private final TilerFactory tilerFactory;
    private final IdGenerator generator;

    public ASMAbstractGenerator(TilerFactory tilerFactory,
                                       IdGenerator generator) {
        this.tilerFactory = tilerFactory;
        this.generator = generator;
    }

    @Override
    public List<ASMLine> generate(IRCompUnit compUnit) {
        List<ASMLine> lines = new ArrayList<>();

        for (IRFuncDecl funcDecl : compUnit.functions().values()) {
            lines.addAll(generate(funcDecl));
        }

        return lines;
    }

    private List<ASMLine> generate(IRFuncDecl funcDecl) {
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

        return lines;
    }

    private List<ASMTempArg> uniqueTemps(List<ASMLine> lines) {
        Set<ASMTempArg> temps = new HashSet<>();
        var tempVisitor = new TempVisitor();
        lines.forEach(l -> temps.addAll(l.accept(tempVisitor)));
        return new ArrayList<>(temps);
    }

}
