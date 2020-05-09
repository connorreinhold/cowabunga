package cyr7.cfg.asm;

import cyr7.cfg.asm.dfa.WorklistAnalysis;
import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.cfg.asm.nodes.AsmCFGOpNode;
import cyr7.cfg.asm.nodes.AsmCFGReturnNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cfg.asm.reg.LiveVariableAnalysis;
import cyr7.util.Sets;
import cyr7.x86.ASMConstants;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempRegArg;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLiveVariableAnalysis {

    public static final ASMLineFactory make = new ASMLineFactory();
    public static final ASMArgFactory arg = ASMArgFactory.instance;

    @Test
    void testLectureExample() {
        // 0: start
        // 1: lea b, [ a + 2 ]
        // 2: mov c, b
        // 3: mul c, b
        // 4: lea d, [ c + e ]
        // 5: mov rax, d
        // 6: ret

        AsmCFGNode[] nodes = new AsmCFGNode[7];
        nodes[6] = new AsmCFGReturnNode(-1, make.Ret());
        nodes[5] = new AsmCFGOpNode(-1,
            make.Mov(ASMReg.RAX, arg.temp("d")),
            nodes[6]);
        nodes[4] = new AsmCFGOpNode(-1,
            make.Lea(
                arg.temp("d"),
                arg.mem(arg.temp("c"), arg.temp("e"))),
            nodes[5]);
        nodes[3] = new AsmCFGOpNode(-1,
            make.Mul(arg.temp("c"), arg.temp("b")),
            nodes[4]);
        nodes[2] = new AsmCFGOpNode(-1,
            make.Mov(arg.temp("c"), arg.temp("b")),
            nodes[3]);
        nodes[1] = new AsmCFGOpNode(-1,
            make.Lea(arg.temp("b"), arg.mem(arg.temp("a"), 2)),
            nodes[2]);
        nodes[0] = new AsmCFGStartNode(nodes[1]);

        Map<AsmCFGNode, Set<? extends ASMTempRegArg>> result
            = WorklistAnalysis.analyze((AsmCFGStartNode) nodes[0],
            new LiveVariableAnalysis("_Imain_i"));

        assertEquals(liveVariables("a", "e"), result.get(nodes[0]));
        assertEquals(liveVariables("a", "e"), result.get(nodes[1]));
        assertEquals(liveVariables("b", "e"), result.get(nodes[2]));
        assertEquals(liveVariables("b", "c", "e"), result.get(nodes[3]));
        assertEquals(liveVariables("c", "e"), result.get(nodes[4]));
        assertEquals(liveVariables("d"), result.get(nodes[5]));
        assertEquals(
            Sets.union(Set.of(ASMReg.RAX), Set.of(ASMConstants.CALLEE_SAVED_REGISTERS)),
            result.get(nodes[6]));
    }

    private static Set<ASMTempRegArg> liveVariables(String... tempNames) {
        // union in the callee saved registers
        return Sets.union(
            Arrays.stream(tempNames).map(arg::temp).collect(Collectors.toSet()),
            Set.of(ASMConstants.CALLEE_SAVED_REGISTERS));
    }

}
