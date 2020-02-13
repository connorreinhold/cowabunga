package cyr7.parser;

import cyr7.ast.INode;
import cyr7.lexer.MultiFileLexer;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class ParserUtil {

    public static void parse(Reader reader, Writer writer, boolean isIXI) {
        ScannerBuffer lexer = new ScannerBuffer(new MultiFileLexer(reader, isIXI));

        try {
            XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
            INode node = (INode) p.parse().value;
            CodeWriterSExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(writer));
            node.prettyPrint(printer);
            printer.flush();
            printer.close();
        } catch (Exception e) {
            try {
                writer.write(e.getMessage());
            } catch (IOException io) {
                System.out.println(io.getMessage());
            }
        }
    }

    public static void printSExpr(INode node) {
        CodeWriterSExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(System.out));
        node.prettyPrint(printer);
        printer.flush();
        printer.close();
    }

}
