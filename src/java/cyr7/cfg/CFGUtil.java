package cyr7.cfg;

import cyr7.cfg.flatten.CFGFlattener;
import cyr7.cfg.nodes.ir.CFGNode;
import cyr7.cfg.visitor.DotVisitor;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.ir.cfg.CFGConstructor;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRSeq;
import cyr7.typecheck.IxiFileOpener;
import polyglot.util.Pair;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

public class CFGUtil {

    /**
     * Transforms a control-flow graph into an Lowered-IR function body.
     * @param start Must be an instance of CFGStartNode. The CFG must either
     * terminate or lead to an infinite loop in all possible paths from the
     * {@code start} node.
     *
     * @return
     */
    public static IRSeq generateIR(CFGNode start) {
        return CFGFlattener.flatten(start);
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
        printer.println("    node [shape=circle]");
        for(String label: dv.getDotNodes()) {
            printer.println("    \""+label+"\"");
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