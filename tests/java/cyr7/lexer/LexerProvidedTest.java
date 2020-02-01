package cyr7.lexer;

import cyr7.exceptions.LexerException;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class LexerProvidedTest {

    private void testFile(String filename) throws IOException {
        MyLexer lexer = new MyLexer(new FileReader(getClass().getClassLoader()
                .getResource("lexer/pa1/" + filename + ".xi").getFile()));
        Scanner solution = new Scanner(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(
                        "lexer/pa1/" + filename + ".lexedsol")));

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
                        fail("Error message does not contain line and column numbers.");
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
    void testAdd() throws IOException {
        testFile("add");
    }

    @Test
    void testArrayInit() throws IOException {
        testFile("arrayinit");
    }

    @Test
    void testArrayInit2() throws IOException {
        testFile("arrayinit2");
    }

    @Test
    void testBeauty() throws IOException {
        testFile("beauty");
    }

    @Test
    void testEx1() throws IOException {
        testFile("ex1");
    }

    @Test
    void testEx2() throws IOException {
        testFile("ex2");
    }

    @Test
    void testGcd() throws IOException {
        testFile("gcd");
    }

    @Test
    void testInsertionSort() throws IOException {
        testFile("insertionSort");
    }

    @Test
    void testMdarrays() throws IOException {
        testFile("mdarrays");
    }

    @Test
    void testRatAdd() throws IOException {
        testFile("ratadd");
    }

    @Test
    void testRatAddUse() throws IOException {
        testFile("ratAddUse");
    }

    @Test
    void testSpec1() throws IOException {
        testFile("spec1");
    }

    @Test
    void testSpec2() throws IOException {
        testFile("spec2");
    }

    @Test
    void testSpec3() throws IOException {
        testFile("spec3");
    }

}
