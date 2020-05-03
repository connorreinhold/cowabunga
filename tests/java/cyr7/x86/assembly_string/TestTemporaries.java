package cyr7.x86.assembly_string;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cyr7.x86.asm.ASMRegSize;
import org.junit.jupiter.api.Test;

import cyr7.x86.asm.ASMTempArg;

class TestTemporaries {

    @Test
    void testIntelTemporaries() {
        ASMTempArg _t1 = new ASMTempArg("_t1", ASMRegSize.QWORD);
        ASMTempArg _t2 = new ASMTempArg("_t2", ASMRegSize.QWORD);
        ASMTempArg x = new ASMTempArg("x", ASMRegSize.QWORD);
        ASMTempArg y = new ASMTempArg("y", ASMRegSize.QWORD);

        assertEquals("_t1", _t1.getIntelArg());
        assertEquals("_t2", _t2.getIntelArg());
        assertEquals("x", x.getIntelArg());
        assertEquals("y", y.getIntelArg());
    }

}