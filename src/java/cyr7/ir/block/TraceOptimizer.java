package cyr7.ir.block;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;

import java.util.List;
import java.util.stream.Collectors;

public final class TraceOptimizer {

    public static void optimize(IRCompUnit compUnit, IdGenerator generator) {
        IRNodeFactory factory = new IRNodeFactory_c(compUnit.location());

        IRCompUnit optimized = factory.IRCompUnit(compUnit.name());

        compUnit.functions().forEach((name, decl) -> {
            List<IRStmt> statements;
            if (decl.body() instanceof IRSeq) {
                statements = ((IRSeq) decl.body()).stmts();
            } else {
                statements = List.of(decl.body());
            }

            BasicBlockList blocks = BasicBlockGenerator.getBlocks(statements);
            List<List<BasicBlock>> traces = BlockTraceGenerator.getTraces(generator, blocks);
            IRSeq newBody = new IRSeq(decl.body().location(), flatten(traces));

            IRFuncDecl funcDecl = new IRFuncDecl(decl.location(), decl.name(), newBody);
            optimized.appendFunc(funcDecl);
        });
    }

    private static List<IRStmt> flatten(List<List<BasicBlock>> traces) {
        return traces.stream()
            .flatMap(trace -> trace.stream()
                .flatMap(basicBlock -> basicBlock.stmts.stream())
            ).collect(Collectors.toList());
    }
}
