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
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class Ay339FlattenCFGVisitor extends AbstractCFGVisitor<Void> {

    /**
     * A wrapper class so that stmts can be compared via pointer addresses.
     * @author ayang
     *
     */
    class IRStmtWrapper {
        protected final IRStmt stmt;
        public IRStmtWrapper(IRStmt stmt) {
            this.stmt = stmt;
        }
        public Location location() {
            return this.stmt.location();
        }
    }

    /**
     * This is the list of statements corresponding to this control flow graph.
     */
    private final List<IRStmtWrapper> stmts;

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
    private final IdentityHashMap<CFGNode, IRStmtWrapper> cfgNodeToIRStmt;

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

    private CFGNode predecessor;

    private final IdGenerator generator;


    /**
     * Testing variable to ensure start node is only entered once.
     */
    private boolean hasEntered;

    public Ay339FlattenCFGVisitor() {
        this.visitedNodes = new HashSet<>();
        this.cfgNodeToLabels = new IdentityHashMap<>();
        this.cfgNodeToIRStmt = new IdentityHashMap<>();
        this.trueBranches = new LinkedList<>();
        this.stmts = new LinkedList<>();
        this.generator = new DefaultIdGenerator();
        this.hasEntered = false;
    }


    /**
     * Sets node {@code n} to be visited and the predecessor node for the next
     * node.
     * @param n
     */
    private void epilogueProcess(CFGNode n) {
        this.visitedNodes.add(n);
        this.predecessor = n;
    }

    private IRNodeFactory createMake(CFGNode n) {
        return new IRNodeFactory_c(n.location());
    }

    private IRNodeFactory createMake(IRStmtWrapper s) {
        return new IRNodeFactory_c(s.location());
    }

    private IRStmtWrapper wrapStmt(IRStmt s) {
        return new IRStmtWrapper(s);
    }

    private void insertJumpForNode(CFGNode from, String target) {
        final var make = this.createMake(from);
        final var fromStmt = this.cfgNodeToIRStmt.get(from);
        final int i = this.stmts.indexOf(fromStmt);
        this.stmts.add(i + 1, this.wrapStmt(make.IRJump(make.IRName(target))));
    }

    /**
     * Inserts a newly-created label before the IRStmt associated to node
     * {@code n}.
     * @param n
     */
    private void insertLabelForNode(CFGNode n) {
        IRStmtWrapper stmt = this.cfgNodeToIRStmt.get(n);
        final String label = generator.newLabel();
        final var make = this.createMake(stmt);
        final int i = this.stmts.indexOf(stmt);
        this.stmts.add(i, this.wrapStmt(make.IRLabel(label)));
        this.cfgNodeToLabels.put(n, label);
    }

    /**
     * Adds the statement {@code stmt} to the end of the list of statements.
     * <p>
     * Associates node {@code n} to this statement {@code stmt} in
     * {@code cfgNodeToIRStmt}.
     *
     * @param n
     * @param stmt
     */
    private void appendStmt(CFGNode n, IRStmt stmt) {
        final var wrappedStmt = this.wrapStmt(stmt);
        this.stmts.add(wrappedStmt);
        this.cfgNodeToIRStmt.put(n, wrappedStmt);
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

    /**
     * Three possibilities:
     * <ol>
     * <li> This node has never been visited.
     * <li> This node has been visited but there is no associated label to
     * jump to.
     * <li> This node has been visited and there is an associated label.
     * </ol>
     */
    @Override
    public Void visit(CFGReturnNode n) {
        final var make = this.createMake(n);
        if (this.visitedNodes.contains(n)) {
            if (this.cfgNodeToLabels.containsKey(n)) {
               final String target = this.cfgNodeToLabels.get(n);
               this.insertJumpForNode(this.predecessor, target);
            } else {
                this.insertLabelForNode(n);
            }
        } else {
            this.appendStmt(n, make.IRReturn());
        }
        this.epilogueProcess(n);
        return null;
    }

    /**
     * There are no labels or statements associated with the start node.
     */
    @Override
    public Void visit(CFGStartNode n) {
        if (hasEntered) {
            throw new UnsupportedOperationException(
                    "Cannot enter start node twice.");
        }
        this.epilogueProcess(n);

        final CFGNode next = n.out().get(0);
        next.accept(this);
        return null;
    }
}