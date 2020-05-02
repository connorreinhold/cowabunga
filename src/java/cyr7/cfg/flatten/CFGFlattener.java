package cyr7.cfg.flatten;

import cyr7.cfg.nodes.ir.CFGNode;
import cyr7.cfg.nodes.ir.CFGStartNode;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGFlattener {

    public static IRStmt flatten(CFGNode root) {
        assert root instanceof CFGStartNode;

        var flattener = new FlattenCFGVisitor();
        root.accept(flattener);
        return new IRSeq(new Location(-1, -1), flattener.getFunctionBody());
    }

}
