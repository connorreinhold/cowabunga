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
        make.IRBinOp(
                OpType.SUB,
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
        make.IRBinOp(
                OpType.SUB,
                        make.IRBinOp(OpType.MUL,
                                make.IRTemp("bleh"),
                                make.IRConst(4)),
                        make.IRConst(8))
        );

        assertEqualsTiled(constTempOffset, "leaq _t0, [ 4 * bleh + -8 ]");
    }

//    @Test
    void testOffsetAndConstTimesTemp() {
        IRBinOp constTempOffset = makeIR(make ->
        make.IRBinOp(
                OpType.SUB,
                        make.IRConst(8),
                        make.IRBinOp(OpType.MUL,
                                make.IRConst(4),
                                make.IRTemp("bleh"))
                )
        );

        assertEqualsTiled(constTempOffset, "leaq _t0, [ 4 * bleh + -8 ]");
    }

//    @Test
    void testOffsetAndTempTimesConstant() {
        IRBinOp constTempOffset = makeIR(make ->
        make.IRBinOp(
                OpType.SUB,
                        make.IRConst(8),
                        make.IRBinOp(OpType.MUL,
                                make.IRTemp("bleh"),
                                make.IRConst(4))
                )
        );

        assertEqualsTiled(constTempOffset, "leaq _t0, [ 4 * bleh + -8 ]");
    }

}
