package cyr7.integration;

public class TestAddMutableArray extends TestProgram {

    @Override
    protected String filename() {
        return "addMutableArray";
    }

    @Override
    protected String expected() {
        return "123\n" + "43\n" + "65\n" + "123\n" + "43\n" + "65\n" + "500\n"
                + "43\n" + "65\n" + "500\n" + "43\n" + "65\n";
    }

}
