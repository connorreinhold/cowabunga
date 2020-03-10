package cyr7.ir;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import cyr7.ast.Node;
import cyr7.cli.CLI;
import cyr7.ir.fold.ConstFoldVisitor;
import cyr7.ir.interpret.IRSimulator;
import cyr7.ir.lowering.LoweringVisitor;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.visit.CheckConstFoldedIRVisitor;
import cyr7.parser.ParserUtil;
import cyr7.parser.SExpVisitor;
import cyr7.typecheck.IxiFileOpener;
import cyr7.typecheck.TypeCheckUtil;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class IrUtil {

    public static class Configuration {

        public final boolean cFoldEnabled;
        public final boolean commutativeEnabled;

        public Configuration(boolean cFoldEnabled,
                              boolean commutativeEnabled) {
            this.cFoldEnabled = cFoldEnabled;
            this.commutativeEnabled = commutativeEnabled;
        }

    }

    public static IRCompUnit lower(
        IRCompUnit compUnit,
        IdGenerator generator,
        Configuration configuration) {

        IRNode constFolded = compUnit;

        CLI.debugPrint("Constant Folding Enabled: " + configuration.cFoldEnabled);
        if (configuration.cFoldEnabled) {
            constFolded =
                compUnit.accept(new ConstFoldVisitor()).assertSecond();
        }
        assert constFolded instanceof IRCompUnit;

        IRCompUnit lowered = constFolded.accept(
            new LoweringVisitor(generator, configuration.commutativeEnabled))
            .assertThird();

        CLI.debugPrint("Actually Const Folded? " + lowered.aggregateChildren(new CheckConstFoldedIRVisitor()));
        CLI.debugPrint("Actually Canonical? " + lowered.aggregateChildren(new CheckConstFoldedIRVisitor()));

        return lowered;
    }

    public static void mirRun(
        Reader reader,
        Writer writer,
        String filename,
        boolean isIXI,
        IxiFileOpener opener) throws Exception {

        Node result = ParserUtil.parseNode(reader, filename, isIXI);
        TypeCheckUtil.typeCheck(result, opener);

        IRCompUnit compUnit;
        {
            IRNode node = result.accept(new AstToIrVisitor()).assertSecond();
            assert node instanceof IRCompUnit;
            compUnit = (IRCompUnit) node;
        }

        IRSimulator sim = new IRSimulator(compUnit);
        long retVal = sim.call("_Imain_paai");
        writer.append(String.valueOf(retVal)).append(System.lineSeparator());
    }

    public static void irGen(
        Reader reader,
        Writer writer,
        String filename,
        boolean isIXI,
        IxiFileOpener fileOpener,
        Configuration configuration) throws Exception {

        Node result = ParserUtil.parseNode(reader, filename, isIXI);
        TypeCheckUtil.typeCheck(result, fileOpener);

        IdGenerator generator = new DefaultIdGenerator();

        IRCompUnit compUnit;
        {
            IRNode node = result.accept(new AstToIrVisitor()).assertSecond();
            assert node instanceof IRCompUnit;
            compUnit = (IRCompUnit) node;
        }

        IRNode lowered = lower(compUnit, generator, configuration);

        SExpPrinter printer =
            new CodeWriterSExpPrinter(new PrintWriter(writer));
        lowered.printSExp(printer);
        printer.flush();
    }

    public static void irRun(
        Reader reader,
        Writer writer,
        String filename,
        boolean isIXI,
        IxiFileOpener fileOpener,
        Configuration configuration) throws Exception {

        Node result = ParserUtil.parseNode(reader, filename, isIXI);
        TypeCheckUtil.typeCheck(result, fileOpener);

        IdGenerator generator = new DefaultIdGenerator();

        IRCompUnit compUnit;
        {
            IRNode node = result.accept(new AstToIrVisitor()).assertSecond();
            assert node instanceof IRCompUnit;
            compUnit = (IRCompUnit) node;
        }

        IRCompUnit lowered = lower(compUnit, generator, configuration);

        IRSimulator sim = new IRSimulator(lowered);
        long retVal = sim.call("_Imain_paai", 0);
        writer.append(String.valueOf(retVal)).append(System.lineSeparator());
    }
    
    public static void printSExpr(IRNode node) {
        SExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(System.out));
        node.printSExp(printer);
        printer.flush();
    }
}