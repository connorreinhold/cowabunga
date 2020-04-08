package cyr7.parser;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.exceptions.lexer.LexerException;
import cyr7.exceptions.parser.ParserException;
import cyr7.lexer.MultiFileLexer;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

public class ParserUtil {

    public static boolean parse(Reader reader, Writer writer, String filename,
            boolean isIXI) throws IOException {
        ScannerBuffer lexer = new ScannerBuffer(
                new MultiFileLexer(reader, filename, isIXI));

        try {
            XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
            AbstractNode node = (AbstractNode) p.parse().value;
            SExpVisitor visitor = new SExpVisitor(writer);
            node.accept(visitor);
            visitor.flush();
            return true;
        } catch (ParserException | LexerException e) {
            writer.append(e.getMessage()).append(System.lineSeparator());
            return false;
        } catch (Exception e) {
            writer.append(e.getMessage()).append(System.lineSeparator());
            return false;
        }
    }

    public static Node parseNode(Reader reader, String filename, boolean isIxi)
            throws Exception {
        ScannerBuffer lexer = new ScannerBuffer(new MultiFileLexer(reader,
                filename, isIxi));
        XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
        return (Node) p.parse().value;
    }

    public static void printSExpr(Node node) {
        SExpVisitor visitor = new SExpVisitor(new PrintWriter(System.out));
        node.accept(visitor);
        visitor.flush();
    }

}
