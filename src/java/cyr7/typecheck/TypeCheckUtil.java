package cyr7.typecheck;

import cyr7.ast.Node;
import cyr7.exceptions.LexerException;
import cyr7.exceptions.ParserException;
import cyr7.exceptions.semantics.SemanticException;
import cyr7.parser.ParserUtil;

import java.io.Reader;
import java.io.Writer;

public class TypeCheckUtil {
    
    public static void typeCheck(Reader reader, Writer writer, 
            IxiFileOpener opener) throws Exception {
        try {
            Node node = ParserUtil.parseNode(reader, false);
            node.accept(new TypeCheckVisitor(opener));
            writer.append("Valid Xi Program").append(System.lineSeparator());
        } catch (ParserException | SemanticException | LexerException e) {
            writer.append(e.getMessage()).append(System.lineSeparator());
        }
    }

}
