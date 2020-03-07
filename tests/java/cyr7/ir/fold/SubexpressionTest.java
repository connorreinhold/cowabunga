package cyr7.ir.fold;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import cyr7.util.OneOfThree;
import cyr7.visitor.MyIRVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;
import polyglot.util.Pair;

class SubexpressionTest {

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

    private void test(Pair<IRNode, IRNode> t) {
        var expected = t.part1();
        var actual = t.part2().accept(folder).assertFirst();
        assertEquals(expected, actual);
    }

    @Test
    void testMem() {
        var actual = make.IRMem(
                make.IRBinOp(OpType.ADD, make.IRConst(0), make.IRConst(1)));
        var expected = make.IRMem(make.IRConst(1));

        test(pairOf(expected, actual));
        test(pairOf(expected, expected));
    }

    @Test
    void testCall() {
        var actual = make.IRCall(make.IRName("main"), make.IRTemp("arg0"),
                make.IRBinOp(OpType.ADD, make.IRConst(0), make.IRConst(1)));
        var expected = make.IRCall(make.IRName("main"), make.IRTemp("arg0"),
                make.IRConst(1));
        test(pairOf(expected, actual));
        test(pairOf(expected, expected));
    }

    @Test
    void testCallStmt() {
        var actual = make.IRCallStmt(List.of(), make.IRName("main"),
                List.of(
                make.IRTemp("arg0"),
                        make.IRBinOp(OpType.ADD, make.IRConst(0),
                                make.IRConst(1))));
        var expected = make.IRCallStmt(List.of(), make.IRName("main"),
                List.of(make.IRTemp("arg0"), make.IRConst(1)));
        assertEquals(expected, actual.accept(folder).assertSecond());
        assertEquals(expected, expected.accept(folder).assertSecond());

    }

    @Test
    void testCJump() {
        var actual = make.IRCJump(make.IRConst(1), "lt", "lf");
        var expected = make.IRCJump(make.IRConst(1), "lt", "lf");
        assertEquals(expected, actual.accept(folder).assertSecond());
        assertEquals(expected, expected.accept(folder).assertSecond());
    }

    @Test
    void testJump() {
        var actual = make.IRJump(make.IRName("l1"));
        var expected = make.IRJump(make.IRName("l1"));
        assertEquals(expected, actual.accept(folder).assertSecond());
        assertEquals(expected, expected.accept(folder).assertSecond());
    }

    @Test
    void testSeq() {
        var actual = make.IRSeq(
                make.IRExp(make.IRBinOp(OpType.ADD, make.IRConst(1),
                        make.IRConst(0))),
                make.IRExp(make.IRBinOp(OpType.ADD, make.IRConst(1),
                        make.IRConst(0))));
        var expected = make.IRSeq(
                make.IRExp(make.IRConst(1)), make.IRExp(make.IRConst(1)));
        assertEquals(expected, actual.accept(folder).assertSecond());
        assertEquals(expected, expected.accept(folder).assertSecond());
    }
}
