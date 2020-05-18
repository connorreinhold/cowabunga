package cyr7.benchmark;

import cyr7.cli.Optimization;

public class BenchmarkReg3 extends Benchmark {
    @Override
    protected String filename() {
        return "benchmarks/reg/reg_benchmark3";
    }

    @Override
    Optimization[] testedOptimizations() {
        return new Optimization[] { Optimization.REG };
    }
}
