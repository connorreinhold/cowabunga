package cyr7.ir.integration;

import cyr7.ir.integration.Run.RunConfiguration;

public class TestDictionary extends TestProgram {

    @Override
    protected String filename() {
        // TODO Auto-generated method stub
        return "dictionary";
    }

    @Override
    protected String expected() {
        return "Length: 3\n"
                + "Length: 2\n"
                + "Length: 2\n"
                + "Length: 2\n"
                + "Value1: 12\n"
                + "Found1: true\n"
                + "Value2: 0\n"
                + "Found2: false\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().bigHeap(true);
    }

}
