package cyr7.cfg.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

public class FlattenCFGVisitor extends AbstractCFGVisitor<List<IRStmt>>{

    private IdGenerator generator;
    private Stack<List<IRStmt>> joinNodeTranslations;
    
    public FlattenCFGVisitor(IdGenerator generator) {
        this.generator = generator;
        this.joinNodeTranslations = new Stack<List<IRStmt>>();
    }
    
    @Override
    public List<IRStmt> visit(CFGCallNode n) {
        List<IRStmt> stmts = List.of(n.call);
        stmts.addAll(n.out().get(0).accept(this));
        return stmtsOrPushJoin(n, stmts);
    }

    @Override
    public List<IRStmt> visit(CFGIfNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        String lt = generator.newLabel();
        String lf = generator.newLabel();
        String end = generator.newLabel();
        List<IRStmt> stmts = new ArrayList<IRStmt>();
    
        stmts.add(make.IRCJump(n.guard, lt, lf));
        stmts.add(make.IRLabel(lf));
        stmts.addAll(n.falseBranch().accept(this));
        stmts.add(make.IRLabel(lt));
        stmts.addAll(n.trueBranch().accept(this));
        stmts.add(make.IRLabel(end));
        stmts.addAll(joinNodeTranslations.pop());

        return stmtsOrPushJoin(n,stmts);
    }

    @Override
    public List<IRStmt> visit(CFGVarAssignNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        List<IRStmt> stmts = List.of(make.IRMove(make.IRTemp(n.variable), n.value));
        stmts.addAll(n.out().get(0).accept(this));
        return stmtsOrPushJoin(n, stmts);
    }

    @Override
    public List<IRStmt> visit(CFGMemAssignNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        List<IRStmt> stmts = List.of(make.IRMove(n.target, n.value));
        stmts.addAll(n.out().get(0).accept(this));
        return stmtsOrPushJoin(n, stmts);
    }

    @Override
    public List<IRStmt> visit(CFGReturnNode n) {
        IRNodeFactory make = new IRNodeFactory_c(n.location());
        return stmtsOrPushJoin(n, List.of(make.IRReturn()));
    }

    @Override
    public List<IRStmt> visit(CFGStartNode n) {
        return n.out().get(0).accept(this);
    }

    // This method ensures that if there are multiple entry points to a node, we save
    // the translation to put it in the correct spot when translating to IR. Currently,
    // joinNodeTranslations will only contain the nodes after an if/else block.
    private List<IRStmt> stmtsOrPushJoin(CFGNode n, List<IRStmt> stmts) {
        if (n.in().size() > 1) {
            joinNodeTranslations.push(stmts);
            return List.of();
        }
        return stmts;
    }
}