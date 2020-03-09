package cyr7.ir.block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyr7.ir.IdGenerator;
import cyr7.ir.block.BlockTraceGenerator.TraceSet;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;

public class TraceOptimizer {

    private final IRCompUnit compUnit;
    private final IdGenerator generate;

    public TraceOptimizer(IRCompUnit compUnit, IdGenerator generate) {
        this.compUnit = compUnit;
        this.generate = generate;
    }

    public IRCompUnit optimize() {
        return this.finishOptimization(
                  this.toTraces(
                        this.mapToBlocks()));
    }

    private Map<String, BasicBlockSet> mapToBlocks() {
        Map<String, BasicBlockSet> funcToSet = new HashMap<>();
        compUnit.functions().forEach((f, decl) -> {
            IRStmt body = decl.body();
            BasicBlockGenerator blockGenerator;
            if (body instanceof IRSeq) {
                blockGenerator = new BasicBlockGenerator(((IRSeq) body).stmts());
            } else {
                blockGenerator = new BasicBlockGenerator(List.of(body));
            }
            funcToSet.put(f, blockGenerator.getBlocks());
        });
        return funcToSet;
    }

    private Map<String, TraceSet> toTraces(
            Map<String, BasicBlockSet> blockMap) {
        Map<String, TraceSet> funcToTraces = new HashMap<>();
        blockMap.forEach((f, blockSet) -> {
            BlockTraceGenerator trace = new BlockTraceGenerator(generate);
            funcToTraces.put(f, trace.getTraces(blockSet));
        });
        return funcToTraces;
    }

    private IRCompUnit finishOptimization(Map<String, TraceSet> traceSet) {
        IRNodeFactory make = new IRNodeFactory_c(this.compUnit.location());
        IRCompUnit optimizedCompUnit = make.IRCompUnit(this.compUnit.name());
        traceSet.forEach((f, trace) -> {
            List<IRStmt> stmts = trace.flattenToStmt();
            IRFuncDecl func = make.IRFuncDecl(f, make.IRSeq(stmts));
            optimizedCompUnit.appendFunc(func);
        });
        return optimizedCompUnit;
    }
}
