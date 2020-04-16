package cyr7.x86.tiler;

import cyr7.ir.nodes.IRBinOp;
import org.junit.jupiter.api.Test;

import static cyr7.x86.tiler.ASMTestUtils.*;

public class TestConstTimesTempPlusTemp {

    @Test
    void testTempAndConstTimesTemp() {
        IRBinOp tempConstTemp = makeIR(make ->
                make.IRBinOp(IRBinOp.OpType.ADD,
                        make.IRTemp("bleh1"),
                        make.IRBinOp(IRBinOp.OpType.MUL,
                                make.IRConst(4),
                                make.IRTemp("bleh2"))
                )
        );
        ComplexTiler tiler = makeTiler();
        tempConstTemp.accept(tiler);
        assertEqualsTiled(tempConstTemp, "leaq _t0, [ bleh1 + 4 * bleh2 ]");
    }

    @Test
    void testConstTimesTempAndTemp() {
        IRBinOp tempConstTemp = makeIR(make ->
                make.IRBinOp(IRBinOp.OpType.ADD,
                        make.IRBinOp(IRBinOp.OpType.MUL,
                                make.IRConst(4),
                                make.IRTemp("bleh2")),
                        make.IRTemp("bleh1")
                )
        );
        ComplexTiler tiler = makeTiler();
        tempConstTemp.accept(tiler);
        assertEqualsTiled(tempConstTemp, "leaq _t0, [ bleh1 + 4 * bleh2 ]");
    }
}
