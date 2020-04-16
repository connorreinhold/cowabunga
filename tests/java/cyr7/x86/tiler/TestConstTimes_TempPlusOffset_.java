package cyr7.x86.tiler;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import org.junit.jupiter.api.Test;

import static cyr7.x86.tiler.ASMTestUtils.assertEqualsTiled;
import static cyr7.x86.tiler.ASMTestUtils.makeIR;

public class TestConstTimes_TempPlusOffset_ {

    @Test
    void testConstTimesTempAndOffset() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.MUL,
                        make.IRBinOp(OpType.ADD,
                                make.IRConst(7),
                                make.IRTemp("bleh")),
                        make.IRConst(4))
        );

        assertEqualsTiled(constTempOffset, "leaq _t6, [ 4 * bleh + 28 ]");
    }

    @Test
    void testConstTimesOffsetAndTemp() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.MUL,
                        make.IRBinOp(OpType.ADD,
                                make.IRTemp("bleh"),
                                make.IRConst(2)),
                        make.IRConst(8))
        );

        assertEqualsTiled(constTempOffset, "leaq _t6, [ 8 * bleh + 16 ]");
    }

    @Test
    void testOffsetAndConstTimesTemp() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.MUL,
                        make.IRConst(1),
                        make.IRBinOp(OpType.ADD,
                                make.IRConst(3),
                                make.IRTemp("bleh"))
                )
        );

        assertEqualsTiled(constTempOffset, "leaq _t5, [ 1 * bleh + 3 ]");
    }

    @Test
    void testOffsetAndTempTimesConstant() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.MUL,
                        make.IRConst(4),
                        make.IRBinOp(OpType.ADD,
                                make.IRTemp("bleh"),
                                make.IRConst(3))
                )
        );

        assertEqualsTiled(constTempOffset, "leaq _t5, [ 4 * bleh + 12 ]");
    }
}