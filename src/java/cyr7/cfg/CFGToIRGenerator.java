package cyr7.cfg;

import java.util.List;

import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.visitor.FlattenCFGVisitor;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;

public class CFGToIRGenerator {
    public static IRNode generateIR(CFGNode start, IdGenerator generator) {
        FlattenCFGVisitor fv = new FlattenCFGVisitor(generator);
        IRNodeFactory make = new IRNodeFactory_c(start.location());
        List<IRStmt> stmts = start.accept(fv);
        return make.IRSeq(stmts);
    }
}