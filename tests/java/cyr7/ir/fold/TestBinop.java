package cyr7.ir.fold;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import cyr7.util.OneOfThree;
import cyr7.visitor.MyIRVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;
import polyglot.util.Pair;

class TestBinop {

    /**
     * IRNodeFactory with a default location. Location is more or less
     * irrelevant during testing.
     */
    private final IRNodeFactory make = new IRNodeFactory_c(
            new Location(-1, -1));

    private final MyIRVisitor<OneOfThree<IRExpr, IRStmt, IRFuncDecl>> folder = new ConstFoldVisitor();

    private <T, E> Pair<T, E> pairOf(T expected, E test) {
        return new Pair<T, E>(expected, test);
    }

    private IRExpr constant(long n) {
        return make.IRConst(n);
    }

    private IRExpr constant(boolean n) {
        return make.IRConst(n ? 1 : 0);
    }

    private IRExpr binop(OpType t, long l, long r) {
        return make.IRBinOp(t, constant(l), constant(r));
    }

    private IRExpr binop(OpType t, boolean l, boolean r) {
        return make.IRBinOp(t, constant(l ? 1 : 0), constant(r ? 1 : 0));
    }

    private IRExpr binop(OpType t, IRExpr l, IRExpr r) {
        return make.IRBinOp(t, l, r);
    }

    private void test(Pair<IRExpr, IRExpr> t) {
        var expected = t.part1();
        var actual = t.part2().accept(folder).assertFirst();
        assertEquals(expected, actual);
    }

    @Test
    void testAdd() {
        OpType type = OpType.ADD;
        var t1 = pairOf(constant(2), binop(type, 1, 1));
        var t2 = pairOf(constant(-1),
                binop(type, -9223372036854775808L, 9223372036854775807L));
        var t3 = pairOf(constant(0), binop(type, 0, 0));
        var t4 = pairOf(constant(9223372036854775806L),
                binop(type, 9223372036854775807L, 9223372036854775807L));
        var t5 = pairOf(constant(0L), binop(type,
                -9223372036854775808L, -9223372036854775808L));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
    }

    @Test
    void testSub() {
        OpType type = OpType.SUB;
        var t1 = pairOf(constant(0), binop(type, 0, 0));
        var t2 = pairOf(constant(0), binop(type, 1, 1));
        var t3 = pairOf(constant(-9223372036854775807L),
                binop(type, -9223372036854775808L, 9223372036854775807L));
        var t4 = pairOf(constant(0),
                binop(type, 9223372036854775807L, 9223372036854775807L));
        var t5 = pairOf(constant(0),
                binop(type, -9223372036854775808L,
                -9223372036854775808L));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
    }

    @Test
    void testMulti() {
        // Numbers crunched via an online calculator
        // https://www.calculator.net/big-number-calculator.html
        OpType type = OpType.MUL;
        var t1 = pairOf(constant(0), binop(type, 0, 0));
        var t2 = pairOf(constant(1), binop(type, 1, 1));
        var t3 = pairOf(constant(9223372036854775807L),
                binop(type, 1, 9223372036854775807L));
        var t4 = pairOf(constant(9223372036854775807L),
                binop(type, 9223372036854775807L, 1));
        var t5 = pairOf(constant(0),
                binop(type, -9223372036854775808L, 9223372036854775807L));
        var t6 = pairOf(constant(1),
                binop(type, 9223372036854775807L, 9223372036854775807L));
        var t7 = pairOf(constant(0),
                binop(type, -9223372036854775808L, -9223372036854775808L));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
    }

    @Test
    void testDiv() {
        OpType type = OpType.DIV;
        var t1 = pairOf(binop(type, 0, 0), binop(type, 0, 0));
        var t2 = pairOf(constant(1), binop(type, 1, 1));

        var t3 = pairOf(constant(0),
                binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(constant(9223372036854775807L),
                binop(type, 9223372036854775807L, 1));

        var t5 = pairOf(constant(-1),
                binop(type, -9223372036854775808L, 9223372036854775807L));

        var t6 = pairOf(constant(1),
                binop(type, 9223372036854775807L, 9223372036854775807L));

        var t7 = pairOf(constant(1),
                binop(type, -9223372036854775808L, -9223372036854775808L));

        var t8 = pairOf(constant(0), binop(type, 1, -9223372036854775808L));

        var t9 = pairOf(constant(2), binop(type, 21, 9));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

    @Test
    void testMod() {
        // When mod,
        OpType type = OpType.MOD;
        var t1 = pairOf(binop(type, 0, 0), binop(type, 0, 0));
        var t2 = pairOf(constant(0), binop(type, 1, 1));

        var t3 = pairOf(constant(1), binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(constant(0),
                binop(type, 9223372036854775807L, 1));

        var t5 = pairOf(constant(-1),
                binop(type, -9223372036854775808L, 9223372036854775807L));

        var t6 = pairOf(constant(0),
                binop(type, 9223372036854775807L, 9223372036854775807L));

        var t7 = pairOf(constant(0),
                binop(type, -9223372036854775808L, -9223372036854775808L));

        var t8 = pairOf(constant(1), binop(type, 1, -9223372036854775808L));

        var t9 = pairOf(constant(3), binop(type, 21, 9));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

    @Test
    void testHighMulti() {
        OpType type = OpType.HMUL;
        var t1 = pairOf(constant(0), binop(type, 0, 0));
        var t2 = pairOf(constant(0), binop(type, 1, 1));

        var t3 = pairOf(constant(0), binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(constant(0), binop(type, 9223372036854775807L, 1));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
    }

    @Test
    void testAnd() {
        OpType type = OpType.AND;
        var t1 = pairOf(constant(true), binop(type, true, true));
        var t2 = pairOf(constant(false), binop(type, false, false));
        var t3 = pairOf(constant(false), binop(type, false, true));
        var t4 = pairOf(constant(false), binop(type, true, false));
        var t5 = pairOf(constant(false),
                binop(type, binop(type, true, true), constant(false)));

        var t6 = pairOf(constant(false),
                binop(type, binop(type, true, false), constant(true)));

        var t7 = pairOf(constant(false),
                binop(type, binop(type, true, false),
                        binop(type, binop(type, true, true), constant(true))));

        var t8 = pairOf(constant(false), binop(type, binop(type, true, true),
                binop(type, binop(type, true, true), constant(false))));

        var t9 = pairOf(constant(true), binop(type, binop(type, true, true),
                binop(type, binop(type, true, true), constant(true))));


        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

    @Test
    void testOr() {
        OpType type = OpType.OR;
        var t1 = pairOf(constant(true), binop(type, true, true));
        var t2 = pairOf(constant(false), binop(type, false, false));
        var t3 = pairOf(constant(true), binop(type, false, true));
        var t4 = pairOf(constant(true), binop(type, true, false));
        var t5 = pairOf(constant(true),
                binop(type, binop(type, true, true), constant(false)));

        var t6 = pairOf(constant(true),
                binop(type, binop(type, true, false), constant(true)));

        var t7 = pairOf(constant(true), binop(type, binop(type, true, false),
                binop(type, binop(type, true, true), constant(true))));

        var t8 = pairOf(constant(true), binop(type, binop(type, true, true),
                binop(type, binop(type, true, true), constant(false))));

        var t9 = pairOf(constant(false), binop(type, binop(type, false, false),
                binop(type, binop(type, false, false), constant(false))));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

    @Test
    void testXor() {
        OpType type = OpType.XOR;
        var t1 = pairOf(constant(false), binop(type, true, true));
        var t2 = pairOf(constant(false), binop(type, false, false));
        var t3 = pairOf(constant(true), binop(type, false, true));
        var t4 = pairOf(constant(true), binop(type, true, false));
        var t5 = pairOf(constant(false),
                binop(type, binop(type, true, true), constant(false)));

        var t6 = pairOf(constant(false),
                binop(type, binop(type, true, false), constant(true)));

        var t7 = pairOf(constant(false), binop(type, binop(type, true, false),
                binop(type, binop(type, true, true), constant(true))));

        var t8 = pairOf(constant(false), binop(type, binop(type, true, true),
                binop(type, binop(type, true, true), constant(false))));

        var t9 = pairOf(constant(false), binop(type, binop(type, false, false),
                binop(type, binop(type, false, false), constant(false))));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

    @Test
    void testLEQ() {
        OpType type = OpType.LEQ;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        var t1 = pairOf(TRUE, binop(type, 0, 0));
        var t2 = pairOf(TRUE, binop(type, 1, 1));

        var t3 = pairOf(TRUE, binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(FALSE, binop(type, 9223372036854775807L, 1));

        var t5 = pairOf(TRUE,
                binop(type, -9223372036854775808L, 9223372036854775807L));

        var t6 = pairOf(TRUE,
                binop(type, 9223372036854775807L, 9223372036854775807L));

        var t7 = pairOf(TRUE,
                binop(type, -9223372036854775808L, -9223372036854775808L));

        var t8 = pairOf(FALSE, binop(type, 1, -9223372036854775808L));

        var t9 = pairOf(FALSE, binop(type, 21, 9));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

    @Test
    void testGEQ() {
        OpType type = OpType.GEQ;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        var t1 = pairOf(TRUE, binop(type, 0, 0));
        var t2 = pairOf(TRUE, binop(type, 1, 1));

        var t3 = pairOf(FALSE, binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(TRUE, binop(type, 9223372036854775807L, 1));

        var t5 = pairOf(FALSE,
                binop(type, -9223372036854775808L, 9223372036854775807L));

        var t6 = pairOf(TRUE,
                binop(type, 9223372036854775807L, 9223372036854775807L));

        var t7 = pairOf(TRUE,
                binop(type, -9223372036854775808L, -9223372036854775808L));

        var t8 = pairOf(TRUE, binop(type, 1, -9223372036854775808L));

        var t9 = pairOf(TRUE, binop(type, 21, 9));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

    @Test
    void testLT() {
        OpType type = OpType.LT;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        var t1 = pairOf(FALSE, binop(type, 0, 0));
        var t2 = pairOf(FALSE, binop(type, 1, 1));

        var t3 = pairOf(TRUE, binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(FALSE, binop(type, 9223372036854775807L, 1));

        var t5 = pairOf(TRUE,
                binop(type, -9223372036854775808L, 9223372036854775807L));

        var t6 = pairOf(FALSE,
                binop(type, 9223372036854775807L, 9223372036854775807L));

        var t7 = pairOf(FALSE,
                binop(type, -9223372036854775808L, -9223372036854775808L));

        var t8 = pairOf(FALSE, binop(type, 1, -9223372036854775808L));

        var t9 = pairOf(FALSE, binop(type, 21, 9));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

    @Test
    void testGT() {
        OpType type = OpType.GT;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        var t1 = pairOf(FALSE, binop(type, 0, 0));
        var t2 = pairOf(FALSE, binop(type, 1, 1));

        var t3 = pairOf(FALSE, binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(TRUE, binop(type, 9223372036854775807L, 1));

        var t5 = pairOf(FALSE,
                binop(type, -9223372036854775808L, 9223372036854775807L));

        var t6 = pairOf(FALSE,
                binop(type, 9223372036854775807L, 9223372036854775807L));

        var t7 = pairOf(FALSE,
                binop(type, -9223372036854775808L, -9223372036854775808L));

        var t8 = pairOf(TRUE, binop(type, 1, -9223372036854775808L));

        var t9 = pairOf(TRUE, binop(type, 21, 9));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

    @Test
    void testEq() {
        OpType type = OpType.EQ;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        var t1 = pairOf(TRUE, binop(type, 0, 0));
        var t2 = pairOf(TRUE, binop(type, 1, 1));

        var t3 = pairOf(FALSE, binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(FALSE, binop(type, 9223372036854775807L, 1));

        var t5 = pairOf(FALSE,
                binop(type, -9223372036854775808L, 9223372036854775807L));

        var t6 = pairOf(TRUE,
                binop(type, 9223372036854775807L, 9223372036854775807L));

        var t7 = pairOf(TRUE,
                binop(type, -9223372036854775808L, -9223372036854775808L));

        var t8 = pairOf(FALSE, binop(type, 1, -9223372036854775808L));

        var t9 = pairOf(FALSE, binop(type, 21, 9));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

    @Test
    void testNeq() {
        OpType type = OpType.NEQ;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        var t1 = pairOf(FALSE, binop(type, 0, 0));
        var t2 = pairOf(FALSE, binop(type, 1, 1));

        var t3 = pairOf(TRUE, binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(TRUE, binop(type, 9223372036854775807L, 1));

        var t5 = pairOf(TRUE,
                binop(type, -9223372036854775808L, 9223372036854775807L));

        var t6 = pairOf(FALSE,
                binop(type, 9223372036854775807L, 9223372036854775807L));

        var t7 = pairOf(FALSE,
                binop(type, -9223372036854775808L, -9223372036854775808L));

        var t8 = pairOf(TRUE, binop(type, 1, -9223372036854775808L));

        var t9 = pairOf(TRUE, binop(type, 21, 9));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

    @Test
    void testARightShift() {
        OpType type = OpType.ARSHIFT;
        var t1 = pairOf(constant(0), binop(type, 0, 0));
        var t2 = pairOf(constant(0), binop(type, 1, 1));

        var t3 = pairOf(constant(0), binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(constant(4611686018427387903L),
                binop(type, 9223372036854775807L, 1));

        var t5 = pairOf(constant(-1),
                binop(type, -9223372036854775808L, 9223372036854775807L));

        var t6 = pairOf(constant(0),
                binop(type, 9223372036854775807L, 9223372036854775807L));

        var t7 = pairOf(constant(0),
                binop(type, -9223372036854775808L, -9223372036854775808L));

        // Equivalent to 1 >> 0 = 1
        // Because -9223372036854775808L mod 2^64 = 0
        var t8 = pairOf(constant(1), binop(type, 1, -9223372036854775808L));

        var t9 = pairOf(constant(0), binop(type, 21, 9));

        var t10 = pairOf(constant(-4611686018427387904L),
                binop(type, -9223372036854775808L, 1));

        var t11 = pairOf(constant(-1), binop(type, -1, 1));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
        test(t10);
        test(t11);

    }

    @Test
    void testLRightShift() {
        OpType type = OpType.RSHIFT;
        var t1 = pairOf(constant(0), binop(type, 0, 0));
        var t2 = pairOf(constant(0), binop(type, 1, 1));

        var t3 = pairOf(constant(0), binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(constant(4611686018427387903L),
                binop(type, 9223372036854775807L, 1));

        // Just shifts by 0, because of Java specifications.
        var t5 = pairOf(constant(1),
                binop(type, -9223372036854775808L, 9223372036854775807L));

        var t6 = pairOf(constant(0),
                binop(type, 9223372036854775807L, 9223372036854775807L));

        var t7 = pairOf(constant(0),
                binop(type, -9223372036854775808L, -9223372036854775808L));

        // Equivalent to 1 >> 0 = 1
        // Because -9223372036854775808L mod 2^64 = 0
        var t8 = pairOf(constant(1), binop(type, 1, -9223372036854775808L));

        var t9 = pairOf(constant(0), binop(type, 21, 9));

        var t10 = pairOf(constant(4611686018427387904L),
                binop(type, -9223372036854775808L, 1));

        var t11 = pairOf(constant(9223372036854775807L), binop(type, -1, 1));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
        test(t10);
        test(t11);

    }

    @Test
    void testLeftShift() {
        OpType type = OpType.LSHIFT;
        var t1 = pairOf(constant(0), binop(type, 0, 0));
        var t2 = pairOf(constant(2), binop(type, 1, 1));

        var t3 = pairOf(constant(0),
                binop(type, 1, 9223372036854775807L));

        var t4 = pairOf(constant(-2), binop(type, 9223372036854775807L, 1));

        var t5 = pairOf(constant(0),
                binop(type, -9223372036854775808L, 9223372036854775807L));

        var t6 = pairOf(constant(0),
                binop(type, 9223372036854775807L, 9223372036854775807L));

        var t7 = pairOf(constant(0),
                binop(type, -9223372036854775808L, -9223372036854775808L));

        var t8 = pairOf(constant(1), binop(type, 1, -9223372036854775808L));

        var t9 = pairOf(constant(10752), binop(type, 21, 9));

        test(t1);
        test(t2);
        test(t3);
        test(t4);
        test(t5);
        test(t6);
        test(t7);
        test(t8);
        test(t9);
    }

}
