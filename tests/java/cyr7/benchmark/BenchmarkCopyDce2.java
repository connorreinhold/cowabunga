package cyr7.benchmark;

import cyr7.cli.Optimization;

public class BenchmarkCopyDce2 extends Benchmark {
    @Override
    protected String filename() {
        return "benchmarks/copy_dce/copy_dce_benchmark2";
    }

    @Override
    Optimization[] testedOptimizations() {
        return new Optimization[] { Optimization.COPY, Optimization.DCE };
    }
}
