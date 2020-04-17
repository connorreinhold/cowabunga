package cyr7.x86.tiler;


import org.junit.jupiter.api.Test;
import static cyr7.x86.tiler.ASMTestUtils.makeIR;
import static cyr7.x86.tiler.ASMTestUtils.assertEqualsTiled;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;


public class TestTemp_PlusConstTimesTemp_PlusOffset {

    @Test
    void testTempPlus_ConstTimesTemp_PlusOffset() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, make.IRTemp("left_bleh"), 
                make.IRBinOp(OpType.ADD,
                        make.IRBinOp(OpType.MUL,
                                make.IRConst(4),
                                make.IRTemp("bleh")),
                        make.IRConst(8)))
        );
        assertEqualsTiled(constTempOffset, "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
    }

    @Test
    void testTempPlus_TempTimesConst_PlusOffset() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, make.IRTemp("left_bleh"), 
                make.IRBinOp(OpType.ADD,
                        make.IRBinOp(OpType.MUL,
                                make.IRTemp("bleh"),
                                make.IRConst(4)),
                        make.IRConst(8)))
        );
        assertEqualsTiled(constTempOffset, "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
    }

    @Test
    void testConstTimesTemp_PlusOffset_PlusTemp() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                        make.IRBinOp(OpType.ADD,
                        make.IRBinOp(OpType.MUL,
                                make.IRTemp("bleh"),
                                make.IRConst(4)),
                        make.IRConst(8)), 
                        make.IRTemp("right_bleh")));

        assertEqualsTiled(constTempOffset, "leaq _t0, [ right_bleh + 4 * bleh + 8 ]");
    }


    /**
     * Runs ((4 * bleh) + 8) + right_bleh
     */
    @Test
    void testTempTimesConst_PlusOffset_PlusTemp() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                        make.IRBinOp(OpType.ADD,
                                make.IRBinOp(OpType.MUL,
                                        make.IRConst(4),
                                        make.IRTemp("bleh")),
                                make.IRConst(8)), 
                        make.IRTemp("right_bleh")));
        assertEqualsTiled(constTempOffset, "leaq _t0, [ right_bleh + 4 * bleh + 8 ]");
    }





    @Test
    void testTempPlus_PlusOffset_PlusConstTimesTemp() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                        make.IRTemp("left_bleh"), 
                        make.IRBinOp(OpType.ADD,
                                make.IRConst(8),
                                make.IRBinOp(OpType.MUL,
                                        make.IRConst(4),
                                        make.IRTemp("bleh")))));
        assertEqualsTiled(constTempOffset, "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
    }

    @Test
    void testTempPlus_PlusOffset_PlusTempTimesConst() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                        make.IRTemp("left_bleh"), 
                        make.IRBinOp(OpType.ADD,
                                make.IRConst(8),
                                make.IRBinOp(OpType.MUL,
                                        make.IRTemp("bleh"),
                                        make.IRConst(4)))));
        assertEqualsTiled(constTempOffset, "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
    }

    @Test
    void testOffset_PlusConstTimesTemp_PlusTemp() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                make.IRBinOp(OpType.ADD,
                        make.IRConst(8),
                        make.IRBinOp(OpType.MUL,
                                make.IRTemp("bleh"),
                                make.IRConst(4))), 
                make.IRTemp("right_bleh")));

        assertEqualsTiled(constTempOffset, "leaq _t0, [ right_bleh + 4 * bleh + 8 ]");
    }


    @Test
    void testOffset_PlusTempTimesConst_PlusTemp() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                        make.IRBinOp(OpType.ADD,
                                make.IRConst(8),
                                make.IRBinOp(OpType.MUL,
                                        make.IRConst(4),
                                        make.IRTemp("bleh"))), 
                        make.IRTemp("right_bleh")));
        assertEqualsTiled(constTempOffset, "leaq _t0, [ right_bleh + 4 * bleh + 8 ]");
    }


}