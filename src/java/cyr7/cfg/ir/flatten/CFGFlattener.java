package cyr7.cfg.ir.flatten;

import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.ir.nodes.IRSeq;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGFlattener {

    public static IRSeq flatten(CFGNode root) {
        assert root instanceof CFGStartNode;
        var flattener = new FlattenCFGVisitor();
        root.accept(flattener);
        return new IRSeq(new Location(-1, -1), flattener.getFunctionBody());
    }

}
