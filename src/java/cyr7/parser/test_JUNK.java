package cyr7.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import cyr7.lexer.ixi.IxiLexer;
import cyr7.parser.ixi.IxiParser;

public class test_JUNK {
    public static void main(String args[]) throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(
                        "./tests/resources/parser/ixi/collection.ixi")));
        ScannerBuffer lexer = new ScannerBuffer(new IxiLexer(reader,
                new ComplexSymbolFactory()));
        IxiParser p = new IxiParser(lexer, new ComplexSymbolFactory());
        Object v = p.parse().value;
        System.out.println(v);
    }
}
