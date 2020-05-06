package cyr7.cfg.asm.reg;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.x86.abst.ASMAbstract;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.reg_allocator.ASMGenerator;
import cyr7.x86.tiler.TilerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ASMRegAllocGenerator implements ASMGenerator {

    private final TilerFactory tilerFactory;
    private final IdGenerator generator;

    public ASMRegAllocGenerator(TilerFactory tilerFactory,
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
        List<ASMLine> abstractASM
            = ASMAbstract.generateBody(funcDecl, generator, tilerFactory);

        System.out.println("--- ABSTRACT ASSEMBLY ---");
        print(abstractASM);

        RegisterAllocator registerAllocator
            = new RegisterAllocator(abstractASM, funcDecl.name(), generator);
        registerAllocator.run();

        System.out.println("--- REGISTER ALLOCATED ---");
        print(registerAllocator.program());
        System.out.println(registerAllocator.coalescedMoves());

        FinalProgramRewriter rewriter = new FinalProgramRewriter(
            registerAllocator.program(),
            registerAllocator.coloring(),
            registerAllocator.registers(),
            registerAllocator.coalescedMoves(),
            registerAllocator.alias());
        rewriter.run();

        List<ASMLine> prologue = ASMAbstract.createPrologue(
            funcDecl.name(),
            registerAllocator.allocatedTemps());

        List<ASMLine> epilogue = ASMAbstract.createEpilogue(
            registerAllocator.allocatedTemps());

        List<ASMLine> lines = new ArrayList<>(
            prologue.size() + rewriter.rewritten().size() + epilogue.size());
        lines.addAll(prologue);
        lines.addAll(rewriter.rewritten());
        lines.addAll(epilogue);
        print(lines);
        return lines;
    }

    private void print(List<ASMLine> lines) {
        for (int i = 0; i < lines.size(); i++) {
            System.out.print(String.format("%2d: ", i));
            System.out.println(lines.get(i).getIntelAssembly());
        }
        System.out.flush();
    }

}
