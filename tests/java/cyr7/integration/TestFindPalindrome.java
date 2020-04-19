package cyr7.integration;

import cyr7.integration.Run.RunConfiguration;

public class TestFindPalindrome extends TestProgram {
    @Override
    protected String filename() {
        return "o2findPalindrome";
    }

    @Override
    protected String expected() {
        return "ll\n" + "racecar\n" + "O\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().bigHeap(true);
    }

}
