package cyr7.ir.block;

import cyr7.C;
import cyr7.ir.IdGenerator;
import cyr7.ir.ctranslation.LookaheadIdGenerator;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestBlockGeneration {

    private final IRNodeFactory make = new IRNodeFactory_c(C.LOC);

    private void test(Set<BasicBlock> expected, List<IRStmt> actual,
                      IdGenerator generator) {
        List<BasicBlock> blockSet = BlockGenerator.getBlocks(generator, actual);
        blockSet.forEach(b ->
            assertTrue(
                expected.contains(b),
                getErrorMessage(expected, blockSet)
            )
        );
        expected.forEach(b ->
            assertTrue(
                blockSet.contains(b),
                getErrorMessage(expected, blockSet)
            )
        );
    }

    private static String getErrorMessage(Set<BasicBlock> expected, List<BasicBlock> actual) {
        return "Expected some permutation of: \n"
            + expected.stream()
            .map(BasicBlock::toString)
            .map(s -> s.replace("\n", ""))
            .collect(Collectors.joining("\n"))
            + "\nActual:\n"
            + actual.stream()
            .map(BasicBlock::toString)
            .map(s -> s.replace("\n", ""))
            .collect(Collectors.joining("\n"))
            + "\n";
    }

    private Set<BasicBlock> createExpectedSet(BasicBlock... blocks) {
        return new HashSet<>(Arrays.asList(blocks));
    }

    private BasicBlock block(IRStmt... stmts) {
        return new BasicBlock(Arrays.asList(stmts));
    }

    @Test
    void basicBlockTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();
        String l1 = generate.peekLabel(0);
        String l2 = generate.peekLabel(1);
        List<IRStmt> stmts = List.of(
            make.IRLabel("main"),
            make.IRJump(make.IRName(l1)),
            make.IRLabel(l2),
            make.IRMove(
                make.IRTemp("_t1"),
                make.IRBinOp(OpType.ADD,
                    make.IRConst(1),
                    make.IRConst(32)
                )
            ),
            make.IRLabel(l1),
            make.IRJump(make.IRName(l2))
        );

        Set<BasicBlock> expectedBlocks = createExpectedSet(
            block(
                make.IRLabel("main"),
                make.IRJump(make.IRName(l1))
            ),
            block(
                make.IRLabel(l2),
                make.IRMove(make.IRTemp("_t1"),
                    make.IRBinOp(OpType.ADD,
                        make.IRConst(1),
                        make.IRConst(32))
                ),
                make.IRJump(make.IRName(l1))
            ),
            block(
                make.IRLabel(l1),
                make.IRJump(make.IRName(l2))
            )
        );
        test(expectedBlocks, stmts, generate);
    }

    @Test
    void eachStmtIsALabelTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();

        List<IRStmt> stmts = List.of(
            make.IRLabel("main"),
            make.IRLabel("go"),
            make.IRLabel("truth"),
            make.IRLabel("compilers"),
            make.IRLabel("Ok_World"),
            make.IRReturn()
        );

        Set<BasicBlock> expectedBlocks = createExpectedSet(
            block(make.IRLabel("main"), make.IRJump(make.IRName("go"))),
            block(make.IRLabel("go"), make.IRJump(make.IRName("truth"))),
            block(make.IRLabel("truth"), make.IRJump(make.IRName("compilers"))),
            block(make.IRLabel("compilers"), make.IRJump(make.IRName("Ok_World"))),
            block(make.IRLabel("Ok_World"), make.IRReturn())
        );
        test(expectedBlocks, stmts, generate);
    }

    @Test
    void callDoesNotTerminateBlockTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();

        List<IRStmt> stmts = List.of(
            make.IRLabel("main"),
            make.IRReturn(),

            make.IRLabel("go"),

            make.IRLabel("truth"),
            make.IRCallStmt(make.IRName("main")),
            make.IRMove(
                make.IRTemp("t1"),
                make.IRConst(1232)
            ),

            make.IRLabel("compilers"),
            make.IRMove(
                make.IRTemp(generate.argTemp(0)),
                make.IRConst(0)
            ),
            make.IRCallStmt(make.IRName("print")),
            make.IRReturn()
        );

        Set<BasicBlock> expectedBlocks = createExpectedSet(
            block(make.IRLabel("main"), make.IRReturn()),
            block(make.IRLabel("go"), make.IRJump(make.IRName("truth"))),
            block(
                make.IRLabel("truth"),
                make.IRCallStmt(make.IRName("main")),
                make.IRMove(
                    make.IRTemp("t1"),
                    make.IRConst(1232)
                ),
                make.IRJump(make.IRName("compilers"))
            ),
            block(
                make.IRLabel("compilers"),
                make.IRMove(
                    make.IRTemp(generate.argTemp(0)),
                    make.IRConst(0)),
                make.IRCallStmt(make.IRName("print")),
                make.IRReturn()
            )
        );
        test(expectedBlocks, stmts, generate);
    }

    @Test
    void onlyJumpsTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();

        List<IRStmt> stmts = List.of(
            make.IRJump(make.IRName("h")),
            make.IRJump(make.IRName("e")),
            make.IRJump(make.IRName("l")),
            make.IRJump(make.IRName("l")),
            make.IRJump(make.IRName("o"))
        );

        Set<BasicBlock> expectedBlocks = createExpectedSet(
            block(make.IRLabel(generate.peekLabel(0)), make.IRJump(make.IRName("h"))),
            block(make.IRLabel(generate.peekLabel(1)), make.IRJump(make.IRName("e"))),
            block(make.IRLabel(generate.peekLabel(2)), make.IRJump(make.IRName("l"))),
            block(make.IRLabel(generate.peekLabel(3)), make.IRJump(make.IRName("l"))),
            block(make.IRLabel(generate.peekLabel(4)), make.IRJump(make.IRName("o")))
        );
        test(expectedBlocks, stmts, generate);

        stmts = List.of(
            make.IRJump(make.IRName("hello")),
            make.IRJump(make.IRName("kello")),
            make.IRLabel("main"),
            make.IRJump(make.IRName("jello")),
            make.IRJump(make.IRName("mellow"))
        );

        expectedBlocks = createExpectedSet(
            block(make.IRLabel(generate.peekLabel(0)), make.IRJump(make.IRName("hello"))),
            block(make.IRLabel(generate.peekLabel(1)), make.IRJump(make.IRName("kello"))),
            block(make.IRLabel("main"), make.IRJump(make.IRName("jello"))),
            block(make.IRLabel(generate.peekLabel(2)), make.IRJump(make.IRName("mellow")))
        );
        test(expectedBlocks, stmts, generate);
    }

    @Test
    void singleBlockTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();
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
        test(expectedBlocks, stmts, generate);
    }

    @Test
    void singleBlockNoLabelTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();
        String t = generate.peekTemp(0);
        String r = generate.peekTemp(1);
        List<IRStmt> stmts = List.of(
            make.IRMove(make.IRTemp(t), make.IRConst(123)),
            make.IRCallStmt(make.IRName("randomString")),
            make.IRMove(make.IRTemp(r), make.IRTemp(generate.retTemp(0))),
            make.IRMove(make.IRTemp(generate.argTemp(0)), make.IRTemp(r)),
            make.IRCallStmt(make.IRName("print")), make.IRReturn());

        Set<BasicBlock> expectedBlocks = createExpectedSet(block(
            make.IRLabel(generate.peekLabel(0)),
            make.IRMove(make.IRTemp(t), make.IRConst(123)),
            make.IRCallStmt(make.IRName("randomString")),
            make.IRMove(make.IRTemp(r), make.IRTemp(generate.retTemp(0))),
            make.IRMove(make.IRTemp(generate.argTemp(0)), make.IRTemp(r)),
            make.IRCallStmt(make.IRName("print")), make.IRReturn()));
        test(expectedBlocks, stmts, generate);
    }

    @Test
    void cJumpTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();
        String t = generate.peekTemp(0);
        String r = generate.peekTemp(1);
        String lt = generate.peekLabel(0);
        String lf = generate.peekLabel(1);
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
                make.IRCallStmt(make.IRName("print")),
                make.IRJump(make.IRName(lf))
            ),
            block(
                make.IRLabel(lf), make.IRReturn()
            ));

        test(expectedBlocks, stmts, generate);
    }


    @Test
    void cJumpReorderingTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();
        String lt = generate.newLabel();
        String lf = generate.newLabel();
        /*
         * Comments from William: So Anthony wrote this test case. I think it
         * roughly corresponds to this program, but I'm not totally sure
         *
         *  if false
         *      return
         *  else
         *      _few = true
         */
        List<IRStmt> stmts = List.of(
            make.IRCJump(make.IRConst(0), lt, lf),
            make.IRLabel(lt),
            make.IRReturn(),
            make.IRJump(make.IRName("end")),
            make.IRLabel(lf),
            make.IRMove(make.IRTemp("_few"), make.IRConst(1)),
            make.IRLabel("end"),
            make.IRReturn()
        );

        Set<BasicBlock> expectedBlocks = createExpectedSet(
            block(
                make.IRLabel(generate.peekLabel(0)),
                make.IRCJump(
                    make.IRConst(0), lt, lf
                )
            ),
            block(
                make.IRLabel(lt), make.IRReturn()
            ),
            block(
                make.IRLabel(generate.peekLabel(1)),
                make.IRJump(make.IRName("end"))
            ),
            block(
                make.IRLabel(lf),
                make.IRMove(
                    make.IRTemp("_few"),
                    make.IRConst(1)
                ),
                make.IRJump(make.IRName("end"))
            ),
            block(
                make.IRLabel("end"),
                make.IRReturn()
            )
        );
        test(expectedBlocks, stmts, generate);
    }

}
