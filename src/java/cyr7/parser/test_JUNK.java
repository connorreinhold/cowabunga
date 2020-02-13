package cyr7.parser;

import java.io.*;
import java.util.LinkedList;

import cyr7.ast.XiProgramNode;
import cyr7.lexer.MultiFileLexer;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;

public class test_JUNK {
    public static void main(String args[]) throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(
                        "./tests/resources/parser/xi/exprs.xi")));

        ScannerBuffer lexer = new ScannerBuffer(new MultiFileLexer(reader, false));

        XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
        XiProgramNode v = (XiProgramNode) p.parse().value;
        CodeWriterSExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(System.out));
        v.prettyPrint(printer);
        printer.flush();
        printer.close();
    }

}
