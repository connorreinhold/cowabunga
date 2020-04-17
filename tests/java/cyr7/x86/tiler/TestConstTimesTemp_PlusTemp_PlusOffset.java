package cyr7.x86.tiler;

import org.junit.jupiter.api.Test;
import static cyr7.x86.tiler.ASMTestUtils.makeIR;
import static cyr7.x86.tiler.ASMTestUtils.assertEqualsTiled;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;

public class TestConstTimesTemp_PlusTemp_PlusOffset {

        /**
         * (c * t1) + (t2 + n)
         */
        @Test
        void testConstTimesTemp_PlusTemp_PlusOffset() {
                IRBinOp constTempOffset = makeIR(make -> make.IRBinOp(
                                OpType.ADD,
                                make.IRBinOp(OpType.MUL,
                                                make.IRConst(4),
                                                make.IRTemp("bleh")),
                                make.IRBinOp(OpType.ADD,
                                                make.IRTemp("left_bleh"),
                                                make.IRConst(8))));
                assertEqualsTiled(constTempOffset,
                                "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
        }

        /**
         * (t1 * c) + (t2 + n)
         */
        @Test
        void testTempTimesConst_PlusTemp_PlusOffset() {
                IRBinOp constTempOffset = makeIR(make -> make.IRBinOp(
                                OpType.ADD,
                                make.IRBinOp(OpType.MUL,
                                                make.IRTemp("bleh"),
                                                make.IRConst(4)),
                                make.IRBinOp(OpType.ADD,
                                                make.IRTemp("left_bleh"),
                                                make.IRConst(8))));
                assertEqualsTiled(constTempOffset,
                                "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
        }

        /**
         * (c * t1) + (n + t2)
         */
        @Test
        void testConstTimesTemp_PlusOffset_PlusTemp() {
                IRBinOp constTempOffset = makeIR(make -> make.IRBinOp(
                                OpType.ADD,
                                make.IRBinOp(OpType.MUL,
                                                make.IRConst(4),
                                                make.IRTemp("bleh")),
                                make.IRBinOp(OpType.ADD,
                                                make.IRConst(8),
                                                make.IRTemp("left_bleh"))));
                assertEqualsTiled(constTempOffset,
                                "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
        }

        /**
         * (t1 * c) + (n + t2)
         */
        @Test
        void testTempTimesConst_PlusOffset_PlusTemp() {
                IRBinOp constTempOffset = makeIR(make -> make.IRBinOp(
                                OpType.ADD,
                                make.IRBinOp(OpType.MUL,
                                                make.IRTemp("bleh"),
                                                make.IRConst(4)),
                                make.IRBinOp(OpType.ADD,
                                                make.IRConst(8),
                                                make.IRTemp("left_bleh"))));
                assertEqualsTiled(constTempOffset,
                                "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
        }

        /**
         * (t2 + n) + (c * t1)
         */
        @Test
        void testTemp_PlusOffset_PlusConstTimesTemp() {
                IRBinOp constTempOffset = makeIR(make -> make.IRBinOp(
                                OpType.ADD,
                                make.IRBinOp(OpType.ADD,
                                                make.IRTemp("left_bleh"),
                                                make.IRConst(8)),
                                make.IRBinOp(OpType.MUL,
                                                make.IRConst(4),
                                                make.IRTemp("bleh"))));
                assertEqualsTiled(constTempOffset,
                                "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
        }

        /**
         * (t2 + n) + (t1 * c)
         */
        @Test
        void testTemp_PlusOffset_PlusTempTimesConst() {
                IRBinOp constTempOffset = makeIR(make -> make.IRBinOp(
                                OpType.ADD,
                                make.IRBinOp(OpType.ADD,
                                                make.IRTemp("left_bleh"),
                                                make.IRConst(8)),
                                make.IRBinOp(OpType.MUL,
                                                make.IRTemp("bleh"),
                                                make.IRConst(4))));
                assertEqualsTiled(constTempOffset,
                                "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
        }

        /**
         * (n + t2) + (c * t1)
         */
        @Test
        void testOffset_PlusTemp_PlusConstTimesTemp() {
                IRBinOp constTempOffset = makeIR(make -> make.IRBinOp(
                                OpType.ADD,
                                make.IRBinOp(OpType.ADD,
                                                make.IRConst(8),
                                                make.IRTemp("left_bleh")),
                                make.IRBinOp(OpType.MUL,
                                                make.IRConst(4),
                                                make.IRTemp("bleh"))));
                assertEqualsTiled(constTempOffset,
                                "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
        }

        /**
         * (n + t2) + (t1 * c)
         */
        @Test
        void testOffset_PlusTemp_PlusTempTimesConst() {
                IRBinOp constTempOffset = makeIR(make -> make.IRBinOp(
                                OpType.ADD,
                                make.IRBinOp(OpType.ADD,
                                                make.IRConst(8),
                                                make.IRTemp("left_bleh")),
                                make.IRBinOp(OpType.MUL,
                                                make.IRTemp("bleh"),
                                                make.IRConst(4))));
                assertEqualsTiled(constTempOffset,
                                "leaq _t0, [ left_bleh + 4 * bleh + 8 ]");
        }

}