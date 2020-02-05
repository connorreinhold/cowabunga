package cyr7.lexer;

import cyr7.exceptions.LexerException;
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
                                             "lexer/pa1/"+ filename+ ".xi")
                                     .getFile()));
        
        Scanner solution = new Scanner(new InputStreamReader(
                            getClass().getClassLoader()
                                      .getResourceAsStream(
                                              "lexer/pa1/"
                                              + filename 
                                              + ".lexedsol")));

        while (solution.hasNextLine()) {
            String line = solution.nextLine();
            System.out.print(line);

            MyLexer.Token token;
            try {
                token = lexer.nextToken();
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

        assertNull(lexer.nextToken());
        assertFalse(solution.hasNextLine());
        solution.close();
    }

    @Test
    void testAdd() throws Exception {
        testFile("add");
    }

    @Test
    void testArrayInit() throws Exception {
        testFile("arrayinit");
    }

    @Test
    void testArrayInit2() throws Exception {
        testFile("arrayinit2");
    }

    @Test
    void testBeauty() throws Exception {
        testFile("beauty");
    }

    @Test
    void testEx1() throws Exception {
        testFile("ex1");
    }

    @Test
    void testEx2() throws Exception {
        testFile("ex2");
    }

    @Test
    void testGcd() throws Exception {
        testFile("gcd");
    }

    @Test
    void testInsertionSort() throws Exception {
        testFile("insertionSort");
    }

    @Test
    void testMdarrays() throws Exception {
        testFile("mdarrays");
    }

    @Test
    void testRatAdd() throws Exception {
        testFile("ratadd");
    }

    @Test
    void testRatAddUse() throws Exception {
        testFile("ratAddUse");
    }

    @Test
    void testSpec1() throws Exception {
        testFile("spec1");
    }

    @Test
    void testSpec2() throws Exception {
        testFile("spec2");
    }

    @Test
    void testSpec3() throws Exception {
        testFile("spec3");
    }

}
