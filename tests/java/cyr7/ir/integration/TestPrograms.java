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
        String expected = "32767";
        System.out.println(expected);
        assertEquals("1", "2");
    }

}
