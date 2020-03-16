package cyr7.ir.block;

import java.util.List;
import java.util.stream.Collectors;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;

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

            List<BasicBlock> blocks = BlockGenerator.getBlocks(generator, statements);
            List<List<BasicBlock>> traces = BlockTraceGenerator.getTraces(blocks);
            List<List<BasicBlock>> optimizedTraces = BlockTraceOptimizer.optimized(traces);
            List<IRStmt> flattenedStmts = flatten(optimizedTraces);
            IRSeq newBody = new IRSeq(decl.body().location(), flattenedStmts);

            IRFuncDecl funcDecl = new IRFuncDecl(decl.location(), decl.name(), newBody);
            optimized.appendFunc(funcDecl);
        });

        return optimized;
    }

    private static List<IRStmt> flatten(List<List<BasicBlock>> traces) {
        return traces.stream()
            .flatMap(trace -> trace.stream()
                .flatMap(basicBlock -> basicBlock.stmts.stream())
            ).collect(Collectors.toList());
    }

    private TraceOptimizer() { }

}
