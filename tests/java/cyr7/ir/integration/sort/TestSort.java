package cyr7.ir.integration.sort;

import cyr7.ir.integration.Run.RunConfiguration;
import cyr7.ir.integration.TestProgram;

import java.util.ArrayList;
import java.util.Random;

public abstract class TestSort extends TestProgram {

    private static final int TEST_SIZE = 100;

    private final long[] input;
    private final String expected;

    TestSort() {
        ArrayList<Long> longs = new ArrayList<>();
        for (long i = 0; i < TEST_SIZE; i++) {
            longs.add(i);
        }
        input = new long[TEST_SIZE];
        Random random = new Random();
        for (int i = 0; i < TEST_SIZE; i++) {
            input[i] = longs.remove(random.nextInt(longs.size()));
        }

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (int i = 0; i < TEST_SIZE; i++) {
            sb.append(i);
            sb.append(',');
        }
        sb.append('}');
        sb.append('\n');
        expected = sb.toString();
    }

    @Override
    protected String expected() {
        return expected;
    }

    @Override
    protected RunConfiguration configuration() {
        String[] args = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            args[i] = Long.toString(input[i]);
        }
        return new RunConfiguration().args(args);
    }

}
