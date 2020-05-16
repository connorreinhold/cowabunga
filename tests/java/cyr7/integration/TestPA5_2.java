package cyr7.integration;

public class TestPA5_2 extends TestProgram {

    @Override
    protected String filename() {
        return "group_of_anonymous01__side_effect";
    }

    @Override
    protected String expected() {
        return "Pass: binop\n" +
                "Pass: call args\n" +
                "Pass: assign\n" +
                "Pass: assign2\n";
    }
}
