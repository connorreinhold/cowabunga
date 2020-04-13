package cyr7.x86.reg_allocator;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.tiler.TilerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class ASMAbstractGenerator extends ASMTrivialRegAllocGenerator {

    private final TilerFactory tilerFactory;
    private final IdGenerator generator;

    public ASMAbstractGenerator(TilerFactory tilerFactory,
                                       IdGenerator generator) {
        super(tilerFactory, generator);
        this.tilerFactory = tilerFactory;
        this.generator = generator;
    }

    @Override
    public List<ASMLine> generate(IRCompUnit compUnit) {
        List<ASMLine> lines = new ArrayList<>();

        Set<Entry<String, IRFuncDecl>> nameFuncDeclPairs = compUnit.functions()
            .entrySet();

        for (Entry<String, IRFuncDecl> nameFuncDecl : nameFuncDeclPairs) {
            int numRetValues = nameFuncDecl.getValue().numOfReturnValues();
            lines.addAll(generate(nameFuncDecl.getKey(),
                nameFuncDecl.getValue(),
                    numRetValues));
        }

        return lines;
    }

    private List<ASMLine> generate(String funcName, IRFuncDecl funcDecl,
            int numRetValues) {
        return generateAbstractAssembly(funcName, funcDecl, numRetValues).part1();
    }

}
