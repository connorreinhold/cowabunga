package cyr7.cfg.asm;

import cyr7.cfg.asm.dot.AsmCFGAnalysisDotVisitor;
import cyr7.cfg.asm.dot.AsmCFGDotVisitor;
import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cli.CLI;
import polyglot.util.Pair;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.function.Function;

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
        AsmCFGDotVisitor dv = new AsmCFGDotVisitor();
        node.accept(dv);
        printer.println("digraph nfa {");
        printer.println("    node [shape=rectangle]");
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

    public static void debugOutputDotForAnalysis(AsmCFGNode node, Function<AsmCFGNode, String> analysis) {
        PrintWriter printer = new PrintWriter(System.err);
        AsmCFGDotVisitor dv = new AsmCFGAnalysisDotVisitor(analysis);
        node.accept(dv);
        printer.println("digraph nfa {");
        printer.println("    node [shape=oval]");
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

//    public static void testGenerateDotAsm() throws Exception {
//        File f = new File("tests/resources/testJunk.xi");
//        FileReader fr = new FileReader(f);
//        BufferedReader br  = new BufferedReader(fr);
//        Reader reader = new BufferedReader(br);
//        List<ASMLine> lines = ASMUtil.generateAbstractASM(
//            reader,
//            "testJunk.xi",
//            null,
//            new LowerConfiguration(true, true));
//        AsmCFGConstructor cfgConstructor = new AsmCFGConstructor(lines);
//        AsmCFGStartNode startNode = cfgConstructor.constructAsmCFG();
//        Writer writer = new PrintWriter(System.out);
//        outputDotForFunctionAsm(startNode, writer);
//    }

    private AsmCFGUtil() { }

}
