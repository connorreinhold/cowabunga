package cyr7.lexer;

import cyr7.exceptions.lexer.LexerException;
import cyr7.parser.sym;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

import java.io.IOException;
import java.io.Reader;

public class MultiFileLexer extends MyLexer {
    private final boolean isIXI;
    private boolean first = true;

    public MultiFileLexer(Reader r, String filename, boolean isIXI) {
        super(r, filename);
        this.isIXI = isIXI;
    }

    @Override
    public ComplexSymbol next_token() throws IOException, LexerException {
        if (first) {
            first = false;
            return symbol(isIXI ? sym.IXI_FILE : sym.XI_FILE);
        }
        return super.next_token();
    }
}
