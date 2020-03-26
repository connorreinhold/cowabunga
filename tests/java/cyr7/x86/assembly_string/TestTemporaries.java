package cyr7.x86.assembly_string;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cyr7.x86.asm.ASMTempArg;

class TestTemporaries {

    @Test
    void testIntelTemporaries() {
        ASMTempArg _t1 = new ASMTempArg("_t1");
        ASMTempArg _t2 = new ASMTempArg("_t2");
        ASMTempArg x = new ASMTempArg("x");
        ASMTempArg y = new ASMTempArg("y");

        assertEquals("_t1", _t1.getIntelArg());
        assertEquals("_t2", _t2.getIntelArg());
        assertEquals("x", x.getIntelArg());
        assertEquals("y", y.getIntelArg());
    }

}