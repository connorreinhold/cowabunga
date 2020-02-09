package cyr7.parser.xi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import cyr7.lexer.MyLexer;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import cyr7.parser.ixi.IxiParser;

public class test_JUNK {
    public static void main(String args[]) throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(
                        "./tests/resources/parser/ixi/collection.ixi")));
        ScannerBuffer lexer = new ScannerBuffer(new MyLexer(reader));
        IxiParser p = new IxiParser(lexer, new ComplexSymbolFactory());
        p.parse();
    }
}
