package cyr7.cfg.ir.constructor;

import java.util.HashMap;
import java.util.Map;

import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.ir.nodes.IRCompUnit;

public class CFGConstructor {

    /**
     * Generates a CFG Tree for each function defined in the IRTree.
     */
    public static Map<String, CFGStartNode> constructCFG(IRCompUnit c) {

        Map<String, CFGStartNode> cfgCollection = new HashMap<>();

        c.functions().forEach((name, fn) -> {
            CFGStartNode fBody = (CFGStartNode)fn.body().accept(new CFGConstructorVisitor());
            var cleaner = new CFGUnreachableNodeCleaner();
            fBody = cleaner.removeUnreachableNodes(fBody);
            cfgCollection.put(name, fBody);
         });
        return cfgCollection;
    }

}