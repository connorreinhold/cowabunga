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

    @Test
    void testOffsetAndTempTimesOver32Constant() {
        IRBinOp constTempOffset = makeIR(make ->
            make.IRBinOp(OpType.MUL,
                make.IRConst(4),
                make.IRBinOp(OpType.SUB,
                    make.IRTemp("bleh"),
                    make.IRConst(1099511627776L)) // 2 ^40
            )
        );
        assertEqualsTiled(constTempOffset,
            "movq _t0, 1099511627776",
            "movq _t1, bleh",
            "subq _t1, _t0",
            "leaq _t2, [ 4 * _t1 ]");
    }

}