package cyr7.x86.tiler;

import static cyr7.x86.tiler.ASMTestUtils.assertEqualsTiled;
import static cyr7.x86.tiler.ASMTestUtils.makeIR;

import org.junit.jupiter.api.Test;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;

class TestTempTimesConstMinusOffset {

    @Test
    void testConstTimesTempAndOffset() {
        IRBinOp constTempOffset = makeIR(make ->
        make.IRBinOp(OpType.SUB,
            make.IRBinOp(OpType.MUL,
                    make.IRConst(4),
                    make.IRTemp("bleh")),
            make.IRConst(8))
        );

        assertEqualsTiled(constTempOffset, "leaq _t0, [ 4 * bleh + -8 ]");
    }

    @Test
    void testConstTimesOffsetAndTemp() {
        IRBinOp constTempOffset = makeIR(make ->
        make.IRBinOp(OpType.SUB,
            make.IRBinOp(OpType.MUL,
                    make.IRTemp("bleh"),
                    make.IRConst(4)),
            make.IRConst(8))
        );

        assertEqualsTiled(constTempOffset, "leaq _t0, [ 4 * bleh + -8 ]");
    }
    
    @Test
    void testTempTimesConstMinusOver32Constant() {
        IRBinOp constTempOffset = makeIR(make ->
            make.IRBinOp(OpType.SUB,
                make.IRBinOp(OpType.MUL,
                        make.IRTemp("bleh"),
                        make.IRConst(4)),
                make.IRConst(1099511627776L)) // 2 ^ 40
            );
        assertEqualsTiled(constTempOffset,
            "leaq _t0, [ 4 * bleh ]",
            "movq _t1, 1099511627776",
            "movq _t2, _t0",
            "subq _t2, _t1");
    }

}
