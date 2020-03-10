package cyr7.ir.integration;

import cyr7.ast.Node;
import cyr7.ir.AstToIrVisitor;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IdGenerator;
import cyr7.ir.IrUtil.Configuration;
import cyr7.ir.fold.ConstFoldVisitor;
import cyr7.ir.interpret.IRSimulator;
import cyr7.ir.lowering.LoweringVisitor;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.visit.CheckCanonicalIRVisitor;
import cyr7.ir.visit.CheckConstFoldedIRVisitor;
import cyr7.parser.ParserUtil;
import cyr7.typecheck.IxiFileOpener;
import cyr7.typecheck.TypeCheckUtil;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import edu.cornell.cs.cs4120.util.SExpPrinter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class Run {

    private static class Opener implements IxiFileOpener {

        @Override
        public Reader openIxiLibraryFile(String name) throws FileNotFoundException {
            if (name.equals("conv")) {
                return new StringReader(
                    "parseInt(str: int[]): int, bool\n"
                    + "unparseInt(n: int): int[]\n"
                );
            } else if (name.equals("io")) {
                return new StringReader(
                    "print(str: int[])\n"
                    + "println(str: int[])\n"
                    + "readln() : int[]\n"
                    + "getchar() : int\n"
                    + "eof() : bool\n"
                );
            } else {
                throw new FileNotFoundException(name);
            }
        }

    }

    public static String runFile(String filename) throws Exception {
        InputStream filePath = Run.class
            .getClassLoader()
            .getResourceAsStream("irgen/"+ filename + ".xi");
        String program = new String(filePath.readAllBytes());
        return run(program);
    }

    public static String run(String program) throws Exception {
        String mirResult = mirRun(program);

//        String lirResultNoOpts = lirRun(program, new Configuration(false, false));
//        assertEquals(mirResult, lirResultNoOpts);
//
//        String lirResultCFold = lirRun(program, new Configuration(true, false));
//        assertEquals(mirResult, lirResultCFold);
//
//        String lirResultCommute = lirRun(program, new Configuration(false, true));
//        assertEquals(mirResult, lirResultCommute);
//
//        String lirResultAll = lirRun(program, new Configuration(true, true));
//        assertEquals(mirResult, lirResultAll);

        return mirResult;
    }

    private static IRCompUnit lower(
        IRCompUnit compUnit,
        IdGenerator generator,
        Configuration configuration) {

        IRNode constFolded = compUnit;

        if (configuration.cFoldEnabled) {
            constFolded =
                compUnit.accept(new ConstFoldVisitor()).assertSecond();
        }
        assert constFolded instanceof IRCompUnit;

        IRCompUnit lowered = constFolded.accept(
            new LoweringVisitor(generator, configuration.commutativeEnabled))
            .assertThird();

        if (configuration.cFoldEnabled) {
            assertTrue(lowered.aggregateChildren(new CheckConstFoldedIRVisitor()));
        }

        CheckCanonicalIRVisitor visitor = new CheckCanonicalIRVisitor();
        assertTrue(lowered.aggregateChildren(visitor),
            "Program: "
                + sexp(lowered)
                + "\nOffending node: "
                + visitor.noncanonical());

        return lowered;
    }

    private static String mirRun(String program) throws Exception {
        Reader reader = new StringReader(program);

        Node result = ParserUtil.parseNode(reader, "Run", false);
        TypeCheckUtil.typeCheck(result, new Opener());

        IRCompUnit compUnit;
        {
            IRNode node = result.accept(new AstToIrVisitor()).assertSecond();
            assert node instanceof IRCompUnit;
            compUnit = (IRCompUnit) node;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IRSimulator sim = new IRSimulator(compUnit, new PrintStream(outputStream));
        sim.call("_Imain_paai", 0);
        return new String(outputStream.toByteArray(), Charset.defaultCharset());
    }

    private static String lirRun(String program, Configuration configuration) throws Exception {
        Reader reader = new StringReader(program);

        Node result = ParserUtil.parseNode(reader, "Run", false);
        TypeCheckUtil.typeCheck(result, new Opener());

        IdGenerator generator = new DefaultIdGenerator();

        IRCompUnit compUnit;
        {
            IRNode node = result.accept(new AstToIrVisitor()).assertSecond();
            assert node instanceof IRCompUnit;
            compUnit = (IRCompUnit) node;
        }

        IRCompUnit lowered = lower(compUnit, generator, configuration);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IRSimulator sim = new IRSimulator(lowered, new PrintStream(outputStream));
        sim.call("_Imain_paai", 0);
        return new String(outputStream.toByteArray(), Charset.defaultCharset());
    }

    private static String sexp(IRNode n) {
        StringWriter writer = new StringWriter();
        SExpPrinter printer =
            new CodeWriterSExpPrinter(new PrintWriter(writer));
        n.printSExp(printer);
        printer.flush();
        return writer.toString();
    }

    private Run() { }

}
