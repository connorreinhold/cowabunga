package cyr7.ir.cfg;

import java.util.List;

import cyr7.cfg.nodes.CFGNode;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRStmt;

public class CFGConstructor {

    /**
     * Generates a CFG Tree for each function defined in the IRTree.
     */
    public static List<CFGNode> constructCFG(IRCompUnit c) {

        c.functions().forEach((s, fn) -> {
             IRStmt body = fn.body();
         });

    }

}