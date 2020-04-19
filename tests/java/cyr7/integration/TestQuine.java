package cyr7.integration;

import cyr7.integration.Run.RunConfiguration;

import java.io.InputStream;

/*
 * A quine is a program that outputs itself. The expected output of the program
 * should be the program itself. That's why the expected result for this program
 * is just the file itself.
 */
public class TestQuine extends TestProgram {
    @Override
    protected String filename() {
        return "quine";
    }

    @Override
    protected String expected() {
        try {
            InputStream filePath = Run.class
                .getClassLoader()
                .getResourceAsStream("integration/quine.xi");
            return new String(filePath.readAllBytes());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().bigHeap(true);
    }
}
