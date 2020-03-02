package cyr7.typecheck;

import java.io.Reader;
import java.io.Writer;

import cyr7.exceptions.ErrorType;
import cyr7.exceptions.LexerException;
import cyr7.exceptions.ParserException;
import cyr7.exceptions.semantics.InvalidInterfaceException;
import cyr7.exceptions.semantics.SemanticException;
import cyr7.parser.ParserUtil;

public class TypeCheckUtil {

    public static void typeCheck(Reader reader, Writer writer, String filename,
            boolean isIXI, IxiFileOpener opener) throws Exception {
        try {
            ParserUtil.parseNode(reader, filename, isIXI)
                .accept(new TypeCheckVisitor(opener));
            writer.append("Valid Xi Program").append(System.lineSeparator());
        } catch (InvalidInterfaceException e) {
            printErrorToStdOut(e.getInterfaceErrorType(),
                    e.getInterfaceFilename(),
                    e.getInterfaceLine(),
                    e.getInterfaceCol(),
                    e.getInterfaceErrorMsg());
            writer.append(e.getMessage())
                  .append(System.lineSeparator());
        } catch (ParserException e) {
            printErrorToStdOut(ErrorType.Syntax,
                    e.filename,
                    e.line,
                    e.column,
                    e.errorMsg);
            writer.append(e.getMessage()).append(System.lineSeparator());
        } catch (SemanticException e) {
            printErrorToStdOut(ErrorType.Semantic,
                    e.filename,
                    e.line,
                    e.col,
                    e.errorMsg);
            writer.append(e.getMessage()).append(System.lineSeparator());
        } catch (LexerException e) {
            printErrorToStdOut(ErrorType.Lexical,
                    e.filename,
                    e.line,
                    e.col,
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

}
