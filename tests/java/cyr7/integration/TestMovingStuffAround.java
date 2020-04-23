package cyr7.integration;

public class TestMovingStuffAround extends TestProgram {

    @Override
    protected String filename() {
        return "movingStuffAround";
    }

    @Override
    protected String expected() {
        return
            "2\n" +
                "4294967296\n" +
                "1152921504606846976\n" +
                "-2\n" +
                "-4294967296\n" +
                "-1152921504606846976\n" +
                "4294967296\n" +
                "1152921504606846976\n" +
                "2\n" +
                "4294967296\n" +
                "1152921504606846976\n" +
                "-2\n" +
                "-4294967296\n" +
                "-1152921504606846976\n" +
                "2\n" +
                "4294967296\n" +
                "1152921504606846976\n" +
                "-2\n" +
                "-4294967296\n" +
                "-1152921504606846976\n";
    }

}
