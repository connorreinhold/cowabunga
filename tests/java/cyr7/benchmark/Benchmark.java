package cyr7.benchmark;

import cyr7.Bash;
import cyr7.cli.OptConfig;
import cyr7.cli.Optimization;
import cyr7.util.Sets;
import cyr7.x86.ASMUtil.TilerConf;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("benchmark")
public abstract class Benchmark {

    protected abstract String filename();

    abstract OptConfig preOptimizations();
    abstract OptConfig postOptimizations();

    int repetitions() {
        // override this if you want to change the number of reps
        return 5;
    }

    private String getTestAssemblyFilename() {
        return "tests/resources/" + filename() + ".s";
    }

    private String getTestXiFilename() {
        return "tests/resources/" + filename() + ".xi";
    }

    private long runBenchmark(OptConfig optConfig)
        throws Exception {

        Bash.compileToAssembly(
            getTestXiFilename(),
            TilerConf.COMPLEX,
            optConfig);
        File exe = Bash.linkExecutable(getTestAssemblyFilename());

        int repetitions = repetitions();
        long totalTime = 0;
        for (int i = 0; i < repetitions; i++) {
            Instant start = Instant.now();
            Bash.executeFile(exe, filename(), "", new String[]{});
            Instant end = Instant.now();
            totalTime += ChronoUnit.MILLIS.between(start, end);
        }
        return totalTime / repetitions;
    }

    @EnabledOnOs({OS.LINUX})
    @Test
    void benchmark() throws Exception {
        OptConfig pre = preOptimizations();
        long preMillis = runBenchmark(pre);
        System.out.println("Pre-optimization (" + pre.convertToCLI() + "): " + preMillis + "ms");

        OptConfig post = postOptimizations();
        long postMillis = runBenchmark(post);
        System.out.println("Post-optimization (" + post.convertToCLI() + "):" + postMillis + "ms");

        assertTrue(500 < postMillis && postMillis < 3000,
            "Benchmark test cases must execute between 1s and 3s");
        assertTrue(postMillis < preMillis,
            "Unoptimized version was faster than optimized version.");
        System.out.println(String.format("Percent improvement: %.2f%%", (double) preMillis / (double) postMillis * 100.0));
    }

}
