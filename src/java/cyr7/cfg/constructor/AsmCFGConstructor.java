package cyr7.cfg.constructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import cyr7.cfg.nodes.asm.AsmCFGNode;
import cyr7.cfg.nodes.asm.AsmCFGStartNode;
import cyr7.cfg.nodes.asm.AsmCFGStubNode;
import cyr7.cfg.nodes.ir.CFGNode;
import cyr7.util.Pair;
import cyr7.x86.asm.ASMLine;

public class AsmCFGConstructor {

    private final Map<String, AsmCFGNode> labelToCFG;
    private final Queue<Pair<AsmCFGStubNode, String>> jumpTargetFromCFG;

    /**
     * Null until convertToCFG is called, where it is first defined in the
     * method's first for-loop body.
     */
    private AsmCFGNode successor;

    private final List<ASMLine> lines;

    protected AsmCFGConstructorVisitor(List<ASMLine> instructions) {
        this.labelToCFG = new HashMap<>();
        this.jumpTargetFromCFG = new LinkedList<>();
        this.lines = instructions;
    }

    public static AsmCFGStartNode constructAsmCFG(List<ASMLine> instructions) {
        Map<String, AsmCFGNode> labelToCFG = new HashMap<>();
        Queue<Pair<AsmCFGStubNode, String>> jumpTargetFromCFG = new LinkedList<>();

        AsmCFGNode successor;
    }



}