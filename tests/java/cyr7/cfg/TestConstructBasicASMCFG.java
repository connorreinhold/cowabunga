package cyr7.cfg;

import cyr7.cfg.constructor.AsmCFGConstructor;
import cyr7.cfg.nodes.asm.AsmCFGReturnNode;
import cyr7.cfg.nodes.asm.AsmCFGStartNode;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestConstructBasicASMCFG {

    @Test
    void testEmptyProgram() {
        ASMLineFactory make = new ASMLineFactory();
        List<ASMLine> lines = List.of(
            make.Ret()
        );
        AsmCFGConstructor constructor = new AsmCFGConstructor(lines);
        AsmCFGStartNode startNode = constructor.constructAsmCFG();

        assertTrue(startNode.out() instanceof AsmCFGReturnNode);
    }

    @Test
    void testJumpProgram() {
        ASMLineFactory make = new ASMLineFactory();
        List<ASMLine> lines = List.of(

        );
    }

}
