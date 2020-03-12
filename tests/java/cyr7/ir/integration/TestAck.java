package cyr7.ir.integration;

public class TestAck extends TestProgram {

    @Override
    protected String filename() {
        return "ack";
    }

    @Override
    protected String expected() {
        return "Ack(2,11): 25\n";
    }

}
