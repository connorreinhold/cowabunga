package cyr7.benchmark;

import cyr7.cli.Optimization;

public class BenchmarkCpDce1 extends Benchmark {
    @Override
    protected String filename() {
        return "benchmarks/cp_dce/cp_dce_benchmark1";
    }

    @Override
    Optimization[] testedOptimizations() {
        return new Optimization[] { Optimization.CP, Optimization.DCE };
    }
}
