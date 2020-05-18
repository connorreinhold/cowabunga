package cyr7.benchmark;

import cyr7.cli.Optimization;

public class BenchmarkReg1 extends Benchmark {
    @Override
    String filename() {
        return "benchmarks/reg/reg_benchmark1";
    }

    @Override
    Optimization[] testedOptimizations() {
        return new Optimization[] { Optimization.REG };
    }
}
