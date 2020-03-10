package cyr7.ir.integration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPrograms {

    //@Test
    void testAck() throws Exception {
        String result = Run.runFile("ack");
        assertEquals("Ack(2,11): 25\n", result);
    }

    //@Test
    void testArithmetic() throws Exception {
        String result = Run.runFile("arithmetic");
        String expected =
            "16\n"
            + "500\n";
        assertEquals(expected, result);
    }
    
    //@Test
    void testArithmeticIteration() throws Exception {
        String result = Run.runFile("arithmetic2");
        String expected = "3\n2\n7\n10\nlHe\nsm\nCdefghijklmnopqrstuvwxyzAb\n100\n101\n";
        assertEquals(expected, result);
    }
    
    //@Test
    void testHighMult() throws Exception {
        String result = Run.runFile("highMult");
        String expected = "9999";
        assertEquals(expected, result);
    }
    
    @Test
    void testCTranslation() throws Exception {
        String result = Run.runFile("ctranslation");
        String expected = "1\n0\n0\n5\n";
        assertEquals(expected, result);
    }

}
