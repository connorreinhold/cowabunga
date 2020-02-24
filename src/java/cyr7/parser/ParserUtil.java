package cyr7.parser;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.exceptions.ParserException;
import cyr7.lexer.MultiFileLexer;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class ParserUtil {

    public static void parse(Reader reader, Writer writer, boolean isIXI) 
            throws Exception {
        ScannerBuffer lexer = new ScannerBuffer(
                            new MultiFileLexer(reader, isIXI));

        try {
            XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
            AbstractNode node = (AbstractNode) p.parse().value;
            SExpVisitor visitor = new SExpVisitor(writer);
            node.accept(visitor);
        } catch (ParserException e) {
            writer.append(e.getMessage()).append(System.lineSeparator());
        }
    }

    public static void printSExpr(Node node) {
        SExpVisitor visitor = new SExpVisitor(new PrintWriter(System.out));
        node.accept(visitor);
        visitor.flush();
    }

    public static String toSExpr(Node node) {
        StringWriter writer = new StringWriter();
        SExpVisitor visitor = new SExpVisitor(new PrintWriter(writer));
        node.accept(visitor);
        return writer.toString().strip();
    }
}
