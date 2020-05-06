package cyr7.cfg.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import polyglot.util.Pair;

public class IrCfgTestUtil {

    private IrCfgTestUtil() {}

    /**
     * A commutative version of Pair, i.e. ordering of the values does
     * not matter.
     */
    private static class CFGPair extends Pair<CFGNode, CFGNode> {

        public CFGPair(CFGNode p1, CFGNode p2) {
            super(p1, p2);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof CFGPair) {
                CFGPair pair = (CFGPair)o;
                CFGNodeEqualVisitor equalsPart1 = new CFGNodeEqualVisitor(this.part1);
                CFGNodeEqualVisitor equalsPart2 = new CFGNodeEqualVisitor(this.part2);
                return pair.part1.accept(equalsPart1) && pair.part2.accept(equalsPart2);
            }
            return false;
        }

    }

    /**
     * Asserts the equality of a graph by checking that it contains every
     * asserted node and edges.
     * @param start The CFG being tested.
     * @param expectedNodes
     * @param expectedEdges
     */
    public static boolean assertEqualGraphs(CFGNode start,
            Set<CFGNode> expectedNodes, List<Pair<CFGNode, CFGNode>> expectedEdges) {
        Set<CFGNode> actualNodes = getAllNodes(start);
        List<CFGPair> actualEdges = getAllEdges(start);

        var expectedEdgesMod = expectedEdges.stream().map(pair ->
                new CFGPair(pair.part1(), pair.part2())).collect(Collectors.toList());
        boolean sameEdges = actualEdges.size() == expectedEdges.size();
        for (CFGPair p: expectedEdgesMod) {
            if (!actualEdges.contains(p))
                sameEdges = false;
        }
        for (CFGPair p: actualEdges) {
            if (!expectedEdgesMod.contains(p))
                sameEdges = false;
        }

        boolean sameNodes = actualNodes.size() == expectedNodes.size();
        for (CFGNode n: expectedNodes) {
            if (!actualNodes.contains(n))
                sameEdges = false;
        }
        for (CFGNode n: actualNodes) {
            if (!expectedNodes.contains(n))
                sameEdges = false;
        }
        return sameEdges && sameNodes;
    }

    /**
     *
     * Checks that two CFG nodes are equal.
     *
     */
    private static class CFGNodeEqualVisitor implements IrCFGVisitor<Boolean> {

        private final CFGNode nodeToCompare;

        public CFGNodeEqualVisitor(CFGNode nodeToCompare) {
            this.nodeToCompare = nodeToCompare;
        }

        @Override
        public Boolean visit(CFGCallNode n) {
            if (nodeToCompare instanceof CFGCallNode) {
                CFGCallNode castedNode = (CFGCallNode)nodeToCompare;
                return castedNode.call.equals(n.call);
            }
            return false;
        }

        @Override
        public Boolean visit(CFGIfNode n) {
            if (nodeToCompare instanceof CFGIfNode) {
                CFGIfNode castedNode = (CFGIfNode)nodeToCompare;
                return castedNode.cond.equals(n.cond);
            }
            return false;
        }

        @Override
        public Boolean visit(CFGVarAssignNode n) {
            if (nodeToCompare instanceof CFGVarAssignNode) {
                CFGVarAssignNode castedNode = (CFGVarAssignNode)nodeToCompare;
                return castedNode.value.equals(n.value)
                    && castedNode.variable.equals(n.variable);
            }
            return false;
        }

        @Override
        public Boolean visit(CFGMemAssignNode n) {
            if (nodeToCompare instanceof CFGMemAssignNode) {
                CFGMemAssignNode castedNode = (CFGMemAssignNode)nodeToCompare;
                return castedNode.value.equals(n.value)
                    && castedNode.target.equals(n.target);
            }
            return false;
        }

        @Override
        public Boolean visit(CFGReturnNode n) {
            return nodeToCompare instanceof CFGReturnNode;
        }

        @Override
        public Boolean visit(CFGStartNode n) {
            return nodeToCompare instanceof CFGStartNode;
        }

    }


    /**
     * Returns a list of all nodes in a CFG graph.
     */
    private static Set<CFGNode> getAllNodes(CFGNode start) {
        Set<CFGNode> nodes = new HashSet<>();
        Queue<CFGNode> list = new LinkedList<>();
        list.add(start);
        while (!list.isEmpty()) {
            CFGNode node = list.remove();
            if (!nodes.contains(node)) {
                nodes.add(node);
                list.addAll(node.out());
            }
        }
        return nodes;
    }


    /**
     * Returns a list of all edges in a CFG graph.
     */
    private static List<CFGPair> getAllEdges(CFGNode start) {
        List<CFGPair> edges = new LinkedList<>();
        Queue<CFGNode> list = new LinkedList<>(getAllNodes(start));
        while (!list.isEmpty()) {
            CFGNode node = list.remove();
            for (CFGNode outNode: node.out()) {
                edges.add(new CFGPair(node, outNode));
            }
        }
        return edges;
    }
}
