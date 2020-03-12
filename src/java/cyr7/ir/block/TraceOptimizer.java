package cyr7.ir.block;

import cyr7.ir.IdGenerator;
import cyr7.ir.lowering.ContainsJumpsStmtVisitor;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public final class TraceOptimizer {

    public static IRCompUnit optimize(IRCompUnit compUnit, IdGenerator generator) {
        IRNodeFactory factory = new IRNodeFactory_c(compUnit.location());

        IRCompUnit optimized = factory.IRCompUnit(compUnit.name());

        compUnit.functions().forEach((name, decl) -> {
            List<IRStmt> statements;
            if (decl.body() instanceof IRSeq) {
                statements = ((IRSeq) decl.body()).stmts();
            } else {
                statements = List.of(decl.body());
            }

            List<BasicBlock> blocks = getBlocks(statements);
            List<List<BasicBlock>> traces = BlockTraceGenerator.getTraces(generator, blocks);
            IRSeq newBody = new IRSeq(decl.body().location(), flatten(traces));

            IRFuncDecl funcDecl = new IRFuncDecl(decl.location(), decl.name(), newBody);
            optimized.appendFunc(funcDecl);
        });

        return optimized;
    }

    /**
     * A block is defined as: The last statement is a JUMP/CJUMP/RETURN.
     * OR
     * The first statement of the next block is a LABEL.
     *
     * @param stmts
     * @return
     */
    public static List<BasicBlock> getBlocks(List<IRStmt> stmts) {
        ContainsJumpsStmtVisitor hasJumps = new ContainsJumpsStmtVisitor();
        List<BasicBlock> blocks = new LinkedList<>();
        List<IRStmt> currentBlockStmts = new LinkedList<>();

        stmts.forEach(s -> {
            if (s.accept(hasJumps)) {
                currentBlockStmts.add(s);
                blocks.add(new BasicBlock(currentBlockStmts));
                currentBlockStmts.clear();
            } else if (s instanceof IRLabel) {
                if (!currentBlockStmts.isEmpty())
                    blocks.add(new BasicBlock(currentBlockStmts));
                currentBlockStmts.clear();
                currentBlockStmts.add(s);
            } else {
                currentBlockStmts.add(s);
            }
        });
        if (!currentBlockStmts.isEmpty()) {
            blocks.add(new BasicBlock(currentBlockStmts));
        }
        return blocks;
    }

    private static List<IRStmt> flatten(List<List<BasicBlock>> traces) {
        return traces.stream()
            .flatMap(trace -> trace.stream()
                .flatMap(basicBlock -> basicBlock.stmts.stream())
            ).collect(Collectors.toList());
    }

}
