package cyr7.ir.integration;

import cyr7.ir.integration.Run.RunConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPrograms {

    @Test
    void testAck() throws Exception {
        String result = Run.runFile("ack");
        assertEquals("Ack(2,11): 25\n", result);
    }

    @Test
    void testArithmetic() throws Exception {
        String result = Run.runFile("arithmetic");
        String expected =
            "16\n"
            + "500\n";
        assertEquals(expected, result);
    }
    
    @Test
    void testArithmeticIteration() throws Exception {
        RunConfiguration configuration = new RunConfiguration().bigHeap(true);
        String result = Run.runFile("arithmetic2", configuration);
        String expected = "3\n2\n7\n10\nlHe\nsm\nCdefghijklmnopqrstuvwxyzAb\n100\n101\n";
        assertEquals(expected, result);
    }
    
    @Test
    void testHighMult() throws Exception {
        String result = Run.runFile("highMult");
        String expected = "72057594037927935\n51764673395029616\n";
        assertEquals(expected, result);
    }
    
    @Test
    void testCTranslation() throws Exception {
        String result = Run.runFile("ctranslation");
        String expected = "1\n0\n0\n5\n50\n";
        assertEquals(expected, result);
    }

    @Test
    void testFibonacci() throws Exception {
        String result = Run.runFile("fibonacci");
        String expected = "8";
        assertEquals(expected, result);
    }

    @Test
    void testFactorial() throws Exception {
        String result = Run.runFile("factorial");
        String expected = "Factorial 10 is: 3628800";
        assertEquals(expected, result);
    }

    @Test
    void testStarTriangle() throws Exception {
        String result = Run.runFile("startriangle");
        System.out.println(result);
        String expected =
            "\n" +
            "*\n" +
            "**\n" +
            "***\n" +
            "****\n" +
            "*****\n" +
            "******\n" +
            "*******\n" +
            "********\n" +
            "*********\n" +
            "***********\n" +
            "**********\n" +
            "*********\n" +
            "********\n" +
            "*******\n" +
            "******\n" +
            "*****\n" +
            "****\n" +
            "***\n" +
            "**\n" +
            "*\n";
        assertEquals(expected, result);
    }

    @Test
    void testMultiDimArray() throws Exception {
        String result = Run.runFile("multidimarray");
        String expected = "abcdeghijkmnopqstuvwyz{|}\n"
            + "[[3, 0, 2, 1][0, 2, 3, 1][0, 3, 1, 2][1, 3, 2, 0]]";
        assertEquals(expected, result);
    }

    @Test
    void testEcho() throws Exception {
        RunConfiguration runConfiguration = new RunConfiguration().args(new long[][]{
            new long[]{ 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100 }
        });

        String result = Run.runFile("echo", runConfiguration);
        assertEquals("Hello world\n", result);
    }

    @Test
    void testScope() throws Exception {
        String result = Run.runFile("scope");
        assertEquals("0", result);
    }

}
