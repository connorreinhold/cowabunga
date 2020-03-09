package cyr7.ir;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import cyr7.ast.Node;
import cyr7.ir.block.TraceOptimizer;
import cyr7.ir.interpret.IRSimulator;
import cyr7.ir.lowering.LoweringVisitor;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.parser.ParserUtil;
import cyr7.typecheck.IxiFileOpener;
import cyr7.typecheck.TypeCheckUtil;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class IrUtil {

    public static void mirRun(Reader reader, Writer writer, String filename,
                              boolean isIXI, IxiFileOpener opener) throws Exception {

        Node result = ParserUtil.parseNode(reader, filename, isIXI);
        TypeCheckUtil.typeCheck(result, opener);
        IRCompUnit compUnit = (IRCompUnit) result.accept(new AstToIrVisitor())
                .assertSecond();

        SExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(System.out));
        compUnit.printSExp(printer);
        printer.flush();

        IRSimulator sim = new IRSimulator(compUnit);
        long retVal = sim.call("_Imain_paai", 0);
        writer.append(String.valueOf(retVal)).append(System.lineSeparator());
    }

    public static void irGen(Reader reader, Writer writer, String filename,
            boolean isIXI, IxiFileOpener fileOpener) throws Exception {
        IRNodeFactory make = new IRNodeFactory_c(new Location(1,1));
        Node result = ParserUtil.parseNode(reader, filename, isIXI);
        TypeCheckUtil.typeCheck(result, fileOpener);
        IdGenerator generator = new DefaultIdGenerator();
        IRNode node = result.accept(new AstToIrVisitor(generator))
                .assertSecond();
        IRCompUnit lowered = node.accept(new LoweringVisitor(generator))
                .assertThird();

        // TODO: Add optimization conditions

        // Blocks and Traces
        var trace = new TraceOptimizer(lowered, generator);
        IRCompUnit optimized = trace.optimize();

        SExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(writer));
        optimized.printSExp(printer);
        printer.flush();
    }

    public static void irRun(Reader reader, Writer writer, String filename,
            boolean isIXI, IxiFileOpener fileOpener) throws Exception {
        Node result = ParserUtil.parseNode(reader, filename, isIXI);
        TypeCheckUtil.typeCheck(result, fileOpener);
        IdGenerator generator = new DefaultIdGenerator();
        IRNode node = result.accept(new AstToIrVisitor(generator))
                .assertSecond();
        IRCompUnit lowered = node.accept(new LoweringVisitor(generator)).assertThird();

        // TODO: Add optimization conditions
        IRSimulator sim = new IRSimulator(lowered);
        long retVal = sim.call("_Imain_paai", 0);
        writer.append(String.valueOf(retVal)).append(System.lineSeparator());
    }


}