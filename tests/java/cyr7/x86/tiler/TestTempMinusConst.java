package cyr7.x86.tiler;

import static cyr7.x86.tiler.ASMTestUtils.assertEqualsTiled;
import static cyr7.x86.tiler.ASMTestUtils.makeIR;

import org.junit.jupiter.api.Test;

import cyr7.ir.nodes.IRBinOp;

class TestTempMinusConst {

    @Test
    void test() {
        IRBinOp tempTemp = makeIR(make ->
        make.IRBinOp(
                IRBinOp.OpType.SUB,
                make.IRTemp("bleh1"),
                make.IRConst(16)));

        assertEqualsTiled(tempTemp, "leaq _t0, [ bleh1 + -16 ]");
    }

}
