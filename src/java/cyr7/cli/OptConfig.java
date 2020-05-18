package cyr7.cli;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class OptConfig {

    public static OptConfig none() {
        return new OptConfig(Set.of());
    }

    public static OptConfig allEnabled() {
        return new OptConfig(Set.of(Optimization.values()));
    }

    public static OptConfig of(Optimization... optimizations) {
        return new OptConfig(Set.of(optimizations));
    }

    private final HashSet<Optimization> enabledOptimizations;

    private OptConfig(Set<Optimization> enabledOptimizations) {
        this.enabledOptimizations = new HashSet<>(enabledOptimizations);
    }

    public List<String> convertToCLI() {
        if (enabledOptimizations.isEmpty()) {
            return List.of("-O");
        } else {
            return enabledOptimizations
                .stream()
                .map(opt -> "-O" + opt.name().toLowerCase())
                .collect(Collectors.toList());
        }
    }

    public boolean cf() {
        return enabledOptimizations.contains(Optimization.CF);
    }

    public boolean copy() {
        return enabledOptimizations.contains(Optimization.COPY);
    }

    public boolean reg() {
        return enabledOptimizations.contains(Optimization.REG);
    }

    public boolean dce() {
        return enabledOptimizations.contains(Optimization.DCE);
    }

    public void set(Optimization opt, boolean enabled) {
        if (enabled) {
            enabledOptimizations.add(opt);
        } else {
            enabledOptimizations.remove(opt);
        }
    }

    public void disableAll() {
        enabledOptimizations.clear();
    }

}
