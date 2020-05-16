package cyr7.x86.reg_allocator;

import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.x86.asm.ASMLine;

import java.util.ArrayList;
import java.util.List;

public interface ASMGenerator {

    default List<ASMLine> generate(IRCompUnit compUnit) throws ASMGeneratorException {
        List<ASMLine> lines = new ArrayList<>();

        for (IRFuncDecl funcDecl : compUnit.functions().values()) {
            lines.addAll(generate(funcDecl));
        }

        return lines;
    }

    List<ASMLine> generate(IRFuncDecl funcDecl) throws ASMGeneratorException;

}
