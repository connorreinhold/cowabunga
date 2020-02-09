package cyr7.parser.xi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import cyr7.lexer.MyLexer;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import cyr7.ast.IxiProgramNode;
import cyr7.ast.NodeInterface;
import cyr7.parser.ixi.IxiParser;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;

public class test_JUNK {
    public static void main(String args[]) throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(
                        "./tests/resources/parser/ixi/collection.ixi")));
        ScannerBuffer lexer = new ScannerBuffer(new MyLexer(reader, true));
        IxiParser p = new IxiParser(lexer, new ComplexSymbolFactory());
        Object v = p.parse().value;
        IxiProgramNode b = (IxiProgramNode) v;
        CodeWriterSExpPrinter printer = new CodeWriterSExpPrinter(
                new PrintWriter(System.out));
        ((NodeInterface)v).prettyPrint(printer);
        printer.flush();
        printer.close();
    }
}
