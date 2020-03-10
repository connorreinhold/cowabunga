package cyr7.ir.integration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WilliamTestProgram {

    @Test
    void testMultiDimArray() throws Exception {
        String result = Run.runFile("multidimarray");
        assertEquals("", result);
    }

    @Test
    void testGaleShapley() throws Exception {
        String result = Run.runFile("galeshapley", new int[][]{
            new int[]{1}
        });
        assertEquals("", result);
        result = Run.runFile("galeshapley", new int[][]{
            new int[]{ 1, 3, 0, 2 },
            new int[]{ 2, 1, 3, 0 },
            new int[]{ 2, 3, 0, 1 },
            new int[]{ 2, 0, 1, 3 },
            new int[]{ 1, 3, 2, 0 },
            new int[]{ 0, 3, 1, 2 },
            new int[]{ 0, 2, 3, 1 },
            new int[]{ 3, 0, 2, 1 },
        });
        assertEquals("", result);
    }

    @Test
    void testEcho() throws Exception {
        String result = Run.runFile("echo", new int[][]{
            new int[]{ 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100 }
        });
        assertEquals("Hello world\n", result);
    }

    @Test
    void testScope() throws Exception {
        String result = Run.runFile("scope");
        assertEquals("0", result);
    }

}
