package cyr7.cfg;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cyr7.C;
import cyr7.cfg.ir.dfa.WorklistAnalysis;
import cyr7.cfg.ir.dfa.loops.DominatorAnalysis;
import cyr7.cfg.ir.dfa.loops.DominatorUtil;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGNodeFactory;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRBinOp.OpType;

public class TestDominatorAnalysis {

    private static final CFGNodeFactory make = new CFGNodeFactory(C.LOC);
    private static final IRNodeFactory makeIR = new IRNodeFactory_c(C.LOC);

    /**
     * 1:   x = 1
     * 2:   if x < 2
     * 3:       y = 5 + x
     * else
     * 5:       y = f(x)
     * 6:   z = y * y
     * <p>
     * analysis shows that:
     * x = 1
     * y = 6
     * z = 36
     */
    @Test
    void testLectureExample() {
        CFGNode line7 = make.Return();
        CFGNode line6 = make.VarAssign(
            "z",
            makeIR.IRBinOp(OpType.MUL,
                makeIR.IRTemp("y"),
                makeIR.IRTemp("y")),
            line7);
        CFGNode line5 = make.Call(
            makeIR.IRCallStmt(List.of("y"), makeIR.IRName("f"),
                List.of(makeIR.IRTemp("x"))),
            line6);
        CFGNode line3 = make.VarAssign(
            "y",
            makeIR.IRBinOp(OpType.ADD,
                makeIR.IRConst(5),
                makeIR.IRTemp("x")),
            line6);
        CFGNode line2 = make.If(
            line3,
            line5,
            makeIR.IRBinOp(OpType.LT,
                makeIR.IRTemp("x"),
                makeIR.IRConst(2)));
        CFGNode line1 = make.VarAssign(
            "x",
            makeIR.IRConst(1),
            line2);
        CFGStartNode line0 = make.Start(line1);

        var result = WorklistAnalysis.analyze(line0, DominatorAnalysis.INSTANCE);
        
        Map<CFGNode, Set<CFGNode>> dominators = DominatorUtil.generateMap(result.out());
        
        assertEquals(Set.of(line0, line1), 
                dominators.get(line1));
        assertEquals(Set.of(line0, line1, line2), 
                dominators.get(line2));
        assertEquals(Set.of(line0, line1, line2, line3), 
                dominators.get(line3));
        assertEquals(Set.of(line0, line1, line2, line5), 
                dominators.get(line5));
        assertEquals(Set.of(line0, line1, line2, line6), 
                dominators.get(line6));
        assertEquals(Set.of(line0, line1, line2, line6, line7), 
                dominators.get(line7));
    }
    
    /**
     * 1:   x = 12
     * 1.1: y = -x
     * 1.2: a = f(x)
     * 2:   if a
     * 3:       z = x
     * 4:       y = y + z
     * 4.1: else
     * 5:       y = y + 3 * 4
     */
    @Test
    void testMultipleStatementIfBlock() {
        CFGNode line6 = make.Return();
        CFGNode line5 = make.VarAssign("y",
            makeIR.IRBinOp(OpType.ADD,
                makeIR.IRTemp("y"),
                makeIR.IRBinOp(OpType.MUL,
                    makeIR.IRConst(3),
                    makeIR.IRConst(4))),
            line6);
        CFGNode line4 = make.VarAssign(
            "y",
            makeIR.IRBinOp(OpType.ADD,
                makeIR.IRTemp("y"),
                makeIR.IRTemp("z")
            ),
            line6);
        CFGNode line3 = make.VarAssign(
            "z",
            makeIR.IRTemp("x"),
            line4);
        CFGNode line2 = make.If(line3, line5, makeIR.IRTemp("a"));
        CFGNode line1Point2 = make.Call(
            makeIR.IRCallStmt(
                List.of("a"),
                makeIR.IRName("f"),
                List.of(makeIR.IRTemp("x"))),
            line2);
        CFGNode line1Point1 = make.VarAssign(
            "y",
            makeIR.IRBinOp(OpType.SUB,
                makeIR.IRConst(0),
                makeIR.IRTemp("x")),
            line1Point2);
        CFGNode line1 = make.VarAssign(
            "x",
            makeIR.IRConst(12),
            line1Point1);
        CFGStartNode line0 = make.Start(line1);

        var result = WorklistAnalysis.analyze(line0, DominatorAnalysis.INSTANCE);
        Map<CFGNode, Set<CFGNode>> dominators = DominatorUtil.generateMap(result.out());
        
        assertEquals(Set.of(line0, line1), 
                dominators.get(line1));
        assertEquals(Set.of(line0, line1, line1Point1), 
                dominators.get(line1Point1));
        assertEquals(Set.of(line0, line1, line1Point1, line1Point2), 
                dominators.get(line1Point2));
        assertEquals(Set.of(line0, line1, line1Point1, line1Point2, line2), 
                dominators.get(line2));
        assertEquals(Set.of(line0, line1, line1Point1, line1Point2, line2, line3), 
                dominators.get(line3));
        assertEquals(Set.of(line0, line1, line1Point1, line1Point2, line2, line3, line4), 
                dominators.get(line4));
        assertEquals(Set.of(line0, line1, line1Point1, line1Point2, line2, line5), 
                dominators.get(line5));
        assertEquals(Set.of(line0, line1, line1Point1, line1Point2, line2, line6), 
                dominators.get(line6));
    }
    
    /**
     * 1:   x = 12
     * 2:   while (x < 12)
     * 3:       x = 5
     * 4:       a = 3
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
                makeIR.IRConst(3),
                line2);
        CFGNode line3 = make.VarAssign("x", makeIR.IRConst(5), 
                line4);
        line2.replaceOutEdge(line3Stub, line3);
        CFGNode line1 = make.VarAssign("x", makeIR.IRConst(12), 
                line2);
        CFGStartNode line0 = make.Start(line1);

        var result = WorklistAnalysis.analyze(line0, DominatorAnalysis.INSTANCE);
        Map<CFGNode, Set<CFGNode>> dominators = DominatorUtil.generateMap(result.out());
        
        assertEquals(Set.of(line0, line1), 
                dominators.get(line1));
        assertEquals(Set.of(line0, line1, line2), 
                dominators.get(line2));
        assertEquals(Set.of(line0, line1, line2, line3), 
                dominators.get(line3));
        assertEquals(Set.of(line0, line1, line2, line3, line4), 
                dominators.get(line4));
        assertEquals(Set.of(line0, line1, line2, line5), 
                dominators.get(line5));
        assertEquals(Set.of(line0, line1, line2, line5, line6), 
                dominators.get(line6));
    }
    
    
}
