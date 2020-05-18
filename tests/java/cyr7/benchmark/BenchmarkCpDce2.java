package cyr7.benchmark;

import cyr7.cli.Optimization;

public class BenchmarkCpDce2 extends Benchmark {
    @Override
    String filename() {
        return "benchmarks/cp_dce/cp_dce_benchmark2";
    }

    @Override
    Optimization[] testedOptimizations() {
        return new Optimization[] { Optimization.CP, Optimization.DCE };
    }
}
