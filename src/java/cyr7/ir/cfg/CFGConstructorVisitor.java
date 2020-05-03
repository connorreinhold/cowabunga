package cyr7.ir.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import cyr7.cfg.nodes.ir.CFGCallNode;
import cyr7.cfg.nodes.ir.CFGIfNode;
import cyr7.cfg.nodes.ir.CFGMemAssignNode;
import cyr7.cfg.nodes.ir.CFGNode;
import cyr7.cfg.nodes.ir.CFGReturnNode;
import cyr7.cfg.nodes.ir.CFGStartNode;
import cyr7.cfg.nodes.ir.CFGStubNode;
import cyr7.cfg.nodes.ir.CFGVarAssignNode;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRTemp;
import cyr7.visitor.MyIRVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;
import polyglot.util.Pair;

public class CFGConstructorVisitor implements MyIRVisitor<CFGNode> {

    private final Map<String, CFGNode> labelToCFG;
    private final Queue<Pair<CFGStubNode, String>> jumpTargetFromCFG;
    private final CFGNode absoluteLastReturn = new CFGReturnNode(
        new Location(Integer.MAX_VALUE, Integer.MAX_VALUE));
    private CFGNode successor;

    /**
     * This boolean is for testing purposes, enforcing that IRSeq is only found
     * at the top-level of the IRTree.
     */
    private boolean hasEnteredIRSeq;

    protected CFGConstructorVisitor() {
        this.labelToCFG = new HashMap<>();
        this.jumpTargetFromCFG = new LinkedList<>();
        this.hasEnteredIRSeq = false;
        this.successor = absoluteLastReturn;
    }

    private CFGStubNode createStubNode() {
        return new CFGStubNode();
    }

    @Override
    public CFGNode visit(IRSeq n) {
        if (this.hasEnteredIRSeq) {
            throw new UnsupportedOperationException(
                    "Cannot enter the IRSeq visitor twice");
        } else {
            this.hasEnteredIRSeq = true;
        }

        ArrayList<IRStmt> stmts = new ArrayList<>(n.stmts());
        for (int i = stmts.size() - 1; i >= 0; i--) {
            var stmt = stmts.get(i);
            successor = stmt.accept(this);
        }
        successor = new CFGStartNode(n.location(), successor);

        while (!this.jumpTargetFromCFG.isEmpty()) {
            var nextPair = this.jumpTargetFromCFG.poll();
            CFGStubNode stub = nextPair.part1();
            String target = nextPair.part2();

            if (this.labelToCFG.containsKey(target)) {
                CFGNode targetNode = this.labelToCFG.get(target);
                for (CFGNode incoming: stub.in()) {
                    // Target node may be itself, which
                    // indicates an empty loop coming from the parent node.
                    if (targetNode == stub) {
                        incoming.convertFromStub(stub, incoming);
                    } else {
                        incoming.convertFromStub(stub, targetNode);
                    }
                }

            } else {
                throw new UnsupportedOperationException(
                        "Target label was never found in the program.");
            }
        }
        return successor;
    }


    @Override
    public CFGNode visit(IRCallStmt n) {
        return new CFGCallNode(n.location(), n, successor);
    }


    @Override
    public CFGNode visit(IRCJump n) {
        assert !n.hasFalseLabel();  // IR should be lowered, meaning false
                                    // branches are fall-throughs.
        String trueBranch = n.trueLabel();
        if (this.labelToCFG.containsKey(trueBranch)) {
            return new CFGIfNode(n.location(),
                    this.labelToCFG.get(trueBranch),
                    successor, n.cond());
        } else {
            // Create stub node, and connect target to the stub node.
            CFGStubNode stub = this.createStubNode();
            CFGIfNode ifNode = new CFGIfNode(n.location(),
                    stub, successor, n.cond());
            this.jumpTargetFromCFG.add(new Pair<>(stub, trueBranch));
            return ifNode;
        }
    }


    @Override
    public CFGNode visit(IRJump n) {
        if (n.target() instanceof IRName) {
            String target = ((IRName) n.target()).name();
            if (this.labelToCFG.containsKey(target)) {
                // Make successor the target node.
                return this.labelToCFG.get(target);
            } else {
                // Create a stub node for later computation
                CFGStubNode stub = this.createStubNode();
                this.jumpTargetFromCFG.add(new Pair<>(stub, target));
                return stub;
            }
        } else {
            throw new UnsupportedOperationException(
                    "IRJump contains a non-name target.");
        }
    }


    @Override
    public CFGNode visit(IRLabel n) {
        this.labelToCFG.put(n.name(), successor);
        return successor;
    }


    @Override
    public CFGNode visit(IRMove n) {
        if (n.target() instanceof IRTemp) {
            IRTemp temp = (IRTemp) n.target();
            return new CFGVarAssignNode(n.location(),
                        temp.name(), n.source(), successor);
        } else {
            return new CFGMemAssignNode(n.location(),
                        n.target(), n.source(), successor);
        }
    }


    @Override
    public CFGNode visit(IRReturn n) {
        return new CFGReturnNode(n.location());
    }




    @Override
    public CFGNode visit(IRCompUnit n) {
        throw new UnsupportedOperationException(
                "Cannot use IRCompUnit in this visitor.");
    }

    @Override
    public CFGNode visit(IRExp n) {
        throw new UnsupportedOperationException("Cannot use IRExp in LIR.");
    }

    @Override
    public CFGNode visit(IRFuncDecl n) {
        throw new UnsupportedOperationException(
                "Cannot use IRFuncDecl in this visitor.");
    }

    @Override
    public CFGNode visit(IRBinOp n) {
        throw new UnsupportedOperationException(
                "Cannot use IR expressions in this visitor.");
    }

    @Override
    public CFGNode visit(IRCall n) {
        throw new UnsupportedOperationException("Cannot use IRCall in LIR.");
    }

    @Override
    public CFGNode visit(IRConst n) {
        throw new UnsupportedOperationException(
                "Cannot use IR expressions in this visitor.");
    }

    @Override
    public CFGNode visit(IRESeq n) {
        throw new UnsupportedOperationException("Cannot use IRESeq in LIR.");
    }

    @Override
    public CFGNode visit(IRMem n) {
        throw new UnsupportedOperationException(
                "Cannot use IR expressions in this visitor.");
    }

    @Override
    public CFGNode visit(IRName n) {
        throw new UnsupportedOperationException(
                "There are no reasons to use IRName other than it being inside of IRJump or IRCallStmt.");
    }

    @Override
    public CFGNode visit(IRTemp n) {
        throw new UnsupportedOperationException(
                "Cannot use IR expressions in this visitor.");
    }
}