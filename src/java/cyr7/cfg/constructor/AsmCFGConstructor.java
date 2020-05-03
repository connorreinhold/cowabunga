package cyr7.cfg.constructor;

import java.util.List;
import java.util.Map;

import cyr7.cfg.nodes.ir.CFGNode;
import cyr7.x86.asm.ASMLine;

public class AsmCFGConstructor {

    /**
     * Generates a CFG Tree for a function defined with assembly instructions.
     */
    public static Map<String, CFGNode> constructAsmCFG(List<ASMLine> instructions) {

        return cfgCollection;
    }

}