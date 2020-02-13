package cyr7.parser;

import cyr7.ast.INode;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class ParserUtil {

    public static void parse(Reader reader, Writer writer) {

    }

    public static void printSExpr(INode node) {
        CodeWriterSExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(System.out));
        node.prettyPrint(printer);
        printer.flush();
        printer.close();
    }

}
