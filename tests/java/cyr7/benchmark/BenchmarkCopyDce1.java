package cyr7.benchmark;

import cyr7.cli.Optimization;

public class BenchmarkCopyDce1 extends Benchmark {
    @Override
    protected String filename() {
        return "benchmarks/copy_dce/copy_dce_benchmark1";
    }

    @Override
    Optimization[] testedOptimizations() {
        return new Optimization[] { Optimization.COPY, Optimization.DCE };
    }
}
