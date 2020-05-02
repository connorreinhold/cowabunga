package cyr7.cfg.constructor;

import java.util.HashMap;
import java.util.Map;

import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.ir.nodes.IRCompUnit;

public class CFGConstructor {

    /**
     * Generates a CFG Tree for each function defined in the IRTree.
     */
    public static Map<String, CFGNode> constructCFG(IRCompUnit c) {

        Map<String, CFGNode> cfgCollection = new HashMap<>();

        c.functions().forEach((name, fn) -> {
            CFGNode fBody = fn.body().accept(new CFGConstructorVisitor());
            var cleaner = new CFGUnreachableNodeCleaner();
            fBody = cleaner.removeUnreachableNodes((CFGStartNode) fBody);
            cfgCollection.put(name, fBody);
         });
        return cfgCollection;
    }

}