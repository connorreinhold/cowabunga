package cyr7.parser;

import cyr7.ast.AbstractNode;
import cyr7.exceptions.ParserException;
import cyr7.lexer.MultiFileLexer;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

import java.io.*;

public class ParserUtil {

    public static void parse(Reader reader, Writer writer, boolean isIXI) throws Exception {
        ScannerBuffer lexer = new ScannerBuffer(new MultiFileLexer(reader, isIXI));

        try {
            XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
            AbstractNode node = (AbstractNode) p.parse().value;
            CodeWriterSExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(writer));
            node.prettyPrint(printer);
            printer.flush();
            printer.close();
        } catch (ParserException e) {
            writer.append(e.getMessage()).append(System.lineSeparator());
            writer.flush();
            writer.close();
        }
    }

    public static void printSExpr(AbstractNode node) {
        CodeWriterSExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(System.out));
        node.prettyPrint(printer);
        printer.flush();
        printer.close();
    }

    public static String toSExpr(AbstractNode node) {
        StringWriter writer = new StringWriter();
        CodeWriterSExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(writer));
        node.prettyPrint(printer);
        printer.flush();
        printer.close();
        return writer.toString().trim();
    }

}
