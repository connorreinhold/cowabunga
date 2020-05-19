package cyr7.benchmark;

import cyr7.cli.OptConfig;
import cyr7.cli.Optimization;

public class BenchmarkReg2 extends Benchmark {

    @Override
    protected String filename() {
        return "benchmarks/reg/reg_benchmark2";
    }

    @Override
    OptConfig preOptimizations() {
        return OptConfig.none();
    }

    @Override
    OptConfig postOptimizations() {
        return OptConfig.of(Optimization.REG);
    }

}
