package cyr7.cfg.asm.constructor;

import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class AsmCFGUnreachableNodeCleaner {

    /**
     * Contains a set of reachable nodes. May be redundant with the reachable
     * nodes set.
     */
    private final Set<AsmCFGNode> visitedNodes;

    /**
     * For optimization, if a reachable node is encountered when trying to find
     * a path from the initial node to the start node, then the initial node
     * must be reachable.
     */
    private final Set<AsmCFGNode> reachableNodes;

    /**
     * List of nodes to check if they are reachable. Outgoing nodes are
     * prioritized over incoming nodes.
     */
    private final Deque<AsmCFGNode> nextNodesToCheck;

    public AsmCFGUnreachableNodeCleaner() {
        this.visitedNodes = new HashSet<>();
        this.reachableNodes = new HashSet<>();
        this.nextNodesToCheck = new ArrayDeque<>();
    }

    /**
     * Process that adds node {@code n} to the set of visited nodes, the set
     * of reachable nodes, and adds any non-visited nodes that neighbors
     * {@code n} to the set of next nodes to visit.
     *
     * @param n
     */
    private void epilogueProcess(AsmCFGNode n) {
        this.reachableNodes.add(n);
        this.visitedNodes.add(n);
        for (AsmCFGNode outgoing: n.outNodes()) {
            if (!this.visitedNodes.contains(outgoing)) {
                this.nextNodesToCheck.addFirst(outgoing);
            }
        }
        for (AsmCFGNode incoming: n.inNodes()) {
            if (!this.visitedNodes.contains(incoming)) {
                this.nextNodesToCheck.addLast(incoming);
            }
        }
    }

    /**
     * Removes any references to node {@code n} in the {@code out} nodes.
     * @param n
     */
    private void removeNodeFromGraph(AsmCFGNode n) {
        for (AsmCFGNode out: n.outNodes()) {
            out.inNodes().remove(n);
        }
    }

    /**
     * Performs DFS to try to reach a reachable node using only incoming edges.
     * Returns {@code true} if the DFS never encounters reachable node.
     * Otherwise {@code false}.
     */
    private boolean isNotReachable(AsmCFGNode n) {
        final Deque<AsmCFGNode> stack = new ArrayDeque<>();
        final Set<AsmCFGNode> dfsVisited = new HashSet<>();
        stack.push(n);
        while (!stack.isEmpty()) {
            AsmCFGNode next = stack.pop();
            if (this.reachableNodes.contains(next)) {
                return false;
            } else {
                dfsVisited.add(next);
                for (AsmCFGNode incoming: n.inNodes()) {
                    if (!dfsVisited.contains(incoming))
                        stack.add(incoming);
                }
            }
        }
        return true;
    }

    /**
     * Removes unreachable nodes in the graph where the start node is the root.
     * @param n The start node.
     * @return The same start node {@code n}.
     */
    public AsmCFGStartNode removeUnreachableNodes(AsmCFGStartNode n) {
        this.epilogueProcess(n);
        while (!this.nextNodesToCheck.isEmpty()) {
            AsmCFGNode next = this.nextNodesToCheck.pop();
            if (this.isNotReachable(next)) {
                this.visitedNodes.add(next);
                this.removeNodeFromGraph(next);
            } else {
                this.epilogueProcess(next);
            }
        }
        return n;
    }

}
