package cyr7.cfg.ir.constructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.ir.block.BasicBlock;
import cyr7.ir.nodes.IRCompUnit;

public class CFGConstructor {

    /**
     * Generates a CFG Tree for each function defined in the IRTree.
     */
    public static Map<String, CFGStartNode> constructCFG(IRCompUnit c) {

        Map<String, CFGStartNode> cfgCollection = new HashMap<>();

        c.functions().forEach((name, fn) -> {
            CFGStartNode fBody = (CFGStartNode)fn.body()
                                    .accept(new CFGConstructorVisitor());
            var cleaner = new CFGUnreachableNodeCleaner();
            fBody = cleaner.removeUnreachableNodes(fBody);
            cfgCollection.put(name, fBody);
         });
        return cfgCollection;
    }


    public static Map<String, CFGStartNode>
            constructBlockCFG(Map<String, List<List<BasicBlock>>> traces) {

        Map<String, CFGStartNode> cfgCollection = new HashMap<>();

        traces.forEach((name, blocks) -> {
            CFGStartNode fBody = BlockCfgConstructor.construct(blocks);
            var cleaner = new CFGUnreachableNodeCleaner();
            fBody = cleaner.removeUnreachableNodes(fBody);
            cfgCollection.put(name, fBody);
        });
        return cfgCollection;
    }


}