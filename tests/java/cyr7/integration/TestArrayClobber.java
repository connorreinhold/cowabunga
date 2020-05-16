package cyr7.integration;

public class TestArrayClobber extends TestProgram {

    @Override
    protected String filename() {
        return "arrayClobber";
    }

    @Override
    protected String expected() {
        return "No Clobbering: Array\n" +
                "No Clobbering: Int\n";
    }
}
