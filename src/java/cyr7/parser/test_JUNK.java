package cyr7.parser;

import java.io.InputStreamReader;

import cyr7.parser.MyParser;
import cyr7.lexer.MyLexer;

public class test_JUNK {
	public static void main(String args[]) throws Exception {
		MyParser p = new MyParser();
        p.setScanner(new MyLexer(new InputStreamReader(System.in)));
		p.parse();
	}
}
