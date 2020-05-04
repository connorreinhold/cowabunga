package cyr7.cfg.ir;

import cyr7.cfg.asm.constructor.AsmCFGConstructor;
import cyr7.cfg.asm.nodes.AsmCFGIfNode;
import cyr7.cfg.asm.nodes.AsmCFGOpNode;
import cyr7.cfg.asm.nodes.AsmCFGReturnNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMLabel;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMReg;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestConstructBasicAsmCFG {

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
            make.Mov(ASMReg.RAX, new ASMConstArg(5)),
            make.JumpE(new ASMLabelArg("trueLabel")),
            make.Add(ASMReg.RBX, ASMReg.RCX),
            make.Jump(new ASMLabelArg("doneLabel")),
            new ASMLabel("trueLabel"),
            make.Add(ASMReg.RCX, ASMReg.RBX),
            new ASMLabel("doneLabel"),
            make.Ret()
        );

        AsmCFGConstructor constructor = new AsmCFGConstructor(lines);
        AsmCFGStartNode startNode = constructor.constructAsmCFG();

        assertTrue(startNode.out() instanceof AsmCFGOpNode);

        AsmCFGOpNode movRax5 = (AsmCFGOpNode) startNode.out();
        assertEquals(lines.get(0), movRax5.sourceInstr());

        AsmCFGIfNode jumpETrueLabel = (AsmCFGIfNode) movRax5.out();
        assertEquals(lines.get(1), jumpETrueLabel.sourceInstr());

        AsmCFGOpNode addRbxRcx = (AsmCFGOpNode) jumpETrueLabel.falseBranch();
        assertEquals(lines.get(2), addRbxRcx.sourceInstr());

        AsmCFGOpNode jumpDoneLabel = (AsmCFGOpNode) addRbxRcx.out();
        assertEquals(lines.get(3), jumpDoneLabel.sourceInstr());

        AsmCFGOpNode addRcxRbx = (AsmCFGOpNode) jumpETrueLabel.trueBranch();
        assertEquals(lines.get(5), addRcxRbx.sourceInstr());

        AsmCFGReturnNode retNode = (AsmCFGReturnNode) addRcxRbx.out();
        assertEquals(lines.get(7), retNode.sourceInstr());
    }

}
