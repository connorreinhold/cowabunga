package cyr7.x86.reg_allocator;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.semantics.types.FunctionType;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.tiler.TilerData;
import cyr7.x86.tiler.TilerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<ASMLine> generate(IRCompUnit compUnit) {
        List<ASMLine> lines = new ArrayList<>();

        Set<Entry<String, IRFuncDecl>> nameFuncDeclPairs = compUnit.functions()
            .entrySet();

        Map<String, FunctionType> fMap = new HashMap<>();
        for (Entry<String, IRFuncDecl> nameFuncDecl : nameFuncDeclPairs) {
            fMap.put(nameFuncDecl.getKey(),
                nameFuncDecl.getValue()
                    .type());
        }

        for (Entry<String, IRFuncDecl> nameFuncDecl : nameFuncDeclPairs) {
            lines.addAll(generate(nameFuncDecl.getKey(),
                nameFuncDecl.getValue(),
                fMap));
        }

        return lines;
    }

    private List<ASMLine> generate(String funcName, IRFuncDecl funcDecl,
                                   Map<String, FunctionType> fMap) {

        String returnLbl = "end_" + funcName;

        MyIRVisitor<TilerData> tiler = tilerFactory.constructTiler(generator,
            funcName,
            fMap,
            returnLbl);

        List<ASMLine> functionBody = funcDecl.body()
            .accept(tiler).optimalInstructions;
        return functionBody;
    }

}
