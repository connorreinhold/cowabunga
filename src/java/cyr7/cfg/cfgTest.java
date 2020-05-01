package cyr7.cfg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Map;

import cyr7.cfg.nodes.CFGNode;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.ir.cfg.CFGConstructor;
import cyr7.ir.nodes.IRCompUnit;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class cfgTest {
    public static void main(String[] args) throws Exception {
        Reader reader = new BufferedReader(new FileReader(new File("tests/resources/testJunk.xi")));
        IRCompUnit lowered = IRUtil.generateIR(
                reader,
                "testJunk.xi",
                null,
                new LowerConfiguration(true, true),
                new DefaultIdGenerator());

            SExpPrinter printer
                = new CodeWriterSExpPrinter(new PrintWriter(new PrintWriter(System.out)));
            lowered.printSExp(printer);
            printer.flush();

        Map<String, CFGNode> result = CFGConstructor.constructCFG(lowered);
        
    }
}
