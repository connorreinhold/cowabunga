package cyr7.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class test_JUNK {

    public static void main(String[] args) {
        try {
            Reader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(
                            "./tests/resources/parser/testjunk.xi")));

            /*
             * ParserUtil.parseNode(reader, "testjunk.xi", false) .accept(new
             * TypeCheckVisitor(null));
             * 
             * System.out.println("Accepted!");
             */
            /*
             * ScannerBuffer lexer = new ScannerBuffer(new
             * MultiFileLexer(reader, false));
             * 
             * XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
             * Node v = (Node) p.parse().value;
             * 
             * SExpVisitor visitor = new SExpVisitor(new
             * PrintWriter(System.out)); v.accept(visitor); visitor.flush();
             */
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

}
