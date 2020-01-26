package cyr7.lexer;

import java.io.*;

public class TempMain {

    public static void main(String[] args) throws IOException {
//        String toLex =
//                "use io\n" +
//                "\n" +
//                "main(args: int[][]) {\n" +
//                "  print(\"Hello, Worl\\x64!\\n\")\n" +
//                "  c3po: int = 'x' + 47;\n" +
//                "  r2d2: int = c3po // No Han Solo\n" +
//                "}\n";
//
//        lex(new StringReader(toLex), System.out);

        File toLex = new File("pa1/ex1.xi");
        lex(new FileReader(toLex), System.out);
    }

    private static void lex(Reader input, PrintStream output) throws IOException {
        MyLexer lexer = new MyLexer(input);
        MyLexer.Token token;
        while ((token = lexer.nextToken()) != null) {
            output.println((token.lineNumber + 1) + ":" + (token.columnNumber + 1) + " " + token);
        }
    }

}
