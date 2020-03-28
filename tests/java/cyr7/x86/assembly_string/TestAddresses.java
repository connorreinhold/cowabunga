package cyr7.x86.assembly_string;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;

class TestAddresses {

    private void testIntel(String expected, ASMAddrExpr actual) {
        assertEquals(expected, actual.getIntelExpr());
    }

    @Test
    void testIntelTemporaries() {
        ASMAddrExpr rax = new ASMAddrExpr(Optional.of(ASMReg.RAX),
                ScaleValues.ONE, Optional.empty(), 0);
        ASMAddrExpr raxPlusRdi = new ASMAddrExpr(Optional.of(ASMReg.RAX),
                ScaleValues.ONE, Optional.of(ASMReg.RDI), 0);
        ASMAddrExpr rdiTimesFour = new ASMAddrExpr(Optional.empty(),
                ScaleValues.FOUR, Optional.of(ASMReg.RDI), 0);
        ASMAddrExpr raxPlusTen = new ASMAddrExpr(Optional.of(ASMReg.RAX),
                ScaleValues.FOUR, Optional.empty(), 10);
        ASMAddrExpr rdiTimesEightPlusTen = new ASMAddrExpr(Optional.empty(),
                ScaleValues.EIGHT, Optional.of(ASMReg.RDI), 10);
        ASMAddrExpr raxPlusRdiTimesEightPlusTen = new ASMAddrExpr(Optional.of(
                ASMReg.RAX), ScaleValues.EIGHT, Optional.of(ASMReg.RDI), 10);

        testIntel("rax", rax);
        testIntel("rax + 1 * rdi", raxPlusRdi);
        testIntel("4 * rdi", rdiTimesFour);
        testIntel("rax + 10", raxPlusTen);
        testIntel("8 * rdi + 10", rdiTimesEightPlusTen);
        testIntel("rax + 8 * rdi + 10", raxPlusRdiTimesEightPlusTen);

        ASMAddrExpr raxTimesOne = new ASMAddrExpr(Optional.empty(),
                ScaleValues.ONE, Optional.of(ASMReg.RAX), 0);
        ASMAddrExpr raxTimesTwo = new ASMAddrExpr(Optional.empty(),
                ScaleValues.TWO, Optional.of(ASMReg.RAX), 0);
        ASMAddrExpr raxTimesFour = new ASMAddrExpr(Optional.empty(),
                ScaleValues.FOUR, Optional.of(ASMReg.RAX), 0);
        ASMAddrExpr raxTimesEight = new ASMAddrExpr(Optional.empty(),
                ScaleValues.EIGHT, Optional.of(ASMReg.RAX), 0);
        testIntel("1 * rax", raxTimesOne);
        testIntel("2 * rax", raxTimesTwo);
        testIntel("4 * rax", raxTimesFour);
        testIntel("8 * rax", raxTimesEight);
    }

}