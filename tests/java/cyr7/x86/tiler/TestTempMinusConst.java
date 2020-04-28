package cyr7.x86.tiler;

import static cyr7.x86.tiler.ASMTestUtils.assertEqualsTiled;
import static cyr7.x86.tiler.ASMTestUtils.makeIR;

import org.junit.jupiter.api.Test;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;

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
    
    @Test
    void testTempMinusOver32Constant() {
        IRBinOp constTempOffset = makeIR(make ->
            make.IRBinOp(OpType.SUB,
                make.IRTemp("bleh1"),
                make.IRConst(1099511627776L)) // 2 ^ 40
            );
        assertEqualsTiled(constTempOffset,
            "movq _t0, 1099511627776",
            "movq _t1, bleh1",
            "subq _t1, _t0"
        );
    }

}
