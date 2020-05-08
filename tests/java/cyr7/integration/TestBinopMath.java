package cyr7.integration;

import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestBinopMath extends TestProgram {

    private final long[] array = new long[]
               { 72, 101, 108, 110, 111, 119, 120, 124, 138, 200 };

    @Override
    protected String filename() {
        return "binopMath";
    }

    @Override
    protected String expected() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 7; i++) {
            buffer.append("500500" + "\n");
        }
        buffer.append("14\n");
        return buffer.toString();
    }
}
