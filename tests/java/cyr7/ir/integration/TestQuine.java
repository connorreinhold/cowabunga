package cyr7.ir.integration;

import cyr7.ir.integration.Run.RunConfiguration;

import java.io.InputStream;

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
                .getResourceAsStream("irgen/quine.xi");
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
