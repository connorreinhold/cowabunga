package cyr7.integration;

import cyr7.integration.Run.RunConfiguration;

/**
 * Note this interpreter does not care about precedence. So 1 - 1 * 3 = 0
 * <p>
 * Also it doesn't like parentheses.
 * <p>
 * This is a sad intepreter. It's having it rough. Please treat it with care.
 *
 * @author ayang
 *
 */
public class TestExprInterpreter extends TestProgram {

    @Override
    protected String filename() {
        return "exprInterpreter";
    }

    @Override
    protected String expected() {
        return "4\n" + "0\n" + "2\n" + "8\n";
    }

    @Override
    protected RunConfiguration configuration() {
        return new RunConfiguration().args(
            "2 + 2",
            "2-2*3",
            "1 + 1",
            "2*4"
        ).bigHeap(true);
    }

}

