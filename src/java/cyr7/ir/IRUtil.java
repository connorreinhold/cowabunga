package cyr7.ir;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import cyr7.ast.Node;
import cyr7.cfg.ir.constructor.CFGConstructor;
import cyr7.cfg.ir.flatten.CFGFlattener;
import cyr7.cfg.ir.opt.CopyPropagationOptimization;
import cyr7.cfg.ir.opt.DeadCodeElimOptimization;
import cyr7.cfg.ir.opt.LoopUnrollingOptimization;
import cyr7.cli.CLI;
import cyr7.cli.OptConfig;
import cyr7.ir.block.TraceOptimizer;
import cyr7.ir.interpret.IRSimulator;
import cyr7.ir.lowering.LoweringVisitor;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.optimize.IRConstFoldVisitor;
import cyr7.ir.visit.CheckConstFoldedIRVisitor;
import cyr7.parser.ParserUtil;
import cyr7.typecheck.IxiFileOpener;
import cyr7.typecheck.TypeCheckUtil;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import edu.cornell.cs.cs4120.util.SExpPrinter;

public class IRUtil {

    private IRUtil() {}

    public static IRCompUnit lower(
        IRCompUnit compUnit,
        IdGenerator generator,
        OptConfig optConfig) {

        CLI.debugPrint("Constant Folding Enabled: " + optConfig.cf());

        CLI.lazyDebugPrint(compUnit, unit -> "MIR: \n" + unit);

        if (optConfig.cf()) {
            IRNode node = compUnit.accept(new IRConstFoldVisitor()).assertSecond();
            compUnit = (IRCompUnit) node;
            CLI.lazyDebugPrint(compUnit, unit -> "Constant-Folded MIR: \n" + unit);
        }

        compUnit = compUnit.accept(new LoweringVisitor(generator)).assertThird();
        final var functionToBlocks =
                TraceOptimizer.getOptimizedBasicBlocks(compUnit, generator);
        final var alt = CFGConstructor.constructBlockCFG(functionToBlocks);

        if (optConfig.copy()) {
            alt.keySet().stream().forEach(functionName -> {
                var optimizedCfg = alt.get(functionName);
                optimizedCfg = CopyPropagationOptimization.optimize(optimizedCfg);
                alt.put(functionName, optimizedCfg);
            });
        }
        if (optConfig.dce()) {
            // Perform dead code removal 3 times to be safe.
            for (int i = 0; i < 3; i++) {
                alt.keySet().stream().forEach(functionName -> {
                    var optimizedCfg = alt.get(functionName);
                    optimizedCfg = DeadCodeElimOptimization.optimize(optimizedCfg);
                    alt.put(functionName, optimizedCfg);
                });
            }
        }

        compUnit = CFGFlattener.flatten(alt, compUnit);

        if (optConfig.lu()) {
            final var loopUnrollCFG = CFGConstructor.constructCFG(compUnit);
            loopUnrollCFG.keySet().stream().forEach(functionName -> {
                if (!functionName.equals("_I*premain*_p")) {
                    var optimizedCfg = loopUnrollCFG.get(functionName);
                    optimizedCfg = LoopUnrollingOptimization.optimize(optimizedCfg);
                    loopUnrollCFG.put(functionName, optimizedCfg);
                }
            });
            compUnit = CFGFlattener.flatten(loopUnrollCFG, compUnit);
        }

        if (optConfig.cf()) {
            compUnit = (IRCompUnit)compUnit.accept(new IRConstFoldVisitor()).assertSecond();
        }

        CLI.lazyDebugPrint(compUnit, unit -> "Lowered MIR: \n" + unit);
        CLI.debugPrint("Actually Const Folded? " + compUnit.aggregateChildren(new CheckConstFoldedIRVisitor()));
        CLI.debugPrint("Actually Canonical? " + compUnit.aggregateChildren(new CheckConstFoldedIRVisitor()));
        CLI.debugPrint("Optimizations Performed: " + optConfig.convertToCLI());
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
        OptConfig optConfig) throws Exception {

        IRCompUnit lowered = generateIR(
            reader,
            filename,
            fileOpener,
            optConfig,
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
        OptConfig optConfig) throws Exception {

        IRCompUnit lowered = generateIR(
            reader,
            filename,
            fileOpener,
            optConfig,
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

        return (IRCompUnit) result.accept(new ASTToIRVisitor(generator)).assertSecond();
    }

    public static IRCompUnit generateIR(
        Reader reader,
        String filename,
        IxiFileOpener fileOpener,
        OptConfig optConfig,
        IdGenerator generator) throws Exception {

        Node result = ParserUtil.parseNode(reader, filename, false);
        TypeCheckUtil.typeCheck(result, fileOpener);

        IRCompUnit compUnit = (IRCompUnit)
            result.accept(new ASTToIRVisitor(generator)).assertSecond();

        return lower(compUnit, generator, optConfig);
    }

}