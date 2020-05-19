package cyr7.cfg.ir.ssa;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import cyr7.cfg.ir.nodes.CFGBlockNode;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGPhiFunctionBlock;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.visitor.IrCFGVisitor;

public class SSATransformer {

    public static CFGStartNode convert(CFGStartNode start) {
        // Create the dominance frontier.
        final var domTreeComp = new DominatorTreeComputer(start);
        final var domFrontier = domTreeComp.computeDFMap();
        final var childrenNodes = domTreeComp.getChildren();

        final var defsites = getDefsites(start);
        final var phiLocations = computePhiFuncLoc(defsites, domFrontier, start);

        final var phiFuncsInserted = insertPhiFunctions(phiLocations, start);

        final var varsNamed = RenameVariable
                        .execute(phiFuncsInserted, childrenNodes, defsites);

        return start;
    }

    private static class RenameVariable {

        private RenameVariable() {}

        public static CFGStartNode execute(final CFGStartNode start,
                Map<CFGNode, Set<CFGNode>> domTree,
                Map<String, Set<CFGNode>> defsites) {

            // Initialization stage
            final Map<String, Integer> count = new HashMap<>();
            final Map<String, Deque<Integer>> stack = new HashMap<>();
            defsites.keySet().forEach(a -> {
                count.put(a, 0);
                stack.put(a, new ArrayDeque<>());
                stack.get(a).push(0);
            });
            return nameVariables(count, stack, domTree, start);
        }


        private static CFGStartNode nameVariables(
                final Map<String, Integer> count,
                final Map<String, Deque<Integer>> stack,
                Map<CFGNode, Set<CFGNode>> domTree,
                final CFGStartNode start) {

            final Set<CFGNode> visited = new HashSet<>();
            final Queue<CFGNode> worklist = new ArrayDeque<>();
            final var visitor = new Visitor(count, stack);
            worklist.add(start);
            while (!worklist.isEmpty()) {
                final var node = worklist.remove();
                if (visited.contains(node)) continue;

                node.accept(visitor);  // First part of rename(n)

                visited.add(node);
                for (CFGNode out: domTree.get(node)) {
                    if (!visited.contains(out)) {
                        worklist.add(start);
                    }
                }
            }
            return start;
        }

        /**
         * This visitor will rename variables based on the algorithm described
         * by Appel.
         * <p>
         * This use to run the procedure for the first loop of the
         * Rename(n) function in Algorithm 19.7.
         *
         */
        private static class Visitor implements IrCFGVisitor<CFGNode> {

            private final Map<String, Integer> count;
            private final Map<String, Deque<Integer>> stack;

            public Visitor(final Map<String, Integer> count,
                    final Map<String, Deque<Integer>> stack) {
                this.count = count;
                this.stack = stack;
            }

            @Override
            public CFGNode visit(CFGCallNode n) {
                return null;
            }

            @Override
            public CFGNode visit(CFGIfNode n) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public CFGNode visit(CFGVarAssignNode n) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public CFGNode visit(CFGMemAssignNode n) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public CFGNode visit(CFGReturnNode n) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public CFGNode visit(CFGStartNode n) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public CFGNode visit(CFGSelfLoopNode n) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public CFGNode visit(CFGBlockNode n) {
                // TODO Auto-generated method stub
                return null;
            }

        }

    }


    private static CFGStartNode insertPhiFunctions(
            final Map<String, Set<CFGNode>> phiLocations,
            final CFGStartNode start) {

        // Invert the phiLocation mapping
        final Map<CFGNode, Set<String>> requiredPhiFuncMap = new HashMap<>();
        phiLocations.forEach((var, nodeSet) -> {
            nodeSet.forEach(node -> {
                if (requiredPhiFuncMap.containsKey(node)) {
                    requiredPhiFuncMap.get(node).add(var);
                } else {
                    requiredPhiFuncMap.put(node, new HashSet<>());
                    requiredPhiFuncMap.get(node).add(var);
                }
            });
        });

        requiredPhiFuncMap.keySet().forEach(n -> {
            if (requiredPhiFuncMap.containsKey(n)) {
                final var setOfVars = requiredPhiFuncMap.get(n);
                final List<CFGNode> incoming = List.copyOf(n.in());
                final var paramSize = incoming.size();

                n.in().clear();
                final var phiBlock =
                        new CFGPhiFunctionBlock(n.location(), n, paramSize, setOfVars);
                for (CFGNode in: incoming) {
                    in.replaceOutEdge(n, phiBlock);
                }
            }
        });

        return start;
    }


    /**
     * Returns a map of defined variables to the set of nodes that those
     * variables are defined in.
     *
     * <p>
     * See page 407 (Algorithm 19.6) of Appel for algorithm.
     *
     * @param start
     * @return
     */
    private static Map<String, Set<CFGNode>> getDefsites(CFGStartNode start) {
        Set<CFGNode> visited = new HashSet<>();
        Deque<CFGNode> worklist = new ArrayDeque<>();
        Map<String, Set<CFGNode>> defsites = new HashMap<>();
        worklist.add(start);

        while (!worklist.isEmpty()) {
            final var node = worklist.remove();
            node.defs().forEach(def -> {
                if (defsites.containsKey(def)) {
                    defsites.get(def).add(node);
                } else {
                    defsites.put(def, new HashSet<>());
                    defsites.get(def).add(node);
                }
            });
            visited.add(node);
            for (CFGNode out: node.out()) {
                if (!visited.contains(out)) {
                    worklist.add(out);
                }
            }
        }
        return defsites;
    }


    /**
     * Computes the set of nodes a variable needs to have a phi-function.
     * <p>
     * See part 2 of algorithm 19.6 by Appel.
     * @param defsites Variable v -> Set of CFGNodes where v is defined.
     * @param domFrontier CFGNode n -> Set of CFGNodes that dominate n.
     * @param start The start node a function.
     * @return
     */
    private static Map<String, Set<CFGNode>> computePhiFuncLoc(
            final Map<String, Set<CFGNode>> defsites,
            final Map<CFGNode, Set<CFGNode>> domFrontier,
            final CFGStartNode start) {
        final Set<String> variables = Set.copyOf(defsites.keySet());
        final Deque<CFGNode> worklist = new ArrayDeque<>();
        final Map<String, Set<CFGNode>> nodesRequiringPhiForVar = new HashMap<>();
        for (String a: variables) {
            worklist.addAll(defsites.get(a));
            nodesRequiringPhiForVar.put(a, new HashSet<>());
            while (!worklist.isEmpty()) {
                final var node = worklist.remove();
                final var nodesInFrontier = domFrontier.get(node);
                for (CFGNode y: nodesInFrontier) {
                    if (!nodesRequiringPhiForVar.get(a).contains(y)) {
                        nodesRequiringPhiForVar.get(a).add(y);
                        if (!y.defs().contains(a)) {
                            worklist.add(y);
                        }
                    }
                }
            }
        }
        return nodesRequiringPhiForVar;
    }


}
