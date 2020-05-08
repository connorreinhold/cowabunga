package cyr7.cfg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cyr7.C;
import cyr7.cfg.ir.dfa.WorklistAnalysis;
import cyr7.cfg.ir.dfa.loops.DominatorAnalysis;
import cyr7.cfg.ir.dfa.loops.DominatorUtil;
import cyr7.cfg.ir.dfa.loops.StrengthReductionUtil;
import cyr7.cfg.ir.dfa.loops.inductionvars.DefinedInductionVariable;
import cyr7.cfg.ir.dfa.loops.inductionvars.InductionVariable;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGNodeFactory;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRBinOp.OpType;

public class TestDerivedInductionVariableAnalysis {

    private static final CFGNodeFactory make = new CFGNodeFactory(C.LOC);
    private static final IRNodeFactory makeIR = new IRNodeFactory_c(C.LOC);
    
    /**
     * 1:   x = 12
     * 2:   while (x < 12)
     * 3:       x = x + 1
     * 4:       a = x - 2
     * 5:   x = 2
     */
    
    @Test
    void testSimpleLoop() {
        CFGNode line6 = make.Return();
        CFGNode line5 = make.VarAssign("x",
                makeIR.IRConst(2),
                line6);
        CFGStubNode line3Stub = new CFGStubNode();
        CFGNode line2 = make.If(line3Stub, line5, makeIR.IRBinOp(OpType.LT, 
                makeIR.IRTemp("x"), makeIR.IRConst(12)));
        CFGNode line4 = make.VarAssign("a",
                makeIR.IRBinOp(OpType.SUB, makeIR.IRTemp("x"), makeIR.IRConst(2)),
                line2);
        CFGNode line3 = make.VarAssign("x", makeIR.IRBinOp(OpType.ADD, makeIR.IRTemp("x"), 
                makeIR.IRConst(1)), 
                line4);
        line2.replaceOutEdge(line3Stub, line3);
        CFGNode line1 = make.VarAssign("x", makeIR.IRConst(12), 
                line2);
        CFGStartNode line0 = make.Start(line1);

        var result = WorklistAnalysis.analyze(line0, DominatorAnalysis.INSTANCE);
        Map<CFGNode, Set<CFGNode>> dominators = DominatorUtil.generateMap(result.out());
        Map<CFGNode, Map<CFGNode, Map<String, InductionVariable>>> loopIVAnalysis = 
                StrengthReductionUtil.runIVAnalysis(dominators);
        
        Map<String, InductionVariable> line2Expected = new HashMap<>();
        line2Expected.put("x", new DefinedInductionVariable("x", 1, 0));
        
        Map<String, InductionVariable> line3Expected = new HashMap<>();
        line3Expected.put("x", new DefinedInductionVariable("x", 1, 1));

        Map<String, InductionVariable> line4Expected = new HashMap<>();
        line4Expected.put("x", new DefinedInductionVariable("x", 1, 1));
        line4Expected.put("a", new DefinedInductionVariable("x", 1, -1));
        
        assertEquals(line2Expected, 
                loopIVAnalysis.get(line2).get(line2));
        assertEquals(line3Expected, 
                loopIVAnalysis.get(line2).get(line3));
        assertEquals(line4Expected, 
                loopIVAnalysis.get(line2).get(line4));
        
    }
}