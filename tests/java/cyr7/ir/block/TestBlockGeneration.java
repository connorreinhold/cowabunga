package cyr7.ir.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import java_cup.runtime.ComplexSymbolFactory.Location;

class TestBlockGeneration {

    private final Location loc = new Location(-1, -1);
    private final IRNodeFactory make = new IRNodeFactory_c(loc);
    private final IdGenerator generate = new DefaultIdGenerator();

    void test(Set<BasicBlock> expected, List<IRStmt> actual) {
        BasicBlockList blockSet = BasicBlockGenerator.getBlocks(actual);
        assertEquals(expected.size(), blockSet.size());
        // Redundant here but just in case.
        blockSet.forEach(b -> assertTrue(expected.contains(b)));
        expected.forEach(b -> assertTrue(blockSet.contains(b)));
    }

    private Set<BasicBlock> createExpectedSet(BasicBlock... blocks) {
        return new HashSet<>(Arrays.asList(blocks));
    }

    private BasicBlock block(IRStmt ...stmts) {
        return new BasicBlock(Arrays.asList(stmts));
    }


    @Test
    void basicBlockTest() {
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

        Set<BasicBlock> expectedBlocks = createExpectedSet(
                block(
                        make.IRLabel("main"),
                        make.IRJump(make.IRName(l1))),
                block(
                        make.IRLabel(l2),
                        make.IRMove(make.IRTemp("_t1"),
                                    make.IRBinOp(OpType.ADD, make.IRConst(1),
                                        make.IRConst(32)))),
                block(make.IRLabel(l1), make.IRJump(make.IRName(l2)))
        );
        test(expectedBlocks, stmts);
    }

    @Test
    void eachStmtIsALabelTest() {
        List<IRStmt> stmts = List.of(
                make.IRLabel("main"),
                make.IRLabel("go"),
                make.IRLabel("truth"),
                make.IRLabel("compilers"),
                make.IRLabel("Ok_World")
                );

        Set<BasicBlock> expectedBlocks = createExpectedSet(
                block(make.IRLabel("main")),
                block(make.IRLabel("go")),
                block(make.IRLabel("truth")),
                block(make.IRLabel("compilers")),
                block(make.IRLabel("Ok_World"))
        );
        test(expectedBlocks, stmts);
    }

    @Test
    void callDoesNotTerminateBlockTest() {
        List<IRStmt> stmts = List.of(
                make.IRLabel("main"),
                make.IRReturn(),

                make.IRLabel("go"),

                make.IRLabel("truth"),
                make.IRCallStmt(make.IRName("main")),
                make.IRMove(make.IRTemp("t1"), make.IRConst(1232)),

                make.IRLabel("compilers"),
                make.IRMove(
                        make.IRTemp(generate.argTemp(0)),
                        make.IRConst(0)),
                make.IRCallStmt(make.IRName("print"))
        );

        Set<BasicBlock> expectedBlocks = createExpectedSet(
                block(make.IRLabel("main"), make.IRReturn()),

                block(make.IRLabel("go")),

                block(make.IRLabel("truth"),
                        make.IRCallStmt(make.IRName("main")),
                        make.IRMove(make.IRTemp("t1"), make.IRConst(1232))),

                block(make.IRLabel("compilers"),
                        make.IRMove(make.IRTemp(generate.argTemp(0)),
                                make.IRConst(0)),
                        make.IRCallStmt(make.IRName("print")))
        );
        test(expectedBlocks, stmts);
    }


    @Test
    void onlyJumpsTest() {
        List<IRStmt> stmts = List.of(
                make.IRJump(make.IRName("h")),
                make.IRJump(make.IRName("e")),
                make.IRJump(make.IRName("l")),
                make.IRJump(make.IRName("lo")),
                make.IRJump(make.IRName("o"))
        );

        Set<BasicBlock> expectedBlocks = createExpectedSet(
                block(make.IRJump(make.IRName("h"))),
                block(make.IRJump(make.IRName("e"))),
                block(make.IRJump(make.IRName("l"))),
                block(make.IRJump(make.IRName("lo"))),
                block(make.IRJump(make.IRName("o")))
        );
        test(expectedBlocks, stmts);


        stmts = List.of(
                make.IRJump(make.IRName("hello")),
                make.IRJump(make.IRName("kello")),
                make.IRLabel("main"),
                make.IRJump(make.IRName("jello")),
                make.IRJump(make.IRName("mellow"))
        );

        expectedBlocks = createExpectedSet(
                block(make.IRJump(make.IRName("hello"))),
                block(make.IRJump(make.IRName("kello"))),
                block(make.IRLabel("main"), make.IRJump(make.IRName("jello"))),
                block(make.IRJump(make.IRName("mellow")))
        );
        test(expectedBlocks, stmts);
    }

    @Test
    void singleBlockTest() {
        String t = generate.newTemp();
        String r = generate.newTemp();
        List<IRStmt> stmts = List.of(
                make.IRLabel("main"),
                make.IRMove(make.IRTemp(t), make.IRConst(123)),
                make.IRCallStmt(make.IRName("randomString")),
                make.IRMove(make.IRTemp(r), make.IRTemp(generate.retTemp(0))),
                make.IRMove(make.IRTemp(generate.argTemp(0)), make.IRTemp(r)),
                make.IRCallStmt(make.IRName("print")),
                make.IRReturn()
        );

        Set<BasicBlock> expectedBlocks = createExpectedSet(
           block(make.IRLabel("main"),
                 make.IRMove(make.IRTemp(t), make.IRConst(123)),
                 make.IRCallStmt(make.IRName("randomString")),
                 make.IRMove(make.IRTemp(r), make.IRTemp(generate.retTemp(0))),
                 make.IRMove(make.IRTemp(generate.argTemp(0)), make.IRTemp(r)),
                 make.IRCallStmt(make.IRName("print")),
                 make.IRReturn())
        );
        test(expectedBlocks, stmts);
    }

    @Test
    void singleBlockNoLabelTest() {
        String t = generate.newTemp();
        String r = generate.newTemp();
        List<IRStmt> stmts = List.of(
                make.IRMove(make.IRTemp(t), make.IRConst(123)),
                make.IRCallStmt(make.IRName("randomString")),
                make.IRMove(make.IRTemp(r), make.IRTemp(generate.retTemp(0))),
                make.IRMove(make.IRTemp(generate.argTemp(0)), make.IRTemp(r)),
                make.IRCallStmt(make.IRName("print")), make.IRReturn());

        Set<BasicBlock> expectedBlocks = createExpectedSet(block(
                make.IRMove(make.IRTemp(t), make.IRConst(123)),
                make.IRCallStmt(make.IRName("randomString")),
                make.IRMove(make.IRTemp(r), make.IRTemp(generate.retTemp(0))),
                make.IRMove(make.IRTemp(generate.argTemp(0)), make.IRTemp(r)),
                make.IRCallStmt(make.IRName("print")), make.IRReturn()));
        test(expectedBlocks, stmts);
    }


    @Test
    void cJumpTest() {
        String t = generate.newTemp();
        String r = generate.newTemp();
        String lt = generate.newLabel();
        String lf = generate.newLabel();
        List<IRStmt> stmts = List.of(
                make.IRLabel("main"),
                make.IRMove(make.IRTemp(t), make.IRConst(123)),
                make.IRCallStmt(make.IRName("randomString")),
                make.IRMove(make.IRTemp(r), make.IRTemp(generate.retTemp(0))),
                make.IRMove(make.IRTemp(generate.argTemp(0)), make.IRTemp(r)),
                make.IRCJump(make.IRConst(1), lt, lf),
                make.IRLabel(lt),
                make.IRMove(make.IRTemp(r),
                        make.IRTemp(generate.retTemp(0))),
                make.IRCallStmt(make.IRName("print")),
                make.IRLabel(lf),
                make.IRReturn()
        );

        Set<BasicBlock> expectedBlocks = createExpectedSet(
                block(
                   make.IRLabel("main"),
                   make.IRMove(make.IRTemp(t), make.IRConst(123)),
                   make.IRCallStmt(make.IRName("randomString")),
                   make.IRMove(make.IRTemp(r), make.IRTemp(generate.retTemp(0))),
                   make.IRMove(make.IRTemp(generate.argTemp(0)), make.IRTemp(r)),
                   make.IRCJump(make.IRConst(1), lt, lf)
                 ),
                block(make.IRLabel(lt),
                        make.IRMove(make.IRTemp(r),
                                make.IRTemp(generate.retTemp(0))),
                        make.IRCallStmt(make.IRName("print"))),
                block(
                        make.IRLabel(lf), make.IRReturn()
                ));

        test(expectedBlocks, stmts);
    }



    @Test
    void cJumpReorderingTest() {
        String lt = generate.newLabel();
        String lf = generate.newLabel();
        List<IRStmt> stmts = List.of(
                make.IRCJump(make.IRConst(0), lt, lf),
                make.IRLabel(lt),
                make.IRReturn(),
                make.IRJump(make.IRName("end")),
                make.IRLabel(lf),
                make.IRMove(make.IRTemp("_few"), make.IRConst(1)),
                make.IRLabel("end")
        );

        Set<BasicBlock> expectedBlocks = createExpectedSet(
                block(make.IRCJump(make.IRConst(0), lt, lf)),
                block(make.IRLabel(lt), make.IRReturn()),
                block(make.IRJump(make.IRName("end"))),
                block(make.IRLabel(lf),
                        make.IRMove(make.IRTemp("_few"), make.IRConst(1))),
                block(make.IRLabel("end"))
        );
        test(expectedBlocks, stmts);

    }

}
