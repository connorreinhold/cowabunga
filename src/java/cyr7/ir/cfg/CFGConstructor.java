package cyr7.ir.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyr7.cfg.nodes.CFGNode;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.visit.CFGConstructorVisitor;

public class CFGConstructor {

    /**
     * Generates a CFG Tree for each function defined in the IRTree.
     */
    public static Map<String, CFGNode> constructCFG(IRCompUnit c) {

        Map<String, CFGNode> cfgCollection = new HashMap<>();

        c.functions().forEach((name, fn) -> {
            cfgCollection.put(name, fn.body().accept(new CFGConstructorVisitor()).assertFirst());
         });
        return cfgCollection;
    }

}