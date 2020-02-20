package cyr7.parser;

import java.io.*;

import cyr7.ast.AbstractNode;
import cyr7.exceptions.ParserException;
import cyr7.lexer.MultiFileLexer;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;

public class test_JUNK {
    public static void main(String args[]) throws Exception {
//        LexerUtil.lex(new FileReader("./tests/resources/parser/testjunk.ixi"), new PrintWriter(System.out));
        try {
            Reader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(
                            "./tests/resources/parser/testjunk.ixi")));

            ScannerBuffer lexer = new ScannerBuffer(new MultiFileLexer(reader, true));

            XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
            AbstractNode v = (AbstractNode) p.parse().value;
            SExpVisitor visitor = new SExpVisitor(new PrintWriter(System.out));
            v.accept(visitor);
            visitor.flush();
        } catch (ParserException e) {
            System.out.println(e.getMessage());
        }
    }

}
