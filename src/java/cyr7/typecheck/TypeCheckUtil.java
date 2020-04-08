package cyr7.typecheck;

import cyr7.ast.Node;
import cyr7.exceptions.ErrorType;
import cyr7.exceptions.lexer.LexerException;
import cyr7.exceptions.parser.ParserException;
import cyr7.exceptions.semantics.InvalidInterfaceException;
import cyr7.exceptions.semantics.SemanticException;
import cyr7.parser.ParserUtil;
import cyr7.visitor.PostOrderReduceTraversal;

import java.io.Reader;
import java.io.Writer;

public class TypeCheckUtil {

    public static boolean typeCheck(Reader reader, Writer writer, String filename,
            boolean isIXI, IxiFileOpener opener) throws Exception {
        try {
            Node result = ParserUtil.parseNode(reader, filename, isIXI);
            result.accept(new TypeCheckVisitor(opener));
            assert satisfiesInvariants(result);
            writer.append("Valid Xi Program").append(System.lineSeparator());
            return true;
        } catch (InvalidInterfaceException e) {
            printErrorToStdOut(e.getInterfaceErrorType(),
                    e.getInterfaceFilename(),
                    e.getInterfaceLine(),
                    e.getInterfaceCol(),
                    e.getInterfaceErrorMsg());
            writer.append(e.getMessage())
                  .append(System.lineSeparator());
            return false;
        } catch (ParserException e) {
            printErrorToStdOut(ErrorType.Syntax,
                    e.filename,
                    e.line,
                    e.column,
                    e.errorMsg);
            writer.append(e.getMessage()).append(System.lineSeparator());
            return false;
        } catch (SemanticException e) {
            printErrorToStdOut(ErrorType.Semantic,
                    e.filename,
                    e.line,
                    e.col,
                    e.errorMsg);
            writer.append(e.getMessage()).append(System.lineSeparator());
            return false;
        } catch (LexerException e) {
            printErrorToStdOut(ErrorType.Lexical,
                    e.filename,
                    e.line,
                    e.col,
                    e.errorMsg);
            writer.append(e.getMessage()).append(System.lineSeparator());
            return false;
        }
    }

    private static void printErrorToStdOut(ErrorType type, String filename,
            int line, int col, String msg) {
        System.out.println(
                String.format("%s error beginning at %s:%d:%d: %s",
                        type, filename, line, col, msg));
    }

    public static void typeCheckNoIxiFiles(Node n) {
        typeCheck(n, filename -> Reader.nullReader());
    }

    /**
     * Typechecks an Xi Program node. Interface files are completely discarded
     * by this method.
     *
     * @param n
     */
    public static void typeCheck(Node n, IxiFileOpener fileOpener) {
        n.accept(new TypeCheckVisitor(fileOpener));
        assert satisfiesInvariants(n);
    }

    public static boolean satisfiesInvariants(Node n) {
        return new PostOrderReduceTraversal<>(new AstInvariantVisitor()).traverse(n);
    }

}
