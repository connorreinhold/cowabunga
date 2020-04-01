package cyr7.x86.reg_allocator;

import cyr7.ir.nodes.IRCompUnit;
import cyr7.x86.asm.ASMLine;

import java.util.List;

public interface ASMGenerator {

    List<ASMLine> generate(IRCompUnit compUnit);

}
