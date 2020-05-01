package cyr7.cfg;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.visitor.DotVisitor;
import cyr7.cfg.visitor.FlattenCFGVisitor;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;

import polyglot.util.Pair;

public class CFGUtil {
    public static IRNode generateIR(CFGNode start, IdGenerator generator) {
        FlattenCFGVisitor fv = new FlattenCFGVisitor(generator);
        IRNodeFactory make = new IRNodeFactory_c(start.location());
        List<IRStmt> stmts = start.accept(fv);
        return make.IRSeq(stmts);
    }
    
    public static void outputToDot(CFGNode node, Writer writer) {
        PrintWriter printer = new PrintWriter(writer);
        DotVisitor dv = new DotVisitor();
        node.accept(dv);
        printer.println("digraph nfa {");
        for(String label: dv.getNodes()) {
            printer.println("    \""+label+"\" [shape=circle]");
        }
        printer.println();
        for(Pair<String, String> edge:dv.getEdges()) {
            String edgeName = "    \""+edge.part1()+"\" -> \""+edge.part2()+"\"";
            printer.println(edgeName);
        }
        printer.println("}");
        printer.flush();
    }
}