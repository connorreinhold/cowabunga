package cyr7.cfg;

import cyr7.C;
import cyr7.cfg.dfa.LiveVariableAnalysis;
import cyr7.cfg.dfa.WorklistAnalysis;
import cyr7.cfg.nodes.CFGNodeFactory;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;
import cyr7.ir.interpret.Configuration;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLiveVariableAnalysis {

    private static final CFGNodeFactory make = new CFGNodeFactory(C.LOC);
    private static final IRNodeFactory makeIR = new IRNodeFactory_c(C.LOC);

    @Test
    void testLectureExample() {
        // 1: int b = a + 2;
        // 2: int c = b * b;
        // 3: int d = c + e;
        // 4: return d;

        CFGReturnNode line4 = make.Return(1);
        CFGVarAssignNode line3 = make.VarAssign(
            Configuration.ABSTRACT_RET_PREFIX + "0",
            makeIR.IRBinOp(OpType.ADD,
                makeIR.IRTemp("c"),
                makeIR.IRTemp("e")),
            line4);
        CFGVarAssignNode line2 = make.VarAssign(
            "c",
            makeIR.IRBinOp(OpType.MUL,
                makeIR.IRTemp("b"),
                makeIR.IRTemp("b")),
            line3);
        CFGVarAssignNode line1 = make.VarAssign(
            "b",
            makeIR.IRBinOp(OpType.ADD,
                makeIR.IRTemp("a"),
                makeIR.IRConst(2)),
            line2);
        CFGStartNode line0 = make.Start(line1);

        var result = WorklistAnalysis.analyze(line0, LiveVariableAnalysis.INSTANCE);
        assertEquals(Map.of(
            line0, Set.of(),
            line1, Set.of("a"),
            line2, Set.of("b"),
            line3, Set.of("c", "e"),
            line4, Set.of("_RET0")),
            result);
    }

}
