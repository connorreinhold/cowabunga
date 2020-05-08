package cyr7.cfg.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.cfg.ir.flatten.CFGFlattener;
import cyr7.cfg.ir.nodes.CFGBlockNode;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRStmt;
import polyglot.util.Pair;

public class IrCfgTestUtil {

    public static void printIR(CFGStartNode start) {
        IRStmt result = CFGFlattener.flatten(start);
        System.out.println(result);
    }

    public static Set<CFGNode> nodeSet(CFGNode ...nodes) {
        var cloner = new CfgNodeClonerVisitor();
        Set<CFGNode> nodeSet = new HashSet<>();
        for (CFGNode n: nodes) {
            nodeSet.add(n.accept(cloner));
        }
        return nodeSet;
    }

    @SafeVarargs
    public static List<Pair<CFGNode, CFGNode>>
                edgeList(Pair<CFGNode, CFGNode> ... edges) {
        var cloner = new CfgNodeClonerVisitor();
        List<Pair<CFGNode, CFGNode>> edgeList = new ArrayList<>();
        for (Pair<CFGNode, CFGNode> e: edges) {
            edgeList.add(new Pair<>(e.part1().accept(cloner),
                                   e.part2().accept(cloner)));
        }
        return edgeList;
    }


    public static class CfgNodeClonerVisitor implements IrCFGVisitor<CFGNode> {

        private final CFGNode stubReference = new CFGStubNode();

        @Override
        public CFGNode visit(CFGCallNode n) {
            return new CFGCallNode(n.location(), n.call, stubReference);
        }

        @Override
        public CFGNode visit(CFGIfNode n) {
            return new CFGIfNode(n.location(), stubReference,
                                    stubReference, n.cond);
        }

        @Override
        public CFGNode visit(CFGVarAssignNode n) {
            return new CFGVarAssignNode(n.location(), n.variable,
                    n.value, stubReference);
        }

        @Override
        public CFGNode visit(CFGMemAssignNode n) {
            return new CFGMemAssignNode(n.location(), n.target,
                    n.value, stubReference);
        }

        @Override
        public CFGNode visit(CFGReturnNode n) {
            return new CFGReturnNode(n.location());
        }

        @Override
        public CFGNode visit(CFGStartNode n) {
            return new CFGStartNode(n.location(), stubReference);
        }

        @Override
        public CFGNode visit(CFGSelfLoopNode n) {
            return new CFGSelfLoopNode();
        }

        @Override
        public CFGNode visit(CFGBlockNode n) {
            return new CFGBlockNode(n.location(), n.block, stubReference);
        }

    }


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
            var equalsVisitor = new CFGNodeEqualVisitor(n);
            if (!actualNodes.stream().anyMatch(node -> node.accept(equalsVisitor)))
                sameNodes = false;
        }
        for (CFGNode n: actualNodes) {
            var equalsVisitor = new CFGNodeEqualVisitor(n);
            if (!expectedNodes.stream().anyMatch(node -> node.accept(equalsVisitor)))
                sameNodes = false;
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
        public Boolean visit(CFGBlockNode n) {
            if (nodeToCompare instanceof CFGBlockNode) {
                CFGBlockNode block = (CFGBlockNode)nodeToCompare;
                CFGNode nodeToCompareToTraverse = block.block;
                CFGNode incomingNodeToTraverse = n.block;

                while (!(nodeToCompareToTraverse instanceof CFGStubNode)
                        && !(incomingNodeToTraverse instanceof CFGStubNode)) {
                    if (incomingNodeToTraverse
                            .accept(new CFGNodeEqualVisitor(nodeToCompareToTraverse))) {
                        nodeToCompareToTraverse = nodeToCompareToTraverse.out().get(0);
                        incomingNodeToTraverse = incomingNodeToTraverse.out().get(0);
                    } else {
                        return false;
                    }
                }
                return nodeToCompareToTraverse instanceof CFGStubNode
                        && incomingNodeToTraverse instanceof CFGStubNode;
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

        @Override
        public Boolean visit(CFGSelfLoopNode n) {
            return nodeToCompare instanceof CFGSelfLoopNode;
        }
    }


    /**
     * Returns a list of all nodes in a CFG graph.
     */
    private static Set<CFGNode> getAllNodes(CFGNode start) {
        Set<CFGNode> nodes = new HashSet<>();
        Queue<CFGNode> list = new ArrayDeque<>();
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
        List<CFGPair> edges = new ArrayList<>();
        Queue<CFGNode> list = new ArrayDeque<>(getAllNodes(start));
        while (!list.isEmpty()) {
            CFGNode node = list.remove();
            for (CFGNode outNode: node.out()) {
                edges.add(new CFGPair(node, outNode));
            }
        }
        return edges;
    }
}
