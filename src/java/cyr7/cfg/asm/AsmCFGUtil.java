package cyr7.cfg.asm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import cyr7.cfg.asm.constructor.AsmCFGConstructor;
import cyr7.cfg.asm.dot.AsmCFGDotVisitor;
import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cli.OptimizationSetting;
import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.x86.ASMUtil;
import cyr7.x86.asm.ASMLine;
import polyglot.util.Pair;

public final class AsmCFGUtil {

    public static void outputDotForFunctionAsm(AsmCFGNode node, Writer writer) {
        PrintWriter printer = new PrintWriter(writer);
        AsmCFGDotVisitor dv = new AsmCFGDotVisitor();
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

    public static void testGenerateDotAsm() throws Exception {
        File f = new File("tests/resources/testJunk.xi");
        FileReader fr = new FileReader(f);
        BufferedReader br  = new BufferedReader(fr);
        Reader reader = new BufferedReader(br);
        List<ASMLine> lines = ASMUtil.generateAbstractASM(
            reader,
            "testJunk.xi",
            null,
            new LowerConfiguration(new OptimizationSetting(), true));
        AsmCFGConstructor cfgConstructor = new AsmCFGConstructor(lines);
        AsmCFGStartNode startNode = cfgConstructor.constructAsmCFG();
        Writer writer = new PrintWriter(System.out);
        outputDotForFunctionAsm(startNode, writer);
    }

    private AsmCFGUtil() { }

}
