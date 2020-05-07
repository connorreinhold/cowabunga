package cyr7.cfg.ir.flatten;

import java.util.HashMap;
import java.util.Map;

import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRSeq;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGFlattener {

    public static IRCompUnit flatten(Location location, String filename,
                Map<String, CFGStartNode> cfgMap) {
        Map<String, IRFuncDecl> functions = new HashMap<>();
        cfgMap.forEach((functionName, startNode) -> {
            IRFuncDecl function = new IRFuncDecl(startNode.location(),
                        functionName, flatten(startNode));
            functions.put(functionName, function);
        });
        return new IRCompUnit(location, filename, functions);
    }

    public static IRSeq flatten(CFGNode root) {
        assert root instanceof CFGStartNode;
        var flattener = new FlattenCFGVisitor();
        root.accept(flattener);
        return new IRSeq(new Location(-1, -1), flattener.getFunctionBody());
    }

}
