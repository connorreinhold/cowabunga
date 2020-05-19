package cyr7.benchmark;

import cyr7.cli.OptConfig;
import cyr7.cli.Optimization;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class IRBenchmark extends Benchmark {

    abstract Collection<? extends Optimization> testedOptimizations();

    @Override
    OptConfig preOptimizations() {
        return OptConfig.of(Optimization.CF, Optimization.REG);
    }

    @Override
    OptConfig postOptimizations() {
        Set<Optimization> optimizations = new HashSet<>(testedOptimizations());
        optimizations.add(Optimization.CF);
        optimizations.add(Optimization.REG);
        return OptConfig.of(optimizations);
    }
}
