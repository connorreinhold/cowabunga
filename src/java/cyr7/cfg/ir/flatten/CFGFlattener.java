package cyr7.cfg.ir.flatten;

import java.util.HashMap;
import java.util.Map;

import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRSeq;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGFlattener {

    private CFGFlattener() {}

    public static IRCompUnit flatten(Map<String, CFGStartNode> cfgMap,
                IRCompUnit compUnit) {
        Map<String, IRFuncDecl> functions = new HashMap<>();
        IdGenerator generator = new DefaultIdGenerator();
        cfgMap.forEach((functionName, startNode) -> {
            IRSeq flattened = flatten(startNode, generator);
            IRFuncDecl function = new IRFuncDecl(startNode.location(),
                        functionName, flattened, compUnit.getFunction(functionName).type());
            functions.put(functionName, function);
        });
        return new IRCompUnit(compUnit.location(), compUnit.name(), functions);
    }

    public static IRSeq flatten(CFGStartNode root, IdGenerator generator) {
        var flattener = new FlattenCFGVisitor(generator);
        root.accept(flattener);
        return new IRSeq(root.location(), flattener.getFunctionBody());
    }

    public static IRSeq flatten(CFGNode root) {
        assert root instanceof CFGStartNode;
        var flattener = new FlattenCFGVisitor();
        root.accept(flattener);
        return new IRSeq(new Location(-1, -1), flattener.getFunctionBody());
    }

}
