package cyr7.x86.assembly_string;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempArg.Size;

class TestTemporaries {

    @Test
    void testIntelTemporaries() {
        ASMTempArg _t1 = new ASMTempArg("_t1", Size.QWORD);
        ASMTempArg _t2 = new ASMTempArg("_t2", Size.QWORD);
        ASMTempArg x = new ASMTempArg("x", Size.QWORD);
        ASMTempArg y = new ASMTempArg("y", Size.QWORD);

        assertEquals("_t1", _t1.getIntelArg());
        assertEquals("_t2", _t2.getIntelArg());
        assertEquals("x", x.getIntelArg());
        assertEquals("y", y.getIntelArg());
    }

}