package cyr7.ir;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import cyr7.ast.Node;
import cyr7.cli.CLI;
import cyr7.ir.block.TraceOptimizer;
import cyr7.ir.fold.IRConstFoldVisitor;
import cyr7.ir.fold.LIRConstFoldVisitor;
import cyr7.ir.interpret.IRSimulator;
import cyr7.ir.lowering.LoweringVisitor;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.visit.CheckConstFoldedIRVisitor;
import cyr7.parser.ParserUtil;
import cyr7.typecheck.IxiFileOpener;
import cyr7.typecheck.TypeCheckUtil;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class IRUtil {

    public static class LowerConfiguration {

        public final boolean cFoldEnabled;
        public final boolean traceEnabled;

        public LowerConfiguration(boolean cFoldEnabled, boolean traceEnabled) {
            this.cFoldEnabled = cFoldEnabled;
            this.traceEnabled = traceEnabled;
        }

    }

    public static IRCompUnit lower(
        IRCompUnit compUnit,
        IdGenerator generator,
        LowerConfiguration lowerConfiguration) {

        CLI.debugPrint("Constant Folding Enabled: " + lowerConfiguration.cFoldEnabled);

        CLI.lazyDebugPrint(compUnit, unit -> "MIR: \n" + unit);

        if (lowerConfiguration.cFoldEnabled) {
            IRNode node = compUnit.accept(new IRConstFoldVisitor()).assertSecond();
            compUnit = (IRCompUnit) node;
            CLI.lazyDebugPrint(compUnit, unit -> "Constant-Folded MIR: \n" + unit);
        }

        compUnit =
            compUnit.accept(new LoweringVisitor(generator)).assertThird();

        if (lowerConfiguration.traceEnabled) {
            compUnit = TraceOptimizer.optimize(compUnit, generator);
        }

        if (lowerConfiguration.cFoldEnabled) {
            IRNode node =
                compUnit.accept(new LIRConstFoldVisitor()).assertSecond();
            assert node instanceof IRCompUnit;
            compUnit = (IRCompUnit) node;
        }

        CLI.lazyDebugPrint(compUnit, unit -> "Lowered MIR: \n" + unit);

        CLI.debugPrint("Actually Const Folded? " + compUnit.aggregateChildren(new CheckConstFoldedIRVisitor()));
        CLI.debugPrint("Actually Canonical? " + compUnit.aggregateChildren(new CheckConstFoldedIRVisitor()));

        return compUnit;
    }

    public static void mirRun(
        Reader reader,
        Writer writer,
        String filename,
        boolean isIXI,
        IxiFileOpener opener) throws Exception {

        Node result = ParserUtil.parseNode(reader, filename, isIXI);
        TypeCheckUtil.typeCheck(result, opener);

        IdGenerator generator = new DefaultIdGenerator();
        IRCompUnit compUnit = (IRCompUnit)
            result.accept(new ASTToIRVisitor(generator)).assertSecond();

        IRSimulator sim = new IRSimulator(compUnit);
        long retVal = sim.call("_Imain_paai");
        writer.append(String.valueOf(retVal)).append(System.lineSeparator());
    }

    public static void irGen(
        Reader reader,
        Writer writer,
        String filename,
        IxiFileOpener fileOpener,
        LowerConfiguration lowerConfiguration) throws Exception {

        IRCompUnit lowered = generateIR(
            reader,
            filename,
            fileOpener,
            lowerConfiguration,
            new DefaultIdGenerator());

        SExpPrinter printer
            = new CodeWriterSExpPrinter(new PrintWriter(writer));
        lowered.printSExp(printer);
        printer.flush();
    }

    public static void initialIRGen(
            Reader reader,
            Writer writer,
            String filename,
            IxiFileOpener fileOpener) throws Exception {

        IRCompUnit lowered = generateInitialIR(
                reader,
                filename,
                fileOpener,
                new DefaultIdGenerator());

        SExpPrinter printer
                = new CodeWriterSExpPrinter(new PrintWriter(writer));
        lowered.printSExp(printer);
        printer.flush();
    }

    public static void irRun(
        Reader reader,
        Writer writer,
        String filename,
        IxiFileOpener fileOpener,
        LowerConfiguration lowerConfiguration) throws Exception {

        IRCompUnit lowered = generateIR(
            reader,
            filename,
            fileOpener,
            lowerConfiguration,
            new DefaultIdGenerator());

        IRSimulator sim = new IRSimulator(lowered);
        long retVal = sim.call("_Imain_paai", 0);
        writer.append(String.valueOf(retVal)).append(System.lineSeparator());
    }

    public static String sexpr(IRNode node) {
        StringWriter writer = new StringWriter();
        SExpPrinter printer =
            new CodeWriterSExpPrinter(new PrintWriter(writer));
        node.printSExp(printer);
        printer.flush();
        return writer.toString();
    }

    public static IRCompUnit generateInitialIR(
            Reader reader,
            String filename,
            IxiFileOpener fileOpener,
            IdGenerator generator) throws Exception {

        Node result = ParserUtil.parseNode(reader, filename, false);
        TypeCheckUtil.typeCheck(result, fileOpener);

        IRCompUnit compUnit = (IRCompUnit)
                result.accept(new ASTToIRVisitor(generator)).assertSecond();

        return compUnit;
    }

    public static IRCompUnit generateIR(
        Reader reader,
        String filename,
        IxiFileOpener fileOpener,
        LowerConfiguration lowerConfiguration,
        IdGenerator generator) throws Exception {

        Node result = ParserUtil.parseNode(reader, filename, false);
        TypeCheckUtil.typeCheck(result, fileOpener);

        IRCompUnit compUnit = (IRCompUnit)
            result.accept(new ASTToIRVisitor(generator)).assertSecond();

        return lower(compUnit, generator, lowerConfiguration);
    }

}