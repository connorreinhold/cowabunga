package cyr7.lexer;

import cyr7.exceptions.*;
import cyr7.parser.sym;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

import java.io.IOException;
import java.io.Reader;

public class MultiFileLexer extends MyLexer {
    private final boolean isIXI;
    private boolean first = true;

    public MultiFileLexer(Reader r, boolean isIXI) {
        super(r);
        this.isIXI = isIXI;
    }

    @Override
    public ComplexSymbol next_token() throws IOException, InvalidCharacterLiteralException, InvalidStringEscapeCharacterException, LeadingZeroIntegerException, InvalidTokenException, MultiLineStringException, MultiLineCharacterException {
        if (first) {
            first = false;
            return symbol(isIXI ? sym.IXI_FILE : sym.XI_FILE);
        }
        return super.next_token();
    }
}
