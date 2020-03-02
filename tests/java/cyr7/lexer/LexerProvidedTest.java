package cyr7.lexer;

import cyr7.exceptions.lexer.LexerException;
import cyr7.parser.sym;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class LexerProvidedTest {

    private void testFile(String filename) throws Exception {
        MyLexer lexer = new MyLexer(new FileReader(
                           getClass().getClassLoader()
                                     .getResource(
                                             "lexer/"+ filename+ ".xi")
                                     .getFile()));

        Scanner solution = new Scanner(new InputStreamReader(
                            getClass().getClassLoader()
                                      .getResourceAsStream(
                                              "lexer/"
                                              + filename
                                              + ".lexedsol")));

        while (solution.hasNextLine()) {
            String line = solution.nextLine();
            System.out.print(line);

            ComplexSymbol token;
            try {
                token = lexer.next_token();
                assertEquals(line, LexerUtil.fullDescription(token));
            } catch (LexerException e) {
                Pattern regexActual = Pattern.compile("[1-9][0-9]*");
                Matcher matchActual = regexActual.matcher(e.getMessage());

                Pattern regexExpected = Pattern.compile("[1-9][0-9]*");
                Matcher matchExpected = regexExpected.matcher(line);
                for (int i = 0; i < 2; i++) {
                    if (matchActual.find() && matchExpected.find()) {
                        String numberActual = matchActual.group();
                        String numberExpected = matchExpected.group();
                        assertEquals(numberActual, numberExpected);
                    } else {
                        fail("Error message does not contain "
                                + "line and column numbers.");
                    }
                }
                assertFalse(solution.hasNextLine());
                solution.close();
                System.out.println(" (pass)");
                return;
            }
            System.out.println(" (pass)");
        }

        assertEquals(sym.EOF, lexer.next_token().sym);
        assertFalse(solution.hasNextLine());
        solution.close();
    }

    @Test
    void testFactorial() throws Exception {
        testFile("factorial");
    }

    @Test
    void testFibonacci() throws Exception {
        testFile("fibonacci");
    }

    @Test
    void testUnescapingChar() throws Exception {
        testFile("unescapingChar");
    }

    @Test
    void testUnescapingString() throws Exception {
        testFile("unescapingstring");
    }

}
