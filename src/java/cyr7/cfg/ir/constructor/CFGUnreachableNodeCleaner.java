package cyr7.cfg.ir.constructor;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;


public class CFGUnreachableNodeCleaner {

    /**
     * Contains a set of reachable nodes. May be redundant with the reachable
     * nodes set.
     */
    private final Set<CFGNode> visitedNodes;

    /**
     * For optimization, if a reachable node is encountered when trying to find
     * a path from the initial node to the start node, then the initial node
     * must be reachable.
     */
    private final Set<CFGNode> reachableNodes;

    /**
     * List of nodes to check if they are reachable. Outgoing nodes are
     * prioritized over incoming nodes.
     */
    private final Deque<CFGNode> nextNodesToCheck;

    public CFGUnreachableNodeCleaner() {
        this.visitedNodes = new HashSet<>();
        this.reachableNodes = new HashSet<>();
        this.nextNodesToCheck = new LinkedList<>();
    }

    /**
     * Process that adds node {@code n} to the set of visited nodes, the set
     * of reachable nodes, and adds any non-visited nodes that neighbors
     * {@code n} to the set of next nodes to visit.
     *
     * @param n
     */
    private void epilogueProcess(CFGNode n) {
        this.visitedNodes.add(n);
        for (CFGNode outgoing: n.out()) {
            if (!this.visitedNodes.contains(outgoing)) {
                this.nextNodesToCheck.addFirst(outgoing);
            }
        }
        for (CFGNode incoming: n.in()) {
            if (!this.visitedNodes.contains(incoming)) {
                this.nextNodesToCheck.addLast(incoming);
            }
        }
    }

    /**
     * Removes any references to node {@code n} in the {@code out} nodes.
     * @param n
     */
    private void removeNodeFromGraph(CFGNode n) {
        for (CFGNode out: n.out()) {
            out.in().remove(n);
        }
    }

    /**
     * Performs DFS to try to reach a reachable node using only incoming edges.
     * Returns {@code true} if the DFS never encounters reachable node.
     * Otherwise {@code false}.
     */
    private boolean isNotReachable(CFGNode n) {
        final Deque<CFGNode> stack = new LinkedList<>();
        final Set<CFGNode> dfsVisited = new HashSet<>();
        stack.push(n);
        while (!stack.isEmpty()) {
            CFGNode next = stack.pop();
            if (this.reachableNodes.contains(next)) {
                return false;
            } else {
                dfsVisited.add(next);
                for (CFGNode incoming: n.in()) {
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
    public CFGStartNode removeUnreachableNodes(CFGStartNode n) {
        this.epilogueProcess(n);
        while (!this.nextNodesToCheck.isEmpty()) {
            CFGNode next = this.nextNodesToCheck.pop();
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
