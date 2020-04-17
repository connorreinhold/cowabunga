package cyr7.x86.tiler;


import org.junit.jupiter.api.Test;
import static cyr7.x86.tiler.ASMTestUtils.makeIR;
import static cyr7.x86.tiler.ASMTestUtils.assertEqualsTiled;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;


public class TestConst_PlusConstTimesTemp_PlusTemp {

    @Test
    void testConstPlus_ConstTimesTemp_PlusTemp() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, make.IRConst(8),
                make.IRBinOp(OpType.ADD,
                        make.IRBinOp(OpType.MUL,
                                make.IRConst(4),
                                make.IRTemp("bleh")),
                                make.IRTemp("left_bleh")))
        );
        assertEqualsTiled(constTempOffset, "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
    }

    @Test
    void testConstPlus_TempTimesConst_PlusTemp() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, make.IRConst(8),
                make.IRBinOp(OpType.ADD,
                        make.IRBinOp(OpType.MUL,
                                make.IRTemp("bleh"),
                                make.IRConst(4)),
                        make.IRTemp("left_bleh")))
        );
        assertEqualsTiled(constTempOffset, "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
    }

    @Test
    void testConstTimesTemp_PlusTemp_PlusConst() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                        make.IRBinOp(OpType.ADD,
                        make.IRBinOp(OpType.MUL,
                                make.IRTemp("bleh"),
                                make.IRConst(4)),
                        make.IRTemp("right_bleh")), 
                        make.IRConst(8)));

        assertEqualsTiled(constTempOffset, "leaq _t0, [ right_bleh + 4 * bleh + 8 ]");
    }


    @Test
    void testTempTimesConst_PlusTemp_PlusConst() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                        make.IRBinOp(OpType.ADD,
                                make.IRBinOp(OpType.MUL,
                                        make.IRConst(4),
                                        make.IRTemp("bleh")),
                                make.IRTemp("right_bleh")), 
                        make.IRConst(8)));
        assertEqualsTiled(constTempOffset, "leaq _t0, [ right_bleh + 4 * bleh + 8 ]");
    }


    @Test
    void testConst_PlusTemp_PlusConstTimesTemp() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                        make.IRConst(8), 
                        make.IRBinOp(OpType.ADD,
                                make.IRTemp("left_bleh"),
                                make.IRBinOp(OpType.MUL,
                                        make.IRConst(4),
                                        make.IRTemp("bleh")))));
        assertEqualsTiled(constTempOffset, "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
    }

    @Test
    void testConst_PlusTemp_PlusTempTimesConst() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                        make.IRConst(8), 
                        make.IRBinOp(OpType.ADD,
                                make.IRTemp("left_bleh") ,
                                make.IRBinOp(OpType.MUL,
                                        make.IRTemp("bleh"),
                                        make.IRConst(4)))));
        assertEqualsTiled(constTempOffset, "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
    }

    @Test
    void testTemp_PlusConstTimesTemp_PlusConst() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                make.IRBinOp(OpType.ADD,
                        make.IRTemp("right_bleh"),
                        make.IRBinOp(OpType.MUL,
                                make.IRTemp("bleh"),
                                make.IRConst(4))), 
                make.IRConst(8)));

        assertEqualsTiled(constTempOffset, "leaq _t0, [ right_bleh + 4 * bleh + 8 ]");
    }


    @Test
    void testTemp_PlusTempTimesConst_PlusConst() {
        IRBinOp constTempOffset = makeIR(make ->
                make.IRBinOp(OpType.ADD, 
                        make.IRBinOp(OpType.ADD,
                        make.IRTemp("right_bleh"),
                                make.IRBinOp(OpType.MUL,
                                        make.IRConst(4),
                                        make.IRTemp("bleh"))), 
                        make.IRConst(8)));
        assertEqualsTiled(constTempOffset, "leaq _t0, [ right_bleh + 4 * bleh + 8 ]");
    }


}