package cyr7.parser;

import java.io.*;
import java.util.LinkedList;

import cyr7.ast.ExprNode;
import cyr7.lexer.MultiFileLexer;
import cyr7.lexer.MyLexer;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import cyr7.ast.IxiProgramNode;
import cyr7.ast.NodeInterface;
import cyr7.parser.XiParser;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import polyglot.util.CodeWriter;

public class test_JUNK {
    public static void main(String args[]) throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(
                        "./tests/resources/parser/xi/exprs.xi")));

        ScannerBuffer lexer = new ScannerBuffer(new MultiFileLexer(reader, false));

        XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
        LinkedList<ExprNode> v = (LinkedList<ExprNode>) p.parse().value;
        for (ExprNode e : v) {
            CodeWriterSExpPrinter printer = new CodeWriterSExpPrinter(new PrintWriter(System.out));
            e.prettyPrint(printer);
            printer.flush();
            printer.close();
        }
//        IxiProgramNode b = (IxiProgramNode) v;
//        CodeWriterSExpPrinter printer = new CodeWriterSExpPrinter(
//                new PrintWriter(System.out));
//        ((NodeInterface)v).prettyPrint(printer);
//        printer.flush();
//        printer.close();
    }
}
