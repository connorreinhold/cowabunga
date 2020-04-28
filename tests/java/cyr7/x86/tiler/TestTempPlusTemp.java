package cyr7.x86.tiler;

import cyr7.ir.nodes.IRBinOp;
import org.junit.jupiter.api.Test;

import static cyr7.x86.tiler.ASMTestUtils.assertEqualsTiled;
import static cyr7.x86.tiler.ASMTestUtils.makeIR;

public class TestTempPlusTemp {

    @Test
    void testTempAndTemp(){
        IRBinOp tempTemp = makeIR(make ->
                make.IRBinOp(IRBinOp.OpType.ADD,
                        make.IRTemp("bleh1"),
                        make.IRTemp("bleh2"))
        );

        assertEqualsTiled(tempTemp, "leaq _t0, [ bleh1 + 1 * bleh2 ]");
    }

}
