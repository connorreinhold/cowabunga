package cyr7.cfg.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
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


    /**
     * A commutative version of Pair, i.e. ordering of the values does
     * not matter.
     */
    private static class CommutativePair extends Pair<CFGNode, CFGNode> {

        private Class<?> classType;

        public CommutativePair(CFGNode p1, CFGNode p2) {
            super(p1, p2);
            this.classType = p1.getClass();
        }

        @Override
        public int hashCode() {
            return new Pair<>(this.part1, this.part2).hashCode()
                    ^ (new Pair<>(this.part2, this.part1).hashCode() + 23457);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof CommutativePair) {
                CommutativePair pair = (CommutativePair)o;
                CFGNodeEqualVisitor equalsPart1 = new CFGNodeEqualVisitor(this.part1);
                CFGNodeEqualVisitor equalsPart2 = new CFGNodeEqualVisitor(this.part2);
                return (pair.part1.accept(equalsPart1) && pair.part2.accept(equalsPart2))
                    || (pair.part1.accept(equalsPart2) && pair.part2.accept(equalsPart1));
            }
            return false;
        }

    }

    /**
     * Asserts the equality of a graph by checking that it contains every
     * asserted node and edges.
     * @param start The CFG being tested.
     * @param expectedNodes The out and in edges of each node in {@code nodes}
     *                      does not matter.
     * @param expectedEdges The order of the CFGNodes in the edge pair does
     *                      not matter;
     */
    public static boolean assertEqualGraphs(CFGNode start,
            Set<CFGNode> expectedNodes, Set<Pair<CFGNode, CFGNode>> expectedEdges) {
        Set<CFGNode> actualNodes = getAllNodes(start);
        Set<CommutativePair> actualEdges = getAllEdges(start);

        boolean sameEdges = expectedEdges.stream().map(pair ->
            new CommutativePair(pair.part1(), pair.part2()))
        .collect(Collectors.toSet()).equals(actualEdges);

        for (CFGNode node: expectedNodes) {
            Optional<CFGNode> check = containsCFG(node, actualNodes);
            if (check.isPresent()) {
                actualNodes.remove(check.get());
            } else {
                return false;
            }
        }
        return sameEdges && actualNodes.isEmpty();
    }

    /**
     * Checks if {@code node} is contained in {@code set} using an equals
     * predicated defined in an instance of {@code CFGNodeEqualVisitor}.
     *
     * <p> The contents of the nodes are equal. The incoming and outgoing
     * edges do not matter.
     *
     * @param node
     * @param set
     * @return If it is contained inside of {@code set}, then an {@code Optional} value
     * containing that node from {@code set} is returned. Otherwise,
     * {@code Optional.empty()} is returned.
     */
    private static Optional<CFGNode> containsCFG(CFGNode node, Set<CFGNode> set) {
        var equalVisitor = new CFGNodeEqualVisitor(node);
        for (CFGNode nodeOfSet: set) {
            if (nodeOfSet.accept(equalVisitor)) {
                return Optional.of(nodeOfSet);
            }
        }
        return Optional.empty();
    }


    /**
     *
     * Checks that two CFG nodes are equal.
     * <p>
     * The contents of the nodes are equal. The incoming and outgoing
     * edges do not matter.
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
     * Returns a set of all nodes in a CFG graph.
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
     * Returns a set of all edges in a CFG graph.
     */
    private static Set<CommutativePair> getAllEdges(CFGNode start) {
        Set<CFGNode> visited = new HashSet<>();
        Set<CommutativePair> edges = new HashSet<>();
        Queue<CFGNode> list = new LinkedList<>();
        list.add(start);
        while (!list.isEmpty()) {
            CFGNode node = list.remove();
            for (CFGNode outNode: node.out()) {
                edges.add(new CommutativePair(node, outNode));
            }
            if (!visited.contains(node)) {
                visited.add(node);
                list.addAll(node.out());
            }
        }
        return edges;
    }

}
