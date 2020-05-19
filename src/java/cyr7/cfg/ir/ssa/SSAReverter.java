package cyr7.cfg.ir.ssa;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import cyr7.cfg.ir.nodes.CFGBlockNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGPhiFunctionBlock;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.ir.nodes.IRTemp;

public class SSAReverter {

    private SSAReverter() {}

    /**
     * Transforms phi-functions into move statements that occur in the
     * incoming nodes.
     * @param start
     * @return
     */
    public static CFGStartNode revert(CFGStartNode start) {
        final Set<CFGNode> visited = new HashSet<>();
        final Queue<CFGNode> worklist = new ArrayDeque<>();

        worklist.add(start);

        while (!worklist.isEmpty()) {
            final var node = worklist.remove();

            if (visited.contains(node)) continue;

            if (node instanceof CFGPhiFunctionBlock) {
                // Remove phi function and append to previous nodes.
                final var phi = (CFGPhiFunctionBlock)node;

                // Remove incoming edges coming from phi.
                for (CFGNode out: node.out()) {
                    out.in().remove(phi);
                }

                List<Set<CFGVarAssignNode>> newNodeSets = new ArrayList<>();
                for (int i = 0; i < phi.in().size(); i++) {
                    newNodeSets.add(new HashSet<>());
                }

                var stubNode = new CFGStubNode();
                phi.mappings.forEach((var, args) -> {
                    for (int j = 0; j < args.size(); j++) {
                        String a = args.get(j);
                        newNodeSets.get(j).add(new CFGVarAssignNode(phi.location(),
                                             var, new IRTemp(phi.location(), a),
                                             stubNode));
                    }
                });
                List<CFGNode> incoming = new ArrayList<>(phi.in());
                phi.in().clear();
                final int numOfIncoming = incoming.size();
                for (int j = 0; j < numOfIncoming; j++) {
                    final var setOfMoves = newNodeSets.get(j);
                    CFGNode base = new CFGStubNode();
                    for (CFGVarAssignNode m: setOfMoves) {
                        m.replaceOutEdge(stubNode, base);
                        base = m;
                    }
                    final var previousNode = incoming.get(j);

                    // Remove incoming edges to phi.
                    previousNode.replaceOutEdge(phi,
                            new CFGBlockNode(phi.location(),
                                             base,
                                             phi.outNode()));
                }
            }

            visited.add(node);

            for (CFGNode out: node.out()) {
                if (!visited.contains(out)) {
                    worklist.add(out);
                }
            }

        }

        return start;
    }

}
