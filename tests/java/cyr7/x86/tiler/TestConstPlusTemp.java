package cyr7.x86.tiler;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import org.junit.jupiter.api.Test;

import static cyr7.x86.tiler.ASMTestUtils.assertEqualsTiled;
import static cyr7.x86.tiler.ASMTestUtils.makeIR;

public class TestConstPlusTemp {

    @Test
    void testConstPlusTemp() {
        IRBinOp constTemp = makeIR(make ->
            make.IRBinOp(
                OpType.ADD,
                make.IRConst(50),
                make.IRTemp("bleh")
            ));

        assertEqualsTiled(constTemp, "leaq _t2, [ bleh + 50 ]");
    }

    @Test
    void testTempPlusConst() {
        IRBinOp constTemp = makeIR(make ->
            make.IRBinOp(
                OpType.ADD,
                make.IRTemp("bleh"),
                make.IRConst(50)
            ));

        assertEqualsTiled(constTemp, "leaq _t2, [ bleh + 50 ]");
    }

}
