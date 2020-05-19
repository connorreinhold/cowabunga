package cyr7.cfg.ir.ssa;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.cfg.ir.nodes.CFGBlockNode;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGPhiFunctionBlock;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.opt.IRTempReplacer;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRCallStmt;

public class SSATransformer {

    /**
     * Returns a CFG that uses phi-function blocks.
     * @param start
     * @return
     */
    public static CFGStartNode convert(CFGStartNode start) {
        // Create the dominance frontier.
        final var domTreeComp = new DominatorTreeComputer(start);
        final var domFrontier = domTreeComp.computeDFMap();
        final var childrenNodes = domTreeComp.getChildren();

        final var defsites = getDefsites(start);
        final var phiLocations = computePhiFuncLoc(defsites, domFrontier, start);

        final var phiFuncsInserted = insertPhiFunctions(phiLocations, start);

        return RenameVariable
                        .execute(phiFuncsInserted, childrenNodes, defsites);
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

            final var visitor = new Visitor(count, stack);
            nameVariables(count, stack, domTree, start, visitor);
            return start;
        }


        private static void nameVariables(
                final Map<String, Integer> count,
                final Map<String, Deque<Integer>> stack,
                final Map<CFGNode, Set<CFGNode>> domTree,
                final CFGNode node,
                final Visitor visitor) {

            List<String> definitions = new ArrayList<>();

            // First part of rename(n)
            if (node instanceof CFGPhiFunctionBlock) {
                // We'll write the case for phi functions here, so
                // that we don't need to add a node to the visitor file.
                // If needed, we can add it to the visitor and copy the
                // following into the visitor.
                // stack and count values are the same as the ones in the
                // visitor b/c of referencing.
                final var phi = (CFGPhiFunctionBlock)node;
                final var defs = phi.mappings.keySet();
                for (String def: defs) {
                    final int i = count.get(def) + 1;
                    definitions.add(def);
                    count.put(def, i);
                    stack.get(def).push(i);
                    List<String> args = phi.mappings.remove(def);
                    phi.mappings.put(def + "_" + i, args);
                }
            } else {
                definitions.addAll(node.accept(visitor));
                node.refreshDfaSets();
            }

            // for each successor y of node, check for phi functions
            for (CFGNode y: node.out()) {
                if (y instanceof CFGPhiFunctionBlock) {
                    final var phi = (CFGPhiFunctionBlock)y;
                    final int j = phi.in().indexOf(node);
                    final var defs = phi.mappings.keySet();
                    for (String def: defs) {
                        final var args = phi.mappings.get(def);
                        final var arg = args.get(j);
                        final int i = stack.get(arg).peek();
                        args.set(j, arg + "_" + i);
                    }
                }
            }
            for (CFGNode out: domTree.get(node)) {
                nameVariables(count, stack, domTree, out, visitor);
            }
            for (String def: definitions) {
                stack.get(def).pop();
            }
        }

        /**
         * This visitor will rename variables based on the algorithm described
         * by Appel.
         * <p>
         * This use to run the procedure for the first loop of the
         * Rename(n) function in Algorithm 19.7.
         *
         */
        private static class Visitor implements IrCFGVisitor<List<String>> {

            private final Map<String, Integer> count;
            private final Map<String, Deque<Integer>> stack;

            public Visitor(final Map<String, Integer> count,
                    final Map<String, Deque<Integer>> stack) {
                this.count = count;
                this.stack = stack;
            }

            @Override
            public List<String> visit(CFGCallNode n) {
                Map<String, String> tempReplaceMapping = new HashMap<>();
                for (String use: n.uses()) {
                    final int i = stack.get(use).peek();
                    tempReplaceMapping.put(use, use + "_" + i);
                }
                final var newArgs = n.call.args()
                                    .stream().map(arg -> IRTempReplacer
                                    .replace(arg, tempReplaceMapping))
                                    .collect(Collectors.toList());

                List<String> originalCollectors = n.call.collectors();

                List<String> collectors = new ArrayList<>();
                for (String def: n.call.collectors()) {
                    final int i = count.get(def) + 1;
                    count.put(def, i);
                    stack.get(def).push(i);
                    collectors.add(def + "_" + i);
                }
                n.call = new IRCallStmt(n.call.location(), collectors,
                                        n.call.target(), newArgs);
                return originalCollectors;
            }

            @Override
            public List<String> visit(CFGIfNode n) {
                Map<String, String> tempReplaceMapping = new HashMap<>();
                for (String use: n.uses()) {
                    final int i = stack.get(use).peek();
                    tempReplaceMapping.put(use, use + "_" + i);
                }
                n.cond = IRTempReplacer.replace(n.cond, tempReplaceMapping);
                return Collections.emptyList();
            }

            @Override
            public List<String> visit(CFGVarAssignNode n) {
                Map<String, String> tempReplaceMapping = new HashMap<>();
                for (String use: n.uses()) {
                    final int i = stack.get(use).peek();
                    tempReplaceMapping.put(use, use + "_" + i);
                }
                n.value = IRTempReplacer.replace(n.value, tempReplaceMapping);

                final int i = count.get(n.variable) + 1;
                final String originalVar = n.variable;
                count.put(n.variable, i);
                stack.get(n.variable).push(i);
                n.variable = n.variable + "_" + i;

                return List.of(originalVar);

            }

            @Override
            public List<String> visit(CFGMemAssignNode n) {
                Map<String, String> tempReplaceMapping = new HashMap<>();
                for (String use: n.uses()) {
                    final int i = stack.get(use).peek();
                    tempReplaceMapping.put(use, use + "_" + i);
                }

                n.target = IRTempReplacer.replace(n.target, tempReplaceMapping);
                n.value = IRTempReplacer.replace(n.value, tempReplaceMapping);
                return Collections.emptyList();
            }

            @Override
            public List<String> visit(CFGReturnNode n) {
                return Collections.emptyList();
            }

            @Override
            public List<String> visit(CFGStartNode n) {
                return Collections.emptyList();
            }

            @Override
            public List<String> visit(CFGSelfLoopNode n) {
                return Collections.emptyList();
            }

            @Override
            public List<String> visit(CFGBlockNode n) {
                List<String> definitions = new ArrayList<>();
                var topNode = n.block;
                while (!(topNode instanceof CFGStubNode)) {
                    definitions.addAll(topNode.accept(this));
                    topNode = topNode.out().get(0);
                }
                return definitions;
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
