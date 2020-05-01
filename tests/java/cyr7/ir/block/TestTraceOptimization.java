package cyr7.ir.block;

import cyr7.C;
import cyr7.ir.IdGenerator;
import cyr7.ir.ctranslation.LookaheadIdGenerator;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestTraceOptimization {

    private final IRNodeFactory make = new IRNodeFactory_c(C.LOC);

    private void test(List<List<BasicBlock>> expected, List<IRStmt> actual,
                      IdGenerator generator) {
        List<BasicBlock> blockSet = BlockGenerator.getBlocks(generator, actual);
        List<List<BasicBlock>> traces = BlockTraceGenerator.getTraces(blockSet);
        List<List<BasicBlock>> optimized =
            BlockTraceOptimizer.optimized(traces);
        optimized.forEach(b ->
            assertTrue(
                expected.contains(b),
                getErrorMessage(expected, optimized)
            )
        );
        expected.forEach(b ->
            assertTrue(
                optimized.contains(b),
                getErrorMessage(expected, optimized)
            )
        );
    }

    private static String getErrorMessage(List<List<BasicBlock>> expected,
                                          List<List<BasicBlock>> actual) {
        return "Expected some permutation of: \n"
            + expected.stream()
            .map(List::toString)
            .map(s -> s.replace("\n", ""))
            .collect(Collectors.joining("\n"))
            + "\nActual:\n"
            + actual.stream()
            .map(List::toString)
            .map(s -> s.replace("\n", ""))
            .collect(Collectors.joining("\n"))
            + "\n";
    }

    private BasicBlock block(IRStmt... stmts) {
        return new BasicBlock(Arrays.asList(stmts));
    }

    @Test
    void basicTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();

        String l0 = generate.newLabel();
        String l1 = generate.newLabel();
        String t0 = generate.newTemp();

        List<IRStmt> stmts = List.of(
            make.IRLabel("main"),
            make.IRJump(make.IRName(l0)),
            make.IRLabel(l1),
            make.IRMove(make.IRTemp(t0),
                make.IRBinOp(OpType.ADD, make.IRConst(1),
                    make.IRConst(32))),
            make.IRLabel(l0),
            make.IRJump(make.IRName(l1))
        );
        BasicBlock b1 = block(make.IRLabel("main"));
        BasicBlock b2 = block(make.IRLabel(l0));
        BasicBlock b3 = block(
            make.IRLabel(l1),
            make.IRMove(make.IRTemp(t0),
                make.IRBinOp(OpType.ADD, make.IRConst(1),
                    make.IRConst(32))),
            make.IRJump(make.IRName(l0))
        );
        List<List<BasicBlock>> expected =
            List.of(List.of(b1, b2, b3));
        test(expected, stmts, generate);
    }

    @Test
    void noFinalJumpStmtTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();
        List<IRStmt> stmts = List.of(
            make.IRLabel("main"),
            make.IRReturn(0),

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
            make.IRCallStmt(
                make.IRName("print")
            ),

            make.IRReturn(0)
        );

        BasicBlock b1 = block(make.IRLabel("main"), make.IRReturn(0));
        BasicBlock b2 = block(make.IRLabel("go"));
        BasicBlock b3 = block(
            make.IRLabel("truth"),
            make.IRCallStmt(make.IRName("main")),
            make.IRMove(make.IRTemp("t1"), make.IRConst(1232))
        );

        BasicBlock b4 = block(
            make.IRLabel("compilers"),
            make.IRMove(
                make.IRTemp(generate.argTemp(0)),
                make.IRConst(0)),
            make.IRCallStmt(make.IRName("print")),
            make.IRReturn(0));

        List<List<BasicBlock>> expected =
            List.of(
                List.of(b1),
                List.of(b2, b3, b4)
            );

        test(expected, stmts, generate);
    }

    @Test
    void cJumpReorderingTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();
        String lt = generate.newLabel();
        String lf = generate.newLabel();
        List<IRStmt> stmts = List.of(
            make.IRCJump(make.IRConst(0), lt, lf),
            make.IRLabel(lt),
            make.IRReturn(0),
            make.IRJump(make.IRName("end")),
            make.IRLabel(lf),
            make.IRMove(make.IRTemp("_few"), make.IRConst(1)),
            make.IRLabel("end"),
            make.IRReturn(0)
        );

        BasicBlock b1 = block(
            make.IRLabel(generate.peekLabel(0)),
            make.IRCJump(make.IRConst(0), lt)
        );
        BasicBlock b2 = block(
            make.IRLabel(lt),
            make.IRReturn(0)
        );
        BasicBlock b3 = block(
            make.IRLabel(generate.peekLabel(1)),
            make.IRJump(make.IRName("end"))
        );
        BasicBlock b4 = block(
            make.IRLabel(lf),
            make.IRMove(make.IRTemp("_few"), make.IRConst(1))
        );
        BasicBlock b5 = block(
            make.IRLabel("end"),
            make.IRReturn(0)
        );

        List<List<BasicBlock>> expected =
            List.of(
                List.of(b1, b4, b5),
                List.of(b2),
                List.of(b3)
            );
        test(expected, stmts, generate);
    }

    @Test
    void emptyTracesTest() {
        LookaheadIdGenerator generate = new LookaheadIdGenerator();
        String lt = generate.newLabel();
        String lf = generate.newLabel();
        List<IRStmt> stmts = List.of(
            make.IRJump(make.IRName(lt)),
            make.IRLabel(lt),
            make.IRJump(make.IRName(lf)),
            make.IRLabel(lf),
            make.IRJump(make.IRName("end")),
            make.IRLabel("end"),
            make.IRReturn(0)
        );

        BasicBlock b1 = block(make.IRLabel(generate.peekLabel(0)));
        BasicBlock b2 = block(make.IRLabel(lt));
        BasicBlock b3 = block(make.IRLabel(lf));
        BasicBlock b4 = block(make.IRLabel("end"), make.IRReturn(0));

        List<List<BasicBlock>> expected =
            List.of(new ArrayList<>(List.of(b1, b2, b3, b4)));
        test(expected, stmts, generate);
    }

}
