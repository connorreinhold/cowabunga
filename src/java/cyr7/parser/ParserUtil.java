package cyr7.parser;

import cyr7.ast.AbstractNode;
import cyr7.exceptions.ParserException;
import cyr7.lexer.MultiFileLexer;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

import java.io.*;

public class ParserUtil {

    public static void parse(Reader reader, Writer writer, boolean isIXI) throws Exception {
        ScannerBuffer lexer = new ScannerBuffer(new MultiFileLexer(reader, isIXI));

        try {
            XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
            AbstractNode node = (AbstractNode) p.parse().value;
            SExpVisitor visitor = new SExpVisitor(writer);
            node.accept(visitor);
            visitor.flush();
        } catch (ParserException e) {
            writer.append(e.getMessage()).append(System.lineSeparator());
            writer.flush();
        }
    }

    public static void printSExpr(AbstractNode node) {
        SExpVisitor visitor = new SExpVisitor(new PrintWriter(System.out));
        node.accept(visitor);
        visitor.flush();
    }

    public static String toSExpr(AbstractNode node) {
        StringWriter writer = new StringWriter();
        SExpVisitor visitor = new SExpVisitor(new PrintWriter(writer));
        node.accept(visitor);
        visitor.flush();
        return writer.toString().trim();
    }

}
