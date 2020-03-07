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

    private IRExpr binop(OpType t, long l, long r) {
        return make.IRBinOp(t, constant(l), constant(r));
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

}
