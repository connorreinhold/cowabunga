package cyr7.x86.assembly_string;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMReg;

class TestInstructions {

    private void testIntel(String expected, ASMInstr actual) {
        assertEquals(expected, actual.getIntelAssembly());
    }

    @Test
    void testIntelTemporaries() {

        var make = ASMLineFactory.instance;
        var arg = ASMArgFactory.instance;

        ASMInstr movabs = make.Mov(ASMReg.RAX, arg.constant(12));
        testIntel("    movabsq rax, 12", movabs);

        ASMInstr memMove = make.Mov(arg.mem(arg.addr(Optional.empty(),
                ScaleValues.ONE,
                Optional.empty(),
                16)), ASMReg.R10);
        testIntel("    mov [ 16 ], r10", memMove);

        ASMInstr cqo = make.CQO();
        testIntel("    cqo", cqo);

        ASMInstr idiv = make.Div(ASMReg.RAX);
        testIntel("    idivq rax", idiv);

    }

}