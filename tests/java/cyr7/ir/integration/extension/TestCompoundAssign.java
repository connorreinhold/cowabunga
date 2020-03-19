package cyr7.ir.integration.extension;

import cyr7.ir.integration.TestProgram;

public class TestCompoundAssign extends TestProgram {

    @Override
    protected String filename() {
        return "compoundAssign";
    }

    @Override
    protected String expected() {
        return "1\n" + "77\n" + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n"
                + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n";
    }
}
