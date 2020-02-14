package cyr7.parser;

import java.io.*;
import java.util.LinkedList;

import cyr7.ast.INode;
import cyr7.ast.XiProgramNode;
import cyr7.exceptions.ParserException;
import cyr7.lexer.MultiFileLexer;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;

public class test_JUNK {
    public static void main(String args[]) throws Exception {
        try {
            Reader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(
                            "./tests/resources/parser/testjunk.ixi")));

            ScannerBuffer lexer = new ScannerBuffer(new MultiFileLexer(reader, true));

            XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
            INode v = (INode) p.parse().value;
            CodeWriterSExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(System.out));
            v.prettyPrint(printer);
            printer.flush();
            printer.close();
        } catch (ParserException e) {
            System.out.println(e.getMessage());
        }
    }

}
