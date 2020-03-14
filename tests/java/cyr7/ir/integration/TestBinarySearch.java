package cyr7.ir.integration;

import cyr7.ir.integration.Run.RunConfiguration;

public class TestBinarySearch extends TestProgram {

    @Override
    protected String filename() {
        return "binarySearch";
    }

    @Override
    protected String expected() {
        return "0\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration()
                .args(new long[][] { new long[] { 72 }, new long[] { 72, 101,
                        108, 108, 111, 119, 120, 124, 138, 200 } });
    }
}
