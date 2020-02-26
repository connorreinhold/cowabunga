package cyr7.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.exceptions.ParserException;
import cyr7.lexer.MultiFileLexer;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

public class test_JUNK {

    public static void main(String[] args) {
        try {
            Reader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(
                            "./tests/resources/parser/testjunk.xi")));

            ScannerBuffer lexer = new ScannerBuffer(new MultiFileLexer(reader, false));

            XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
            Node v = (Node) p.parse().value;

            SExpVisitor visitor = new SExpVisitor(new PrintWriter(System.out));
            v.accept(visitor);
            visitor.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
