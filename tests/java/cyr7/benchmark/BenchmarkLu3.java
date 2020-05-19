package cyr7.benchmark;

import cyr7.cli.Optimization;

public class BenchmarkLu3 extends Benchmark {
    @Override
    protected String filename() {
        return "benchmarks/lu/lu_benchmark3";
    }

    @Override
    Optimization[] testedOptimizations() {
        return new Optimization[] { Optimization.LU };
    }
}