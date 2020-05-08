package cyr7.integration.brainf;

import cyr7.integration.Run.RunConfiguration;
import cyr7.integration.TestProgram;
import org.junit.jupiter.api.Tag;

@Tag("core")
public class TestBrainfHelloWorld extends TestProgram {

    @Override
    protected String filename() {
        return "brainf";
    }

    @Override
    protected String expected() {
        return "Hello World!\n";
    }

    @Override
    protected RunConfiguration configuration() {
        // brainf copied from: https://esolangs.org/wiki/Brainfuck
        return new RunConfiguration().args("++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.", "debug").bigHeap(true);
    }
}
