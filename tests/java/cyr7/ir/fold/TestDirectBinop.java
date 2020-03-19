package cyr7.ir.fold;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.fold.basic.DirectConstFoldVisitor;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import cyr7.util.OneOfThree;
import cyr7.visitor.MyIRVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;
import polyglot.util.Pair;

class TestDirectBinop {

    /**
     * IRNodeFactory with a default location. Location is more or less
     * irrelevant during testing.
     */
    private final IRNodeFactory make = new IRNodeFactory_c(
            new Location(-1, -1));

    private final MyIRVisitor<OneOfThree<IRExpr, IRStmt, IRFuncDecl>> folder = new DirectConstFoldVisitor();

    private <T, E> Pair<T, E> pairOf(T expected, E test) {
        return new Pair<>(expected, test);
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
        test(pairOf(constant(2), binop(type, 1, 1)));
        test(pairOf(constant(-1),
                binop(type, -9223372036854775808L, 9223372036854775807L)));
        test(pairOf(constant(0), binop(type, 0, 0)));
        test(pairOf(constant(-2),
                binop(type, 9223372036854775807L, 9223372036854775807L)));
        test(pairOf(constant(0L),
                binop(type, -9223372036854775808L, -9223372036854775808L)));


    }

    @Test
    void testSub() {
        OpType type = OpType.SUB;
        test(pairOf(constant(0), binop(type, 0, 0)));
        test(pairOf(constant(0), binop(type, 1, 1)));
        test(pairOf(constant(1),
                binop(type, -9223372036854775808L, 9223372036854775807L)));
        test(pairOf(constant(0),
                binop(type, 9223372036854775807L, 9223372036854775807L)));
        test(pairOf(constant(0),
                binop(type, -9223372036854775808L,
                        -9223372036854775808L)));
    }

    @Test
    void testMulti() {
        // Numbers crunched via an online calculator
        // https://www.calculator.net/big-number-calculator.html
        OpType type = OpType.MUL;
        test(pairOf(constant(0), binop(type, 0, 0)));
        test(pairOf(constant(1), binop(type, 1, 1)));

        test(pairOf(constant(9223372036854775807L),
                binop(type, 1, 9223372036854775807L)));
        test(pairOf(constant(9223372036854775807L),
                binop(type, 9223372036854775807L, 1)));

        test(pairOf(constant(-9223372036854775808L),
                binop(type, -9223372036854775808L, 9223372036854775807L)));
        test(pairOf(constant(1),
                binop(type, 9223372036854775807L, 9223372036854775807L)));
        test(pairOf(constant(0),
                binop(type, -9223372036854775808L, -9223372036854775808L)));


    }

    @Test
    void testDiv() {
        OpType type = OpType.DIV;
        test(pairOf(binop(type, 0, 0), binop(type, 0, 0)));
        test(pairOf(constant(1), binop(type, 1, 1)));

        test(pairOf(constant(0), binop(type, 1, 9223372036854775807L)));

        test(pairOf(constant(9223372036854775807L),
                binop(type, 9223372036854775807L, 1)));

        test(pairOf(constant(-1),
                binop(type, -9223372036854775808L, 9223372036854775807L)));

        test(pairOf(constant(1),
                binop(type, 9223372036854775807L, 9223372036854775807L)));

        test(pairOf(constant(1),
                binop(type, -9223372036854775808L, -9223372036854775808L)));

        test(pairOf(constant(0), binop(type, 1, -9223372036854775808L)));

        test(pairOf(constant(2), binop(type, 21, 9)));


    }

    @Test
    void testMod() {
        // When mod,
        OpType type = OpType.MOD;
        test(pairOf(binop(type, 0, 0), binop(type, 0, 0)));
        test(pairOf(constant(0), binop(type, 1, 1)));

        test(pairOf(constant(1), binop(type, 1, 9223372036854775807L)));

        test(pairOf(constant(0), binop(type, 9223372036854775807L, 1)));

        test(pairOf(constant(-1),
                binop(type, -9223372036854775808L, 9223372036854775807L)));

        test(pairOf(constant(0),
                binop(type, 9223372036854775807L, 9223372036854775807L)));

        test(pairOf(constant(0),
                binop(type, -9223372036854775808L, -9223372036854775808L)));

        test(pairOf(constant(1), binop(type, 1, -9223372036854775808L)));

        test(pairOf(constant(3), binop(type, 21, 9)));


    }

    @Test
    void testHighMulti() {
        OpType type = OpType.HMUL;
        test(pairOf(constant(0), binop(type, 0, 0)));
        test(pairOf(constant(0), binop(type, 1, 1)));

        test(pairOf(constant(0), binop(type, 1, 9223372036854775807L)));

        test(pairOf(constant(0), binop(type, 9223372036854775807L, 1)));


    }

    @Test
    void testAnd() {
        OpType type = OpType.AND;
        test(pairOf(constant(true), binop(type, true, true)));
        test(pairOf(constant(false), binop(type, false, false)));
        test(pairOf(constant(false), binop(type, false, true)));
        test(pairOf(constant(false), binop(type, true, false)));
        test(pairOf(constant(false),
                binop(type, binop(type, true, true), constant(false))));

        test(pairOf(constant(false),
                binop(type, binop(type, true, false), constant(true))));

        test(pairOf(constant(false),
                binop(type, binop(type, true, false),
                        binop(type, binop(type, true, true), constant(true)))));

        test(pairOf(constant(false), binop(type, binop(type, true, true),
                binop(type, binop(type, true, true), constant(false)))));

        test(pairOf(constant(true), binop(type, binop(type, true, true),
                binop(type, binop(type, true, true), constant(true)))));



    }

    @Test
    void testOr() {
        OpType type = OpType.OR;
        test(pairOf(constant(true), binop(type, true, true)));
        test(pairOf(constant(false), binop(type, false, false)));
        test(pairOf(constant(true), binop(type, false, true)));
        test(pairOf(constant(true), binop(type, true, false)));
        test(pairOf(constant(true),
                binop(type, binop(type, true, true), constant(false))));

        test(pairOf(constant(true),
                binop(type, binop(type, true, false), constant(true))));

        test(pairOf(constant(true), binop(type, binop(type, true, false),
                binop(type, binop(type, true, true), constant(true)))));

        test(pairOf(constant(true), binop(type, binop(type, true, true),
                binop(type, binop(type, true, true), constant(false)))));

        test(pairOf(constant(false), binop(type, binop(type, false, false),
                binop(type, binop(type, false, false), constant(false)))));


    }

    @Test
    void testXor() {
        OpType type = OpType.XOR;
        test(pairOf(constant(false), binop(type, true, true)));
        test(pairOf(constant(false), binop(type, false, false)));
        test(pairOf(constant(true), binop(type, false, true)));
        test(pairOf(constant(true), binop(type, true, false)));
        test(pairOf(constant(false),
                binop(type, binop(type, true, true), constant(false))));

        test(pairOf(constant(false),
                binop(type, binop(type, true, false), constant(true))));

        test(pairOf(constant(false), binop(type, binop(type, true, false),
                binop(type, binop(type, true, true), constant(true)))));

        test(pairOf(constant(false), binop(type, binop(type, true, true),
                binop(type, binop(type, true, true), constant(false)))));

        test(pairOf(constant(false), binop(type, binop(type, false, false),
                binop(type, binop(type, false, false), constant(false)))));


    }

    @Test
    void testLEQ() {
        OpType type = OpType.LEQ;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        test(pairOf(TRUE, binop(type, 0, 0)));
        test(pairOf(TRUE, binop(type, 1, 1)));

        test(pairOf(TRUE, binop(type, 1, 9223372036854775807L)));

        test(pairOf(FALSE, binop(type, 9223372036854775807L, 1)));

        test(pairOf(TRUE,
                binop(type, -9223372036854775808L, 9223372036854775807L)));

        test(pairOf(TRUE,
                binop(type, 9223372036854775807L, 9223372036854775807L)));

        test(pairOf(TRUE,
                binop(type, -9223372036854775808L, -9223372036854775808L)));

        test(pairOf(FALSE, binop(type, 1, -9223372036854775808L)));

        test(pairOf(FALSE, binop(type, 21, 9)));


    }

    @Test
    void testGEQ() {
        OpType type = OpType.GEQ;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        test(pairOf(TRUE, binop(type, 0, 0)));
        test(pairOf(TRUE, binop(type, 1, 1)));

        test(pairOf(FALSE, binop(type, 1, 9223372036854775807L)));

        test(pairOf(TRUE, binop(type, 9223372036854775807L, 1)));

        test(pairOf(FALSE,
                binop(type, -9223372036854775808L, 9223372036854775807L)));

        test(pairOf(TRUE,
                binop(type, 9223372036854775807L, 9223372036854775807L)));

        test(pairOf(TRUE,
                binop(type, -9223372036854775808L, -9223372036854775808L)));

        test(pairOf(TRUE, binop(type, 1, -9223372036854775808L)));

        test(pairOf(TRUE, binop(type, 21, 9)));


    }

    @Test
    void testLT() {
        OpType type = OpType.LT;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        test(pairOf(FALSE, binop(type, 0, 0)));
        test(pairOf(FALSE, binop(type, 1, 1)));

        test(pairOf(TRUE, binop(type, 1, 9223372036854775807L)));

        test(pairOf(FALSE, binop(type, 9223372036854775807L, 1)));

        test(pairOf(TRUE,
                binop(type, -9223372036854775808L, 9223372036854775807L)));

        test(pairOf(FALSE,
                binop(type, 9223372036854775807L, 9223372036854775807L)));

        test(pairOf(FALSE,
                binop(type, -9223372036854775808L, -9223372036854775808L)));

        test(pairOf(FALSE, binop(type, 1, -9223372036854775808L)));

        test(pairOf(FALSE, binop(type, 21, 9)));


    }

    @Test
    void testGT() {
        OpType type = OpType.GT;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        test(pairOf(FALSE, binop(type, 0, 0)));
        test(pairOf(FALSE, binop(type, 1, 1)));

        test(pairOf(FALSE, binop(type, 1, 9223372036854775807L)));

        test(pairOf(TRUE, binop(type, 9223372036854775807L, 1)));

        test(pairOf(FALSE,
                binop(type, -9223372036854775808L, 9223372036854775807L)));

        test(pairOf(FALSE,
                binop(type, 9223372036854775807L, 9223372036854775807L)));

        test(pairOf(FALSE,
                binop(type, -9223372036854775808L, -9223372036854775808L)));

        test(pairOf(TRUE, binop(type, 1, -9223372036854775808L)));

        test(pairOf(TRUE, binop(type, 21, 9)));


    }

    @Test
    void testEq() {
        OpType type = OpType.EQ;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        test(pairOf(TRUE, binop(type, 0, 0)));
        test(pairOf(TRUE, binop(type, 1, 1)));

        test(pairOf(FALSE, binop(type, 1, 9223372036854775807L)));

        test(pairOf(FALSE, binop(type, 9223372036854775807L, 1)));

        test(pairOf(FALSE,
                binop(type, -9223372036854775808L, 9223372036854775807L)));

        test(pairOf(TRUE,
                binop(type, 9223372036854775807L, 9223372036854775807L)));

        test(pairOf(TRUE,
                binop(type, -9223372036854775808L, -9223372036854775808L)));

        test(pairOf(FALSE, binop(type, 1, -9223372036854775808L)));

        test(pairOf(FALSE, binop(type, 21, 9)));


    }

    @Test
    void testNeq() {
        OpType type = OpType.NEQ;
        final var TRUE = constant(true);
        final var FALSE = constant(false);

        test(pairOf(FALSE, binop(type, 0, 0)));
        test(pairOf(FALSE, binop(type, 1, 1)));

        test(pairOf(TRUE, binop(type, 1, 9223372036854775807L)));

        test(pairOf(TRUE, binop(type, 9223372036854775807L, 1)));

        test(pairOf(TRUE,
                binop(type, -9223372036854775808L, 9223372036854775807L)));

        test(pairOf(FALSE,
                binop(type, 9223372036854775807L, 9223372036854775807L)));

        test(pairOf(FALSE,
                binop(type, -9223372036854775808L, -9223372036854775808L)));

        test(pairOf(TRUE, binop(type, 1, -9223372036854775808L)));

        test(pairOf(TRUE, binop(type, 21, 9)));


    }

    @Test
    void testARightShift() {
        OpType type = OpType.ARSHIFT;
        test(pairOf(constant(0), binop(type, 0, 0)));
        test(pairOf(constant(0), binop(type, 1, 1)));

        test(pairOf(constant(0), binop(type, 1, 9223372036854775807L)));

        test(pairOf(constant(4611686018427387903L),
                binop(type, 9223372036854775807L, 1)));

        test(pairOf(constant(-1),
                binop(type, -9223372036854775808L, 9223372036854775807L)));

        test(pairOf(constant(0),
                binop(type, 9223372036854775807L, 9223372036854775807L)));

        // Equivalent to shift by 0
        test(pairOf(constant(-9223372036854775808L),
                binop(type, -9223372036854775808L, -9223372036854775808L)));

        // Equivalent to 1 >> 0 = 1
        // Because -9223372036854775808L mod 2^64 = 0
        test(pairOf(constant(1), binop(type, 1, -9223372036854775808L)));

        test(pairOf(constant(0), binop(type, 21, 9)));

        test(pairOf(constant(-4611686018427387904L),
                binop(type, -9223372036854775808L, 1)));

        test(pairOf(constant(-1), binop(type, -1, 1)));



    }

    @Test
    void testLRightShift() {
        OpType type = OpType.RSHIFT;
        test(pairOf(constant(0), binop(type, 0, 0)));
        test(pairOf(constant(0), binop(type, 1, 1)));

        test(pairOf(constant(0), binop(type, 1, 9223372036854775807L)));

        test(pairOf(constant(4611686018427387903L),
                binop(type, 9223372036854775807L, 1)));

        // Just shifts by 63, because of Java specifications.
        test(pairOf(constant(1),
                binop(type, -9223372036854775808L, 9223372036854775807L)));

        test(pairOf(constant(0),
                binop(type, 9223372036854775807L, 9223372036854775807L)));

        // No shift b/c last 6 bits of 2^64 are 0.
        test(pairOf(constant(-9223372036854775808L),
                binop(type, -9223372036854775808L, -9223372036854775808L)));

        // Equivalent to 1 >> 0 = 1
        // Because -9223372036854775808L mod 2^64 = 0
        test(pairOf(constant(1), binop(type, 1, -9223372036854775808L)));

        test(pairOf(constant(0), binop(type, 21, 9)));

        test(pairOf(constant(4611686018427387904L),
                binop(type, -9223372036854775808L, 1)));

        test(pairOf(constant(9223372036854775807L), binop(type, -1, 1)));



    }

    @Test
    void testLeftShift() {
        OpType type = OpType.LSHIFT;
        test(pairOf(constant(0), binop(type, 0, 0)));
        test(pairOf(constant(2), binop(type, 1, 1)));

        test(pairOf(constant(-9223372036854775808L),
                binop(type, 1, 9223372036854775807L)));

        // Equivalent to 11111...10 where only the lowest bit is 0.
        test(pairOf(constant(-2), binop(type, 9223372036854775807L, 1)));

        test(pairOf(constant(0),
                binop(type, -9223372036854775808L, 9223372036854775807L)));

        // Shift to the left 63 bits
        test(pairOf(constant(-9223372036854775808L),
                binop(type, 9223372036854775807L, 9223372036854775807L)));

        test(pairOf(constant(-9223372036854775808L),
                binop(type, -9223372036854775808L, -9223372036854775808L)));

        test(pairOf(constant(1), binop(type, 1, -9223372036854775808L)));

        test(pairOf(constant(10752), binop(type, 21, 9)));


    }

}
