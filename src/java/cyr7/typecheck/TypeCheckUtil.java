package cyr7.typecheck;

import java.io.Reader;
import java.io.Writer;

import cyr7.ast.AbstractNode;
import cyr7.exceptions.ParserException;
import cyr7.lexer.MultiFileLexer;
import cyr7.parser.XiParser;
import cyr7.semantics.Context;
import cyr7.semantics.HashMapStackContext;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

public class TypeCheckUtil {
    
    public static void typeCheck(Reader reader, Writer writer, boolean isIXI) 
            throws Exception {
        ScannerBuffer lexer = new ScannerBuffer(
                                    new MultiFileLexer(reader, isIXI));
        
        try {
            XiParser p = new XiParser(lexer, new ComplexSymbolFactory());
            AbstractNode node = (AbstractNode) p.parse().value;
            Context context = new HashMapStackContext();
            TypeCheckVisitor visitor = new TypeCheckVisitor(context);
            node.accept(visitor);
        } catch (ParserException e) {
            writer.append(e.getMessage()).append(System.lineSeparator());
        }
        writer.flush();
    }
    
    
}
