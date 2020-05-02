package cyr7.cfg;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import cyr7.cfg.nodes.CFGNode;
import cyr7.cfg.visitor.DotVisitor;
import cyr7.cfg.visitor.FlattenCFGVisitor;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.IdGenerator;
import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.ir.cfg.CFGConstructor;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import cyr7.typecheck.IxiFileOpener;
import polyglot.util.Pair;

public class CFGUtil {
    public static IRNode generateIR(CFGNode start, IdGenerator generator) {
        FlattenCFGVisitor fv = new FlattenCFGVisitor(generator);
        IRNodeFactory make = new IRNodeFactory_c(start.location());
        List<IRStmt> stmts = start.accept(fv);
        return make.IRSeq(stmts);
    }
    public static void generateDot(
            Reader reader,
            Writer writer,
            String filename,
            IxiFileOpener fileOpener,
            LowerConfiguration lowerConfiguration,
            String functionName) throws Exception {

        IRCompUnit lowered = IRUtil.generateIR(
                reader,
                filename,
                fileOpener,
                lowerConfiguration,
                new DefaultIdGenerator());
        Map<String, CFGNode> result = CFGConstructor.constructCFG(lowered);
        outputDotForFunction(result.get(functionName), writer);
    }
    public static void outputDotForFunction(CFGNode node, Writer writer) {
        PrintWriter printer = new PrintWriter(writer);
        DotVisitor dv = new DotVisitor();
        node.accept(dv);
        printer.println("digraph nfa {");
        for(String label: dv.getDotNodes()) {
            printer.println("    \""+label+"\" [shape=circle]");
        }
        printer.println();
        for(Pair<String, String> edge:dv.getDotEdges()) {
            String edgeName = "    \""+edge.part1()+"\" -> \""+edge.part2()+"\"";
            printer.println(edgeName);
        }
        printer.println("}");
        printer.flush();
    }
}