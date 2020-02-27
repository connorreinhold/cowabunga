package cyr7.typecheck;

import cyr7.ast.Node;
import cyr7.exceptions.LexerException;
import cyr7.exceptions.ParserException;
import cyr7.exceptions.semantics.SemanticException;
import cyr7.parser.ParserUtil;

import java.io.Reader;
import java.io.Writer;

public class TypeCheckUtil {
    
    public static void typeCheck(Reader reader, Writer writer, String filename, 
            IxiFileOpener opener) throws Exception {
        try {
            Node node = ParserUtil.parseNode(reader, filename, false);
            node.accept(new TypeCheckVisitor(opener));
            writer.append("Valid Xi Program").append(System.lineSeparator());
        } catch (ParserException e) {
            printErrorToStdOut(ErrorType.Syntax, e.filename, 
                    e.line, e.column, e.errorMsg);
            writer.append(e.getMessage()).append(System.lineSeparator());
        } catch (SemanticException e) {
            printErrorToStdOut(ErrorType.Semantic, e.filename, e.line, e.col, 
                    e.errorMsg);
            writer.append(e.getMessage()).append(System.lineSeparator());
        } catch (LexerException e) {
            printErrorToStdOut(ErrorType.Lexical, e.filename, e.line, e.col, 
                    e.errorMsg);
            writer.append(e.getMessage()).append(System.lineSeparator());
        }
    }
    
    private static void printErrorToStdOut(ErrorType type, String filename, 
            int line, int col, String msg) {
        System.out.println(
                String.format("%s error beginning at %s:%d:%d: %s", 
                        type, filename, line, col, msg));
    }
    
    protected static enum ErrorType {
        Lexical, Semantic, Syntax;
    }    
}
