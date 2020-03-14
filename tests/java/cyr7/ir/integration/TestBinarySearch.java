package cyr7.ir.integration;

import cyr7.ir.integration.Run.RunConfiguration;

public class TestBinarySearch extends TestProgram {

    private final long[] array = new long[]
               { 72, 101, 108, 110, 111, 119, 120, 124, 138, 200 };

    @Override
    protected String filename() {
        return "binarySearch";
    }

    @Override
    protected String expected() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            buffer.append(i + "\n");
        }
        return buffer.toString();
    }


    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration()
                .args(new long[][] { array });
    }
}
