package cyr7.cfg.flatten;

import java.util.List;

import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.ir.nodes.IRStmt;

public class CFGFlattener {

    public static List<IRStmt> flatten(CFGNode root) {
        assert root instanceof CFGStartNode;

        var flattener = new Ay339FlattenCFGVisitor();
        root.accept(flattener);
        return flattener.getFunctionBody();
    }

}
