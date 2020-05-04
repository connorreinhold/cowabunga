package cyr7.cfg;

import cyr7.cfg.constructor.AsmCFGConstructor;
import cyr7.cfg.constructor.CFGConstructor;
import cyr7.cfg.flatten.CFGFlattener;
import cyr7.cfg.nodes.asm.AsmCFGNode;
import cyr7.cfg.nodes.asm.AsmCFGStartNode;
import cyr7.cfg.nodes.ir.CFGNode;
import cyr7.cfg.visitor.AsmCFGDotVisitor;
import cyr7.cfg.visitor.IrCFGDotVisitor;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRSeq;
import cyr7.typecheck.IxiFileOpener;
import cyr7.x86.ASMUtil;
import cyr7.x86.asm.ASMLine;
import polyglot.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
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
        outputDotForFunctionIR(result.get(functionName), writer);
    }

    public static void outputDotForFunctionIR(CFGNode node, Writer writer) {
        PrintWriter printer = new PrintWriter(writer);
        IrCFGDotVisitor dv = new IrCFGDotVisitor();
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
                new LowerConfiguration(true, true));
        AsmCFGConstructor cfgConstructor = new AsmCFGConstructor(lines);
        AsmCFGStartNode startNode = cfgConstructor.constructAsmCFG();
        Writer writer = new PrintWriter(System.out);
        outputDotForFunctionAsm(startNode, writer);
    }
}