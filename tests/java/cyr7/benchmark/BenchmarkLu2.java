package cyr7.benchmark;

import java.util.Collection;
import java.util.List;

import cyr7.cli.Optimization;

public class BenchmarkLu2 extends IRBenchmark {
    @Override
    protected String filename() {
        return "benchmarks/lu/lu_benchmark2";
    }

    @Override
    Collection<? extends Optimization> testedOptimizations() {
        return List.of(Optimization.LU);
    }
}