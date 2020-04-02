package cyr7.x86.reg_allocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.tiler.TilerData;
import cyr7.x86.tiler.TilerFactory;

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

        String returnLbl = "end_" + funcName;

        MyIRVisitor<TilerData> tiler = tilerFactory.constructTiler(generator,
            funcName,
                numRetValues,
            returnLbl);

        return funcDecl.body().accept(tiler).optimalInstructions;
    }

}
