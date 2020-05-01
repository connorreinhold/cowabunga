package cyr7.cfg.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import cyr7.cfg.nodes.CFGCallNode;
import cyr7.cfg.nodes.CFGIfNode;
import cyr7.cfg.nodes.CFGMemAssignNode;
import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.nodes.CFGReturnNode;
import cyr7.cfg.nodes.CFGStartNode;
import cyr7.cfg.nodes.CFGVarAssignNode;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;

import polyglot.util.Pair;

public class FlattenCFGVisitor implements CFGVisitor<List<IRStmt>> {

    private IdGenerator generator;
    // each element has its CFGNode and the node translated into a list of IRStmts
    private Stack<CFGNode> joinNodes;
    private Map<CFGNode, List<IRStmt>> joinTranslations;
    
    public FlattenCFGVisitor(IdGenerator generator) {
        this.generator = generator;
        this.joinNodes = new Stack<>();
        this.joinTranslations = new HashMap<>();
    }
    
    @Override
    public List<IRStmt> visit(CFGCallNode n) {
        List<IRStmt> stmts = List.of(n.call);
        stmts.addAll(n.out().get(0).accept(this));
        return stmtsOrAddTranslation(n, stmts);
    }

    private enum BranchOrigin {
        FALSE,
        TRUE
    }
    @Override
    public List<IRStmt> visit(CFGIfNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        String lt = generator.newLabel();
        String lf = generator.newLabel();
        String end = generator.newLabel();
        List<IRStmt> stmts = new ArrayList<IRStmt>();
    
        Set<CFGNode> seenFromTrueBranch = new HashSet<CFGNode>();
        Set<CFGNode> seenFromFalseBranch = new HashSet<CFGNode>();
        Queue<Pair<CFGNode, BranchOrigin>> frontier = new LinkedList<Pair<CFGNode, BranchOrigin>>();
        frontier.add(new Pair<CFGNode, BranchOrigin>(n.falseBranch(), BranchOrigin.FALSE));
        frontier.add(new Pair<CFGNode, BranchOrigin>(n.trueBranch(), BranchOrigin.TRUE));
        CFGNode joinNode = null;
        
        // run bfs to find node where the false and true branches come back together
        while(joinNode == null && !frontier.isEmpty()) {
            Pair<CFGNode, BranchOrigin> current = frontier.poll();
        
            // List of out nodes from current node with the same origin branch as parent
            // The filtering prevents loops
            List<Pair<CFGNode, BranchOrigin>> outPairs = current
                    .part1().out().stream()
                    .filter(outNode -> outNode != n)
                    .map(outNode -> new Pair<CFGNode, BranchOrigin>(outNode, current.part2()))
                    .collect(Collectors.toList());
            
            if (current.part2() == BranchOrigin.FALSE) {
                if (seenFromTrueBranch.contains(current.part1())) {
                    joinNode = current.part1();
                } else {
                    seenFromFalseBranch.add(current.part1());
                    frontier.addAll(outPairs);
                }
            } else if (current.part2() == BranchOrigin.TRUE) {
                if (seenFromFalseBranch.contains(current.part1())) {
                    joinNode = current.part1();
                } else {
                    seenFromTrueBranch.add(current.part1());
                    frontier.addAll(outPairs);
                }
            }
        }
        
        // If the branches come back together
        if (joinNode != null) {
            this.joinNodes.push(joinNode);
        }
        
        stmts.add(make.IRCJump(n.cond, lt, lf));
        stmts.add(make.IRLabel(lf));
        stmts.addAll(n.falseBranch().accept(this));
        stmts.add(make.IRLabel(lt));
        stmts.addAll(n.trueBranch().accept(this));
        stmts.add(make.IRLabel(end));
        if (joinNode != null) {
            stmts.addAll(joinTranslations.get(joinNode));
        }
        
        return stmtsOrAddTranslation(n,stmts);
    }

    @Override
    public List<IRStmt> visit(CFGVarAssignNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        List<IRStmt> stmts = new ArrayList<>();
        stmts.add(make.IRMove(make.IRTemp(n.variable), n.value));
        stmts.addAll(n.out().get(0).accept(this));
        return stmtsOrAddTranslation(n, stmts);
    }

    @Override
    public List<IRStmt> visit(CFGMemAssignNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        List<IRStmt> stmts = new ArrayList<IRStmt>();
        stmts.add(make.IRMove(n.target, n.value));
        stmts.addAll(n.out().get(0).accept(this));
        return stmtsOrAddTranslation(n, stmts);
    }

    @Override
    public List<IRStmt> visit(CFGReturnNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        return stmtsOrAddTranslation(n, List.of(make.IRReturn(n.numReturnValues)));
    }

    @Override
    public List<IRStmt> visit(CFGStartNode n) {
        return n.out().get(0).accept(this);
    }

    // This method ensures that if there are multiple entry points to a node, we save
    // the translation to put it in the correct spot when translating to IR. Currently,
    // joinNodes are for where the branches of an if/else statement come back together.
    private List<IRStmt> stmtsOrAddTranslation(CFGNode n, List<IRStmt> stmts) {
        if (!joinNodes.isEmpty() && joinNodes.peek() == n ) {
            joinTranslations.put(n, stmts);
            return List.of();
        }
        return stmts;
    }
}