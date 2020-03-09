package cyr7.ir.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import java_cup.runtime.ComplexSymbolFactory.Location;

class TestBlockGeneration {

    private final Location loc = new Location(-1, -1);

    void test(Set<BasicBlock> expected, BasicBlockSet actual) {
        assertEquals(expected.size(), actual.size());
        actual.forEach(b -> assertTrue(expected.contains(b)));
        expected.forEach(b -> actual.contains(b));
    }

    @Test
    void basicBlockTest() {
        IRNodeFactory make = new IRNodeFactory_c(loc);
        String l1 = "_l1";
        String l2 = "_l2";
        List<IRStmt> stmts = List.of(
                make.IRLabel("main"),
                make.IRJump(make.IRName(l1)),
                make.IRLabel(l2),
                make.IRMove(make.IRTemp("_t1"),
                            make.IRBinOp(OpType.ADD, make.IRConst(1),
                                                     make.IRConst(32))),
                make.IRLabel(l1),
                make.IRJump(make.IRName(l2))
        );

        BasicBlockGenerator gen = new BasicBlockGenerator(stmts);
        BasicBlockSet blockSet = gen.getBlocks();

        Set<BasicBlock> expectedBlocks = new HashSet<>();
        expectedBlocks.add(new BasicBlock(List.of(
                make.IRLabel("main"),
                make.IRJump(make.IRName(l1)
        ))));
        expectedBlocks.add(new BasicBlock(List.of(
                make.IRLabel(l2),
                make.IRMove(make.IRTemp("_t1"),
                            make.IRBinOp(OpType.ADD, make.IRConst(1),
                                                     make.IRConst(32)))
        )));
        expectedBlocks.add(new BasicBlock(List.of(
                make.IRLabel(l1),
                make.IRJump(make.IRName(l2))
        )));

        test(expectedBlocks, blockSet);
    }

    @Test
    void eachStmtIsALabelTest() {
        IRNodeFactory make = new IRNodeFactory_c(loc);
        List<IRStmt> stmts = List.of(
                make.IRLabel("main"),
                make.IRLabel("go"),
                make.IRLabel("truth"),
                make.IRLabel("compilers"),
                make.IRLabel("Ok_World")
                );

        BasicBlockGenerator gen = new BasicBlockGenerator(stmts);
        BasicBlockSet blockSet = gen.getBlocks();

        Set<BasicBlock> expectedBlocks = new HashSet<>();
        expectedBlocks.add(new BasicBlock(List.of(make.IRLabel("main"))));
        expectedBlocks.add(new BasicBlock(List.of(make.IRLabel("go"))));
        expectedBlocks.add(new BasicBlock(List.of(make.IRLabel("truth"))));
        expectedBlocks.add(new BasicBlock(List.of(make.IRLabel("compilers"))));
        expectedBlocks.add(new BasicBlock(List.of(make.IRLabel("Ok_World"))));

        test(expectedBlocks, blockSet);
    }

    @Test
    void callDoesNotTerminateBlockTest() {

    }
}
