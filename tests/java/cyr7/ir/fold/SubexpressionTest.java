package cyr7.ir.fold;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final MyIRVisitor<OneOfThree<IRExpr, IRStmt, IRFuncDecl>> folder = new MIRConstFoldVisitor();

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
        // Call stmts are not supposed to have children
//        var actual = make.IRCallStmt(List.of(), make.IRName("main"),
//                List.of(
//                make.IRTemp("arg0"),
//                        make.IRBinOp(OpType.ADD, make.IRConst(0),
//                                make.IRConst(1))));
//        var expected = make.IRCallStmt(List.of(), make.IRName("main"),
//                List.of(make.IRTemp("arg0"), make.IRConst(1)));
//        assertEquals(expected, actual.accept(folder).assertSecond());
//        assertEquals(expected, expected.accept(folder).assertSecond());
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

    @Test
    void testCompUnit() {

        List<IRStmt> stmtsOfMain = List.of(
                make.IRMove(make.IRTemp("t1"),
                        make.IRBinOp(OpType.ADD, make.IRConst(0),
                                make.IRConst(1))),
                make.IRLabel("here"),
                make.IRExp(
                        make.IRCall(make.IRName("helper"),
                                List.of(make.IRBinOp(OpType.ADD,
                                        make.IRConst(0), make.IRConst(1))))),
                make.IRReturn());
        List<IRStmt> stmtsOfHelper = List.of(
                make.IRMove(
                        make.IRMem(make.IRBinOp(OpType.MUL, make.IRConst(2),
                                make.IRConst(4))),
                        make.IRBinOp(
                                OpType.ADD, make.IRConst(0), make.IRConst(1))),
                make.IRLabel("nope"),
                make.IRExp(
                        make.IRCall(make.IRName("helper"),
                                List.of(make.IRBinOp(OpType.ADD,
                                        make.IRConst(0), make.IRConst(1))))),
                make.IRReturn());

        List<IRStmt> stmtsOfMainFolded = List.of(
                make.IRMove(make.IRTemp("t1"), make.IRConst(1)),
                make.IRLabel("here"),
                make.IRExp(make.IRCall(make.IRName("helper"),
                        List.of(make.IRConst(1)))),
                make.IRReturn());

        List<IRStmt> stmtsOfHelperFolded = List.of(
                make.IRMove(
                        make.IRMem(make.IRConst(8)), make.IRConst(1)),
                make.IRLabel("nope"),
                make.IRExp(
                        make.IRCall(make.IRName("helper"),
                                List.of(make.IRConst(1)))),
                make.IRReturn());

        Map<String, IRFuncDecl> funcs = new HashMap<>();
        funcs.put("main", make.IRFuncDecl("main", make.IRSeq(stmtsOfMain)));
        funcs.put("helper",
                make.IRFuncDecl("helper", make.IRSeq(stmtsOfHelper)));

        Map<String, IRFuncDecl> funcsFolded = new HashMap<>();
        funcsFolded.put("main",
                make.IRFuncDecl("main", make.IRSeq(stmtsOfMainFolded)));
        funcsFolded.put("helper",
                make.IRFuncDecl("helper", make.IRSeq(stmtsOfHelperFolded)));

        var actual = make.IRCompUnit("hello", funcs).accept(folder)
                .assertSecond();
        var expected = make.IRCompUnit("hello", funcsFolded);

        assertEquals(expected, actual);
        assertEquals(expected, expected.accept(folder).assertSecond());
    }
}
