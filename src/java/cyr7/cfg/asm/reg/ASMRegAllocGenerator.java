package cyr7.cfg.asm.reg;

import cyr7.cli.CLI;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.x86.abst.ASMAbstract;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.reg_allocator.ASMGenerator;
import cyr7.x86.tiler.TilerFactory;

import java.util.ArrayList;
import java.util.List;

public final class ASMRegAllocGenerator implements ASMGenerator {

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

//        System.out.println("--- ABSTRACT ASSEMBLY ---");
//        print(abstractASM);

        print(abstractASM);
        RegisterAllocator registerAllocator
            = new RegisterAllocator(abstractASM, funcDecl.name(), generator);
        registerAllocator.run();

        CLI.debugPrint("--- REGISTER ALLOCATED ---");
        print(registerAllocator.program());

        List<ASMLine> prologue = ASMAbstract.createPrologue(
            funcDecl.name(),
            registerAllocator.numSpilledTemps());

        List<ASMLine> epilogue = ASMAbstract.createEpilogue(
            registerAllocator.numSpilledTemps());

        List<ASMLine> lines = new ArrayList<>(
            prologue.size() + registerAllocator.program().size() + epilogue.size());
        lines.addAll(prologue);
        lines.addAll(registerAllocator.program());
        lines.addAll(epilogue);
        CLI.debugPrint("--- FINAL PROGRAM ---");
        print(lines);
        CLI.debugPrint("--- END FINAL PROGRAM ---");
        return lines;
    }

    public static void print(List<ASMLine> lines) {
        for (int i = 0; i < lines.size(); i++) {
            CLI.debugPrint(String.format("%2d: %s", i, lines.get(i).getIntelAssembly()));
        }
        System.err.flush();
    }

}
