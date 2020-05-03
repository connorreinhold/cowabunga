package cyr7.cfg;

import cyr7.C;
import cyr7.cfg.dfa.CCPAnalysis;
import cyr7.cfg.dfa.CCPAnalysis.LatticeElement;
import cyr7.cfg.dfa.CCPAnalysis.VLatticeElement;
import cyr7.cfg.dfa.WorklistAnalysis;
import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGNodeFactory;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCCPAnalysis {

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

        var result = WorklistAnalysis.analyze(line0, CCPAnalysis.INSTANCE);
        assertEquals(
            Map.of(
                line1, LatticeElement.reachable(Map.of())),
            result.get(line0));
        assertEquals(
            Map.of(
                line2, LatticeElement.reachable(Map.of(
                    "x", VLatticeElement.value(1)))),
            result.get(line1));
        assertEquals(
            Map.of(
                line3, LatticeElement.reachable(Map.of(
                    "x", VLatticeElement.value(1))),
                line5, LatticeElement.unreachable),
            result.get(line2));
        assertEquals(
            Map.of(
                line6, LatticeElement.reachable(Map.of(
                    "x", VLatticeElement.value(1),
                    "y", VLatticeElement.value(6)))),
            result.get(line3)
        );
        assertEquals(
            Map.of(
                line6, LatticeElement.unreachable),
            result.get(line5));
        assertEquals(
            Map.of(
                line7, LatticeElement.reachable(Map.of(
                    "x", VLatticeElement.value(1),
                    "y", VLatticeElement.value(6),
                    "z", VLatticeElement.value(36)))),
            result.get(line6));
        assertEquals(
            Map.of(),
            result.get(line7));
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
    void testSameValueDifferentPaths() {
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

        var result = WorklistAnalysis.analyze(line0, CCPAnalysis.INSTANCE);
        System.out.println(result);
        assertEquals(
            Map.of(
                line1, LatticeElement.reachable(Map.of())),
            result.get(line0));
        assertEquals(
            Map.of(
                line1Point1, LatticeElement.reachable(Map.of(
                    "x", VLatticeElement.value(12)))),
            result.get(line1));
        assertEquals(
            Map.of(
                line1Point2, LatticeElement.reachable(Map.of(
                    "x", VLatticeElement.value(12),
                    "y", VLatticeElement.value(-12)))),
            result.get(line1Point1));
        assertEquals(
            Map.of(
                line2, LatticeElement.reachable(Map.of(
                    "x", VLatticeElement.value(12),
                    "y", VLatticeElement.value(-12),
                    "a", VLatticeElement.bot))),
            result.get(line1Point2));
        assertEquals(
            Map.of(
                line3, LatticeElement.reachable(Map.of(
                    "x", VLatticeElement.value(12),
                    "y", VLatticeElement.value(-12),
                    "a", VLatticeElement.bot)),
                line5, LatticeElement.reachable(Map.of(
                    "x", VLatticeElement.value(12),
                    "y", VLatticeElement.value(-12),
                    "a", VLatticeElement.bot))),
            result.get(line2));
        assertEquals(
            Map.of(
                line4, LatticeElement.reachable(Map.of(
                    "x", VLatticeElement.value(12),
                    "y", VLatticeElement.value(-12),
                    "a", VLatticeElement.bot,
                    "z", VLatticeElement.value(12)))),
            result.get(line3));
        assertEquals(
            Map.of(line6, LatticeElement.reachable(Map.of(
                "x", VLatticeElement.value(12),
                "y", VLatticeElement.value(0),
                "a", VLatticeElement.bot,
                "z", VLatticeElement.value(12)))),
            result.get(line4));
        assertEquals(
            Map.of(line6, LatticeElement.reachable(Map.of(
                "x", VLatticeElement.value(12),
                "y", VLatticeElement.value(0),
                "a", VLatticeElement.bot))),
            result.get(line5));
        assertEquals(
            Map.of(),
            result.get(line6));
        System.out.println(result);
    }

}
