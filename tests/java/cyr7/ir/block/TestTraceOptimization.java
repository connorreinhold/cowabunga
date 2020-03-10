package cyr7.ir.block;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import java_cup.runtime.ComplexSymbolFactory.Location;

class TestTraceOptimization {

    private final BasicBlock EMPTY = new BasicBlock(List.of());

    private final Location loc = new Location(-1, -1);
    private final IRNodeFactory make = new IRNodeFactory_c(loc);
    private final IdGenerator generate = new DefaultIdGenerator();

    private void test(List<List<BasicBlock>> expected, List<IRStmt> stmts) {
        BasicBlockList actual = BasicBlockGenerator.getBlocks(stmts);

        IdGenerator generator = new DefaultIdGenerator();
        List<List<BasicBlock>> result = BlockTraceGenerator.getTraces(generator, actual);
        assertEquals(expected.size(), result.size());
        expected.forEach(result::contains);
        result.forEach(expected::contains);
    }

    private BasicBlock block(IRStmt... stmts) {
        return new BasicBlock(Arrays.asList(stmts));
    }

    @Test
    void basicTest() {
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
        BasicBlock b1 = block(make.IRLabel("main"));
        BasicBlock b2 = block(
                make.IRLabel(l2),
                make.IRMove(make.IRTemp("_t1"),
                        make.IRBinOp(OpType.ADD, make.IRConst(1),
                                make.IRConst(32)))
            );
        BasicBlock b3 = block(make.IRLabel(l1));
        List<List<BasicBlock>> expected = 
                List.of(new ArrayList<BasicBlock>(List.of(b1, b3, b2)));
        test(expected, stmts);
    }

    @Test
    void noFinalJumpStmtTest() {
        List<IRStmt> stmts = List.of(
                make.IRLabel("main"), make.IRReturn(),

                make.IRLabel("go"),

                make.IRLabel("truth"), make.IRCallStmt(make.IRName("main")),
                make.IRMove(make.IRTemp("t1"), make.IRConst(1232)),

                make.IRLabel("compilers"),
                make.IRMove(make.IRTemp(generate.argTemp(0)), make.IRConst(0)),
                make.IRCallStmt(make.IRName("print")));

        BasicBlock b1 = block(make.IRLabel("main"), make.IRReturn());
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
                make.IRCallStmt(make.IRName("print")));

        List<List<BasicBlock>> expected =
                List.of(
                    new ArrayList<>(List.of(b1)),
                    new ArrayList<>(List.of(b2)),
                    new ArrayList<>(List.of(b3)),
                    new ArrayList<>(List.of(b4)));

        test(expected, stmts);

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

        BasicBlock b1 = block(make.IRCJump(make.IRConst(0), lt, lf));
        BasicBlock b2 = block(
                make.IRLabel(lt),
                make.IRReturn()
        );
        BasicBlock b3 = block(
                make.IRLabel(lf),
                make.IRMove(make.IRTemp("_few"), make.IRConst(1))
            );

        BasicBlock b4 = block(make.IRLabel("end"));

        List<List<BasicBlock>> expected =
                List.of(new ArrayList<>(List.of(b1, b3)),
                    new ArrayList<>(
                        List.of(EMPTY, b4)),
                    new ArrayList<>(List.of(b2)));
        test(expected, stmts);
    }

    @Test
    void emptyTracesTest() {
        String lt = generate.newLabel();
        String lf = generate.newLabel();
        List<IRStmt> stmts = List.of(
                make.IRJump(make.IRName(lt)),
                make.IRLabel(lt),
                make.IRJump(make.IRName(lf)),
                make.IRLabel(lf),
                make.IRJump(make.IRName("end")),
                make.IRLabel("end")
        );

        BasicBlock b1 = EMPTY;

        BasicBlock b2 = block(make.IRLabel(lt));
        BasicBlock b3 = block(make.IRLabel(lf));
        BasicBlock b4 = block(make.IRLabel("end"));

        List<List<BasicBlock>> expected =
                List.of(new ArrayList<>(List.of(b1, b2, b3, b4)));
        test(expected, stmts);
    }


}
