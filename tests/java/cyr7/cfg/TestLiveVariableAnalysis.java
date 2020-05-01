package cyr7.cfg;

import cyr7.C;
import cyr7.cfg.dfa.LiveVariableAnalysis;
import cyr7.cfg.dfa.WorklistAnalysis;
import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGNodeFactory;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;
import cyr7.ir.interpret.Configuration;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import org.junit.jupiter.api.Test;

public class TestLiveVariableAnalysis {

    private static final CFGNodeFactory make = new CFGNodeFactory(C.LOC);
    private static final IRNodeFactory makeIR = new IRNodeFactory_c(C.LOC);

    @Test
    void testLectureExample() {
        CFGStartNode node = make.Start(
            make.VarAssign("a", makeIR.IRBinOp(OpType.ADD, makeIR.IRTemp("a"), makeIR.IRConst(2)),
                make.VarAssign("c", makeIR.IRBinOp(OpType.MUL, makeIR.IRTemp("b"), makeIR.IRTemp("b")),
                    make.VarAssign(Configuration.ABSTRACT_RET_PREFIX + "0", makeIR.IRBinOp(OpType.ADD, makeIR.IRTemp("c"), makeIR.IRTemp("e")),
                        make.Return(1))))
        );

        var result = WorklistAnalysis.analyze(node, LiveVariableAnalysis.INSTANCE);
        System.out.println(result);
    }

}
