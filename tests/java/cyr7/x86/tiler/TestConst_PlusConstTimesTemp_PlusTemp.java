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

    @Test
    void testConstMoreThan32Bits_PlusTemp_PlusConstTimesTemp() {
        IRBinOp constTempOffset = makeIR(make ->
            make.IRBinOp(OpType.ADD,
                make.IRConst(1099511627776L),
                make.IRBinOp(OpType.ADD,
                    make.IRTemp("left_bleh"),
                    make.IRBinOp(OpType.MUL,
                        make.IRConst(4),
                        make.IRTemp("bleh")))));
        assertEqualsTiled(constTempOffset,
            "movq _t0, 1099511627776",
            "leaq _t1, [ left_bleh + 4 * bleh ]",
            "leaq _t2, [ _t0 + 1 * _t1 ]");
    }

    @Test
    void testConst_PlusTemp_PlusConstOver32BitsTimesTemp() {
        IRBinOp constTempOffset = makeIR(make ->
            make.IRBinOp(OpType.ADD,
                make.IRConst(8),
                make.IRBinOp(OpType.ADD,
                    make.IRTemp("left_bleh"),
                    make.IRBinOp(OpType.MUL,
                        make.IRConst(1099511627776L),
                        make.IRTemp("bleh")))));
        assertEqualsTiled(constTempOffset,
            "movq _t0, 1099511627776",
            "movq _t1, _t0",
            "imulq _t1, bleh",
            "leaq _t2, [ left_bleh + 1 * _t1 ]",
            "movq _t3, 8",
            "addq _t3, _t2");
    }

}