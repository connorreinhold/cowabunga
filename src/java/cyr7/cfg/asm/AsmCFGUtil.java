package cyr7.cfg.asm;

import cyr7.cfg.asm.dot.AsmCFGDotUtil;
import cyr7.cfg.asm.dot.AsmCFGDotUtil.DotData;
import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cli.CLI;
import polyglot.util.Pair;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

public final class AsmCFGUtil {

    public static void debugPrintDotForFunctionAsm(AsmCFGStartNode node) {
        CLI.lazyDebugPrint(() -> {
            CLI.debugPrint("<-- ASM CFG --> ");
            outputDotForFunctionAsm(node, new OutputStreamWriter(System.err));
            CLI.debugPrint("<-- ASM CFG --> ");
        });
    }

    public static void printDotForFunctionAsm(AsmCFGStartNode node) {
        outputDotForFunctionAsm(node, new OutputStreamWriter(System.out));
    }

    public static void outputDotForFunctionAsm(AsmCFGNode node, Writer writer) {
        PrintWriter printer = new PrintWriter(writer);
        DotData data = AsmCFGDotUtil.execute(node);
        printer.println("digraph nfa {");
        printer.println("    node [shape=rectangle]");
        for(String label: data.nodes()) {
            printer.println("    \""+label+"\"");
        }
        printer.println();
        for(Pair<String, String> edge:data.edges()) {
            String edgeName = "    \""+edge.part1()+"\" -> \""+edge.part2()+"\"";
            printer.println(edgeName);
        }
        printer.println("}");
        printer.flush();
    }

    private AsmCFGUtil() { }

}
