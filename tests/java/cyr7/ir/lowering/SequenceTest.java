package cyr7.ir.lowering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import java_cup.runtime.ComplexSymbolFactory.Location;

class SequenceTest {

    IRNodeFactory make = new IRNodeFactory_c(new Location(-1, -1));
    LoweringVisitor lower = new LoweringVisitor(new DefaultIdGenerator());

    private IRSeq seq(IRStmt... s) {
        return make.IRSeq(s);
    }

    private IRESeq eseq(IRExpr e, IRStmt... s) {
        return make.IRESeq(seq(s), e);
    }

    private IRExp exp(IRExpr e) {
        return make.IRExp(e);
    }

    private void test(List<IRStmt> expected, IRSeq actual) {
        assertEquals(expected, actual.accept(lower).assertFirst());
    }

    @Test
    void nestedSequenceTest() {
        IRSeq emptySeq = seq(seq(seq(seq())));
        test(List.of(), emptySeq);

        IRStmt callMain = make.IRCallStmt(make.IRName("main"));
        IRSeq singletonSeq = seq(seq(seq(seq(callMain))));
        test(List.of(callMain), singletonSeq);

        IRStmt moveNum = make.IRExp(make.IRTemp("t1"));
        IRStmt jump = make.IRCJump(make.IRConst(1), "__lt");
        IRSeq sequenceOfThree = seq(
                seq(moveNum, seq(seq(callMain, seq())), jump));
        test(List.of(callMain, jump), sequenceOfThree);
    }

    @Test
    void alternatingSeq() {
        IRSeq empty = seq(exp(make.IRConst(0)), exp(eseq(make.IRConst(1))));
        test(List.of(), empty);
    }

}
