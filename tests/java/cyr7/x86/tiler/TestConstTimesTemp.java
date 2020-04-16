package cyr7.x86.tiler;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import org.junit.jupiter.api.Test;

import static cyr7.x86.tiler.ASMTestUtils.assertEqualsTiled;
import static cyr7.x86.tiler.ASMTestUtils.makeIR;

public class TestConstTimesTemp {

    @Test
    void testConstAndTemp() {
        IRBinOp constTemp = makeIR(make ->
            make.IRBinOp(OpType.MUL,
                make.IRConst(4),
                make.IRTemp("bleh"))
        );

        assertEqualsTiled(constTemp, "leaq _t0, [ 4 * bleh ]");
    }

    @Test
    void testTempAndConst() {
        IRBinOp tempConst = makeIR(make ->
            make.IRBinOp(OpType.MUL,
                make.IRTemp("bleh"),
                make.IRConst(4))
        );

        assertEqualsTiled(tempConst, "leaq _t1, [ 4 * bleh ]");
    }

}
