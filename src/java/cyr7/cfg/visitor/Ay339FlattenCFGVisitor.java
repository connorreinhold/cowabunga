package cyr7.cfg.visitor;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;
import cyr7.ir.nodes.IRStmt;

public class Ay339FlattenCFGVisitor extends AbstractCFGVisitor<Void> {

    /**
     * This is the list of statements corresponding to this control flow graph.
     */
    private final List<IRStmt> stmts;

    /**
     * This is the set of nodes already visited by the visitor class. Each visit
     * method should add the current node to this set.
     */
    private final HashSet<CFGNode> visitedNodes;

    /**
     * Mapping from a CFGNode to a IRLabel, if such a label has been created at
     * this particular program point. When an IRLabel is inserted into the list
     * of statements {@code stmts}, the pair of CFGNode and that label name
     * should be added to this map.
     */
    private final IdentityHashMap<CFGNode, String> cfgNodeToLabels;

    /**
     * Mapping from a CFGNode to an IRStmt. If a CFGNode generates an IRNode in
     * a visit method, then the pairing should be added to this mapping.
     */
    private final IdentityHashMap<CFGNode, IRStmt> cfgNodeToIRStmt;

    /**
     * Queue of untransformed true branches, created from CFGIfNodes. When
     * visiting a CFGIfNode, add the true branch to this queue, and continue
     * propogating/transforming via the false branch.
     * <p>
     * The CFGNode sub-graphs in this collection are then transformed via the
     * same strategy. The particular collection implementation used here does
     * not matter; the order in which these CFGNode sub-graphs are transformed
     * is not important.
     */
    private final Queue<CFGNode> trueBranches;

    public Ay339FlattenCFGVisitor() {
        this.visitedNodes = new HashSet<>();
        this.cfgNodeToLabels = new IdentityHashMap<>();
        this.cfgNodeToIRStmt = new IdentityHashMap<>();
        this.trueBranches = new LinkedList<>();
        this.stmts = new LinkedList<>();
    }

    @Override
    public Void visit(CFGCallNode n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(CFGIfNode n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(CFGVarAssignNode n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(CFGMemAssignNode n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(CFGReturnNode n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void visit(CFGStartNode n) {
        // TODO Auto-generated method stub
        return null;
    }
}