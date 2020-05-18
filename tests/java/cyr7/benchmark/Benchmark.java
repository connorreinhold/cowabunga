package cyr7.benchmark;

import cyr7.Bash;
import cyr7.cli.OptConfig;
import cyr7.cli.Optimization;
import cyr7.x86.ASMUtil.TilerConf;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("benchmark")
public abstract class Benchmark {

    protected abstract String filename();

    abstract Optimization[] testedOptimizations();

    private String getTestAssemblyFilename() {
        return "tests/resources/" + filename() + ".s";
    }

    private String getTestXiFilename() {
        return "tests/resources/" + filename() + ".xi";
    }

    private long runBenchmark(OptConfig optConfig, int repetitions) throws Exception {
        Bash.compileToAssembly(getTestXiFilename(), TilerConf.COMPLEX, optConfig);
        File exe = Bash.linkExecutable(getTestAssemblyFilename());

        long totalTime = 0;
        for (int i = 0; i < repetitions; i++) {
            Instant start = Instant.now();
            Bash.executeFile(exe, filename(), "", new String[] {});
            Instant end = Instant.now();
            totalTime += ChronoUnit.MILLIS.between(start, end);
        }
        return totalTime / repetitions;
    }

    @EnabledOnOs({OS.LINUX})
    @Test
    void benchmark() throws Exception {
        OptConfig unoptimizedOptConfig = OptConfig.allEnabled();
        for (Optimization opt : testedOptimizations()) {
            unoptimizedOptConfig.set(opt, false);
        }

        long unoptimizedMillis = runBenchmark(unoptimizedOptConfig, 10);
        long optimizedMillis = runBenchmark(OptConfig.allEnabled(), 10);
        System.out.println("Unoptimized: " + unoptimizedMillis);
        System.out.println("Optimized: " + optimizedMillis);
        assertTrue(100 < optimizedMillis && optimizedMillis < 10000, "Benchmark test cases must execute between 1s and 3s");
        assertTrue(optimizedMillis < unoptimizedMillis,
            "Unoptimized version was faster than optimized version.");
    }

}
