package cyr7.lexer;

import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class LexerProvidedTest {

    private void testFile(String filename) throws IOException {
        MyLexer lexer = new MyLexer(new FileReader(getClass().getClassLoader().getResource("lexer/pa1/" + filename + ".xi").getFile()));
        Scanner solution = new Scanner(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("lexer/pa1/" + filename + ".lexedsol")));

        MyLexer.Token token;
        while ((token = lexer.nextToken()) != null && solution.hasNextLine()) {
            String line = solution.nextLine();
            assertEquals(line, TokenUtils.fullDescription(token));
        }

        assertNull(lexer.nextToken());
        assertFalse(solution.hasNextLine());
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
