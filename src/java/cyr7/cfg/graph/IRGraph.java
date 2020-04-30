package cyr7.cfg.graph;

import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;

import java.util.HashMap;
import java.util.Iterator;

public class IRGraph extends Graph<IRStmt> {

    public IRGraph (IRCallStmt func) {
        /**
         * Ideally func would be an IRFuncDecl 
            super(func);
            HashMap<Integer, Node> nodeStatementMap = new HashMap<>();

            IRStmt body = func.body();
            //TODO: This is probably not the way to go with this
            IRSeq stmts = body instanceof IRSeq ? (IRSeq) body : new IRSeq(null, body);
            if (stmts.stmts().isEmpty()) return;

            Iterator<IRStmt> iter = stmts.stmts().iterator();
            IRStmt stmt = iter.next();
            Node previous = new Node(stmt);
            int nodeID = 0;
        **/
        super(func);
    }

}