package cyr7.parser;

import cyr7.ast.INode;
import cyr7.ast.XiProgramNode;
import cyr7.lexer.MultiFileLexer;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

import java.io.*;

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

}
