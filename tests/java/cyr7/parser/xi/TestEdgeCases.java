package cyr7.parser.xi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

import cyr7.lexer.MultiFileLexer;
import cyr7.parser.XiParser;
import java_cup.runtime.ComplexSymbolFactory;

class TestEdgeCases {

    String program;
    XiParser parser;
    
    @Test
    void readElementOfBraceTest() throws Exception {
        program = "main() { i: int = {2,4,6}[1]; }";
        parser = new XiParser(
                new MultiFileLexer(new StringReader(program), "", false), 
                new ComplexSymbolFactory());
        assertDoesNotThrow(parser::parse);

        program = "main() { if ({}[0]) { i: int; } else {i: int; } return; }";
        parser = new XiParser(
                new MultiFileLexer(new StringReader(program), "", false), 
                new ComplexSymbolFactory());
        assertDoesNotThrow(parser::parse);
    }

    
    
    @Test
    void returnMustBeLastStatementInABlock() throws Exception {
        program = "main() { return; i: int; }";
        parser = new XiParser(
                new MultiFileLexer(new StringReader(program), "", false), 
                new ComplexSymbolFactory());
        assertThrows(Exception.class, parser::parse);

        program = "main() { if (true) return; else return; }";
        parser = new XiParser(
                new MultiFileLexer(new StringReader(program), "", false), 
                new ComplexSymbolFactory());
        assertThrows(Exception.class, parser::parse);
    }
}
