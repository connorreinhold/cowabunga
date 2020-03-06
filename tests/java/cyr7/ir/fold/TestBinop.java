package cyr7.ir.fold;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import cyr7.util.OneOfThree;
import java_cup.runtime.ComplexSymbolFactory.Location;
import polyglot.util.Pair;

class TestBinop {

    private final IRNodeFactory make = new IRNodeFactory_c(
            new Location(-1, -1));

    private final MyIRVisitor<OneOfThree<IRExpr, IRStmt, IRFuncDecl>> folder = new ConstFoldVisitor();

    private <T, E> Pair<T, E> pairOf(T test, E expected) {
        return new Pair<T, E>(test, expected);
    }

    private IRConst constant(long n) {
        return make.IRConst(n);
    }

    private IRBinOp binop(OpType t, long l, long r) {
        return make.IRBinOp(t, constant(l), constant(r));
    }

    @Test
    void testAdd() {
        List<Pair<IRNode, IRNode>> addTest = List.of(
                pairOf(binop(OpType.ADD, 1, 1), constant(2))
        );

        addTest.forEach(p -> {
            assertEquals(p.part1().accept(folder).assertFirst(), p.part2());
        });
    }

}
