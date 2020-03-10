package cyr7.ir.integration;

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
        String result = Run.runFile("arithmeticiteration");
        System.out.println(result);
        String expected = "2";
        System.out.println(expected);
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
    void testPyramid() throws Exception {

    }

}
