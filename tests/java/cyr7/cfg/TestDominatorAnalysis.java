package cyr7.cfg;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cyr7.C;
import cyr7.cfg.ir.dfa.DominatorAnalysis;
import cyr7.cfg.ir.dfa.WorklistAnalysis;
import cyr7.cfg.ir.dfa.DominatorAnalysis.LatticeElement;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGNodeFactory;
import cyr7.cfg.ir.nodes.CFGStartNode;
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
     * 4:   else
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
        assertEquals(
            Map.of(
                line1, LatticeElement.initialize(Set.of(line0))).toString(),
            result.get(line0).toString());
        assertEquals(
            Map.of(
                line2, LatticeElement.initialize(Set.of(line1, line0))).toString(),
            result.get(line1).toString());
        assertEquals(
            Map.of(
                line3, LatticeElement.initialize(Set.of(line2, line1, line0)),
                line5, LatticeElement.initialize(Set.of(line2, line1, line0))).toString(),
            result.get(line2).toString());
    }
}
