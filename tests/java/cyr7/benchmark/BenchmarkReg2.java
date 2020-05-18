package cyr7.benchmark;

import cyr7.cli.Optimization;

public class BenchmarkReg2 extends Benchmark {
    @Override
    protected String filename() {
        return "benchmarks/reg/reg_benchmark2";
    }

    @Override
    Optimization[] testedOptimizations() {
        return new Optimization[] { Optimization.REG };
    }
}
