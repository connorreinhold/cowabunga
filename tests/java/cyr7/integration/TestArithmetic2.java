package cyr7.integration;

public class TestArithmetic2 extends TestProgram {
    @Override
    protected String filename() {
        return "arithmetic2";
    }

    @Override
    protected String expected() {
        return "3\n2\n7\n10\nlHe\nsm\nCdefghijklmnopqrstuvwxyzAb\n100\n101\n-1\n";
    }
}
