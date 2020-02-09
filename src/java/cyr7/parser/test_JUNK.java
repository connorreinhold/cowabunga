package cyr7.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import polyglot.util.OptimalCodeWriter;
import cyr7.ast.IxiProgramNode;
import cyr7.ast.NodeInterface;
import cyr7.lexer.ixi.IxiLexer;
import cyr7.parser.ixi.IxiParser;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;

public class test_JUNK {
    public static void main(String args[]) throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(
                        "./tests/resources/parser/ixi/dictionary.ixi")));
        ScannerBuffer lexer = new ScannerBuffer(new IxiLexer(reader,
                new ComplexSymbolFactory()));
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
