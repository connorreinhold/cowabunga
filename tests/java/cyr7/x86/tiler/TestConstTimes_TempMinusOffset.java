package cyr7.x86.tiler;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import org.junit.jupiter.api.Test;

import static cyr7.x86.tiler.ASMTestUtils.assertEqualsTiled;
import static cyr7.x86.tiler.ASMTestUtils.makeIR;

public class TestConstTimes_TempMinusOffset {

    @Test
    void testConstTimesOffsetAndTemp() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.MUL,
                        make.IRBinOp(OpType.SUB,
                                make.IRTemp("bleh"),
                                make.IRConst(2)),
                        make.IRConst(8))
        );

        assertEqualsTiled(constTempOffset, "leaq _t0, [ 8 * bleh + -16 ]");
    }

    @Test
    void testOffsetAndTempTimesConstant() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.MUL,
                        make.IRConst(4),
                        make.IRBinOp(OpType.SUB,
                                make.IRTemp("bleh"),
                                make.IRConst(3))
                )
        );

        assertEqualsTiled(constTempOffset, "leaq _t0, [ 4 * bleh + -12 ]");
    }
}