package cyr7.cfg.ir.dfa;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.loops.DominatorAnalysis;
import cyr7.cfg.ir.dfa.loops.DominatorUtil;
import cyr7.cfg.ir.nodes.CFGBlockNode;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGNodeFactory;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRPhiFuncNode;

public class SSATransformer {

    public static CFGStartNode convert(CFGStartNode start) {
        // Create the dominance frontier.
        final var result = WorklistAnalysis.analyze(start, DominatorAnalysis.INSTANCE);
        final var domFrontier = DominatorUtil.generateMap(result.out());

        final var defsites = getDefsites(start);
        final var phiLocations = computePhiFuncLoc(defsites, domFrontier, start);

        final var phiFuncsInserted = insertPhiFunctions(phiLocations, start);

        return start;
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

        final var visitor = new PhiFunctionInsertVisitor(requiredPhiFuncMap);
        requiredPhiFuncMap.keySet().forEach(node -> {
            node.accept(visitor);
        });

        return start;
    }

    private static class PhiFunctionInsertVisitor implements IrCFGVisitor<CFGNode> {

        private final Map<CFGNode, Set<String>> requiredPhiFuncMap;

        public PhiFunctionInsertVisitor(
                Map<CFGNode, Set<String>> requiredPhiFuncMap) {
            this.requiredPhiFuncMap = requiredPhiFuncMap;
        }

        @Override
        public CFGNode visit(CFGCallNode n) {
            final CFGNodeFactory make = new CFGNodeFactory(n.location());
            final var paramSize = n.in().size();
            if (paramSize > 1 && requiredPhiFuncMap.containsKey(n)) {
                final var setOfVars = requiredPhiFuncMap.get(n);
                final var originalOutNode = n.outNode();
                originalOutNode.in().remove(n);
                n.replaceOutEdge(originalOutNode, new CFGStubNode());
                CFGNode base = n;
                for (String var: setOfVars) {
                    final var phi = new IRPhiFuncNode(n.location(), var, paramSize);
                    base = make.VarAssign(var, phi, base);
                }
                final var blockNode = new CFGBlockNode(n.location(), base, originalOutNode);
                final List<CFGNode> incoming = List.copyOf(n.in());
                for (CFGNode in: incoming) {
                    n.in().remove(in);
                    in.replaceOutEdge(n, blockNode);
                }
                return blockNode;
            } else {
                return n;
            }
        }

        @Override
        public CFGNode visit(CFGIfNode n) {
            final CFGNodeFactory make = new CFGNodeFactory(n.location());
            final var paramSize = n.in().size();
            if (paramSize > 1 && requiredPhiFuncMap.containsKey(n)) {
                final var setOfVars = requiredPhiFuncMap.get(n);
                final List<CFGNode> incoming = List.copyOf(n.in());

                if (setOfVars.size() == 1) {
                    for (String var: setOfVars) {
                        final var phi = new IRPhiFuncNode(n.location(), var, paramSize);
                        final var phiNode = make.VarAssign(var, phi, n);
                        for (CFGNode in: incoming) {
                            n.in().remove(in);
                            in.replaceOutEdge(n, phiNode);
                        }
                    }
                    return n;
                }

                CFGNode base = new CFGStubNode();
                for (String var: setOfVars) {
                    final var phi = new IRPhiFuncNode(n.location(), var, paramSize);
                    base = make.VarAssign(var, phi, base);
                }
                final var blockNode = new CFGBlockNode(n.location(), base, n);
                for (CFGNode in: incoming) {
                    n.in().remove(in);
                    in.replaceOutEdge(n, blockNode);
                }
                return blockNode;
            } else {
                return n;
            }
        }

        @Override
        public CFGNode visit(CFGVarAssignNode n) {
            final CFGNodeFactory make = new CFGNodeFactory(n.location());
            final var paramSize = n.in().size();
            if (paramSize > 1 && requiredPhiFuncMap.containsKey(n)) {
                final var setOfVars = requiredPhiFuncMap.get(n);
                final var originalOutNode = n.outNode();
                originalOutNode.in().remove(n);
                n.replaceOutEdge(originalOutNode, new CFGStubNode());
                CFGNode base = n;
                for (String var: setOfVars) {
                    final var phi = new IRPhiFuncNode(n.location(), var, paramSize);
                    base = make.VarAssign(var, phi, base);
                }
                final var blockNode = new CFGBlockNode(n.location(), base, originalOutNode);
                final List<CFGNode> incoming = List.copyOf(n.in());
                for (CFGNode in: incoming) {
                    n.in().remove(in);
                    in.replaceOutEdge(n, blockNode);
                }
                return blockNode;
            } else {
                return n;
            }
        }

        @Override
        public CFGNode visit(CFGMemAssignNode n) {
            final CFGNodeFactory make = new CFGNodeFactory(n.location());
            final var paramSize = n.in().size();
            if (paramSize > 1 && requiredPhiFuncMap.containsKey(n)) {
                final var setOfVars = requiredPhiFuncMap.get(n);
                final var originalOutNode = n.outNode();
                originalOutNode.in().remove(n);
                n.replaceOutEdge(originalOutNode, new CFGStubNode());
                CFGNode base = n;
                for (String var: setOfVars) {
                    final var phi = new IRPhiFuncNode(n.location(), var, paramSize);
                    base = make.VarAssign(var, phi, base);
                }
                final var blockNode = new CFGBlockNode(n.location(), base, originalOutNode);
                final List<CFGNode> incoming = List.copyOf(n.in());
                for (CFGNode in: incoming) {
                    n.in().remove(in);
                    in.replaceOutEdge(n, blockNode);
                }
                return blockNode;
            } else {
                return n;
            }
        }

        @Override
        public CFGNode visit(CFGReturnNode n) {
            return n;
        }

        @Override
        public CFGNode visit(CFGStartNode n) {
            final CFGNodeFactory make = new CFGNodeFactory(n.location());
            final var paramSize = n.in().size();
            if (paramSize > 1 && requiredPhiFuncMap.containsKey(n)) {
                final var setOfVars = requiredPhiFuncMap.get(n);
                final var originalOutNode = n.outNode();
                originalOutNode.in().remove(n);
                n.replaceOutEdge(originalOutNode, new CFGStubNode());
                CFGNode base = n;
                for (String var: setOfVars) {
                    final var phi = new IRPhiFuncNode(n.location(), var, paramSize);
                    base = make.VarAssign(var, phi, base);
                }
                final var blockNode = new CFGBlockNode(n.location(), base, originalOutNode);
                final List<CFGNode> incoming = List.copyOf(n.in());
                for (CFGNode in: incoming) {
                    n.in().remove(in);
                    in.replaceOutEdge(n, blockNode);
                }
                return blockNode;
            } else {
                return n;
            }
        }

        @Override
        public CFGNode visit(CFGSelfLoopNode n) {
            return n;
        }

        @Override
        public CFGNode visit(CFGBlockNode n) {
            final CFGNodeFactory make = new CFGNodeFactory(n.location());
            final var paramSize = n.in().size();
            if (paramSize > 1 && requiredPhiFuncMap.containsKey(n)) {
                final var setOfVars = requiredPhiFuncMap.get(n);
                final List<CFGNode> incoming = List.copyOf(n.in());

                if (setOfVars.size() == 1) {
                    for (String var: setOfVars) {
                        final var phi = new IRPhiFuncNode(n.location(), var, paramSize);
                        final var phiNode = make.VarAssign(var, phi, n);
                        for (CFGNode in: incoming) {
                            n.in().remove(in);
                            in.replaceOutEdge(n, phiNode);
                        }
                    }
                    return n;
                }
                CFGNode base = new CFGStubNode();
                for (String var: setOfVars) {
                    final var phi = new IRPhiFuncNode(n.location(), var, paramSize);
                    base = make.VarAssign(var, phi, base);
                }
                final var blockNode = new CFGBlockNode(n.location(), base, n);
                for (CFGNode in: incoming) {
                    n.in().remove(in);
                    in.replaceOutEdge(n, blockNode);
                }
                return blockNode;
            } else {
                return n;
            }
        }

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
