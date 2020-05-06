package cyr7.cfg.ir;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.opt.DeadCodeElimOptimization;
import cyr7.cfg.util.IrCfgTestUtil;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRTemp;
import java_cup.runtime.ComplexSymbolFactory.Location;
import polyglot.util.Pair;

class TestDeadCodeRemover {

    @Test
    void test() {
        final Location loc = new Location(-1, -1);
        CFGNode returnNode = new CFGReturnNode(loc);
        CFGNode deadAssign = new CFGVarAssignNode(loc, "x",
                                    new IRTemp(loc, "y"), returnNode);
        CFGNode firstAssign = new CFGVarAssignNode(loc, "y",
                                    new IRConst(loc, 15), deadAssign);
        CFGNode root = new CFGStartNode(loc, firstAssign);

        CFGStartNode start = new DeadCodeElimOptimization().optimize(root);

        Set<CFGNode> expectedNodes = Set.of(root, firstAssign, returnNode);
        Set<Pair<CFGNode, CFGNode>> expectedEdges = Set.of(
                new Pair<>(root, firstAssign),
                new Pair<>(firstAssign, returnNode));

        assertTrue(IrCfgTestUtil.assertEqualGraphs(
                            start, expectedNodes, expectedEdges));

    }

}
