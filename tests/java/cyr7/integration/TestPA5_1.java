package cyr7.integration;

public class TestPA5_1 extends TestProgram {

    @Override
    protected String filename() {
        return "group_of_anonymous01__ret_overwrite";
    }

    @Override
    protected String expected() {
        return "_xi_out_of_bounds called\n";
    }
}
